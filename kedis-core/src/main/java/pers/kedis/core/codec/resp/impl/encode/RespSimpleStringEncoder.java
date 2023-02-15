package pers.kedis.core.codec.resp.impl.encode;

import com.alibaba.nacos.shaded.com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import pers.kedis.core.codec.resp.*;

import java.util.Objects;

/**
 * @author kwsc98
 */
public class RespSimpleStringEncoder implements RespEncoder<String> {
    @Override
    public void encode(RespData<String> respData, ByteBuf byteBuf) {
        String data = respData.getData();
        if (Objects.isNull(data)) {
            RespUtil.RESP_BULK_STRING_ENCODER.encode(new RespData<String>(null, RespType.BULK_STRING), byteBuf);
            return;
        }
        byteBuf.writeByte(RespConstants.PLUS_BYTE);
        byteBuf.writeBytes(data.getBytes(Charsets.UTF_8));
        byteBuf.writeBytes(RespConstants.CRLF);
    }
}
