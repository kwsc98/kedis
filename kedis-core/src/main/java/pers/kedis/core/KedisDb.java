package pers.kedis.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pers.kedis.core.common.structure.Dict;
import pers.kedis.core.dto.KedisKey;
import pers.kedis.core.dto.KedisValue;
import pers.kedis.core.exception.KedisException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author kwsc98
 */
@JsonIgnoreProperties(value = {"dictMap"})
public class KedisDb {
    private final Dict<KedisKey, KedisValue> dictMap = new Dict<>(8);

    private final int index;

    public int getIndex() {
        return index;
    }

    public KedisDb(int index) {
        this.index = index;
    }


    public int size() {
        return dictMap.size();
    }


    public KedisValue getValue(KedisKey key) {
        Map.Entry<KedisKey, KedisValue> entry = getEntry(key);
        return entry == null ? null : entry.getValue();
    }

    public Map.Entry<KedisKey, KedisValue> getDictEntry(KedisKey key) {
        return getEntry(key);
    }

    public KedisKey getKey(KedisKey key) {
        Map.Entry<KedisKey, KedisValue> entry = getEntry(key);
        return entry == null ? null : entry.getKey();
    }

    public boolean containsKey(KedisKey key) {
        Map.Entry<KedisKey, KedisValue> entry = getEntry(key);
        return entry != null;
    }

    public KedisValue put(KedisKey key, KedisValue value) {
        Map.Entry<KedisKey, KedisValue> entry = getEntry(key);
        if (entry == null) {
            if (value == null) {
                throw new KedisException("Put Command Value Is Null");
            }
            return dictMap.put(key, value);
        }
        if (key.getCurrentTimeMillis() != null) {
            entry.getKey().setCurrentTimeMillis(key.getCurrentTimeMillis());
        }
        if (value != null) {
            KedisValue res = entry.getValue();
            entry.setValue(value);
            return res;
        }
        return null;
    }

    public Map.Entry<KedisKey, KedisValue> remove(KedisKey kedisKey) {
        Map.Entry<KedisKey, KedisValue> entry = getEntry(kedisKey);
        if (Objects.nonNull(entry)) {
            dictMap.remove(kedisKey);
        }
        return entry;
    }

    private Map.Entry<KedisKey, KedisValue> getEntry(KedisKey key) {
        Map.Entry<KedisKey, KedisValue> entry = dictMap.getEntry(key);
        if (entry == null || entry.getKey().getCurrentTimeMillis() == -1) {
            return entry;
        }
        KedisKey kedisKey = entry.getKey();
        if (kedisKey.getCurrentTimeMillis() < System.currentTimeMillis()) {
            dictMap.remove(kedisKey);
            return null;
        }
        return entry;
    }

    public int getPatternKey(List<KedisKey> list, Pattern pattern, int index, int count) {
        int res = index;
        List<Map.Entry<KedisKey, KedisValue>> tempList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            res = dictMap.getPatternKey(tempList, pattern, res);
            if (res == 0) {
                break;
            }
        }
        for (Map.Entry<KedisKey, KedisValue> kedisEntry : tempList) {
            if (getEntry(kedisEntry.getKey()) != null) {
                list.add(kedisEntry.getKey());
            }
        }
        return res;
    }


}
