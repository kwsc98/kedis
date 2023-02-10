package pers.kedis.core.common;

import pers.kedis.core.dto.RouteNodeDTO;

import java.util.List;

public interface LoadBalancer {

    LoadBalancer init(List<RouteNodeDTO> list);

    String next();

}
