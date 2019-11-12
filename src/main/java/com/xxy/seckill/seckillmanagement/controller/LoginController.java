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
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("")
    public String index(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "login";
    }

    @RequestMapping("/register")
    public String register(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "register";
    }

    @RequestMapping("/home")
    public String home(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "home";
    }
}