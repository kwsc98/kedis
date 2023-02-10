package pers.kedis.core.protocol.codec;


import com.fasterxml.jackson.databind.json.JsonMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.internal.ObjectUtil;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

/**
 * krpc解码器
 * @author kwsc98
 */
public class KedisDecoder extends MessageToMessageDecoder<ByteBuf> {

    private static final JsonMapper JSON_MAPPER;

    private static final Charset CHARSET;

    static {
        JSON_MAPPER = new JsonMapper();
        CHARSET = ObjectUtil.checkNotNull(Charset.defaultCharset(), "charset");
    }

    @Override
    public final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        String msg = in.toString(CHARSET);
        KrpcMsg krpcMsg = JSON_MAPPER.readValue(msg, KrpcMsg.class);
        Object[] objects = krpcMsg.getParams();
        Class<?>[] parameterTypes = krpcMsg.getParameterTypes();
        if (Objects.nonNull(objects)) {
            for (int i = 0; i < objects.length; i++) {
                objects[i] = JSON_MAPPER.convertValue(objects[i], parameterTypes[i]);
            }
        }
        Class<?> clazz = Class.forName(krpcMsg.getClassName());
        Method method = clazz.getMethod(krpcMsg.getMethodName(), krpcMsg.getParameterTypes());
        krpcMsg.setObject(JSON_MAPPER.convertValue(krpcMsg.getObject(), method.getReturnType()));
        out.add(krpcMsg);
    }
}
