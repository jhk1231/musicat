package com.example.musicat.controller;

import com.example.musicat.service.etc.DailyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StatisticsController {

    @Autowired
    DailyServiceImpl dailyService;

    @GetMapping("/daily")
    public ModelAndView managerDaily(ModelAndView mv) {
        mv.setViewName("view/home/viewManagerTemplate");
        mv.addObject("managerContent", "fragments/StatisticsDailyContent");

        return mv;
    }
    @GetMapping("/total")
    public ModelAndView managerTotal(ModelAndView mv) {
        mv.setViewName("view/home/viewManagerTemplate");
        mv.addObject("managerContent", "fragments/StatisticsTotalContent");

        return mv;
    }
}
