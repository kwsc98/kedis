package pers.kedis.core.codec.impl;

import pers.kedis.core.codec.Serializer;
import pers.kedis.core.common.JsonUtils;

/**
 * @author kwsc98
 */
public class JsonSerializer implements Serializer {

    @Override
    public byte[] serialize(Object object) throws Exception {
        return JsonUtils.writeValueAsBytes(object);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws Exception {
        return JsonUtils.readValue(bytes, clazz);
    }
}
