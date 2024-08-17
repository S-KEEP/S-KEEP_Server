package Skeep.backend.kakaoMap.service;

import Skeep.backend.kakaoMap.dto.response.Document;
import Skeep.backend.kakaoMap.dto.response.KakaoResponse;
import Skeep.backend.kakaoMap.dto.response.KakaoResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class KakaoMapService {

    @Value("${kakao.token}")
    private String kakaoApiSecret;

    @Value("${kakao.url}")
    private String kakaoApiUrl;

    private final WebClient webClient;


    public List<KakaoResponseResult> getKakaoLocationIdList(List<String> locationNameList) {

        Flux<KakaoResponseResult> imageTextList = Flux.fromIterable(locationNameList)
                                         .flatMapSequential(this::requestKakaoMap)
                                         .flatMap(this::parseResponse);

        return imageTextList.collectList().block();
    }

    private Mono<Optional<KakaoResponse>> requestKakaoMap(String target) {
        return webClient.get()
                .uri(kakaoApiUrl + "?query=" + target)
                .header("Authorization", "KakaoAK " + kakaoApiSecret)
                .retrieve()
                .bodyToMono(KakaoResponse.class)
                .doOnSuccess(response -> log.info("Kakao API response: {}", response))
                .doOnError(throwable ->
                            log.info("KaKao API 요청 실패: {}", throwable.getMessage())
                )
                .map(Optional::ofNullable)
                .onErrorResume(throwable -> Mono.just(Optional.empty()));
    }

    private Mono<KakaoResponseResult> parseResponse(Optional<KakaoResponse> response) {
        if (response.isEmpty())
            return Mono.just(KakaoResponseResult.of("", "", "", ""));

        KakaoResponse kakaoResponse = response.get();
        List<Document> documentList = kakaoResponse.getDocumentList();
        if (documentList.isEmpty())
            return Mono.just(KakaoResponseResult.of("", "", "", ""));

        Document document = documentList.get(0);
        return Mono.just(
                KakaoResponseResult.of(
                        document.getId(),
                        document.getRoadAddressName(),
                        document.getX(),
                        document.getY()
                )
        );
    }

    public KakaoMapService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(this.kakaoApiUrl)
                                         .build();
    }
}
