package pres.kedis.example.common.enums;

import lombok.Getter;

/**
 * @author kwsc98
 */

@Getter
public enum ResponseEnum {
    /**
     * SUCCESS
     * ERROR
     */
    SUCCESS("0000","Success"),
    ERROR("9999","Error");

    private final String code;

    private final String infoStr;

    ResponseEnum(String code,String infoStr){
        this.code = code;
        this.infoStr = infoStr;
    }

}
