package com.nullpoint.interceptor;

import com.nullpoint.exception.TokenException;
import com.nullpoint.utils.CommonUtil;
import com.nullpoint.utils.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

/**
 * 原文链接：https://blog.csdn.net/FindHuni/article/details/94451136
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    //鉴权失败后返回的HTTP错误码，默认为401
    private final int unauthorizedErrorCode = HttpServletResponse.SC_UNAUTHORIZED;

    /**
     * 存放用户名称和对应的key
     */
    public static final String USER_KEY = "USER_KEY";

    @Autowired
    StringRedisTemplate redisTemplate;

    Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //存放鉴权信息的Header名称，默认是Authorization
        String authorization = "Authorization";
        String token = request.getHeader(authorization);
        String username = "";
//        System.out.println("token:"+token);
        logger.info("token:" + token);
        if (token != null && token.length() != 0) {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            //从redis中根据键token来获取绑定的username
            username = operations.get(token);
            //判断username不为空的时候
            if (!TextUtils.isEmpty(username)) {
                String redisToken = operations.get(username);
                if (!TextUtils.isEmpty(redisToken) && redisToken.equals(token)) {
                    redisTemplate.expire(username, Duration.ofSeconds(CommonUtil.TOKEN_EXPIRE_TIME));
                    redisTemplate.expire(token, Duration.ofSeconds(CommonUtil.TOKEN_EXPIRE_TIME));
                }else {
                    redisTemplate.delete(token);
                    //您的账号已经在其他地方登录，请重新登录
                    //throw new TokenException("Your account is already logged in elsewhere, please log in again");
                    throw new TokenException("您的账号已经在其他地方登录，请重新登录");
                }
            } else {
                //长时间未操作，请重新登录
                //throw new TokenException("Long time no operation, please log in again");
                throw new TokenException("长时间未操作，请重新登录");
            }
        } else {
            //身份验证已过期，请重新登录
            //throw new TokenException("Authentication has expired, please log in again");
            throw new TokenException("身份验证已过期，请重新登录");
        }
        return true;
    }
}
