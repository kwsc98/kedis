package pers.kedis.core.dto;

import lombok.Getter;


/**
 * @author kwsc98
 */
public class KedisValue<V> {

    @Getter
    private final V value;

    public KedisValue(V value) {
        this.value = value;
    }


}
