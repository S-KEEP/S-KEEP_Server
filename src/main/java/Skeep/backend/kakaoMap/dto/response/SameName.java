package Skeep.backend.kakaoMap.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class SameName {

    @JsonProperty("region")
    private List<String> region;

    @JsonProperty("keyword")
    private String keyword;

    @JsonProperty("selected_region")
    private String selectedRegion;
}
