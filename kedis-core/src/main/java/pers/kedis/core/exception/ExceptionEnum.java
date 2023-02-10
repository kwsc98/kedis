package pers.kedis.core.exception;

import pers.kedis.core.common.JsonUtils;

import java.util.Objects;

/**
 * @author kwsc98
 */

public enum ExceptionEnum implements ExceptionInfo {

    /**
     * ERROR
     */
    ERROR("9999", "Exception"),
    RESOURCES_NOT_EXIST("9998", "Resources Not Exist");

    public final String code;

    public final String infoStr;

    ExceptionEnum(String code, String infoStr) {
        this.code = code;
        this.infoStr = infoStr;
    }

    @Override
    public String getExceptionInfoStr() {
        try {
            return JsonUtils.writeValueAsString(Info.build(this));
        } catch (Exception e) {
            return "{}";
        }
    }

    static class Info {
        public String code;

        public String infoStr;

        public static Info build(ExceptionEnum exceptionEnum) {
            Info info = new Info();
            if (Objects.nonNull(exceptionEnum)) {
                info.code = exceptionEnum.code;
                info.infoStr = exceptionEnum.infoStr;
            }
            return info;
        }
    }


    public static void main(String[] args) {
        System.out.println(ExceptionEnum.ERROR.getExceptionInfoStr());
        ;
    }
}
