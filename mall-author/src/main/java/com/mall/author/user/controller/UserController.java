package com.mall.author.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    
    @RequestMapping("/queryLogin")
    public String queryLogin() {return "/login";}

    @RequestMapping("/queryHome")
    public String queryHome() {
        return "/home";
    }
}
