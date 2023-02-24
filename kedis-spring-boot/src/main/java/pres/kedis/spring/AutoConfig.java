package pres.kedis.spring;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.kedis.core.KedisApplicationContext;


/**
 * krpc
 * 2022/8/18 17:54
 *
 * @author wangsicheng
 * @since
 **/
@Configuration
@ConditionalOnClass(KedisProperties.class)
@EnableConfigurationProperties(KedisProperties.class)
@Slf4j
@ConditionalOnProperty(name = "kedis.registeredPath", matchIfMissing = false)
public class AutoConfig {

    @Bean(name = "kedisApplicationContext")
    @ConditionalOnMissingBean
    public KedisApplicationContext init(KedisProperties kedisProperties) {
        log.info("Kedis Start Init");
        return KedisApplicationContext.build().init(kedisProperties);
    }

}
