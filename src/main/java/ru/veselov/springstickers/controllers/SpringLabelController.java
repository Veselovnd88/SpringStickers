package ru.veselov.springstickers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.veselov.springstickers.command.CommandExecutor;
import ru.veselov.springstickers.command.Operation;
import ru.veselov.springstickers.exception.InterruptOperationException;
import ru.veselov.springstickers.model.DTO;
import ru.veselov.springstickers.model.LabelSticker;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.nio.file.Files;
import java.util.Map;

@Controller
@Scope("session")
@RequestMapping("/")

public class SpringLabelController {
    private ControllerInt controllerInt= new LabelController();//сюда все равно кладутся разные, при каждом новом запросе
    @GetMapping()
    //для того чтобы все поля таймлифа были валидные - добавили в первые гет метод объект дто
    public String index(@ModelAttribute("dto") DTO dto, Model model,
                        HttpSession session
                        ) {

        //session.setAttribute("controller",controllerInt);
        //LabelController controllerInt = (LabelController) model.getAttribute("controller");
        //здесь как я понял можно испольщовать не отдельный объект, а обычную модель спринга
        Map<Integer,LabelSticker> map = controllerInt.getModel().getMap();
        //указываем аргументов модель - для передачи туда мапы для генерации списка добавленных
        System.out.println(map);
        model.addAttribute("map",map);
        System.out.println("GET: "+ controllerInt);
        return "/index";
    }

    @PostMapping(params = "place")//ModelAttribute - создает указанный объект и передает его в ThymeLeaf
    //для дальнейшего заполнения.Так как этикетка создается из фабричного метода, создавать ее спрингом не надо
    //нужно посто получить значения позиции и артикула
    public String getData(@ModelAttribute("dto") @Valid  DTO dto,Model model,
                          HttpSession session,
                          BindingResult bindingResult) throws InterruptOperationException {//биндин резалт передает ошибки от валидации
        //для того чтобы таймлиф передавал значение кнопок th:field должно идти вместе с th:value
        if(bindingResult.hasErrors()){
            System.out.println("Нашел ошибки");
            return "/index";//если поля не прошли валидацию - возвращает ту же страницу
            //тут должен был быть не редирект, а ссылка на первую страницу представления
            //теперь надо написать в таймлифе эти ошибки
        }
        //ControllerInt controllerInt= (ControllerInt) session.getAttribute("controller");
        System.out.println("POST: " +controllerInt);
        controllerInt.getModel().setArt(dto.getArt());
        controllerInt.getModel().setPos(dto.getPos());
        controllerInt.getModel().setSerial(dto.getSerial());
        System.out.println(dto.getTask());
        switch (dto.getTask()) {
            case "Разместить":
                CommandExecutor.execute(Operation.CHOOSE);
                model.addAttribute("map",controllerInt.getModel().getMap());
                System.out.println(model.getAttribute("map"));
                break;
            case "Удалить":
                System.out.println("я здесь");
                break;
        }
        return "/index";
    }
    @PostMapping(params = "delete")
    //params - параметр который приходит с инпут сабмита в контроллер (name кнопки)
    public String delete(@ModelAttribute("dto") DTO dto){
        controllerInt.onDelete(dto.getPos());
        return "/index";
    }
    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> download(Model model) throws IOException, InterruptOperationException {
       File generatedImage = controllerInt.getModel().save(null);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(generatedImage));
        model.addAttribute("filename",generatedImage.getName());
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
