package pers.kedis.core.protocol.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import pers.kedis.core.KedisService;
import pers.kedis.core.dto.ChannelDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kwsc98
 */
@Slf4j
public class NettyService {

    protected static final Map<String, ChannelDTO> CHANNELDTO_MAP = new HashMap<>();

    EventLoopGroup bossGroup;

    EventLoopGroup workerGroup;

    public NettyService(int port, KedisService kedisService) {
        //NioEventLoopGroup是对Thread和Selector的封装
        //主线程
        bossGroup = new NioEventLoopGroup(1);
        //工作线程
        workerGroup = new NioEventLoopGroup(1);
        try {
            //ServerBootstrap作用于Netty引导初始化
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    //绑定服务端通道 NioServerSocketChannel
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new NettyServerInitializer(kedisService));
            Channel channel = b.bind(port).sync().channel();
            log.info("server channel start port:{} channel:{}", port, channel);
        } catch (Exception e) {
            log.error("creat netty service error", e);
        }
    }

    public void close() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
