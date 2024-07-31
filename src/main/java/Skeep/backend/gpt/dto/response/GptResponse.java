package Skeep.backend.gpt.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.util.List;

@Getter
public class GptResponse {
    private String id;
    private String object;
    private Long created;
    private String model;
    private List<Choice> choices;
    @JsonIgnore
    private Usage usage;
    @JsonIgnore
    private String fingerprint;
}
