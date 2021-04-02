package com.youchat.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: youchat-common
 * @description:
 * @author: lien6o
 * @create: 2018-08-15 16:12
 * 测试 Thymeleaf 相关
 **/
@Controller
@RequestMapping("/")
public class ThymeleafController {


    @RequestMapping("/test")
    public String get(Model model) {
        model.addAttribute("user", 2);
        return "/th";
    }
    @RequestMapping("/test2")
    @ResponseBody
    public String gete( ) {

        return "aaa";
    }
}
