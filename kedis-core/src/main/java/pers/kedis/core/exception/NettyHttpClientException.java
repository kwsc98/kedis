package pers.kedis.core.exception;

import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * @author kwsc98
 */
public class NettyHttpClientException extends ApiGateWayException {

    HttpResponseStatus httpResponseStatus;

    public NettyHttpClientException() {
    }

    public NettyHttpClientException(String message) {
        super(message);
    }

    public NettyHttpClientException(HttpResponseStatus httpResponseStatus) {
        this.httpResponseStatus = httpResponseStatus;
    }


}
