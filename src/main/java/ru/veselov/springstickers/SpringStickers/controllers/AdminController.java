package ru.veselov.springstickers.SpringStickers.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.veselov.springstickers.SpringStickers.model.LabelEntity;
import ru.veselov.springstickers.SpringStickers.services.LabelService;
import ru.veselov.springstickers.SpringStickers.util.LabelValidator;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final LabelValidator labelValidator;
    private final LabelService labelService;
    @Autowired
    public AdminController(LabelValidator labelValidator, LabelService labelService) {
        this.labelValidator = labelValidator;
        this.labelService = labelService;
    }

    @GetMapping()
    public String adminPage(){
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

    @GetMapping("/add")
    public String showAddForm(@ModelAttribute("label") LabelEntity labelEntity){
        return "admin/add";
    }
    @PostMapping("/add")
    public String addArticle(@ModelAttribute("label") @Valid LabelEntity labelEntity,
                             BindingResult bindingResult){

        labelValidator.validate(labelEntity,bindingResult);
        if(bindingResult.hasErrors()){
            return "admin/add";
        }
        else{
            labelService.save(labelEntity);
            return "redirect:admin/manage";}
    }
    @GetMapping(value = "/edit/{id}",params = "edit")
    public String edit(@PathVariable("id") int id, @ModelAttribute("label") @Valid LabelEntity labelEntity){
        return "admin/edit";
    }

    @DeleteMapping(value = "/delete/{id}",params = "delete")
    public String delete(@PathVariable("id")int id){
        System.out.println("Почему не удаляешь, скотина?");
        labelService.deleteById(id);
        return "redirect:/show";
    }



}