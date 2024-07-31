package Skeep.backend.kakaoMap.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Document {

    @JsonProperty("id")
    private String id;

    @JsonProperty("place_name")
    private String placeName;

    @JsonProperty("category_group_code")
    private String categoryGroupCode;

    @JsonProperty("category_group_name")
    private String categoryGroupName;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("address_name")
    private String addressName;

    @JsonProperty("road_address_name")
    private String roadAddressName;

    @JsonProperty("x")
    private String x;

    @JsonProperty("y")
    private String y;

    @JsonProperty("place_url")
    private String placeUrl;

    @JsonProperty("distance")
    private String distance;
}
