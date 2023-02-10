package pers.kedis.core.codec;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author kwsc98
 */
public interface Serializer {

    /**
     * java 对象转换成二进制
     */
    byte[] serialize(Object object) throws Exception;

    /**
     * 二进制转换成 java 对象
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz) throws Exception;

}
