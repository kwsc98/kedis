package pers.kedis.core.codec.resp.impl.decode;

import io.netty.buffer.ByteBuf;
import pers.kedis.core.codec.resp.RespConstants;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.dto.DataType;
import pers.kedis.core.codec.resp.RespUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kwsc98
 */
public class RespArrayDecoder extends AbstractRespDecoder {

    @Override
    public KedisData decode(ByteBuf buffer) {
        KedisData respArray = new KedisData(DataType.RESP_ARRAY);
        KedisData longKedisData = RespUtil.RESP_INTEGER_DECODER.decode(buffer);
        Long arrayLen = longKedisData.getData();
        if (RespConstants.NEGATIVE_ONE.equals(arrayLen)) {
            return respArray;
        }
        List<KedisData> list = new ArrayList<>();
        for (int i = 0; i < arrayLen.intValue(); i++) {
            list.add(RespUtil.decode(buffer));
        }
        return respArray.setData(list);
    }
}
