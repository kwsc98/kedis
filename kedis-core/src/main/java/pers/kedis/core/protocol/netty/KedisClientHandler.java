package pers.kedis.core.protocol.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import pers.kedis.core.KedisService;
import pers.kedis.core.dto.ChannelDTO;


/**
 * @author kwsc98
 */
@Slf4j
public class KedisClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        NettyService.CHANNELDTO_MAP.put(ctx.channel().id().asLongText(),new ChannelDTO(KedisService.getkedisDb(0),ctx.channel()));
        log.info("Kedis Client Channel Online Channel : {}", ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        NettyService.CHANNELDTO_MAP.remove(ctx.channel().id().asLongText());
        log.info("Kedis Client Channel OffLine Channel : {}", ctx.channel());
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        NettyService.CHANNELDTO_MAP.remove(ctx.channel().id().asLongText());
        log.error("Kedis Client Channel Error Channel : {} ErrorInfo : {}", ctx.channel(), cause.getMessage());
    }
}
