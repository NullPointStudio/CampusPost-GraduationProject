package com.nullpoint.utils;


import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class Md5TokenGenerator{

    public static String generate(String... strings) {
        long timestamp = System.currentTimeMillis();
        String tokenMeta = "";
        for (String s : strings) {
            tokenMeta = tokenMeta + s;
        }
        tokenMeta = tokenMeta + timestamp;
        return DigestUtils.md5DigestAsHex(tokenMeta.getBytes());
    }
}
