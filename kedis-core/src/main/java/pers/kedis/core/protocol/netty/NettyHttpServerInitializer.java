package pers.kedis.core.protocol.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import lombok.extern.slf4j.Slf4j;
import pers.kedis.core.KedisApplicationContext;

/**
 * @author kwsc98
 */
@Slf4j
public class NettyHttpServerInitializer extends ChannelInitializer<SocketChannel> {

    private final KedisApplicationContext kedisApplicationContext;

    public NettyHttpServerInitializer(KedisApplicationContext kedisApplicationContext){
        this.kedisApplicationContext = kedisApplicationContext;
    }


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        // resp编码器，返回结果
        p.addLast(new ResponseEncoder());
        // 处理客户端的链接和断开，管理客户端
        p.addLast(new ClientHandler());
        // 命令解析处理器，原始信息转成命令和参数
        p.addLast(new CommandDecoder());
        // Http请求处理
        p.addLast(new NettyHttpServerHandler(this.kedisApplicationContext));
    }
}
