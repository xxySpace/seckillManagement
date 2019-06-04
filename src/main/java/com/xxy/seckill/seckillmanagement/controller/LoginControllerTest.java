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
public class LoginControllerTest {

    @RequestMapping("/")
    public String index(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "login";
    }

    @RequestMapping("/home")
    public String home(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "home";
    }

    @RequestMapping("/qryitem")
    public String qryitem(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "qryitem";
    }

    @RequestMapping("/listitem")
    public String listitem(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "listitem";
    }

    @RequestMapping("/itemadd")
    public String itemadd(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "itemadd";
    }

    @RequestMapping("/getitem")
    public String getitem(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "getitem";
    }

    @RequestMapping("/admin-role")
    public String adminRole(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "admin-role";
    }

    @RequestMapping("/admin-permission")
    public String adminPermission(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "admin-permission";
    }

    @RequestMapping("/admin-list")
    public String adminList(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "admin-list";
    }

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