package pers.kedis.core.codec.resp.impl.decode;

import io.netty.buffer.ByteBuf;
import io.netty.util.ByteProcessor;
import pers.kedis.core.codec.resp.RespConstants;
import pers.kedis.core.codec.resp.RespDecoder;
import pers.kedis.core.exception.KedisException;

/**
 * @author kwsc98
 */
public abstract class AbstractRespDecoder<T> implements RespDecoder<T> {

    public String readLine(ByteBuf buffer) {
        int endIndex = findEndIndex(buffer);
        int firstIndex = buffer.readerIndex();
        // 计算字节长度
        int size = endIndex - firstIndex;
        byte[] bytes = new byte[size];
        buffer.readBytes(bytes);
        buffer.readerIndex(endIndex + 2);
        return new String(bytes, RespConstants.UTF_8);
    }

    public int findEndIndex(ByteBuf buffer) {
        int index = buffer.forEachByte(ByteProcessor.FIND_LF);
        int res = (index > 0 && buffer.getByte(index - 1) == RespConstants.CR) ? index - 1 : -1;
        if (res <= -1) {
            throw new KedisException();
        }
        return res;
    }

}
