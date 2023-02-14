package pers.kedis.core.protocol.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kwsc98
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("Server Channel Connection: [{}] parent [{}]", ctx.channel(), ctx.channel().parent());
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("Server Channel Error : {}", cause.toString());
        ctx.close();
    }
}
