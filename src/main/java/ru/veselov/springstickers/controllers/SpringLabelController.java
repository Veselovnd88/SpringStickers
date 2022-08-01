package ru.veselov.springstickers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.veselov.springstickers.exception.InterruptOperationException;
import ru.veselov.springstickers.model.DAOarticles;
import ru.veselov.springstickers.model.DTO;
import ru.veselov.springstickers.model.LabelFactory;
import ru.veselov.springstickers.model.LabelSticker;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping("/")
@SessionAttributes("map")//атрибут который хранится в течение всей сессии
public class SpringLabelController {
    private final DAOarticles daOarticles;
    private final List<LabelSticker> list;
    @Autowired
    public SpringLabelController(DAOarticles daOarticles) {
        this.daOarticles = daOarticles;
        list = daOarticles.index();
    }

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
        //List<LabelSticker> list =daOarticles.index();
        System.out.println(list);
        list.forEach(x-> System.out.println(x.getId()));
        model.addAttribute("list",list);//ниспадающий список вместо радиокнопок
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
        model.addAttribute("list",list);
        //для того чтобы он формировал форму через таймлиф нужно обязательно передавать сюда
        if(bindingResult.hasErrors()){
            System.out.println("Нашел ошибки");
            System.out.println(list);
            return "/index";//если поля не прошли валидацию - возвращает ту же страницу
            //тут должен был быть не редирект, а ссылка на первую страницу представления
            //теперь надо написать в таймлифе эти ошибки
        }
        map= (Map<Integer, LabelSticker>) model.getAttribute("map");
        int art= dto.getArt();
        LabelSticker receivedLabel =list.stream().filter(x->x.getId()==art).findAny()
                .orElse(new LabelSticker("Error","Error","Error","Error"));//FIXME - есть вдруг нулл то создавать
        LabelSticker lab = LabelFactory.getLabel(
                receivedLabel.getName(), receivedLabel.getRange(),
                receivedLabel.getPinout(), dto.getSerial());
                if(map!=null){
                map.put(dto.getPos(),lab);}
        model.addAttribute("map",map);//запись в модель - для таймлифа
        return "/index";
    }
    @PostMapping(params = "delete")
    //params - параметр который приходит с инпут сабмита в контроллер (name кнопки)
    public String delete(@ModelAttribute("dto") DTO dto,
                         Model model,
                         @ModelAttribute("map") Map<Integer, LabelSticker> map){
        map.remove(dto.getPos());
        model.addAttribute("list",list);
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
