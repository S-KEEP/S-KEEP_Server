package Skeep.backend.gpt.service;

import Skeep.backend.global.exception.BaseException;
import Skeep.backend.gpt.constant.GptConstants;
import Skeep.backend.gpt.dto.request.GptRequest;
import Skeep.backend.gpt.dto.request.Message;
import Skeep.backend.gpt.dto.response.GptResponse;
import Skeep.backend.gpt.exception.GptErrorCode;
import Skeep.backend.screenshot.util.LocationAndCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class GptService {

    @Value("${gpt.token}")
    private String gptSecretKey;

    private final WebClient webClient;

    public List<LocationAndCategory> getGptAnalyze(List<String> targetTextList) {

        Flux<LocationAndCategory> imageTextList = Flux.fromIterable(targetTextList)
                .flatMapSequential(this::requestGpt)
                .flatMap(this::parseResponse);

        return imageTextList.collectList().block();
    }

    public Mono<GptResponse> requestGpt(String ocrText) {

        GptRequest gptRequest = makeBodyRequest(ocrText);
        log.info("gpt request: {}", gptRequest.toString());

        return webClient.post()
                        .uri(GptConstants.API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + gptSecretKey)
                        .bodyValue(gptRequest)
                        .retrieve()
                        .bodyToMono(GptResponse.class)
                        .doOnSuccess(response -> log.info("GPT API response: {}", response))
                        .doOnError(throwable -> {
                                    log.info("GPT API 요청 실패: {}", throwable.getMessage());
                                    throw BaseException.type(
                                            GptErrorCode.FAILED_GPT_API_REQUEST
                                    );
                                }
                        );
    }

    private GptRequest makeBodyRequest(String ocrText) {
        List<Message> messages = new ArrayList<>();

        messages.add(Message.makeContent("system", GptConstants.ANALYZE_COMMAND));
        messages.add(Message.makeContent("user", ocrText));
        return GptRequest.builder()
                         .messages(messages)
                         .temperature(0.5)
                         .build();
    }

    private Mono<LocationAndCategory> parseResponse(GptResponse response) {

        String content = response.getChoices().get(0).getMessage().getContent();
        String[] splited = content.split("\\|");

        LocationAndCategory locationAndCategory;
        if (splited.length == 2)
            locationAndCategory = LocationAndCategory.of(splited[0], splited[1]);
        else
            throw BaseException.type(GptErrorCode.FAILED_ANALYZE_CATEGORY);

        return Mono.justOrEmpty(locationAndCategory);
    }

    public GptService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(GptConstants.API_URL)
                                         .build();
    }
}
