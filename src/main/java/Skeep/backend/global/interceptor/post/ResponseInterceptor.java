package Skeep.backend.global.interceptor.post;

import Skeep.backend.global.base.BaseResponse;
import Skeep.backend.global.exception.GlobalErrorCode;
import lombok.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(
        basePackages = "Skeep.backend"
)
public class ResponseInterceptor implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, @NonNull Class converterType) {
        return !(returnType.getParameterType() == BaseResponse.class)
                && MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body instanceof GlobalErrorCode)
            return BaseResponse.fail((GlobalErrorCode)body);
        return BaseResponse.success(body);
    }
}