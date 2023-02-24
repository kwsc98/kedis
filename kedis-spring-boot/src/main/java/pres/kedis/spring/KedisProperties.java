package pres.kedis.spring;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * kedis
 * 2022/8/18 17:51
 *
 * @author wangsicheng
 **/
@ConfigurationProperties(prefix = "kedis")
public class KedisProperties extends pers.kedis.core.KedisProperties {

}
