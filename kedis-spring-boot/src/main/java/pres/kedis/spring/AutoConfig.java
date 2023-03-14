package pres.kedis.spring;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import pers.kedis.core.KedisApplicationContext;
import pers.kedis.core.common.utils.FileUtil;

import java.io.FileNotFoundException;


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
@ConditionalOnProperty(name = "kedis.port", matchIfMissing = false)
public class AutoConfig {

    @Bean(name = "kedisApplicationContext")
    @ConditionalOnMissingBean
    public KedisApplicationContext init(KedisProperties kedisProperties) throws FileNotFoundException {
        kedisProperties.setDataResourcesPath(ResourceUtils.getFile("classpath:" + kedisProperties.getDataResourcesPath()).getPath());
        log.info("Kedis Start Init");
        return KedisApplicationContext.build().init(kedisProperties);
    }

}
