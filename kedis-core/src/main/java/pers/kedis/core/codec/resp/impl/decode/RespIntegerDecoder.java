package pers.kedis.core.codec.resp.impl.decode;

import io.netty.buffer.ByteBuf;
import pers.kedis.core.codec.resp.RespConstants;
import pers.kedis.core.codec.resp.RespUtil;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.dto.enums.DataType;

/**
 * @author kwsc98
 */
public class RespIntegerDecoder extends AbstractRespDecoder {

    @Override
    public KedisData decode(ByteBuf buffer) {
        int endIndex = RespUtil.findEndIndex(buffer);
        long res = 0L;
        int firstIndex = buffer.readerIndex();
        boolean negative = false;
        byte firstByte = buffer.getByte(firstIndex);
        // 负数
        if (RespConstants.MINUS_BYTE == firstByte) {
            negative = true;
            firstIndex++;
        }
        for (int i = firstIndex; i < endIndex; i++) {
            byte value = buffer.getByte(i);
            int digit = value - '0';
            res = res * 10 + digit;
        }
        if (negative) {
            res = -res;
        }
        buffer.readerIndex(endIndex + 2);
        return new KedisData(DataType.INTEGER).setData(res);
    }

}
