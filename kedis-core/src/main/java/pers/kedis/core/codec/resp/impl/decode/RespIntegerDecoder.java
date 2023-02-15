package pers.kedis.core.codec.resp.impl.decode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import pers.kedis.core.codec.resp.RespConstants;

/**
 * @author kwsc98
 */
public class RespIntegerDecoder extends AbstractRespDecoder<Long> {

    @Override
    public Long decode(ByteBuf buffer) {
        int endIndex = findEndIndex(buffer);
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
        return res;
    }

    public static void main(String[] args) {
        RespIntegerDecoder respIntegerDecoder = new RespIntegerDecoder();
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        // +OK\r\n
        buffer.writeBytes("13232".getBytes(RespConstants.UTF_8));
        buffer.writeBytes(RespConstants.CRLF);
        Long value = respIntegerDecoder.decode(buffer);
        System.out.println();
    }

}
