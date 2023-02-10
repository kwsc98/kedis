package pers.kedis.core;


import lombok.Getter;
import pers.kedis.core.handler.ChannelService;
import pers.kedis.core.handler.HandlerService;
import pers.kedis.core.protocol.netty.NettyService;
import pers.kedis.core.registry.RegistryBuilderFactory;
import pers.kedis.core.registry.RegistryService;

/**
 * KedisApplicationContext
 * 2022/7/25 15:31
 *
 * @author wangsicheng
 **/
@Getter
public class KedisApplicationContext {

    private final NettyService nettyService;

    private final HandlerService handlerService;

    private final ChannelService channelService;

    private final DisposeService disposeService;

    private final RegistryBuilderFactory registryBuilderFactory;

    private RegistryService registryService;


    public KedisApplicationContext(RegistryBuilderFactory registryBuilderFactory, int port) {
        try {
            this.nettyService = new NettyService(port, this);
            this.handlerService = new HandlerService();
            this.channelService = new ChannelService(this);
            this.disposeService = new DisposeService(this);
            this.registryBuilderFactory = registryBuilderFactory;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public KedisApplicationContext init() {
        this.registryService = registryBuilderFactory.init(this.channelService);
        return this;
    }


}
