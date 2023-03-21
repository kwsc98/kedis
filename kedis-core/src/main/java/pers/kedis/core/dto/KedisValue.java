package pers.kedis.core.dto;

import lombok.Getter;
import pers.kedis.core.dto.enums.ValueType;


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
