package com.nullpoint.controller;

import com.nullpoint.exception.TokenException;
import com.nullpoint.utils.MVUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 全局异常处理，返回json数据
     * @param request
     * @param e         捕获的异常
     * @return          用ModelAndView返回json
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) {
        ModelAndView mv = MVUtils.getJsonMV();
        mv.addObject("msg", e.getMessage());
        if (e instanceof NoHandlerFoundException) {
            //后端没有请求地址时
            mv.addObject("code", 404);
        } else if (e instanceof TokenException){
            //抛出token验证时异常
            mv.addObject("code", 401);
        }else {
            //其他异常
            mv.addObject("code", 500);
        }
        mv.addObject("path", request.getRequestURI());
        return mv;
    }
}
