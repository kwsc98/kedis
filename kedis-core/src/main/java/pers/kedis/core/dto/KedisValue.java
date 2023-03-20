package pers.kedis.core.dto;

import lombok.Getter;


/**
 * @author kwsc98
 */
public class KedisValue {

    @Getter
    private ValueType valueType;
    private final Object data;

    @SuppressWarnings("unchecked")
    public <V> V getData() {
        return (V) data;
    }

    public KedisValue(ValueType valueType, Object data) {
        this.valueType = valueType;
        this.data = data;
    }

}
