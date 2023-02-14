package pers.kedis.core.codec.resp.impl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import pers.kedis.core.codec.resp.AbstractRespDecoder;
import pers.kedis.core.codec.resp.RespConstants;
import pers.kedis.core.exception.KedisException;

/**
 * @author kwsc98
 */
public class RespBulkStringDecoder extends AbstractRespDecoder<String> {

    private static final RespIntegerDecoder RESP_INTEGER_DECODER = new RespIntegerDecoder();

    @Override
    public String decode(ByteBuf buffer) {
        int endIndex = findEndIndex(buffer);
        Long strLen = RESP_INTEGER_DECODER.decode(buffer);
        if (RespConstants.NEGATIVE_ONE.equals(strLen)) {
            return RespConstants.NULL_STRING;
        } else if (RespConstants.ZERO.equals(strLen)) {
            return RespConstants.EMPTY_STRING;
        }
        buffer.readerIndex(endIndex + 2);
        endIndex = findEndIndex(buffer);
        int firstIndex = buffer.readerIndex();
        if(endIndex - firstIndex < strLen.intValue()){
            throw new KedisException();
        }
        byte[] bytes = new byte[strLen.intValue()];
        buffer.readBytes(bytes);
        return new String(bytes, RespConstants.UTF_8);
    }

    public static void main(String[] args) {
        RespBulkStringDecoder respBulkStringDecoder = new RespBulkStringDecoder();
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        // +OK\r\n
        buffer.writeBytes("4".getBytes(RespConstants.UTF_8));
        buffer.writeBytes(RespConstants.CRLF);
        buffer.writeBytes("abc".getBytes(RespConstants.UTF_8));
        buffer.writeBytes(RespConstants.CRLF);
        String value = respBulkStringDecoder.decode(buffer);
        System.out.println();
    }


}
