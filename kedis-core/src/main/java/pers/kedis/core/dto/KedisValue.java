package pers.kedis.core.dto;

import lombok.Getter;


/**
 * @author kwsc98
 */
public class KedisValue<V> {

    @Getter
    private ValueType valueType;
    @Getter
    private final V value;

    public KedisValue(ValueType valueType, V value) {
        this.valueType = valueType;
        this.value = value;
    }

}
