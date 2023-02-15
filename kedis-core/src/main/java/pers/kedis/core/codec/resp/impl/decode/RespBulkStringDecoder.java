package pers.kedis.core.codec.resp.impl.decode;

import io.netty.buffer.ByteBuf;
import pers.kedis.core.codec.resp.RespConstants;
import pers.kedis.core.codec.resp.RespUtil;
import pers.kedis.core.exception.KedisException;

/**
 * @author kwsc98
 */
public class RespBulkStringDecoder extends AbstractRespDecoder<String> {

    @Override
    public String decode(ByteBuf buffer) {
        Long strLen = RespUtil.RESP_INTEGER_DECODER.decode(buffer);
        if (RespConstants.NEGATIVE_ONE.equals(strLen)) {
            return RespConstants.NULL_STRING;
        } else if (RespConstants.ZERO.equals(strLen)) {
            return RespConstants.EMPTY_STRING;
        }
        int endIndex = findEndIndex(buffer);
        int firstIndex = buffer.readerIndex();
        if (endIndex - firstIndex < strLen.intValue()) {
            throw new KedisException();
        }
        byte[] bytes = new byte[strLen.intValue()];
        buffer.readBytes(bytes);
        buffer.readerIndex(endIndex + 2);
        return new String(bytes, RespConstants.UTF_8);
    }

}
