package pers.kedis.core;

import lombok.Getter;
import pers.kedis.core.persistence.PersistenService;
import pers.kedis.core.protocol.netty.NettyService;

/**
 * @author kwsc98
 */
@Getter
public class KedisApplicationContext {

    private NettyService nettyService;


    public static KedisApplicationContext build() {
        return new KedisApplicationContext();
    }

    public KedisApplicationContext init(KedisProperties kedisProperties) {
        KedisService.init(kedisProperties);
        PersistenService.init(kedisProperties);
        this.nettyService = new NettyService(kedisProperties.getPort());
        return this;
    }


}
