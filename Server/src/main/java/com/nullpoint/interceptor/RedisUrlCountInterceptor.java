package com.nullpoint.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 用redis记录请求次数的拦截器
 */
@Component
public class RedisUrlCountInterceptor implements HandlerInterceptor {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求uri
        String uri =  request.getRequestURI();

        //用StringRedisTemplate来操作redis
        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        //默认每次访问这个uri，计数+1
        operations.increment(uri);
        return true;
    }
}
