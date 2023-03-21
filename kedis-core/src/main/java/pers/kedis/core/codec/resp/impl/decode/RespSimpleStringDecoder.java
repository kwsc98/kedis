package pers.kedis.core.codec.resp.impl.decode;

import io.netty.buffer.ByteBuf;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.dto.enums.DataType;

/**
 * @author kwsc98
 */
public class RespSimpleStringDecoder extends AbstractRespDecoder {

    @Override
    public KedisData decode(ByteBuf buffer) {
        return new KedisData(DataType.SIMPLE_STRING).setData(readLine(buffer));
    }

}
