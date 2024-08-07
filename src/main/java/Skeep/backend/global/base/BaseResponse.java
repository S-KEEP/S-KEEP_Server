package Skeep.backend.global.base;

import Skeep.backend.global.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static Skeep.backend.global.exception.GlobalErrorCode.SUCCESS;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"errorCode", "message", "result"})
public class BaseResponse<T> {
    private final String errorCode;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public static <T> BaseResponse<T> success(final T data) {
        return new BaseResponse<>(null, "SUCCESS", data);
    }

    public static <T> BaseResponse<T> fail(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode.getErrorCode(), errorCode.getMessage(), null);
    }


    public BaseResponse(T result) {
        this.errorCode = SUCCESS.getErrorCode();
        this.message = SUCCESS.getMessage();
        this.result = result;
    }
}