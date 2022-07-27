package ru.veselov.springstickers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.veselov.springstickers.model.DTO;
import ru.veselov.springstickers.model.LabelSticker;
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
    //для того чтобы все поля таймлифа были валидные - добавили в первые гет метод объект дто
    public String index(@ModelAttribute("dto") DTO dto){



        return "/index";
    }

    @PostMapping()//ModelAttribute - создает указанный объект и передает его в ThymeLeaf
    //для дальнейшего заполнения.Так как этикетка создается из фабричного метода, создавать ее спрингом не надо
    //нужно посто получить значения позиции и артикула
    public String getData(@ModelAttribute("dto") DTO dto){
        //для того чтобы таймлиф передавал значение кнопок th:field должно идти вместе с th:value
        controllerInt.getModel().setArt(dto.getArt());
        controllerInt.getModel().setPos(dto.getPos());
        if(dto.getTask().equals("Разместить")){

        }
        else if(dto.getTask().equals("Удалить")){
            controllerInt.onDelete();
        }


        return "redirect:/";
    }
    @GetMapping("/rewrite")
    public String rewrite(){
        return "redirect:/rewrite";
    }

}
