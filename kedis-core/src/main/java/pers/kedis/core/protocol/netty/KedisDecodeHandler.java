package pers.kedis.core.protocol.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import pers.kedis.core.codec.resp.RespConstants;
import pers.kedis.core.codec.resp.RespUtil;
import pers.kedis.core.dto.KedisData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kwsc98
 */
@Slf4j
public class KedisDecodeHandler extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        printByteBufInfo(msg);
        List<KedisData> list = new ArrayList<>();
        KedisData pre = null;
        while ((pre = RespUtil.decode(msg)) != null) {
            list.add(pre);
        }
        out.add(list);
    }

    public void printByteBufInfo(ByteBuf msg) {
        StringBuilder stringBuilder = new StringBuilder();
        String pre = msg.toString(RespConstants.UTF_8);
        for (char c : pre.toCharArray()) {
            if (c == RespConstants.CR) {
                stringBuilder.append("\\r");
            } else if (c == RespConstants.LF) {
                stringBuilder.append("\\n");
            } else {
                stringBuilder.append(c);
            }
        }
        log.debug("Receive ByteBuf : {}", stringBuilder);
    }


}
