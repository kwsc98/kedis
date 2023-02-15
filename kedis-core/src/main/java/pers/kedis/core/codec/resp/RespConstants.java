package pers.kedis.core.codec.resp;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author kwsc98
 */
public class RespConstants {

    public static final Charset ASCII = StandardCharsets.US_ASCII;
    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    public static final byte DOLLAR_BYTE = '$';
    public static final byte ASTERISK_BYTE = '*';
    public static final byte PLUS_BYTE = '+';
    public static final byte MINUS_BYTE = '-';
    public static final byte COLON_BYTE = ':';

    public static final String NULL_STRING = null;
    public static final String EMPTY_STRING = "";
    public static final Long ZERO = 0L;
    public static final Long NEGATIVE_ONE = -1L;
    public static final byte CR = (byte) '\r';
    public static final byte LF = (byte) '\n';
    public static final byte[] CRLF = "\r\n".getBytes(ASCII);


}
