package Skeep.backend.kakaoMap.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Meta {

    @JsonProperty("total_count")
    private Integer totalCount;

    @JsonProperty("pageable_count")
    private Integer pageableCount;

    @JsonProperty("is_end")
    private Boolean isEnd;

    @JsonProperty("same_name")
    private SameName sameName;
}
