package pers.kedis.core.codec.resp;

import io.netty.buffer.ByteBuf;

/**
 * @author kwsc98
 */
public interface RespDecoder<V>{
    /**
     * Resp解码接口
     */
    V decode(ByteBuf buffer);

}