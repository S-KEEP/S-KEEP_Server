package Skeep.backend.naverOcr.service;

import Skeep.backend.global.exception.BaseException;
import Skeep.backend.naverOcr.constant.NaverOcrConstants;
import Skeep.backend.naverOcr.exception.NaverOcrErrorCode;
import Skeep.backend.naverOcr.util.MultiFileUtil;
import Skeep.backend.naverOcr.util.MultipartFileResource;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NaverOcrService {

    @Value("${ocr.url}")
    private String naverOcrBaseUrl;

    @Value("${ocr.token}")
    private String naverOcrSecretKey;
    
    private final WebClient webClient;


    public List<String> getImageTextListWithFile(List<MultipartFile> imageList) {

        Flux<String> imageTextList = Flux.fromIterable(imageList)
                                         .flatMapSequential(this::requestOcrWithFile)
                                         .map(optionalText -> optionalText.orElse(""))
                                         .flatMap(this::parseResponse);

        return imageTextList.collectList().block();
    }

    public List<String> getImageTextListWithUrl(List<String> imageList) {

        Flux<String> imageTextList = Flux.fromIterable(imageList)
                                         .flatMapSequential(this::requestOcrWithUrl)
                                         .map(optionalText -> optionalText.orElse(""))
                                         .flatMap(this::parseResponse);

        return imageTextList.collectList().block();
    }

    private Mono<Optional<String>> requestOcrWithFile(MultipartFile file) {

        MultipartBodyBuilder builder = makeRequestBodyWithFile(file);
        log.info("Request body builder 생성 완료: {}", builder.build());

        return webClient.post()
                        .uri(naverOcrBaseUrl)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header("X-OCR-SECRET", naverOcrSecretKey)
                        .bodyValue(builder.build())
                        .retrieve()
                        .bodyToMono(String.class)
                        .doOnSuccess(response -> log.info("NAVER OCR response: {}", response))
                        .doOnError(throwable ->
                                log.info("NAVER OCR API 요청 실패: {}", throwable.getMessage())
                        )
                        .map(Optional::ofNullable)
                        .onErrorResume(throwable -> Mono.just(Optional.empty()));
    }

    private Mono<Optional<String>> requestOcrWithUrl(String imageUrl) {

        Map<String, Object> requestBody = makeRequestBodyWithUrl(imageUrl);
        log.info("Request body 생성 완료: {}", requestBody);

        return webClient.post()
                .uri(naverOcrBaseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-OCR-SECRET", naverOcrSecretKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> log.info("NAVER OCR response: {}", response))
                .doOnError(throwable ->
                        log.info("NAVER OCR API 요청 실패: {}", throwable.getMessage())
                )
                .map(Optional::ofNullable)
                .onErrorResume(throwable -> Mono.just(Optional.empty()));
    }

    private MultipartBodyBuilder makeRequestBodyWithFile(MultipartFile file) {
        
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("version", NaverOcrConstants.VERSION);
        messageMap.put("requestId", UUID.randomUUID().toString());
        messageMap.put("timestamp", System.currentTimeMillis());
        
        Map<String, String> imageMap = new HashMap<>();
        String format = MultiFileUtil.determineImageFormat(file);
        String fileName = file.getOriginalFilename();
        imageMap.put("format", format);
        imageMap.put("name", fileName);
        messageMap.put("images", List.of(imageMap));

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        try {
            String messageJson = new ObjectMapper().writeValueAsString(messageMap);
            builder.part("message", messageJson, MediaType.APPLICATION_JSON);
            builder.part("file", new InputStreamResource(MultipartFileResource.getInputStream(file)))
                   .filename(Objects.requireNonNull(file.getOriginalFilename()))
                   .contentType(MediaType.APPLICATION_OCTET_STREAM);
        } catch (IOException e) {
            throw BaseException.type(NaverOcrErrorCode.FAILED_OCR_API_REQUEST);
        }

        return builder;
    }

    private Map<String, Object> makeRequestBodyWithUrl(String imageUrl) {

        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("version", NaverOcrConstants.VERSION);
        messageMap.put("requestId", UUID.randomUUID().toString());
        messageMap.put("resultType", "string");
        messageMap.put("lang", "ko");
        messageMap.put("timestamp", System.currentTimeMillis());

        Map<String, Object> imageMap = new HashMap<>();
        imageMap.put("format", "jpg");
        imageMap.put("name", "skeep_image");
        imageMap.put("data", null);
        imageMap.put("url", imageUrl);

        messageMap.put("images", List.of(imageMap));
        return messageMap;
    }

    private Mono<String> parseResponse(String response) {
        if (response.isEmpty())
            return Mono.just("");
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(response, new TypeReference<>(){});

            List<Map<String, Object>> images = (List<Map<String, Object>>) map.get("images");
            Map<String, Object> image = images.get(0);

            String inferResult = (String) image.get("inferResult");
            if (!inferResult.equals("SUCCESS"))
                return Mono.just("");

            List<Map<String, Object>> fields = (List<Map<String, Object>>) image.get("fields");
            String extractedText = fields.stream()
                    .map(field -> (String) field.get("inferText"))
                    .collect(Collectors.joining(" "));
            return Mono.justOrEmpty(extractedText);
        } catch (IOException e) {
            return Mono.just("");
        }
    }

    public NaverOcrService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(this.naverOcrBaseUrl)
                                         .build();
    }
}
