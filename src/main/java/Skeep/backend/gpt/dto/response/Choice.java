package Skeep.backend.gpt.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Choice {
    private Long index;
    private Message message;
    @JsonIgnore
    private String logProbs;
    @JsonProperty(value = "finish_reason")
    private String finishReason;
}
