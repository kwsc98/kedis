package pers.kedis.core.common;

import pers.kedis.core.dto.RouteNodeDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author kwsc98
 */
public class RandomLoadBalancer implements LoadBalancer {

    private Random random;

    private List<String> addresRandomList;

    @Override
    public LoadBalancer init(List<RouteNodeDTO> list) {
        this.random = new Random();
        this.addresRandomList = new ArrayList<>();
        for (RouteNodeDTO routeNodeDTO : list) {
            for (int i = 0; i < routeNodeDTO.getWeight(); i++) {
                this.addresRandomList.add(routeNodeDTO.getAddres());
            }
        }
        return this;
    }

    @Override
    public String next() {
        return this.addresRandomList.get(random.nextInt(addresRandomList.size()));
    }
}
