package pres.kedis.spring;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * kedis
 * 2022/8/18 17:51
 *
 * @author wangsicheng
 * @since
 **/
@ConfigurationProperties(prefix = "kedis")
@Data
public class KedisProperties {

    private String registeredPath;

    private int port = 8080;

}
