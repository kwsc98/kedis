package pers.kedis.core.dto;

import lombok.Getter;
import pers.kedis.core.dto.enums.DataType;

import java.util.Objects;

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
    public int hashCode() {
        return data == null ? 0 : data.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof KedisData) {
            Object objKey = ((KedisData) obj).data;
            return Objects.equals(data, objKey);
        }
        return false;
    }

    @Override
    public String toString() {
        return "KedisData{" +
                "data=" + data +
                ", dataType=" + dataType +
                '}';
    }
}
