package pers.kedis.core.protocol.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.codec.resp.RespUtil;

import java.util.List;

/**
 * @author kwsc98
 */
@Slf4j
public class KedisEncodeHandler extends MessageToMessageEncoder<List<KedisData>> {

    @Override
    protected void encode(ChannelHandlerContext ctx, List<KedisData> msg, List<Object> out) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        for (KedisData kedisData : msg) {
            RespUtil.encode(kedisData, byteBuf);
        }
        out.add(byteBuf);
    }

}
