package pers.kedis.core.common.utils;

import pers.kedis.core.dto.enums.DateTimeType;

/**
 * @author kwsc98
 */
public class DateTimeUtils {

    public static long getDateTime(DateTimeType dateTimeType, long timeOut) {
        if (dateTimeType == null || timeOut == -1) {
            return -1;
        }
        if (dateTimeType == DateTimeType.TTL) {
            return timeOut;
        }
        return System.currentTimeMillis() + (dateTimeType.getPre() * timeOut);
    }

}