package pers.kedis.core.codec.resp;

import lombok.Getter;

/**
 * @author kwsc98
 */
@Getter
public class RespData<T> {
    private final T data;
    RespType respType;

    public RespData(T data, RespType respType) {
        this.data = data;
        this.respType = respType;
    }

}
