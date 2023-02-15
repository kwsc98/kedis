package pers.kedis.core.codec.resp.impl.decode;

import io.netty.buffer.ByteBuf;

/**
 * @author kwsc98
 */
public class LineStringDecoder extends AbstractRespDecoder<String> {

    @Override
    public String decode(ByteBuf buffer) {
        return readLine(buffer);
    }

}

