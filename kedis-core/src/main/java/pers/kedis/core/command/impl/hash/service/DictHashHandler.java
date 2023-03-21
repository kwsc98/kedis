package pers.kedis.core.command.impl.hash.service;

import pers.kedis.core.common.structure.Dict;
import pers.kedis.core.dto.KedisData;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author kwsc98
 */
public class DictHashHandler implements HashHandlerInterface<Dict<KedisData, KedisData>> {

    @Override
    public int delByFields(Dict<KedisData, KedisData> dict, Set<KedisData> fieldSet) {
        KedisData kedisData = null;
        for (KedisData field : fieldSet) {
            kedisData = dict.remove(field);
        }
        return Objects.nonNull(kedisData) ? 1 : 0;
    }

    @Override
    public KedisData getValueByField(Dict<KedisData, KedisData> dict, KedisData field) {
        return dict.get(field);
    }

    @Override
    public int getValueByPattern(Dict<KedisData, KedisData> preDict, List<KedisData> dataList, int index, int count, Pattern pattern) {
        List<Map.Entry<KedisData, KedisData>> temp = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            index = preDict.getPatternKey(temp, pattern, index);
            if (index == 0) {
                break;
            }
        }
        for (Map.Entry<KedisData, KedisData> entry : temp) {
            dataList.add(entry.getKey());
            dataList.add(entry.getValue());
        }
        return index;
    }

    @Override
    public void setData(Dict<KedisData, KedisData> dict, KedisData field, KedisData value) {
        dict.put(field, value);
    }

}
