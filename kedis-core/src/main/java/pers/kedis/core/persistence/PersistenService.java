package pers.kedis.core.persistence;

import io.netty.buffer.ByteBuf;
import pers.kedis.core.KedisProperties;

/**
 * @author kwsc98
 */
public class PersistenService {

    private static AofService aofService;

    public static void init(KedisProperties kedisProperties) {
        PersistenService.aofService = new AofService();
        PersistenService.aofService.init(kedisProperties);
    }

    public static void saveCommand(ByteBuf byteBuf, int dbIndex) {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        aofService.saveCommand(bytes, dbIndex);
    }


}
