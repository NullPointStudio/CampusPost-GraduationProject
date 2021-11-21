package com.nullpoint.controller;

import com.github.pagehelper.PageInfo;
import com.nullpoint.domain.DormitoryDevices;
import com.nullpoint.service.DormitoryDevicesService;
import com.nullpoint.utils.MVUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/device")
@Slf4j
public class DeviceController {

    @Autowired
    DormitoryDevicesService dormitoryDevicesService;

    @RequestMapping(value = "getDormitoryDevices", method = RequestMethod.GET)
    public ModelAndView getDormitoryDevices(@RequestParam(defaultValue = "") String query,
                                            @RequestParam(defaultValue = "1") int pageNum,
                                            @RequestParam(defaultValue = "10") int pageSize) {
        ModelAndView mv = MVUtils.getJsonMV();
        PageInfo<DormitoryDevices> pageInfo = new PageInfo<>(dormitoryDevicesService.findByQuery(query, pageNum, pageSize));
        mv.addObject("pageInfo", pageInfo);
        mv.addObject("msg", "success");
        mv.addObject("code", 200);
        return mv;
    }
}
