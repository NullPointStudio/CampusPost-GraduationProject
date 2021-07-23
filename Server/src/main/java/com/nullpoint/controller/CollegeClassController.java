package com.nullpoint.controller;

import com.github.pagehelper.PageInfo;
import com.nullpoint.domain.CollegeClass;
import com.nullpoint.service.CollegeClassService;
import com.nullpoint.utils.MVUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/collegeClass")
public class CollegeClassController {

    @Autowired
    CollegeClassService collegeClassService;

    @RequestMapping(value = "getCollegeClassList",method = RequestMethod.GET)
    public ModelAndView getCollegeClassList(@RequestParam(defaultValue = "") String query,
                                            @RequestParam(defaultValue = "1") int pageNum,
                                            @RequestParam(defaultValue = "10") int pageSize){
        ModelAndView mv = MVUtils.getJsonMV();
        PageInfo<CollegeClass> pageInfo = new PageInfo<>(collegeClassService.getCollegeClassList(query,pageNum,pageSize));
        mv.addObject("code",200);
        mv.addObject("msg","success");
        mv.addObject("pageInfo",pageInfo);
        return mv;
    }

    @RequestMapping(value = "editClass",method = RequestMethod.POST)
    public ModelAndView editClass(@RequestBody Map<String,Object> map){
        Integer headTeacherId = (Integer) map.get("headteacher_id");
        Integer monitorId = (Integer) map.get("monitor_id");
        Integer classId = (Integer) map.get("class_id");
        ModelAndView mv = MVUtils.getJsonMV();
        collegeClassService.editClassTeacherAndMonitor(headTeacherId,monitorId,classId);
        mv.addObject("code",200);
        mv.addObject("msg","修改成功");
        return mv;
    }

    @RequestMapping(value = "addClass",method = RequestMethod.POST)
    public ModelAndView addClass(@RequestBody Map<String,Object> map){
        String name = (String) map.get("name");
        Integer college_id = (Integer) map.get("college_id");
        Integer headteacher_id = (Integer) map.get("headteacher_id");
        ModelAndView mv = MVUtils.getJsonMV();
        collegeClassService.addClass(name,college_id,headteacher_id);
        mv.addObject("code",200);
        mv.addObject("msg","添加成功");
        return mv;
    }

    @RequestMapping(value = "deleteClass",method = RequestMethod.POST)
    public ModelAndView deleteClass(@RequestBody Map<String,Object> map){
        Integer id = (Integer) map.get("id");
        ModelAndView mv = MVUtils.getJsonMV();
        collegeClassService.deleteClass(id);
        mv.addObject("code",200);
        mv.addObject("msg","删除成功");
        return mv;
    }
}
