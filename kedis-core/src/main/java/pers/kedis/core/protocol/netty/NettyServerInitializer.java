package pers.kedis.core.protocol.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kwsc98
 */
@Slf4j
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {


    public NettyServerInitializer( ){
    }


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
//        // resp编码器，返回结果
//        p.addLast(new ResponseEncoder());
//        // 处理客户端的链接和断开，管理客户端
//        p.addLast(new ClientHandler());
//        // 命令解析处理器，原始信息转成命令和参数
//        p.addLast(new CommandDecoder());
        // Http请求处理
        p.addLast(new NettyServerHandler());
    }
}
