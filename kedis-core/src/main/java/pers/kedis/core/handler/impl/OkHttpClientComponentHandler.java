package pers.kedis.core.handler.impl;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pers.kedis.core.common.JsonUtils;
import pers.kedis.core.dto.RequestHandlerDTO;
import pers.kedis.core.dto.ResourceDTO;
import pers.kedis.core.exception.ApiGateWayException;
import pers.kedis.core.exception.ExceptionEnum;
import pers.kedis.core.handler.BaseComponentHandler;
import pers.kedis.core.protocol.okhttp.OkHttpClientService;

import java.util.Objects;

/**
 * @author kwsc98
 */
@Slf4j
public class OkHttpClientComponentHandler extends BaseComponentHandler<RequestHandlerDTO<Object>, ResourceDTO> {


    private final OkHttpClientService okHttpClientService;

    public OkHttpClientComponentHandler() {
        this.okHttpClientService = new OkHttpClientService();
    }


    @Override
    public Object handle(RequestHandlerDTO<Object> requestHandlerDTO, ResourceDTO resourceDTO) {
        log.debug("OkHttpClientComponentHandler Start Handler");
        Response response = null;
        try {
            String addres = resourceDTO.getLoadBalancer().next();
            String resourceUrl = resourceDTO.getRouteResourceUrl(requestHandlerDTO.getResourceUrl());
            Object content = requestHandlerDTO.getContent();
            if (Objects.isNull(content) || !(content instanceof String)) {
                content = JsonUtils.writeValueAsString(content);
            }
            RequestBody body = RequestBody.create(content.toString(), MediaType.get("application/json; charset=utf-8"));
            String url = addres + resourceUrl;
            Request request = new Request.Builder().url(url).post(body).build();
            log.debug("OkHttpClient Request : {}", content);
            response = okHttpClientService.execute(request);
            if (!response.isSuccessful()) {
                log.info("Request Not Successful Response Status : {}  : {}", response.code(), response);
                throw new ApiGateWayException(ExceptionEnum.ERROR);
            }
            String responseStr = null;
            if (Objects.nonNull(response.body())) {
                responseStr = response.body().string();
            }
            log.debug("OkHttpClient Response : {}", responseStr);
            log.debug("OkHttpClientComponentHandler Start Done");
            return responseStr;
        } catch (Exception e) {
            log.error("OkHttpClientComponentHandler Error : {}", e.toString(), e);
            if (e instanceof ApiGateWayException) {
                throw (ApiGateWayException) e;
            }
            throw new ApiGateWayException();
        } finally {
            if (Objects.nonNull(response)) {
                response.close();
            }
        }
    }
}
