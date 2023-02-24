package pers.kedis.core.codec.resp.impl.encode;

import com.alibaba.nacos.shaded.com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import pers.kedis.core.codec.resp.RespConstants;
import pers.kedis.core.common.utils.KedisUtil;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.codec.resp.RespEncoder;
import pers.kedis.core.codec.resp.RespUtil;

import java.util.List;
import java.util.Objects;

/**
 * @author kwsc98
 */
public class RespArrayEncoder implements RespEncoder {

    @Override
    public void encode(KedisData kedisData, ByteBuf byteBuf) {
        List<KedisData> data = KedisUtil.convertList(kedisData.getData());
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
        for (KedisData datum : data) {
            RespUtil.encode(datum, byteBuf);
        }
    }

}
