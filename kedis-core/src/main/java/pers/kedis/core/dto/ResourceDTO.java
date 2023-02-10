package pers.kedis.core.dto;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import pers.kedis.core.channel.Channel;
import pers.kedis.core.common.LoadBalancer;

import java.util.List;

/**
 * @author kwsc98
 */
@Data
public class ResourceDTO {
    private Channel channel;

    private BaseDTO baseDTO;

    private String routeResourceUrl;

    private LoadBalancer loadBalancer;

    private List<String> groupHandlerList;

    private List<String> handlerList;

    public String getRouteResourceUrl(String resourceUrl) {
        String res = resourceUrl;
        String baseResourceUrl = baseDTO.getResourceUrl();
        if (StringUtils.isNotEmpty(routeResourceUrl) &&
                resourceUrl.indexOf(baseResourceUrl) == 0) {
            res = resourceUrl.replaceFirst(baseResourceUrl, routeResourceUrl);
        }
        return res;
    }


    public static ResourceDTO build() {
        return new ResourceDTO();
    }

    public ResourceDTO setChannel(Channel channel) {
        this.channel = channel;
        return this;
    }

    public ResourceDTO setBaseDTO(BaseDTO baseDTO) {
        this.baseDTO = baseDTO;
        return this;
    }

    public ResourceDTO setLoadBalancer(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
        return this;
    }

    public ResourceDTO setRouteResourceUrl(String routeResourceUrl) {
        this.routeResourceUrl = routeResourceUrl;
        return this;
    }

    public ResourceDTO setGroupHandlerList(List<String> groupHandlerList) {
        this.groupHandlerList = groupHandlerList;
        return this;
    }

    public ResourceDTO setHandlerList(List<String> handlerList) {
        this.handlerList = handlerList;
        return this;
    }
}
