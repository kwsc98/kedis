package pers.kedis.core.registry;


import pers.kedis.core.KedisService;
import pers.kedis.core.registry.impl.ConfigurationClient;
import pers.kedis.core.registry.impl.NacosClient;

/**
 * kagw注册中心构建工厂
 * 2022/7/25 16:03
 *
 * @author lanhaifeng
 **/
public class RegistryBuilderFactory {

    private RegistryClientInfo registryClientInfo;

    public static RegistryBuilderFactory build() {
        return new RegistryBuilderFactory();
    }


    public RegistryService init(KedisService kedisService) {
        RegistryService registryService = null;
        switch (registryClientInfo.getClient()) {
            case Nacos:
                registryService = RegistryService.build(new NacosClient(kedisService));
                break;
            case Configuration:
                registryService = RegistryService.build(new ConfigurationClient(kedisService));
                break;
            default:
                throw new RuntimeException();
        }
        registryService.init(registryClientInfo);
        return registryService;
    }

    public RegistryBuilderFactory setRegistryClientInfo(String serverAddr) {
        this.registryClientInfo = RegistryClientInfo.build(serverAddr);
        return this;
    }
}
