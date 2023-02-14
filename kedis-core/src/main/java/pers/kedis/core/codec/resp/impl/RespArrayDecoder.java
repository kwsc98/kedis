package pers.kedis.core.codec.resp.impl;

import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import pers.kedis.core.codec.resp.AbstractRespDecoder;
import pers.kedis.core.codec.resp.RespConstants;

import java.util.List;

/**
 * @author kwsc98
 */
public class RespArrayDecoder extends AbstractRespDecoder<List<Object>> {

    private static final RespIntegerDecoder RESP_INTEGER_DECODER = new RespIntegerDecoder();


    @Override
    public List<Object> decode(ByteBuf buffer) {
        int endIndex = findEndIndex(buffer);
        Long arrayLen = RESP_INTEGER_DECODER.decode(buffer);
        if (RespConstants.NEGATIVE_ONE.equals(arrayLen)) {
            return null;
        } else if (RespConstants.ZERO.equals(arrayLen)) {
            return Lists.newArrayList();
        }


    }
}
