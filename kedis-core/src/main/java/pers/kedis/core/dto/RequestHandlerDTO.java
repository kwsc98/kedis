package pers.kedis.core.dto;

import io.netty.handler.codec.http.HttpMethod;
import lombok.Getter;

/**
 * @author kwsc98
 */
@Getter
public class RequestHandlerDTO<T> {

    private String resourceUrl;

    private T content;

    private HttpMethod httpMethod;

    public static <T> RequestHandlerDTO<T> build() {
        return new RequestHandlerDTO<T>();
    }

    public RequestHandlerDTO<T> setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
        return this;
    }

    public RequestHandlerDTO<T> setContent(T content) {
        this.content = content;
        return this;
    }

    public RequestHandlerDTO<T> setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }
}
