package ru.veselov.springstickers.SpringStickers.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
            return "redirect:/admin/manage";}
    }
    /*Выдача страницы редактирования артикула
    * По pathVariable определяется какой айди у артикула, получаем все данные из бд
    * и пишем в модель*/
    @GetMapping(value = "/edit/{id}",params = "edit")
    public String editForm(@PathVariable("id") int id,
                           Model model){
        LabelEntity label = labelService.findById(id);
        model.addAttribute("lbl",label);
        return "admin/edit";
    }
    /*Вызов метода Пэтч из формы передает id и заполненный объект Ентити
    * Если мы не меняем артикул - срабатывает валидатор -и не дает сохранить под тем же артикулом
    * Для этого забираем артикул переданного id, и проверяем, если он такой же как тот который приходит из формы
    * То ничего не делаем, если другой- то проверяем валидатором, чтобы не попасть на ошибку БД
    * При вызове метода апдейт сервиса - на указанный ентити ставится переданный айди
    * И через репозиторий происходит сохранение*/
    @PatchMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("lbl") @Valid LabelEntity labelEntity,
                       BindingResult bindingResult){
        LabelEntity current = labelService.findById(id);
        if(!current.getArticle().equals(labelEntity.getArticle())){
            labelValidator.validate(labelEntity,bindingResult);
        }
        if(bindingResult.hasErrors()){
            return "admin/edit";
        }
        labelService.update(id,labelEntity);
        return "redirect:/show";
    }



    @DeleteMapping(value = "/delete/{id}",params = "delete")
    public String delete(@PathVariable("id")int id){
        labelService.deleteById(id);
        return "redirect:/show";
    }



}