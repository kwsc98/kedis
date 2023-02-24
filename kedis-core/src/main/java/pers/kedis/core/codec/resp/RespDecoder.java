package pers.kedis.core.codec.resp;

import io.netty.buffer.ByteBuf;
import pers.kedis.core.dto.KedisData;

/**
 * @author kwsc98
 */
public interface RespDecoder{
    /**
     * Resp解码接口
     */
    KedisData decode(ByteBuf buffer);

}