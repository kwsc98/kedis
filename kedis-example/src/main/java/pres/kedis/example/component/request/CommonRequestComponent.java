package pres.kedis.example.component.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pers.kedis.core.common.JsonUtils;
import pers.kedis.core.dto.RequestHandlerDTO;
import pers.kedis.core.exception.ApiGateWayException;
import pers.kedis.core.handler.RequestComponentHandler;
import pres.kedis.example.common.dto.CommonRequest;
import pres.kedis.example.common.enums.ErrorEnum;

/**
 * @author kwsc98
 */
@Component("CommonRequestComponent")
@Slf4j
public class CommonRequestComponent extends RequestComponentHandler<RequestHandlerDTO<Object>, Object> {
    @Override
    public Object handle(RequestHandlerDTO<Object> object, Object config) {
        try {
            log.debug("CommonRequestComponent Handler Start");
            Object bodContent = object.getContent();
            if (bodContent instanceof String) {
                object.setContent(JsonUtils.readValue((String) bodContent, CommonRequest.class));
            }
            log.debug("CommonRequestComponent Handler Done");
            return object;
        } catch (Exception e) {
            log.error("CommonRequestComponent Error");
            throw new ApiGateWayException(ErrorEnum.REQUEST_ERROR);
        }
    }
}
