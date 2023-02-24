package pers.kedis.core.registry.impl;


import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;
import pers.kedis.core.KedisService;
import pers.kedis.core.dto.KedisConfig;
import pers.kedis.core.exception.KedisException;
import pers.kedis.core.registry.RegistryClient;
import pers.kedis.core.registry.RegistryClientInfo;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * kagw
 * 2022/8/17 14:12
 *
 * @author wangsicheng
 **/
@Slf4j
public class NacosClient implements RegistryClient, Listener {

    private final KedisService kedisService;

    public NacosClient(KedisService kedisService) {
        this.kedisService = kedisService;
    }

    @Override
    public void init(RegistryClientInfo registryClientInfo) {
        log.info("NacosClient Init Start");
        try {
            Properties properties = new Properties();
            properties.put("serverAddr", registryClientInfo.getServerAddr());
            ConfigService configService = NacosFactory.createConfigService(properties);
            String yamlStr = configService.getConfigAndSignListener("kagw", "kagw_group", 5000, this);
            KedisConfig kedisConfig = getKedisConfig(yamlStr);
            doRefresh(kedisConfig);
            log.info("NacosClient Init Done");
        } catch (Exception e) {
            log.error("NacosClient Init Error : {}", e.toString(), e);
        }
    }

    private KedisConfig getKedisConfig(String yamlStr) {
        try {
            return null;
        } catch (Exception e) {
            log.error("registrationGroupList Error : {}", e.toString(), e);
            throw new RuntimeException();
        }
    }

    @Override
    public void doRefresh(KedisConfig kedisConfig) {

    }

    @Override
    public Executor getExecutor() {
        log.error("NacosClient Error");
        return null;
    }

    @Override
    public void receiveConfigInfo(String configInfo) {
        log.info("NacosClient Monitor Change Start");
        KedisConfig kedisConfig = getKedisConfig(configInfo);
        doRefresh(kedisConfig);
        log.info("NacosClient Monitor Change Done");
    }

}
