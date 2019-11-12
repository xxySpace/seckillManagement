package com.xxy.seckill.seckillmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: LoginController
 * @Description: URL访问测试类
 * 由于做到前后端分离，所以未启动
 * @Author: 13688
 * @Date: 2019/5/19 20:04
 * @Version: v1.0
 **/
@Controller
@RequestMapping("/system")
public class SystemController {

    @RequestMapping("/system-base")
    public String systemBase(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "system-base";
    }

    @RequestMapping("/system-category")
    public String systemCategory(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "system-category";
    }

    @RequestMapping("/system-data")
    public String systemData(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "system-data";
    }

    @RequestMapping("/system-shielding")
    public String systemShielding(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "system-shielding";
    }

    @RequestMapping("/system-log")
    public String systemLog(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "system-log";
    }
}