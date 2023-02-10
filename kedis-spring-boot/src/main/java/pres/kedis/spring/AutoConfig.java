package pres.kedis.spring;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import pers.kedis.core.KedisApplicationContext;
import pers.kedis.core.KedisBuilderFactory;
import pers.kedis.core.registry.RegistryBuilderFactory;
import pers.kedis.core.registry.RegistryClientInfo;


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
        return KedisBuilderFactory.builder()
                .setRegistryBuilderFactory(
                        RegistryBuilderFactory.builder()
                                .setRegistryClientInfo(
                                        RegistryClientInfo.build(kedisProperties.getRegisteredPath())
                                )
                )
                .setPort(kedisProperties.getPort()).build().init();
    }

    @Bean
    @DependsOn({"kedisApplicationContext"})
    @ConditionalOnMissingBean
    public KedisPostProcessor dtpPostProcessor(@Qualifier("kedisApplicationContext") KedisApplicationContext kedisApplicationContext) {
        return new KedisPostProcessor(kedisApplicationContext);
    }


}
