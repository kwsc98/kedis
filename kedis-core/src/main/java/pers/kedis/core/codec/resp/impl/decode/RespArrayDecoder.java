package pers.kedis.core.codec.resp.impl.decode;

import io.netty.buffer.ByteBuf;
import pers.kedis.core.codec.resp.RespConstants;
import pers.kedis.core.codec.resp.RespUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kwsc98
 */
public class RespArrayDecoder extends AbstractRespDecoder<List<Object>> {

    @Override
    public List<Object> decode(ByteBuf buffer) {
        Long arrayLen = RespUtil.RESP_INTEGER_DECODER.decode(buffer);
        if (RespConstants.NEGATIVE_ONE.equals(arrayLen)) {
            return null;
        }
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < arrayLen.intValue(); i++) {
            list.add(RespUtil.decode(buffer));
        }
        return list;

    }
}
