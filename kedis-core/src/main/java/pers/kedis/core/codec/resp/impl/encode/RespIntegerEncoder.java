package pers.kedis.core.codec.resp.impl.encode;

import com.alibaba.nacos.shaded.com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import pers.kedis.core.codec.resp.*;
import pers.kedis.core.codec.resp.impl.decode.AbstractRespDecoder;

import java.util.Objects;

/**
 * @author kwsc98
 */
public class RespIntegerEncoder implements RespEncoder<Long> {

    @Override
    public void encode(RespData<Long> respData, ByteBuf byteBuf) {
        Long data = respData.getData();
        if (Objects.isNull(data)) {
            RespUtil.RESP_BULK_STRING_ENCODER.encode(new RespData<String>(null, RespType.BULK_STRING), byteBuf);
            return;
        }
        byteBuf.writeByte(RespConstants.COLON_BYTE);
        byteBuf.writeBytes(String.valueOf(data).getBytes(Charsets.UTF_8));
        byteBuf.writeBytes(RespConstants.CRLF);
    }
}
