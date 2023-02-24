package pers.kedis.core.common.utils;

import pers.kedis.core.dto.KedisData;

import java.util.List;

/**
 * @author kwsc98
 */
public class KedisUtil {
    @SuppressWarnings("unchecked")
    public static  <T> List<T> convertList(Object o) {
        return (List<T>) o;
    }

}
