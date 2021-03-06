package com.kuretru.api.common.configuration;

/**
 * @author 呉真 Kuretru < kuretru@gmail.com >
 */
public class GeneralConstants {

    /**
     * 存储Key-Value键值对时使用的分隔符
     */
    public static final String KEY_VALUE_SEPARATOR = ":";

    /**
     * 不同项之间的分隔符，分割时使用
     */
    public static final String ITEMS_SEPARATOR = "\\|";

    /**
     * 不同项之间的分隔符，合成时使用
     */
    public static final String ITEMS_RAW_SEPARATOR = "|";

    /**
     * Redis存储AccessToken时使用的key
     */
    public static final String ACCESS_TOKEN_KEY = ".ACCESS_TOKEN#";

    /**
     * AccessToken过期时间
     */
    public static final Integer ACCESS_TOKEN_EXPIRES_MINUTE = 480;

    /**
     * Access Token在请求头中存放的Key
     */
    public static final String ACCESS_TOKEN_HEADER = "Access-Token";

    /**
     * 测试用万能Token
     */
    public static final String MAGIC_TOKEN = "kuretru742";

}
