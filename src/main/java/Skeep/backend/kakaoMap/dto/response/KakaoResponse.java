package Skeep.backend.kakaoMap.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class KakaoResponse {

    @JsonProperty("meta")
    private Meta meta;

    @JsonProperty("documents")
    private List<Document> documentList;
}
