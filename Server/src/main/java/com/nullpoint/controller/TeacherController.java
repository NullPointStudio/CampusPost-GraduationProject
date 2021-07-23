package com.nullpoint.controller;

import com.github.pagehelper.PageInfo;
import com.nullpoint.domain.Teacher;
import com.nullpoint.interceptor.TokenInterceptor;
import com.nullpoint.service.AccountService;
import com.nullpoint.service.TeacherService;
import com.nullpoint.utils.MVUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    Logger logger = LoggerFactory.getLogger(TeacherController.class);
    @Autowired
    TeacherService teacherService;

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "getTeacherList",method = RequestMethod.GET)
    public ModelAndView getTeacherList(@RequestParam(defaultValue = "") String query,
                                        @RequestParam(defaultValue = "1") int pageNum,
                                       @RequestParam(defaultValue = "10") int pageSize,
                                       Model model){
        ModelAndView mv = MVUtils.getJsonMV();
        PageInfo<Teacher> pageInfo = new PageInfo<>(teacherService.findAllPages(query,pageNum,pageSize));
        mv.addObject("code",200);
        mv.addObject("pageInfo",pageInfo);
        mv.addObject("msg","查询成功");
        return mv;
    }

    @RequestMapping(value = "addTeacherByAdmin",method = RequestMethod.POST)
    public ModelAndView addTeacherByAdmin(@RequestBody Map<String,Object> map) throws Exception {
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

    @RequestMapping(value = "editTeacherByAdmin",method = RequestMethod.POST)
    public ModelAndView editTeacherByAdmin(@RequestBody Map<String,Object> map){
        ModelAndView mv = MVUtils.getJsonMV();
        String id = (String) map.get("id");
        String name = (String) map.get("name");
        String phone = (String) map.get("phone");
        String sex = (String) map.get("sex");
        Integer college_id = (Integer) map.get("select_college");
        teacherService.editAccountByPhone(id,name,phone,sex,college_id);
        mv.addObject("code",200);
        mv.addObject("msg","修改成功");
        return mv;
    }

    @RequestMapping(value = "deleteTeacher",method = RequestMethod.POST)
    public ModelAndView deleteTeacher(@RequestBody Map<String,Object> map){
        ModelAndView mv = MVUtils.getJsonMV();
        String id = (String) map.get("id");
        teacherService.deleteTeacher(id);
        mv.addObject("code",200);
        mv.addObject("msg","删除成功");
        return mv;
    }

    @RequestMapping(value = "getHeadTeacherList",method = RequestMethod.GET)
    public ModelAndView getHeadTeacherList(@RequestParam Integer college_id){
        ModelAndView mv = MVUtils.getJsonMV();
        mv.addObject("data",teacherService.getTeacherByCollege(college_id));
        mv.addObject("code",200);
        mv.addObject("msg","success");
        return mv;
    }



}
