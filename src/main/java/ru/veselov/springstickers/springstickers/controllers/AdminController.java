package ru.veselov.springstickers.springstickers.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.veselov.springstickers.springstickers.dto.LabelDTO;
import ru.veselov.springstickers.springstickers.entity.LabelEntity;
import ru.veselov.springstickers.springstickers.services.LabelService;
import ru.veselov.springstickers.springstickers.services.SerialService;
import ru.veselov.springstickers.springstickers.util.LabelValidator;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final LabelValidator labelValidator;
    private final LabelService labelService;
    private final SerialService serialService;

    private final ModelMapper modelMapper;
    @Autowired
    public AdminController(LabelValidator labelValidator, LabelService labelService, SerialService serialService, ModelMapper modelMapper) {
        this.labelValidator = labelValidator;
        this.labelService = labelService;
        this.serialService = serialService;
        this.modelMapper = modelMapper;
    }
    /*Метод GET по адресу /admin возвращает view с перечнем доступных операций*/
    @GetMapping()
    public String adminPage(){
        return "admin/admin";
    }
    /*Метод GET по адресу /admin/manage возвращает view с операциями управления записями артикулов
    * Удаление и редактирование*/
    @GetMapping("/manage")
    public String manage(){
        return "admin/manage";
    }
    /*Метод GET по адресу /admin/serials возвращает view со списком серийных номеров
    * Еще не реализовано TODO*/
    @GetMapping("/serials")
    public String showSerials(Model model, @RequestParam(value = "page",required = false) Integer page,
                              @RequestParam(value = "items_per_page",required = false) Integer items_per_page){
        String returnPage = "admin/serials";
        if(page==null || items_per_page==null){
            page=0;
            items_per_page=10;

        }
        Pageable pageable= PageRequest.of(page,items_per_page);
        int max_page = serialService.getMaxPage(pageable);
        model.addAttribute("list",serialService.findAll(pageable));
        model.addAttribute("max_page", max_page);
        model.addAttribute("page",page);
        model.addAttribute("items_per_page",items_per_page);
        return returnPage;
    }
    /*Метод GET по адресу /admin/add возвращает view с формой заполнения полей требуемого артикула
    * ModelAttribute передается как пустой объект и заполняется данными с формы для передачи методом POST */
    @GetMapping("/add")
    public String showAddForm(@ModelAttribute("label") LabelDTO labelDTO){
        return "admin/add";
    }
    /*Метод POST принимает объект ModelAttribute
    * Далее проходит валидация полей с помощью Валидатора Гибернейта
    * И валидация артикула с помощью отдельного класса валидатора
    * Ошибки пишутся в BindingResult
    * Если есть ошибки возвращается обратно форма
    * Если всё в порядке - пишется в бд*/
    @PostMapping("/add")
    public String addArticle(@ModelAttribute("label") @Valid LabelDTO labelDTO,
                             BindingResult bindingResult){
        labelValidator.validate(convertToLabel(labelDTO),bindingResult);
        if(bindingResult.hasErrors()){
            return "admin/add";
        }
        else{
            labelService.save(convertToLabel(labelDTO));
            return "redirect:/admin/manage";}
    }

    private LabelEntity convertToLabel(LabelDTO labelDTO){
        return modelMapper.map(labelDTO, LabelEntity.class);
    }

    /*Метод GET по адресу admin/edit{id} с параметров edit
    * Возвращает страницу редактирования артикула
    * По pathVariable определяется какой айди у артикула, получаем все данные из бд
    * и пишем в модель для заполнения полей*/
    @GetMapping(value = "/edit/{id}",params = "edit")
    public String editForm(@PathVariable("id") int id, Model model){
        LabelEntity label = labelService.findById(id);
        model.addAttribute("lbl",label);
        return "admin/edit";
    }
    private LabelDTO convertToLabelDTO(LabelEntity labelEntity){
        return modelMapper.map(labelEntity, LabelDTO.class);
    }


    /*Метод PATCH по адресу admin/edit/{id}
    * из формы передает id и заполненный объект LabelEntity
    * Если мы не меняем артикул - срабатывает валидатор -и не дает сохранить под тем же артикулом
    * Для этого забираем артикул переданного id, и проверяем, если он такой же как тот который приходит из формы
    * То ничего не делаем, если другой- то проверяем валидатором, чтобы не попасть на ошибку БД
    * При вызове метода апдейт сервиса - на указанный ентити ставится переданный айди
    * И через репозиторий происходит сохранение*/
    @PatchMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("lbl") @Valid LabelDTO labelDTO,
                       BindingResult bindingResult){
        LabelEntity current = labelService.findById(id);
        if(!current.getArticle().equals(labelDTO.getArticle())){
            labelValidator.validate(labelDTO,bindingResult);
        }
        if(bindingResult.hasErrors()){
            return "admin/edit";
        }
        labelService.update(id,modelMapper.map(labelDTO, LabelEntity.class));
        return "redirect:/show";
    }


    /*Метод DELETE по адресу admin/delete{id} с параметром delete
    * Происходит удаление элемента по id
    * */
    @DeleteMapping(value = "/delete/{id}",params = "delete")
    public String delete(@PathVariable("id")int id){
        labelService.deleteById(id);
        return "redirect:/show";
    }

}