package pers.kedis.core;

import lombok.extern.slf4j.Slf4j;
import pers.kedis.core.channel.Channel;
import pers.kedis.core.channel.ComponentNode;
import pers.kedis.core.channel.NodeIterator;
import pers.kedis.core.dto.RequestHandlerDTO;
import pers.kedis.core.dto.ResourceDTO;
import pers.kedis.core.exception.ApiGateWayException;
import pers.kedis.core.exception.ExceptionEnum;
import pers.kedis.core.handler.ChannelService;

import java.util.Objects;

/**
 * @author kwsc98
 */
@Slf4j
public class DisposeService {

    private final KedisApplicationContext kedisApplicationContext;


    public DisposeService(KedisApplicationContext kedisApplicationContext) {
        this.kedisApplicationContext = kedisApplicationContext;
    }

    public Object dealWith(RequestHandlerDTO<?> requestHandlerDTO) {
        ChannelService channelService = kedisApplicationContext.getChannelService();
        String url = requestHandlerDTO.getResourceUrl();
        ResourceDTO resourceDTO = channelService.getResource(url);
        if (Objects.isNull(resourceDTO)) {
            log.info("ResourceDTO Not Found");
            throw new ApiGateWayException(ExceptionEnum.RESOURCES_NOT_EXIST);
        }
        if (Objects.isNull(resourceDTO.getChannel())) {
            Channel channel = kedisApplicationContext
                    .getChannelService()
                    .getComponentChannel(resourceDTO);
            resourceDTO.setChannel(channel);
        }
        Channel componentChannel = resourceDTO.getChannel();
        NodeIterator nodeIterator = componentChannel.pipeline().getIterator();
        return handle(requestHandlerDTO, nodeIterator);
    }

    private Object handle(Object object, NodeIterator nodeIterator) {
        if (nodeIterator.hasNext()) {
            ComponentNode componentNode = nodeIterator.next();
            object = componentNode.getHandler().handle(object, componentNode.getConfigObject());
            object = handle(object, nodeIterator);
        }
        return object;
    }


}
