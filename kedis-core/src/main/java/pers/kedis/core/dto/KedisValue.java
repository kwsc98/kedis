package pers.kedis.core.dto;

import lombok.Getter;


/**
 * @author kwsc98
 */
public class KedisValue {

    @Getter
    private ValueType valueType;
    private final Object value;
    @SuppressWarnings("unchecked")
    public <V> V getValue() {
        return (V) value;
    }

    public KedisValue(ValueType valueType, Object value) {
        this.valueType = valueType;
        this.value = value;
    }

}
