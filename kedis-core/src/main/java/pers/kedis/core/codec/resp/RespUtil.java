package pers.kedis.core.codec.resp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.ByteProcessor;
import pers.kedis.core.codec.resp.impl.decode.*;
import pers.kedis.core.codec.resp.impl.encode.*;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.dto.DataType;
import pers.kedis.core.exception.KedisException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author kwsc98
 */
public class RespUtil {

    public static final RespArrayDecoder RESP_ARRAY_DECODER = new RespArrayDecoder();
    public static final RespBulkStringDecoder RESP_BULK_STRING_DECODER = new RespBulkStringDecoder();
    public static final RespErrorDecoder RESP_ERROR_DECODER = new RespErrorDecoder();
    public static final RespIntegerDecoder RESP_INTEGER_DECODER = new RespIntegerDecoder();
    public static final RespSimpleStringDecoder RESP_SIMPLE_STRING_DECODER = new RespSimpleStringDecoder();


    public static final RespArrayEncoder RESP_ARRAY_ENCODER = new RespArrayEncoder();
    public static final RespBulkStringEncoder RESP_BULK_STRING_ENCODER = new RespBulkStringEncoder();
    public static final RespErrorEncoder RESP_ERROR_ENCODER = new RespErrorEncoder();
    public static final RespIntegerEncoder RESP_INTEGER_ENCODER = new RespIntegerEncoder();
    public static final RespSimpleStringEncoder RESP_SIMPLE_STRING_ENCODER = new RespSimpleStringEncoder();

    public static void encode(KedisData data, ByteBuf byteBuf) {
        if (Objects.isNull(data)) {
            throw new KedisException();
        }
        RespEncoder respEncoder = getRespEncoder(data.getDataType());
        respEncoder.encode(data, byteBuf);
    }

    public static List<KedisData> decodeAll(ByteBuf msg) {
        List<KedisData> list = new ArrayList<>();
        KedisData pre = null;
        while ((pre = decode(msg)) != null) {
            list.add(pre);
        }
        return list;
    }

    public static KedisData decode(ByteBuf byteBuf) {
        if (byteBuf.readableBytes() <= 0) {
            return null;
        }
        int firstIndex = byteBuf.readerIndex();
        RespDecoder respDecoder = getRespDecoder(byteBuf.getByte(firstIndex));
        byteBuf.readerIndex(firstIndex + 1);
        return respDecoder.decode(byteBuf);
    }

    protected static RespEncoder getRespEncoder(DataType dataType) {
        switch (dataType) {
            case SIMPLE_STRING:
                return RESP_SIMPLE_STRING_ENCODER;
            case ERROR:
                return RESP_ERROR_ENCODER;
            case INTEGER:
                return RESP_INTEGER_ENCODER;
            case BULK_STRING:
                return RESP_BULK_STRING_ENCODER;
            case RESP_ARRAY:
                return RESP_ARRAY_ENCODER;
            default:
                throw new KedisException();
        }
    }


    protected static RespDecoder getRespDecoder(byte commandType) {
        char s = (char) commandType;
        switch (commandType) {
            case RespConstants.PLUS_BYTE:
                return RESP_SIMPLE_STRING_DECODER;
            case RespConstants.MINUS_BYTE:
                return RESP_ERROR_DECODER;
            case RespConstants.COLON_BYTE:
                return RESP_INTEGER_DECODER;
            case RespConstants.DOLLAR_BYTE:
                return RESP_BULK_STRING_DECODER;
            case RespConstants.ASTERISK_BYTE:
                return RESP_ARRAY_DECODER;
            default:
                throw new KedisException();
        }
    }

    public static int findEndIndex(ByteBuf buffer) {
        int index = buffer.forEachByte(ByteProcessor.FIND_LF);
        int res = (index > 0 && buffer.getByte(index - 1) == RespConstants.CR) ? index - 1 : -1;
        if (res <= -1) {
            throw new KedisException();
        }
        return res;
    }

    public static void main(String[] args) {
        ByteBuf buffer;
        //*2\r\n$3\r\nfoo\r\n$3\r\nbar\r\n
        buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeBytes("*2".getBytes(RespConstants.UTF_8));
        buffer.writeBytes(RespConstants.CRLF);
        buffer.writeBytes("-Errror fefe".getBytes(RespConstants.UTF_8));
        buffer.writeBytes(RespConstants.CRLF);
        buffer.writeBytes("*1".getBytes(RespConstants.UTF_8));
        buffer.writeBytes(RespConstants.CRLF);
        buffer.writeBytes("$3".getBytes(RespConstants.UTF_8));
        buffer.writeBytes(RespConstants.CRLF);
        buffer.writeBytes("foo".getBytes(RespConstants.UTF_8));
        buffer.writeBytes(RespConstants.CRLF);
        KedisData o = RespUtil.decode(buffer);
        ByteBuf byteBuf1 = ByteBufAllocator.DEFAULT.buffer();
        RespUtil.encode(o, byteBuf1);
        String ds = byteBuf1.toString(RespConstants.UTF_8);
        System.out.println();
        System.out.println("11000000000200100100000000000100".substring(14, 16));
    }


}
