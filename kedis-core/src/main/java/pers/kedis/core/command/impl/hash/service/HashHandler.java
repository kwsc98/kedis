package pers.kedis.core.command.impl.hash.service;

import pers.kedis.core.common.structure.Dict;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.dto.KedisValue;
import pers.kedis.core.dto.ValueType;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author kwsc98
 */
public class HashHandler {

    private static final ListHashHandler LIST_HASH_HANDLER = new ListHashHandler();

    private static final DictHashHandler DICT_HASH_HANDLER = new DictHashHandler();


    public static int delByFields(KedisValue value, Set<KedisData> fieldSet) {
        if (value.getData() instanceof List) {
            return LIST_HASH_HANDLER.delByFields(value.getData(), fieldSet);
        } else {
            return DICT_HASH_HANDLER.delByFields(value.getData(), fieldSet);
        }
    }


    public static KedisData getValueByField(KedisValue value, KedisData field) {
        if (value.getData() instanceof List) {
            return LIST_HASH_HANDLER.getValueByField(value.getData(), field);
        } else {
            return DICT_HASH_HANDLER.getValueByField(value.getData(), field);
        }
    }


    public static int getValueByPattern(KedisValue value, List<KedisData> dataList, int index, int count, Pattern pattern) {
        if (value.getData() instanceof List) {
            return LIST_HASH_HANDLER.getValueByPattern(value.getData(), dataList, index, count, pattern);
        } else {
            return DICT_HASH_HANDLER.getValueByPattern(value.getData(), dataList, index, count, pattern);
        }
    }


    public static KedisValue setData(KedisValue kedisValue, KedisData field, KedisData value) {
        if (kedisValue.getData() instanceof List) {
            List<KedisData> kedisData = kedisValue.getData();
            if (kedisData.size() / 2 < 7) {
                LIST_HASH_HANDLER.setData(kedisData, field, value);
            } else {
                kedisValue = new KedisValue(ValueType.Hash, new Dict<KedisData, KedisData>(32));
                for (int i = 0; i < kedisData.size(); i += 2) {
                    DICT_HASH_HANDLER.setData(kedisValue.getData(), kedisData.get(i), kedisData.get(i + 1));
                }
            }
        }
        if (kedisValue.getData() instanceof Dict) {
            DICT_HASH_HANDLER.setData(kedisValue.getData(), field, value);
        }
        return kedisValue;
    }
}
