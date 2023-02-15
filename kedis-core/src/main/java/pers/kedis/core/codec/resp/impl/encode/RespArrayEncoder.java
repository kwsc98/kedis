package pers.kedis.core.codec.resp.impl.encode;

import com.alibaba.nacos.shaded.com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import pers.kedis.core.codec.resp.RespConstants;
import pers.kedis.core.codec.resp.RespData;
import pers.kedis.core.codec.resp.RespEncoder;
import pers.kedis.core.codec.resp.RespUtil;
import pers.kedis.core.codec.resp.impl.decode.AbstractRespDecoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author kwsc98
 */
public class RespArrayEncoder implements RespEncoder<List<RespData<Object>>> {

    @Override
    public void encode(RespData<List<RespData<Object>>> respData, ByteBuf byteBuf) {
        List<RespData<Object>> data = respData.getData();
        long listLen = -1;
        if (Objects.nonNull(data)) {
            listLen = data.size();
        }
        byteBuf.writeByte(RespConstants.ASTERISK_BYTE);
        byteBuf.writeBytes(String.valueOf(listLen).getBytes(Charsets.UTF_8));
        byteBuf.writeBytes(RespConstants.CRLF);
        if (Objects.isNull(data) || data.size() == 0) {
            return;
        }
        for (RespData<Object> datum : data) {
            RespUtil.encode(datum, byteBuf);
        }
        byteBuf.writeBytes(RespConstants.CRLF);
    }

}
