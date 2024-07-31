package Skeep.backend.gpt.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Message {

    private String role;
    private String content;

    @Builder
    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public static Message makeContent(String role, String content){
        return Message.builder()
                .role(role)
                .content(content)
                .build();
    }
}
