package pers.kedis.core.command.impl.hash.service;

import pers.kedis.core.dto.KedisData;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author kwsc98
 */
public interface HashHandlerInterface<T> {

    int delByFields(T data, Set<KedisData> fieldSet);

    KedisData getValueByField(T data, KedisData field);

    int getValueByPattern(T preList, List<KedisData> dataList, int index, int count, Pattern pattern);

    void setData(T list, KedisData field, KedisData value);

}
