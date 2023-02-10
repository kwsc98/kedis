package pers.kedis.core.common;

import pers.kedis.core.dto.RouteNodeDTO;

import java.util.List;

/**
 * @author kwsc98
 */
public class WrrSmoothImpl implements LoadBalancer {

    static class Wrr {
        String addres;
        int weight;
        int current = 0;

        Wrr(RouteNodeDTO routeNodeDTO) {
            this.addres = routeNodeDTO.getAddres();
            this.weight = routeNodeDTO.getWeight();
        }
    }

    private Wrr[] cachedWeights;

    @Override
    public LoadBalancer init(List<RouteNodeDTO> list) {
        this.cachedWeights = list.stream().map(Wrr::new).toArray(Wrr[]::new);
        return this;
    }

    @Override
    public String next() {
        int total = 0;
        Wrr shed = cachedWeights[0];
        for (Wrr item : cachedWeights) {
            int weight = item.weight;
            total += weight;
            item.current += weight;
            if (item.current > shed.current) {
                shed = item;
            }
        }
        shed.current -= total;
        return shed.addres;
    }

}
