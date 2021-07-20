package com.nullpoint.controller;

import com.nullpoint.service.AccountService;
import com.nullpoint.utils.CommonUtil;
import com.nullpoint.utils.MVUtils;
import com.nullpoint.utils.Md5TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.time.Duration;
import java.util.Map;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    /**
     * 操作redis的
     */
    @Autowired
    StringRedisTemplate redisTemplate;

    public String setRedisData(String username,String password){
        String token = Md5TokenGenerator.generate(username,password);
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(username, token);
        redisTemplate.expire(username, Duration.ofSeconds(CommonUtil.TOKEN_EXPIRE_TIME));

        operations.set(token,username);
        redisTemplate.expire(token,Duration.ofSeconds(CommonUtil.TOKEN_EXPIRE_TIME));

        return token;
    }

    @RequestMapping(value = "/loginByPhone",method = RequestMethod.POST)
    public ModelAndView loginByPhone(@RequestBody Map<String,Object> map){
        ModelAndView mv = MVUtils.getJsonMV();
        String phone = (String) map.get("phone");
        String password = (String) map.get("password");
        Integer userId = accountService.loginByPhone(phone,password);
        if (userId != null && !userId.equals(0) && userId != 0){
            String token = setRedisData(phone,password);
            mv.addObject("code",200);
            mv.addObject("msg","登录成功");
            mv.addObject("userId",userId);
            mv.addObject("token",token);
        }else {
            mv.addObject("code",400);
            mv.addObject("msg","用户名不存在或密码错误");
        }

        return mv;
    }

    @RequestMapping(value = "/adminLogin",method = RequestMethod.POST)
    public ModelAndView adminLogin(@RequestBody Map<String,Object> map){
        ModelAndView mv = MVUtils.getJsonMV();
        String userId = (String) map.get("username");
        String password = (String) map.get("password");
        if(userId.equals("100001")){
            doLogin(mv, userId, password);
        }else {
            mv.addObject("code",401);
            mv.addObject("msg","您并不是管理员，无法登录");
        }
        return mv;
    }

    private void doLogin(ModelAndView mv, String userId, String password) {
        if (accountService.login(userId,password)){
            String token = setRedisData(userId,password);
            mv.addObject("code",200);
            mv.addObject("msg","登录成功");
            mv.addObject("userId",userId);
            mv.addObject("token",token);
        }else {
            mv.addObject("code",400);
            mv.addObject("msg","用户名不存在或密码错误");
        }
    }

    @RequestMapping(value = "/test")
    public ModelAndView login(){
        ModelAndView mv = MVUtils.getJsonMV();
        mv.addObject("code",200);
        mv.addObject("msg","登录成功");
        mv.addObject("data",accountService.findAll());
        return mv;
    }
}
