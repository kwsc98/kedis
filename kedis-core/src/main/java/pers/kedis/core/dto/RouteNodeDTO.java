package pers.kedis.core.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * @author kwsc98
 */
@Data
public class RouteNodeDTO implements Serializable {

    private String addres;

    private int weight = 1;

    public RouteNodeDTO setAddres(String addres) {
        this.addres = addres;
        return this;
    }

    public RouteNodeDTO setWeight(int weight) {
        this.weight = weight;
        return this;
    }
}
