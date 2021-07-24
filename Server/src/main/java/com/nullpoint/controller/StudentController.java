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

import java.util.Arrays;
import java.util.List;
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

    /**
     * 用account_id作为已排除学生的Id，获取除排除学生外的所有该班级学生
     * @param map
     * @return
     */
    @RequestMapping(value = "getClassStudentListNotIn",method = RequestMethod.POST)
    public ModelAndView getClassStudentListNotIn(@RequestBody Map<String,Object> map){
        ModelAndView mv = MVUtils.getJsonMV();
        Integer class_id = (Integer) map.get("class_id");
        List<Integer> ids = (List<Integer>) map.get("ids");
//        mv.addObject("data",ids);
        mv.addObject("data",studentService.getClassStudentListNotIn(class_id,ids));
        mv.addObject("code",200);
        mv.addObject("msg","success");
        return mv;
    }

    /**
     * 根据宿舍Id获取宿舍学生
     * @param dormitory_id
     * @return
     */
    @RequestMapping(value = "getDormitoryStudentList",method = RequestMethod.GET)
    public ModelAndView getDormitoryStudentList(@RequestParam Integer dormitory_id){
        ModelAndView mv = MVUtils.getJsonMV();
        mv.addObject("data",studentService.getDormitoryStudentList(dormitory_id));
        mv.addObject("code",200);
        mv.addObject("msg","success");
        return mv;
    }


    /**
     * 修改宿舍号
     * dropIds 是从原有宿舍删除的ID
     * addIds 是从其他地方添加的ID
     * @param map
     * @return
     */
    @RequestMapping(value = "changeDormitory",method = RequestMethod.POST)
    public ModelAndView changeDormitory(@RequestBody Map<String,Object> map){
        ModelAndView mv = MVUtils.getJsonMV();
        Integer dormitory_id = (Integer) map.get("dormitory_id");
        List<Integer> dropIds = (List<Integer>) map.get("dropIds");
        List<Integer> addIds = (List<Integer>) map.get("addIds");
        if (dropIds.size()==0&&addIds.size()==0){
            mv.addObject("msg","不做修改");
        }else {
            studentService.changeDormitory(dormitory_id,dropIds,addIds);
            mv.addObject("msg","修改成功");
        }
        mv.addObject("code",200);
        return mv;
    }
}
