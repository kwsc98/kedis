package pers.kedis.core.codec.resp.impl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import pers.kedis.core.codec.resp.AbstractRespDecoder;
import pers.kedis.core.codec.resp.RespConstants;

/**
 * @author kwsc98
 */
public class LineStringDecoder extends AbstractRespDecoder<String> {

    @Override
    public String decode(ByteBuf buffer) {
        return readLine(buffer);
    }


    public static void main(String[] args) {
        LineStringDecoder lineStringDecoder = new LineStringDecoder();
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        // +OK\r\n
        buffer.writeBytes("".getBytes(RespConstants.UTF_8));
        buffer.writeBytes(RespConstants.CRLF);
        String value = lineStringDecoder.decode(buffer);
        System.out.println();
    }
}

