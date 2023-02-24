package pers.kedis.core.codec.resp.impl.decode;

import io.netty.buffer.ByteBuf;
import pers.kedis.core.codec.resp.RespConstants;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.dto.DataType;
import pers.kedis.core.codec.resp.RespUtil;
import pers.kedis.core.exception.KedisException;

/**
 * @author kwsc98
 */
public class RespBulkStringDecoder extends AbstractRespDecoder {

    @Override
    public KedisData decode(ByteBuf buffer) {
        KedisData kedisData = new KedisData(DataType.BULK_STRING);
        KedisData longKedisData = RespUtil.RESP_INTEGER_DECODER.decode(buffer);
        Long strLen = (Long) longKedisData.getData();
        if (RespConstants.NEGATIVE_ONE.equals(strLen)) {
            return kedisData.setData(RespConstants.NULL_STRING);
        } else if (RespConstants.ZERO.equals(strLen)) {
            return kedisData.setData(RespConstants.EMPTY_STRING);
        }
        int endIndex = RespUtil.findEndIndex(buffer);
        int firstIndex = buffer.readerIndex();
        if (endIndex - firstIndex < strLen.intValue()) {
            throw new KedisException();
        }
        byte[] bytes = new byte[strLen.intValue()];
        buffer.readBytes(bytes);
        buffer.readerIndex(endIndex + 2);
        return kedisData.setData(new String(bytes, RespConstants.UTF_8));
    }

}
