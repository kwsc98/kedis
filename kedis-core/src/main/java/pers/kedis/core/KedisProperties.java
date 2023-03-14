package pers.kedis.core;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * kedis
 * 2022/8/18 17:51
 *
 * @author wangsicheng
 * @since
 **/
@Data
public class KedisProperties {

    private String dataResourcesPath;

    private int dbCount = 16;

    private int port = 6379;

}
