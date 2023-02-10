package pers.kedis.core.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import pers.kedis.core.common.LoadBalancer;
import pers.kedis.core.common.RandomLoadBalancer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author kwsc98
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class GroupDTO extends BaseDTO implements Serializable {


    private List<RouteNodeDTO> routeList = new ArrayList<>();

    private List<InterfaceDTO> interfaceDTOList = new ArrayList<>();

    public LoadBalancer getLoadBalancer() {
        if (Objects.isNull(routeList) || routeList.isEmpty()) {
            log.error("GroupDTO : {} Init Error : RouteList Is Empty", getResourceName());
        }
        return new RandomLoadBalancer().init(routeList);
    }


}
