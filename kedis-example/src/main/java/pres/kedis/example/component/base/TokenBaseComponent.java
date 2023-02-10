package pres.kedis.example.component.base;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import pers.kedis.core.dto.RequestHandlerDTO;
import pers.kedis.core.exception.ApiGateWayException;
import pers.kedis.core.handler.BaseComponentHandler;
import pres.kedis.example.common.dto.CommonRequest;
import pres.kedis.example.common.enums.ErrorEnum;
import pres.kedis.example.service.TokenService;

import javax.annotation.Resource;

/**
 * @author kwsc98
 */
@Component("TokenBaseComponent")
@Slf4j
public class TokenBaseComponent extends BaseComponentHandler<RequestHandlerDTO<CommonRequest>, Long> {

    @Resource
    private TokenService tokenService;

    @Override
    public Object handle(RequestHandlerDTO<CommonRequest> requestHandlerDTO, Long timeOut) {
        log.debug("TokenBaseComponent Handler Start");
        try {
            CommonRequest commonRequest = requestHandlerDTO.getContent();
            String key = commonRequest.getUserId() + ":" + commonRequest.getMerchantNo();
            String token = tokenService.setToken(key, timeOut);
            log.debug("TokenBaseComponent Handler Start");
            return token;
        } catch (Exception e) {
            log.error("Get Token Error : {}]", e.toString());
            throw new ApiGateWayException(ErrorEnum.TOKEN_ERROR);
        }
    }

    public Long init(String configStr) {
        if (StringUtils.isNotEmpty(configStr)) {
            return Long.valueOf(configStr);
        }
        return 60L;
    }

}
