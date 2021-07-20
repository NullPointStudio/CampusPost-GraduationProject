package com.nullpoint.controller;

import com.nullpoint.service.AccountService;
import com.nullpoint.service.TeacherService;
import com.nullpoint.utils.MVUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "getTeacherList",method = RequestMethod.GET)
    public ModelAndView getTeacherList(){
        ModelAndView mv = MVUtils.getJsonMV();
        mv.addObject("data",teacherService.findAll());
        return mv;
    }

    @RequestMapping(value = "addTeacherByAdmin",method = RequestMethod.POST)
    public ModelAndView addTeacher(@RequestBody Map<String,Object> map) throws Exception {
        ModelAndView mv = MVUtils.getJsonMV();
        String name = (String) map.get("name");
        String phone = (String) map.get("phone");
        String sex = (String) map.get("sex");
        int select_college = (int) map.get("select_college");
        teacherService.addAccountByPhone(name,phone,sex,select_college);
        mv.addObject("code",200);
        mv.addObject("msg","添加成功");
        return mv;
    }


}
