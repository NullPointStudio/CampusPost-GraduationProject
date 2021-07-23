package com.nullpoint.controller;

import com.github.pagehelper.PageInfo;
import com.nullpoint.domain.Student;
import com.nullpoint.domain.Teacher;
import com.nullpoint.service.AccountService;
import com.nullpoint.service.StudentService;
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
@RequestMapping("/student")
public class StudentController {
    Logger logger = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    StudentService studentService;

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "getStudentList",method = RequestMethod.GET)
    public ModelAndView getTeacherList(@RequestParam(defaultValue = "") String query,
                                        @RequestParam(defaultValue = "1") int pageNum,
                                       @RequestParam(defaultValue = "10") int pageSize){
        ModelAndView mv = MVUtils.getJsonMV();
        PageInfo<Student> pageInfo = new PageInfo<>(studentService.findAllPages(query,pageNum,pageSize));
        mv.addObject("code",200);
        mv.addObject("pageInfo",pageInfo);
        mv.addObject("msg","查询成功");
        return mv;
    }

    @RequestMapping(value = "addStudentByAdmin",method = RequestMethod.POST)
    public ModelAndView addStudentByAdmin(@RequestBody Map<String,Object> map) throws Exception {
        ModelAndView mv = MVUtils.getJsonMV();
        String name = (String) map.get("name");
        String phone = (String) map.get("phone");
        String sex = (String) map.get("sex");
        int select_college = (int) map.get("select_college");
        studentService.addAccountByPhone(name,phone,sex,select_college);
        mv.addObject("code",200);
        mv.addObject("msg","添加成功");
        return mv;
    }

    @RequestMapping(value = "editStudentByAdmin",method = RequestMethod.POST)
    public ModelAndView editStudentByAdmin(@RequestBody Map<String,Object> map){
        ModelAndView mv = MVUtils.getJsonMV();
        String id = (String) map.get("id");
        String name = (String) map.get("name");
        String phone = (String) map.get("phone");
        String sex = (String) map.get("sex");
        Integer college_id = (Integer) map.get("select_college");
        studentService.editAccountByPhone(id,name,phone,sex,college_id);
        mv.addObject("code",200);
        mv.addObject("msg","修改成功");
        return mv;
    }

    @RequestMapping(value = "deleteStudent",method = RequestMethod.POST)
    public ModelAndView deleteStudent(@RequestBody Map<String,Object> map){
        ModelAndView mv = MVUtils.getJsonMV();
        String id = (String) map.get("id");
        studentService.deleteStudent(id);
        mv.addObject("code",200);
        mv.addObject("msg","删除成功");
        return mv;
    }
    @RequestMapping(value = "getClassStudentList",method = RequestMethod.GET)
    public ModelAndView getClassStudentList(@RequestParam Integer class_id){
        ModelAndView mv = MVUtils.getJsonMV();
        mv.addObject("data",studentService.getClassStudentList(class_id));
        mv.addObject("code",200);
        mv.addObject("msg","success");
        return mv;
    }

}
