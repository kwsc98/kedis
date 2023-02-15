package pers.kedis.core.codec.resp;

import io.netty.buffer.ByteBuf;

/**
 * @author kwsc98
 */
public interface RespEncoder<T> {
    /**
     * encode
     */
    void encode(RespData<T> data, ByteBuf byteBuf);

}
