package pers.kedis.core.dto;

import lombok.Getter;

import java.util.Objects;

/**
 * @author kwsc98
 */
public class KedisKey {

    private final String key;

    @Getter
    private Long currentTimeMillis;

    public KedisKey(String key,Long currentTimeMillis) {
        this.key = key;
        this.currentTimeMillis = null;
    }

    public void setCurrentTimeMillis(Long currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
    }

    @Override
    public int hashCode() {
        return key == null ? 0 : key.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof KedisKey) {
            String objKey = ((KedisKey) obj).key;
            return Objects.equals(key, objKey);
        }
        return false;
    }

}
