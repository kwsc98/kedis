package pers.kedis.core.protocol.netty;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author kwsc98
 */
@Slf4j
public class NettyClient {

    private final Bootstrap bootstrap;

    /**
     * 采用共享变量的方式，进行异步回调处理
     **/
    public static final Cache<String, Promise<Object>> MSG_CACHE = Caffeine.newBuilder().expireAfterAccess(10000L, TimeUnit.MILLISECONDS).build();

    public static final Map<String, Map<Integer, Channel>> CHANNEL_MAP = new HashMap<>();

    public NettyClient() {
        EventLoopGroup group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        this.bootstrap.group(group).channel(NioSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO)).handler(new NettyHttpClientInitializer());
    }

    public Channel getChannel(String host, int port) {
        Map<Integer, Channel> portMap = CHANNEL_MAP.get(host);
        if (Objects.isNull(portMap)) {
            portMap = new HashMap<>();
            CHANNEL_MAP.put(host, portMap);
        }
        Channel channel = portMap.get(port);
        if (Objects.nonNull(channel) && channel.isActive()) {
            return channel;
        }
        try {
            channel = this.bootstrap.connect(host, port).sync().channel();
            log.info("Client Channel Create Done : {}", channel);
            portMap.put(port, channel);
            return channel;
        } catch (Exception e) {
            log.error("Client Channel Create Error : {}", e.toString(), e);
            throw new RuntimeException();
        }
    }


}
