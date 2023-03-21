package pers.kedis.core.codec.resp.impl.encode;

import com.alibaba.nacos.shaded.com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import pers.kedis.core.codec.resp.*;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.dto.enums.DataType;

import java.util.Objects;

/**
 * @author kwsc98
 */
public class RespSimpleStringEncoder implements RespEncoder {
    @Override
    public void encode(KedisData kedisData, ByteBuf byteBuf) {
        String data = (String) kedisData.getData();
        if (Objects.isNull(data)) {
            RespUtil.RESP_BULK_STRING_ENCODER.encode(new KedisData(DataType.BULK_STRING).setData(null), byteBuf);
            return;
        }
        byteBuf.writeByte(RespConstants.PLUS_BYTE);
        byteBuf.writeBytes(data.getBytes(Charsets.UTF_8));
        byteBuf.writeBytes(RespConstants.CRLF);
    }
}
