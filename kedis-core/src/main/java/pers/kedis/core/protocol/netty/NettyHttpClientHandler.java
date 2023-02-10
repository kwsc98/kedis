package pers.kedis.core.protocol.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;
import pers.kedis.core.exception.NettyHttpClientException;

import java.util.Objects;


/**
 * @author kwsc98
 */
@Slf4j
public class NettyHttpClientHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("Client Channel Connection : [{}] parent [{}]", ctx.channel(), ctx.channel().parent());
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
        if (msg instanceof FullHttpResponse) {
            FullHttpResponse fullHttpResponse = (FullHttpResponse) msg;
            log.info("HttpResponse Status : {}", fullHttpResponse.status());
            if (!HttpResponseStatus.OK.equals(fullHttpResponse.status())) {
                throw new NettyHttpClientException(fullHttpResponse.status());
            }
            String uniqueIdentifier = fullHttpResponse.headers().get("uuid");
            //从共享变量中获取Promise
            Promise<Object> promise = NettyClient.MSG_CACHE.getIfPresent(uniqueIdentifier);
            if (Objects.nonNull(promise)) {
                promise.setSuccess(fullHttpResponse.content().toString(CharsetUtil.UTF_8));
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("Client Channel Error", cause);
        ctx.close();
    }
}
