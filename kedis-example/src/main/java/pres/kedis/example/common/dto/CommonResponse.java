package pres.kedis.example.common.dto;

import lombok.Getter;
import pres.kedis.example.common.enums.ResponseEnum;

/**
 * @author kwsc98
 */
@Getter
public class CommonResponse {

    private String code;

    private String infoStr;

    private Object resData;

    public static CommonResponse build(){
        return new CommonResponse();
    }

    public static CommonResponse build(ResponseEnum responseEnum){
        return CommonResponse.build().setCode(responseEnum.getCode()).setInfoStr(responseEnum.getInfoStr());
    }

    public CommonResponse setCode(String code) {
        this.code = code;
        return this;
    }

    public CommonResponse setInfoStr(String infoStr) {
        this.infoStr = infoStr;
        return this;
    }

    public CommonResponse setResData(Object resData) {
        this.resData = resData;
        return this;
    }
}
