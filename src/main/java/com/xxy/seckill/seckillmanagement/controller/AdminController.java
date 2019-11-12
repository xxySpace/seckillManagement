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
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/admin-role")
    public String adminRole(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "admin-role";
    }

    @RequestMapping("/adminRoleAdd")
    public String adminRoleAdd(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "admin-role-add";
    }

    @RequestMapping("/admin-permission")
    public String adminPermission(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "admin-permission";
    }

    @RequestMapping("/adminPermissionAdd")
    public String adminPermissionAdd(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "admin-permission-add";
    }

    @RequestMapping("/admin-list")
    public String adminList(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "admin-list";
    }
}