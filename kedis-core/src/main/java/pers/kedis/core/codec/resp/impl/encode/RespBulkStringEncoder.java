package pers.kedis.core.codec.resp.impl.encode;

import com.alibaba.nacos.shaded.com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import pers.kedis.core.codec.resp.RespConstants;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.codec.resp.RespEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author kwsc98
 */
public class RespBulkStringEncoder implements RespEncoder {

    @Override
    public void encode(KedisData kedisData, ByteBuf byteBuf) {
        String data = (String) kedisData.getData();
        long strLen = -1;
        byte[] bytes = null;
        if (Objects.nonNull(data)) {
            bytes = data.getBytes(StandardCharsets.UTF_8);
            strLen = bytes.length;
        }
        byteBuf.writeByte(RespConstants.DOLLAR_BYTE);
        byteBuf.writeBytes(String.valueOf(strLen).getBytes(Charsets.UTF_8));
        byteBuf.writeBytes(RespConstants.CRLF);
        if (Objects.isNull(bytes)) {
            return;
        }
        byteBuf.writeBytes(bytes);
        byteBuf.writeBytes(RespConstants.CRLF);
        String s = byteBuf.toString(RespConstants.UTF_8);
        System.out.println();
    }
}
