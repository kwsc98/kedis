package pers.kedis.core.dto;

import com.alibaba.nacos.common.utils.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author kwsc98
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceDTO extends BaseDTO implements Serializable {

    private boolean groupExtends = true;


    public String getResourcesRouteResourceUrl() {
        return StringUtils.isNotEmpty(super.getRouteResourceUrl()) ? super.getRouteResourceUrl() : super.getResourceUrl();
    }

}
