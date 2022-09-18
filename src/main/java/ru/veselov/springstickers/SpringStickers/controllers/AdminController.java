package ru.veselov.springstickers.SpringStickers.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping()
    public String adminPAge(){
        return "admin/admin";
    }

    @GetMapping("/manage")
    public String manage(){
        return "admin/manage";
    }

    @GetMapping("/serials")
    public String showSerials(){
        return "admin/serials";
    }

}