package ru.veselov.springstickers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.veselov.springstickers.command.CommandExecutor;
import ru.veselov.springstickers.command.Operation;
import ru.veselov.springstickers.exception.InterruptOperationException;
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
    public String getData(@ModelAttribute("dto") DTO dto) throws InterruptOperationException {
        //для того чтобы таймлиф передавал значение кнопок th:field должно идти вместе с th:value
        controllerInt.getModel().setArt(dto.getArt());
        controllerInt.getModel().setPos(dto.getPos());
        controllerInt.getModel().setSerial(dto.getSerial());
        System.out.println(controllerInt.getModel());
        System.out.println(controllerInt.getModel().getArt());
        System.out.println(dto.getArt()+"|||"+dto.getPos());
        if(dto.getTask().equals("Разместить")){
            CommandExecutor.execute(Operation.CHOOSE);
            dto.setMessage(String.format("Размещена позиция %s %s %s"//output to chosen source
                    ,dto.getPos(),
                    controllerInt.getModel().getMap().get(dto.getPos()).getName(),
                    controllerInt.getModel().getMap().get(dto.getPos()).getSerial()));
        }

        else if(dto.getTask().equals("Удалить")){
            controllerInt.onDelete();
        }
        else if(dto.getTask().equals("Сохранить")){
            controllerInt.onSave();
        }
        return "redirect:/";
    }





    @GetMapping("/rewrite")
    public String rewrite(){
        return "/rewrite";
    }

}
