package com.nullpoint.controller;

import com.nullpoint.dao.AdminSecondMenuDao;
import com.nullpoint.dao.AdminThirdMenuDao;
import com.nullpoint.domain.AdminSecondMenu;
import com.nullpoint.domain.AdminThirdMenu;
import com.nullpoint.service.AdminFirstMenuService;
import com.nullpoint.service.AdminSecondMenuService;
import com.nullpoint.service.AdminThirdMenuService;
import com.nullpoint.utils.MVUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/adminMenu")
public class AdminMenuController {

    @Autowired
    AdminFirstMenuService adminFirstMenuService;

    @Autowired
    AdminSecondMenuService adminSecondMenuService;

    @Autowired
    AdminThirdMenuService adminThirdMenuService;

    @RequestMapping(value = "/getFirstMenu",method = RequestMethod.GET)
    public ModelAndView getFirstMenu(){
        ModelAndView mv = MVUtils.getJsonMV();
        mv.addObject("code",200);
        mv.addObject("data",adminFirstMenuService.findAll());
        mv.addObject("msg","success");
        return mv;
    }

    @RequestMapping(value = "/getSecondMenu",method = RequestMethod.GET)
    public ModelAndView getSecondMenu(@RequestParam String parentId){
        ModelAndView mv = MVUtils.getJsonMV();
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = null;
        for (AdminSecondMenu adminSecondMenu : adminSecondMenuService.findByParentId(parentId)) {
            map = new HashMap<>();
            map.put("menuId",adminSecondMenu.getMenuId());
            map.put("menuName",adminSecondMenu.getMenuName());
            map.put("menuIco",adminSecondMenu.getMenuIco());
            map.put("parentId",adminSecondMenu.getParentId());
            List<Map<String,Object>> childs = new ArrayList<>();
            for (AdminThirdMenu adminThirdMenu : adminThirdMenuService.findByParentId(adminSecondMenu.getMenuId() + "")) {
                Map<String,Object> child = new HashMap<>();
                child.put("menuId",adminThirdMenu.getMenuId());
                child.put("menuName",adminThirdMenu.getMenuName());
                child.put("menuIco",adminThirdMenu.getMenuIco());
                child.put("parentId",adminThirdMenu.getParentId());
                child.put("path",adminThirdMenu.getPath());
                childs.add(child);
            }
            map.put("childs",childs);
            list.add(map);
        }
        mv.addObject("code",200);
        mv.addObject("data",list);
        mv.addObject("msg","success");
        return mv;
    }

}
