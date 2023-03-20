package pers.kedis.core.dto;

import lombok.Getter;

import java.util.Objects;

/**
 * @author kwsc98
 */
public class KedisKey {

    @Getter
    private final KedisData key;

    @Getter
    private Long currentTimeMillis;

    public KedisKey(KedisData key, Long currentTimeMillis) {
        this.key = key;
        this.currentTimeMillis = currentTimeMillis;
    }

    public KedisKey(KedisData key) {
        this.key = key;
    }

    public KedisKey setCurrentTimeMillis(Long currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
        return this;
    }

    @Override
    public int hashCode() {
        return key == null ? 0 : key.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof KedisKey) {
            KedisData objKey = ((KedisKey) obj).key;
            return Objects.equals(key, objKey);
        }
        return false;
    }

    @Override
    public String toString() {
        return key.getData().toString();
    }
}
