package com.nullpoint.controller;

import com.github.pagehelper.PageInfo;
import com.nullpoint.domain.Dormitory;
import com.nullpoint.service.DormitoryService;
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
@RequestMapping("/dormitory")
public class DormitoryController {

    @Autowired
    DormitoryService dormitoryService;

    @RequestMapping(value = "getDormitoryListList",method = RequestMethod.GET)
    public ModelAndView getDormitoryListList(@RequestParam(defaultValue = "") String query,
                                             @RequestParam(defaultValue = "1") int pageNum,
                                             @RequestParam(defaultValue = "10") int pageSize){
        ModelAndView mv = MVUtils.getJsonMV();
        PageInfo<Dormitory> pageInfo = new PageInfo<>(dormitoryService.findAllByQuery(query,pageNum,pageSize));
        mv.addObject("code",200);
        mv.addObject("msg","success");
        mv.addObject("pageInfo",pageInfo);
        return mv;
    }

    @RequestMapping(value = "getBuildings",method = RequestMethod.GET)
    public ModelAndView getBuildings(){
        ModelAndView mv = MVUtils.getJsonMV();
        mv.addObject("code",200);
        mv.addObject("msg","success");
        mv.addObject("data",dormitoryService.getBuildings());
        return mv;
    }

    @RequestMapping(value = "addDormitoryByAdmin",method = RequestMethod.POST)
    public ModelAndView addDormitoryByAdmin(@RequestBody Map<String,Object> map){
        ModelAndView mv = MVUtils.getJsonMV();
        int building_id = (int) map.get("building_id");
        String dormitory_name = (String) map.get("name");
        dormitoryService.addDormitoryByAdmin(building_id,dormitory_name);
        mv.addObject("code",200);
        mv.addObject("msg","添加成功");
        return mv;
    }

    @RequestMapping(value = "deleteDormitory",method = RequestMethod.POST)
    public ModelAndView deleteDormitory(@RequestBody Map<String,Object> map){
        ModelAndView mv = MVUtils.getJsonMV();
        int id = (int) map.get("id");
        dormitoryService.deleteDormitory(id);
        mv.addObject("code",200);
        mv.addObject("msg","删除成功");
        return mv;
    }

    @RequestMapping(value = "getDormitoryList",method = RequestMethod.GET)
    public ModelAndView getDormitoryList(@RequestParam Integer id){
        ModelAndView mv = MVUtils.getJsonMV();
        mv.addObject("code",200);
        mv.addObject("msg","success");
        mv.addObject("data",dormitoryService.findAllByBuildingId(id));
        return mv;
    }
}
