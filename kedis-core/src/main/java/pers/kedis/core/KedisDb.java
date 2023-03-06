package pers.kedis.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pers.kedis.core.common.structure.Dict;
import pers.kedis.core.common.structure.DictType;
import pers.kedis.core.dto.KedisKey;
import pers.kedis.core.dto.KedisValue;
import pers.kedis.core.exception.KedisException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author kwsc98
 */
@JsonIgnoreProperties(value = {"map"})
public class KedisDb {
    private final Map<DictType, Dict<KedisKey, KedisValue<?>>> dictMap = new Dict<>(8);

    KedisDb() {
        dictMap.put(DictType.String, new Dict<>(2));
        dictMap.put(DictType.Hash, new Dict<>(2));
        dictMap.put(DictType.List, new Dict<>(2));
        dictMap.put(DictType.Set, new Dict<>(2));
        dictMap.put(DictType.SortSet, new Dict<>(2));
    }

    public KedisValue<?> get(DictType dictType, KedisKey key) {
        Map.Entry<KedisKey, KedisValue<?>> entry = getEntry(dictType, key);
        return entry == null ? null : entry.getValue();
    }

    public boolean containsKey(DictType dictType, KedisKey key) {
        Map.Entry<KedisKey, KedisValue<?>> entry = getEntry(dictType, key);
        return entry != null;
    }

    public KedisValue<?> put(DictType dictType, KedisKey key, KedisValue<?> value) {
        Map.Entry<KedisKey, KedisValue<?>> entry = getEntry(dictType, key);
        if (entry == null) {
            if (value == null) {
                throw new KedisException("Put Command Value Is Null");
            }
            return dictMap.get(dictType).put(key, value);
        }
        if (key.getCurrentTimeMillis() != null) {
            if (key.getCurrentTimeMillis() == -1) {
                entry.getKey().setCurrentTimeMillis(null);
            } else {
                entry.getKey().setCurrentTimeMillis(key.getCurrentTimeMillis());
            }
        }
        if (value != null) {
            KedisValue<?> res = entry.getValue();
            entry.setValue(value);
            return res;
        }
        return null;
    }

    private Map.Entry<KedisKey, KedisValue<?>> getEntry(DictType dictType, KedisKey key) {
        Map.Entry<KedisKey, KedisValue<?>> entry = dictMap.get(dictType).getEntry(key);
        if (entry == null || entry.getKey().getCurrentTimeMillis() == null) {
            return entry;
        }
        KedisKey kedisKey = entry.getKey();
        if (kedisKey.getCurrentTimeMillis() < System.currentTimeMillis()) {
            dictMap.get(dictType).remove(kedisKey);
            return null;
        }
        return entry;
    }

    public int getPatternKey(DictType dictType, List<String> list, String pattern, int index, int count) {
        int res = index;
        List<KedisKey> tempList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            res = dictMap.get(dictType).getPatternKey(tempList, pattern, res);
            if (res == 0) {
                break;
            }
        }
        for (KedisKey kedisKey : tempList) {
            if (getEntry(dictType, kedisKey) != null) {
                list.add(kedisKey.getKey());
            }
        }
        return res;
    }


}
