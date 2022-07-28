package ru.veselov.springstickers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.veselov.springstickers.command.CommandExecutor;
import ru.veselov.springstickers.command.Operation;
import ru.veselov.springstickers.exception.InterruptOperationException;
import ru.veselov.springstickers.model.DTO;
import ru.veselov.springstickers.model.LabelSticker;

import java.io.*;
import java.nio.file.Files;
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
        //здесь как я понял можно испольщовать не отдельный объект, а обычную модель спринга
        Map<Integer,LabelSticker> map = controllerInt.getModel().getMap();
        //указываем аргументов модель - для передачи туда мапы для генерации списка добавленных
        System.out.println(map);
        model.addAttribute("map",map);
        return "/index";
    }

    @PostMapping(params = "place")//ModelAttribute - создает указанный объект и передает его в ThymeLeaf
    //для дальнейшего заполнения.Так как этикетка создается из фабричного метода, создавать ее спрингом не надо
    //нужно посто получить значения позиции и артикула
    public String getData(@ModelAttribute("dto") DTO dto) throws InterruptOperationException {
        //для того чтобы таймлиф передавал значение кнопок th:field должно идти вместе с th:value
        controllerInt.getModel().setArt(dto.getArt());
        controllerInt.getModel().setPos(dto.getPos());
        controllerInt.getModel().setSerial(dto.getSerial());
        System.out.println(dto.getTask());
        switch (dto.getTask()) {
            case "Разместить":
                CommandExecutor.execute(Operation.CHOOSE);
                // System.out.println(controllerInt.getModel().getMap());
                break;
            case "Удалить":
                System.out.println("я здесь");
                controllerInt.onDelete(dto.getPos());
                break;
        }
        return "redirect:/";
    }
    @PostMapping(params = "delete")
    //params - параметр который приходит с инпут сабмита в контроллер (name кнопки)
    public String delete(@ModelAttribute("dto") DTO dto){
        controllerInt.onDelete(dto.getPos());
        return "redirect:/";
    }
    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> download() throws IOException, InterruptOperationException {

        File generatedImage = controllerInt.getModel().save(null);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(generatedImage));

        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + generatedImage.getName())
                // Content-Type
                .contentType(MediaType.IMAGE_PNG)
                // Contet-Length
                .contentLength(generatedImage.length()) //
                .body(resource);
    }

}
