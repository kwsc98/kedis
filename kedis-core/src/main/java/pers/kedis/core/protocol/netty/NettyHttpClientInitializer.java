package pers.kedis.core.protocol.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;


/**
 * @author kwsc98
 */
public class NettyHttpClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();

        p.addLast(new HttpClientCodec());

        p.addLast(new HttpContentDecompressor());

        p.addLast(new HttpObjectAggregator(1024 * 1024));

        p.addLast(new NettyHttpClientHandler());
    }
}
