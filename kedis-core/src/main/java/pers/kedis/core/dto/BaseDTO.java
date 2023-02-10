package pers.kedis.core.dto;

import lombok.Data;

import java.util.List;

/**
 * @author kwsc98
 */
@Data
public class BaseDTO {

    private String resourceName;

    private String resourceUrl;

    private String routeResourceUrl;

    private long timeOut = -1;

    private List<String> handlerList;

}
