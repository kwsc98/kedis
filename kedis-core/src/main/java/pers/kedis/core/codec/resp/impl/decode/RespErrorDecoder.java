package pers.kedis.core.codec.resp.impl.decode;

import io.netty.buffer.ByteBuf;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.dto.DataType;

/**
 * @author kwsc98
 */
public class RespErrorDecoder extends AbstractRespDecoder {

    @Override
    public KedisData decode(ByteBuf buffer) {
        return new KedisData(DataType.ERROR).setData(readLine(buffer));
    }

}
