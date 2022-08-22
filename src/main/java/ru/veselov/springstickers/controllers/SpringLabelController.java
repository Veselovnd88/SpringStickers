package ru.veselov.springstickers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.veselov.springstickers.exception.InterruptOperationException;
import ru.veselov.springstickers.model.DAOarticles;
import ru.veselov.springstickers.model.DTO;
import ru.veselov.springstickers.model.LabelFactory;
import ru.veselov.springstickers.model.LabelSticker;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/")
@SessionAttributes("map")//атрибут который хранится в течение всей сессии
public class SpringLabelController {
    private final DAOarticles daOarticles;//дао потом будем пользоваться также для передачи номеров в общую базу
    private final List<LabelSticker> list;//list для списка этикеток, который мы помещаем в выпадающее меню в форме
    @Autowired
    public SpringLabelController(DAOarticles daOarticles) {
        this.daOarticles = daOarticles;
        this.list = daOarticles.index();
    }

    @ModelAttribute("map")//получение атрибута Session attribute - который мы храним во время сессии дял всех методов
    //эта мапа со всеми позициями  №Позиция=Этикета
    public Map<Integer,LabelSticker> getMap(){
        return new TreeMap<>();
    }
    @GetMapping()
    //для того чтобы все поля таймлифа были валидные - добавили в первые гет метод объект дто
    public String index(@ModelAttribute("dto") DTO dto, Model model,
                        @ModelAttribute("map") Map<Integer, LabelSticker> map
                        ) {
        //указываем аргументов модель - для передачи туда мапы для генерации списка добавленных
        model.addAttribute("list",list);//ниспадающий список вместо радиокнопок
        model.addAttribute("map",map);//добавили мапу, которая сешн атрибут
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
            return "/index";//если поля не прошли валидацию - возвращает ту же страницу
            //тут должен был быть не редирект, а ссылка на первую страницу представления
            //теперь надо написать в таймлифе эти ошибки
        }
        map= (Map<Integer, LabelSticker>) model.getAttribute("map");
        int art= dto.getArt();
        LabelSticker receivedLabel =list.stream().filter(x->x.getId()==art).findAny()
                .orElse(new LabelSticker("Error","Error","Error","Error","Error",-1));
        LabelSticker lab = LabelFactory.getLabel(
                receivedLabel.getName(), receivedLabel.getRange(),
                receivedLabel.getPinout(),receivedLabel.getManufacturer(), dto.getSerial(),receivedLabel.getId());
                if(map!=null){
                    map.put(dto.getPos(),lab);}
        model.addAttribute("map",map);
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
    @PostMapping(params = "reset")
    //params - параметр который приходит с инпут сабмита в контроллер (name кнопки)
    public String reset(@ModelAttribute("dto") DTO dto,
                         Model model,
                         @ModelAttribute("map") Map<Integer, LabelSticker> map){
        map.clear();
        model.addAttribute("list",list);
        return "/index";
    }

    @RequestMapping("/download")
    public void download(Model model,
                         @ModelAttribute("map") Map<Integer,LabelSticker> map,
                         HttpServletResponse response) throws IOException, InterruptOperationException {
        ControllerInt controllerInt= new LabelController();
        Map<Integer,LabelSticker> receivedMap = (Map<Integer,LabelSticker>)model.getAttribute("map");
        if (receivedMap.isEmpty()){//проверяем мапу - если пустая то возвращаем информацию о том что ничего не добавлено
            String message = "Ничего не добавлено\n"+
                    "<a href=\"/\"> Перейти на главную страницу </a>";
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.println(message);
            return;
        }
        controllerInt.getModel().setMap(receivedMap);
        InputStream in = controllerInt.getModel().save();
        List<LabelSticker> serials= new ArrayList<>(map.values());
        daOarticles.addSerials(serials);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mmssS");//шаблон для указания даты
        Date date = new Date();
        String timeStamp = formatter.format(date);
        response.setContentType("image/jpg");
        response.setHeader("Content-disposition", "attachment; filename=" + timeStamp+".jpg");
        OutputStream out = response.getOutputStream();
        try{
            byte[] buf = new byte[8192];
            int length;
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
        }}
        catch (IOException ignored){

        }
        //in.transferTo(out);
        out.close();
        in.close();
        //скопировали из инпутстрима файла в аутпутстрим объекта респонз, и удаляем наш файл.
    }
    @GetMapping("/show")
    public String show(Model model){

        model.addAttribute("list",list);
        return "/show";
    }
}
