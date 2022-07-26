package ru.veselov.springstickers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.veselov.springstickers.model.MainModelLabel;

@Controller
@RequestMapping("/")
public class SpringLabelController {
    private ControllerInt controllerInt;//контроллер который управляет моделью изображений

    @Autowired
    public SpringLabelController(ControllerInt controllerInt){
        this.controllerInt = controllerInt;
    }
    @GetMapping()
    public String index(){
        return "/index";
    }

    @PostMapping()
    public String getData(@ModelAttribute("labelModel")MainModelLabel labelModel){


        return "redirect:/";
    }

}
