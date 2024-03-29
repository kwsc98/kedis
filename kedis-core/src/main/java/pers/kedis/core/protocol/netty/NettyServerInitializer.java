package pers.kedis.core.protocol.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import lombok.extern.slf4j.Slf4j;
import pers.kedis.core.KedisService;

/**
 * @author kwsc98
 */
@Slf4j
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new KedisClientHandler());
        p.addLast(new KedisDecodeHandler());
        p.addLast(new KedisEncodeHandler());
        p.addLast(new NettyServerHandler());
    }
}
