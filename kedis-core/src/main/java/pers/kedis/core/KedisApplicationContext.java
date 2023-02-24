package pers.kedis.core;

import lombok.Getter;
import pers.kedis.core.protocol.netty.NettyService;
import pers.kedis.core.registry.RegistryBuilderFactory;
import pers.kedis.core.registry.RegistryService;

/**
 * @author kwsc98
 */
@Getter
public class KedisApplicationContext {

    private NettyService nettyService;

    private KedisService kedisService;

    private RegistryService registryService;

    public static KedisApplicationContext build() {
        return new KedisApplicationContext();
    }

    public KedisApplicationContext init(KedisProperties kedisProperties) {
        this.kedisService = new KedisService();
        this.registryService =  RegistryBuilderFactory.build().setRegistryClientInfo(kedisProperties.getRegisteredPath()).init(kedisService);
        this.nettyService = new NettyService(kedisProperties.getPort(), this.kedisService);
        return this;
    }


}
