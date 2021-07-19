package com.nullpoint.utils;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class MVUtils {
    public static ModelAndView getJsonMV(){
        return new ModelAndView(new MappingJackson2JsonView());
    }
}
