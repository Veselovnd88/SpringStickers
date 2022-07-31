package ru.veselov.springstickers.controllers;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.veselov.springstickers.exception.InterruptOperationException;
import ru.veselov.springstickers.model.DTO;
import ru.veselov.springstickers.model.LabelFactory;
import ru.veselov.springstickers.model.LabelSticker;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping("/")
@SessionAttributes("map")//атрибут который хранится в течение всей сессии
public class SpringLabelController {
    @ModelAttribute("map")//получение атрибута, без этого метода бросает ошибку
    public Map<Integer,LabelSticker> getMap(){
        return new TreeMap<>();
    }
    @GetMapping()
    //для того чтобы все поля таймлифа были валидные - добавили в первые гет метод объект дто
    public String index(@ModelAttribute("dto") DTO dto, Model model,
                        @ModelAttribute("map") Map<Integer, LabelSticker> map
                        ) {
        //здесь как я понял можно испольщовать не отдельный объект, а обычную модель спринга
        //указываем аргументов модель - для передачи туда мапы для генерации списка добавленных
        model.addAttribute("map",map);
        return "/index";
    }

    @PostMapping(params = "place")//ModelAttribute - создает указанный объект и передает его в ThymeLeaf
    //для дальнейшего заполнения.
    /*Метод через пост запрос забирает значения полей, срабатывает при клике на сабмит с именем
    * place. Далее исходя из значение позиции выбирает артикул и добавляет в мапу*/
    public String getData(@ModelAttribute("dto") @Valid DTO dto,
                          BindingResult bindingResult, Model model,
                          @ModelAttribute("map") Map<Integer,LabelSticker> map) {//биндин резалт передает ошибки от валидации
        //для того чтобы таймлиф передавал значение кнопок th:field должно идти вместе с th:value
        if(bindingResult.hasErrors()){
            System.out.println("Нашел ошибки");
            return "/index";//если поля не прошли валидацию - возвращает ту же страницу
            //тут должен был быть не редирект, а ссылка на первую страницу представления
            //теперь надо написать в таймлифе эти ошибки
        }
        map= (Map<Integer, LabelSticker>) model.getAttribute("map");
        int art= dto.getArt();
                String name="";
                String range="";
                String pinout="";
                if(art==1) {
                    name = "ADZ-SML-20.11";
                    range = "1 MPa    0.5...5V";
                    pinout = "1+, 2-, 3 Out, 4 Gehause";}
                if(art==2) {
                    name = "ADZ-SML-20.11";
                    range = "10 bar    0.5...5V";
                    pinout = "1+, 2-, 3 Out, 4 Gehause";
                }
                if (art==3) {
                    name = "ADZ-SML-20.11";
                    range = "6 bar    0.5...5V";
                    pinout = "3+, 2-, 1 Out";
                }
                if (art==4) {
                    name = "ADZ-SML-20.11";
                    range = "0.6 MPa    0.5...5V";
                    pinout = "1+, 2-, 3 Out, 4 Gehause";
                }
        LabelSticker lab = LabelFactory.getLabel(name,range,pinout,dto.getSerial());
                if(map!=null){
                map.put(dto.getPos(),lab);}
        model.addAttribute("map",map);//запись в модель - для таймлифа
        return "/index";
    }
    @PostMapping(params = "delete")
    //params - параметр который приходит с инпут сабмита в контроллер (name кнопки)
    public String delete(@ModelAttribute("dto") DTO dto,
                         @ModelAttribute("map") Map<Integer, LabelSticker> map){
        map.remove(dto.getPos());
        return "/index";
    }
    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> download(Model model,
              @ModelAttribute("map") Map<Integer,LabelSticker> map) throws IOException, InterruptOperationException {
        ControllerInt controllerInt= new LabelController();
        controllerInt.getModel().setMap((Map<Integer, LabelSticker>) model.getAttribute("map"));
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
