package pers.kedis.core.dto.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author kwsc98
 */
public enum DateTimeType {
    /**
     * EX (秒)
     * PX (毫秒)
     * TTL (毫秒TTL)
     */
    EX(1000), PX(1), TTL(0);

    @Getter
    final int pre;

    DateTimeType(int pre) {
        this.pre = pre;
    }


    public static DateTimeType getEnum(String str) {
        return Arrays.stream(DateTimeType.values()).filter(e -> e.name().equalsIgnoreCase(str)).findFirst().orElse(null);
    }
}
