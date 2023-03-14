package pers.kedis.core.protocol.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import pers.kedis.core.KedisService;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.dto.ChannelDTO;

import java.util.List;

/**
 * @author kwsc98
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<List<KedisData>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, List<KedisData> kedisDataList) {
        ChannelDTO channelDTO = NettyService.CHANNELDTO_MAP.get(ctx.channel().id().asLongText());
        channelDTO.setKedisDataList(kedisDataList);
        ctx.writeAndFlush(KedisService.handles(channelDTO));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("Netty Server Channel Error : {}", cause.toString(), cause);
        ctx.close();
    }
}
