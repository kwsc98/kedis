package pers.kedis.core.dto;

import lombok.Getter;

/**
 * @author kwsc98
 */
@Getter
public class KedisData {
    private Object data;
    DataType dataType;

    @SuppressWarnings("unchecked")

    public <D> D getData() {
        return (D) data;
    }

    public KedisData(DataType dataType) {
        this.dataType = dataType;
    }

    public KedisData setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "KedisData{" +
                "data=" + data +
                ", dataType=" + dataType +
                '}';
    }
}
