package pers.kedis.core.protocol.codec;


import com.fasterxml.jackson.databind.json.JsonMapper;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.internal.ObjectUtil;
import pers.krpc.core.protocol.KrpcMsg;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;

/**
 * krpc编码器
 * @author kwsc98
 */
public class KedisEncoder extends MessageToMessageEncoder<KrpcMsg> {

    private static final JsonMapper JSON_MAPPER;

    private static final Charset CHARSET;

    static {
        JSON_MAPPER = new JsonMapper();
        CHARSET = ObjectUtil.checkNotNull(Charset.defaultCharset(), "charset");
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, KrpcMsg msg, List<Object> out) throws Exception {
        String jsonStr = JSON_MAPPER.writeValueAsString(msg) + "\r\n";
        out.add(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(jsonStr), CHARSET));
    }
}
