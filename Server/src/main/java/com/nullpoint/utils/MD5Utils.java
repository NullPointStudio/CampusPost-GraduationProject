package com.nullpoint.utils;

import org.springframework.util.Base64Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    public static String getMd5val(String val) throws NoSuchAlgorithmException {
        String salt = "li";
        //获取一个Md5实例
        MessageDigest digest = MessageDigest.getInstance("MD5");
        //使用这个MD5生成值
        byte[] buf = digest.digest((val+salt).getBytes());
        return new String(Base64Utils.encode(buf));
    }
}
