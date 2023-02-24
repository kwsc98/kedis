package pers.kedis.core.codec.resp;

import io.netty.buffer.ByteBuf;
import pers.kedis.core.dto.KedisData;

/**
 * @author kwsc98
 */
public interface RespEncoder {
    /**
     * encode
     */
    void encode(KedisData data, ByteBuf byteBuf);

}
