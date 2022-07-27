package ru.veselov.springstickers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.veselov.springstickers.command.CommandExecutor;
import ru.veselov.springstickers.command.Operation;
import ru.veselov.springstickers.exception.InterruptOperationException;
import ru.veselov.springstickers.model.DTO;
import ru.veselov.springstickers.model.LabelSticker;

import java.util.Map;

@Controller
@RequestMapping("/")
public class SpringLabelController {
    private final ControllerInt controllerInt;//контроллер который управляет моделью изображений

    @Autowired
    public SpringLabelController(ControllerInt controllerInt){
        this.controllerInt = controllerInt;
    }
    @GetMapping()
    //для того чтобы все поля таймлифа были валидные - добавили в первые гет метод объект дто
    public String index(@ModelAttribute("dto") DTO dto, Model model){
        Map<Integer,LabelSticker> map = controllerInt.getModel().getMap();
        System.out.println(map);
        model.addAttribute("map",map);
        if(!map.isEmpty()){
            StringBuilder sb = new StringBuilder("Размещены позиции");
            for(Map.Entry<Integer,LabelSticker> entry: map.entrySet()){
                sb.append(String.format("Позиция %d Датчик %s на %s с номером %s",
                        entry.getKey(),entry.getValue().getName(),
                        entry.getValue().getRange(),
                        entry.getValue().getSerial()));
            }
        dto.setMessage(sb.toString());}
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
        switch (dto.getTask()) {
            case "Разместить":
                CommandExecutor.execute(Operation.CHOOSE);
                // System.out.println(controllerInt.getModel().getMap());
                break;
            case "Удалить":
                controllerInt.onDelete();
                break;
            case "Сохранить":
                controllerInt.onSave();
                break;
        }
        return "redirect:/";
    }





    @GetMapping("/rewrite")
    public String rewrite(){
        return "/rewrite";
    }

}
