package pers.kedis.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import pers.kedis.core.dto.KedisData;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kwsc98
 */
@JsonIgnoreProperties(value = {"map"})

public class KedisDb {
    @Getter
    private final int index;

    private final Map<String, KedisData> map = new HashMap<>();

    public KedisDb(int index) {
        this.index = index;
    }

    public boolean setKey(String key, KedisData value) {
        map.put(key, value);
        return true;
    }

    public KedisData getKey(String key) {
        return map.get(key);
    }

    public void remove(String key) {
        map.remove(key);
    }


}
