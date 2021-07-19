package com.nullpoint.utils;

public final class CommonUtil {

    /**
     * redis存储token设置的过期时间，1天
     */
    public static final Integer TOKEN_EXPIRE_TIME = 60 * 60 * 24;

    /**
     * 设置可以重置token过期时间的时间界限
     */
//    public static final Integer TOKEN_RESET_TIME = 1000 * 100;
    public static final Integer TOKEN_RESET_TIME = 60 * 60 * 24;
}
