package pers.kedis.core.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import pers.kedis.core.exception.ApiGateWayException;
import pers.kedis.core.exception.ExceptionEnum;

/**
 * @author kwsc98
 */
public class JsonUtils {

    private static final JsonMapper JSON_MAPPER = new JsonMapper();

    public static <T> T readValue(byte[] bytes, Class<T> clazz) {
        try {
            return JSON_MAPPER.readValue(bytes, clazz);
        } catch (Exception e) {
            throw new ApiGateWayException(ExceptionEnum.ERROR);
        }
    }

    public static <T> T readValue(String jsonStr, Class<T> clazz) {
        try {
            return JSON_MAPPER.readValue(jsonStr, clazz);
        } catch (Exception e) {
            throw new ApiGateWayException(ExceptionEnum.ERROR);
        }
    }

    public static <T> T readValue(String jsonStr, TypeReference<T> typeReference) {
        try {
            return JSON_MAPPER.readValue(jsonStr, typeReference);
        } catch (Exception e) {
            throw new ApiGateWayException(ExceptionEnum.ERROR);
        }
    }


    public static byte[] writeValueAsBytes(Object object) {
        try {
            return JSON_MAPPER.writeValueAsBytes(object);
        } catch (Exception e) {
            throw new ApiGateWayException(ExceptionEnum.ERROR);
        }
    }

    public static String formatJsonStr(String jsonStr) {
        try {
            return JSON_MAPPER.writeValueAsString(JSON_MAPPER.readValue(jsonStr, Object.class));
        } catch (Exception e) {
            throw new ApiGateWayException(ExceptionEnum.ERROR);
        }
    }

    public static String writeValueAsString(Object object) {
        try {
            return JSON_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new ApiGateWayException(ExceptionEnum.ERROR);
        }
    }
}



