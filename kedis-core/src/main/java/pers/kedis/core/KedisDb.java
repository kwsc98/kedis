package pers.kedis.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.internal.NotNull;
import pers.kedis.core.common.structure.Dict;
import pers.kedis.core.common.structure.DictEntry;
import pers.kedis.core.common.structure.DictType;
import pers.kedis.core.dto.KedisKey;
import pers.kedis.core.dto.KedisValue;
import pers.kedis.core.exception.KedisException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author kwsc98
 */
@JsonIgnoreProperties(value = {"map"})
public class KedisDb {
    private final Map<DictType, Dict<KedisKey, KedisValue<?>>> dataTypeMap = new Dict<>(8);

    KedisDb() {
        dataTypeMap.put(DictType.Key, new Dict<>(1024));
        dataTypeMap.put(DictType.String, new Dict<>(1024));
        dataTypeMap.put(DictType.Hash, new Dict<>(1024));
        dataTypeMap.put(DictType.List, new Dict<>(1024));
        dataTypeMap.put(DictType.Set, new Dict<>(1024));
        dataTypeMap.put(DictType.SortSet, new Dict<>(1024));
    }

    public KedisValue<?> get(DictType dictType, @NotNull KedisKey key) {
        Map.Entry<KedisKey, KedisValue<?>> entry = getEntry(dictType, key);
        return entry == null ? null : entry.getValue();
    }

    public boolean containsKey(DictType dictType, @NotNull KedisKey key) {
        Map.Entry<KedisKey, KedisValue<?>> entry = getEntry(dictType, key);
        return entry != null;
    }

    public KedisValue<?> put(DictType dictType, @NotNull KedisKey key, KedisValue<?> value) {
        Map.Entry<KedisKey, KedisValue<?>> entry = getEntry(dictType, key);
        if (entry == null) {
            if (value == null) {
                throw new KedisException("Put Command Value Is Null");
            }
            return dataTypeMap.get(dictType).put(key, value);
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

    private Map.Entry<KedisKey, KedisValue<?>> getEntry(DictType dictType, @NotNull KedisKey key) {
        Map.Entry<KedisKey, KedisValue<?>> entry = dataTypeMap.get(dictType).getEntry(key);
        if (entry == null || entry.getKey().getCurrentTimeMillis() == null) {
            return entry;
        }
        KedisKey kedisKey = entry.getKey();
        if (kedisKey.getCurrentTimeMillis() < System.currentTimeMillis()) {
            dataTypeMap.get(dictType).remove(kedisKey);
            return null;
        }
        return entry;
    }


}
