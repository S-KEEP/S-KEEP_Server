package Skeep.backend.global.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static Skeep.backend.global.exception.GlobalErrorCode.SUCCESS;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"status", "errorCode", "message", "result"})
public class BaseResponse<T> {
    private final HttpStatus status;
    private final String errorCode;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public BaseResponse(T result) {
        this.status = SUCCESS.getStatus();
        this.errorCode = SUCCESS.getErrorCode();
        this.message = SUCCESS.getMessage();
        this.result = result;
    }
}