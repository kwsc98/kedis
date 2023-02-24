package pers.kedis.core.codec.resp.impl.decode;

import io.netty.buffer.ByteBuf;
import io.netty.util.ByteProcessor;
import pers.kedis.core.codec.resp.RespConstants;
import pers.kedis.core.codec.resp.RespDecoder;
import pers.kedis.core.codec.resp.RespUtil;
import pers.kedis.core.exception.KedisException;

/**
 * @author kwsc98
 */
public abstract class AbstractRespDecoder implements RespDecoder {

    public String readLine(ByteBuf buffer) {
        int endIndex = RespUtil.findEndIndex(buffer);
        int firstIndex = buffer.readerIndex();
        // 计算字节长度
        int size = endIndex - firstIndex;
        byte[] bytes = new byte[size];
        buffer.readBytes(bytes);
        buffer.readerIndex(endIndex + 2);
        return new String(bytes, RespConstants.UTF_8);
    }

}
