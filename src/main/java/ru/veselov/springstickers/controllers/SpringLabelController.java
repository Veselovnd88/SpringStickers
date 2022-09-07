package ru.veselov.springstickers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.veselov.springstickers.exception.InterruptOperationException;
import ru.veselov.springstickers.model.*;
import ru.veselov.springstickers.services.LabelService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/")
@SessionAttributes("map")//атрибут который хранится в течение всей сессии
public class SpringLabelController {
    /*LabelEntityList - получаем список из бд через сервис и помещаем в список
    * Этот список отвечает за выпадающее меню*/
    private final List<LabelEntity> labelEntityList;//list для списка этикеток, который мы помещаем в выпадающее меню в форме
    private final LabelService labelService;
    @Autowired
    public SpringLabelController(DAOarticles daOarticles, LabelService labelService) {
        this.labelService = labelService;
        this.labelEntityList = labelService.findAll();
    }

    @ModelAttribute("map")
    /*получение атрибута Session attribute - который мы храним во время сессии дял всех методов
    эта мапа со всеми позициями  №Позиция=Этикетка*/
    public Map<Integer,LabelSticker> getMap(){
        return new TreeMap<>();
    }
    @GetMapping()
    //для того чтобы все поля таймлифа были валидные - добавили в первые гет метод объект дто
    public String index(@ModelAttribute("dto") DTO dto, Model model,
                        @ModelAttribute("map") Map<Integer, LabelSticker> map
                        ) {
        //указываем аргументов модель - для передачи туда мапы для генерации списка добавленных
        model.addAttribute("list", labelEntityList);//выпадающий список вместо радиокнопок
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
        model.addAttribute("list", labelEntityList);
        //для того чтобы он формировал форму через таймлиф нужно обязательно передавать сюда
        if(bindingResult.hasErrors()){
            System.out.println("Нашел ошибки");
            return "/index";//если поля не прошли валидацию - возвращает ту же страницу
            //тут должен был быть не редирект, а ссылка на первую страницу представления
            //теперь надо написать в таймлифе эти ошибки
        }
        map= (Map<Integer, LabelSticker>) model.getAttribute("map");
        int art= dto.getArt();
        /*получили артикул и теперь должны на его основе сделать Стикер, поиск по списку по Id
        * И далее конструирование по полям через фабрику*/
        LabelEntity receivedLabel = labelEntityList.stream().filter(x->x.getId()==art).findAny()
                .orElse(null);
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
        model.addAttribute("list", labelEntityList);
        return "/index";
    }
    @PostMapping(params = "reset")
    //params - параметр который приходит с инпут сабмита в контроллер (name кнопки)
    public String reset(@ModelAttribute("dto") DTO dto,
                         Model model,
                         @ModelAttribute("map") Map<Integer, LabelSticker> map){
        map.clear();
        model.addAttribute("list", labelEntityList);
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
        //List<LabelSticker> serials= new ArrayList<>(map.values());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mmssS");//шаблон для указания даты
        Date date = new Date();
        String timeStamp = formatter.format(date);
        response.setContentType("image/jpg");
        response.setHeader("Content-disposition", "attachment; filename=" + timeStamp+".jpg");
        OutputStream out = response.getOutputStream();
        in.transferTo(out);
        out.close();
        in.close();
        //daOarticles.addSerials(serials);
        //скопировали из инпутстрима файла в аутпутстрим объекта респонз, и удаляем наш файл.
    }


    /*Метод выводит все заведенные типы номенклатур
    * Возможна настройка пагинации через RequestParam
    * Если параметры не передаются - то выводится первая страница с 10 ю позициями.
    * Если передаются, то выбираем что и как хотим видеть
    * Добавлены ссылки Предыдущая и Следующая (для определения максимального количества страниц и текущей страницы
    * для таймлифа передаем их значения в модель аттрибют
    * @return представление из views*/
    @GetMapping("/show")
    public String show(Model model, @RequestParam(value = "page",required = false) Integer page,
                       @RequestParam(value = "items_per_page",required = false) Integer items_per_page){
        String returnPage = "/show";
        if(page==null || items_per_page==null){
            page=0;
            items_per_page=10;

        }
        Pageable pageable=PageRequest.of(page,items_per_page);
        int max_page = labelService.getMaxPage(pageable);
        model.addAttribute("list",labelService.findAll(pageable));
        model.addAttribute("max_page", max_page);
        model.addAttribute("page",page);
        model.addAttribute("items_per_page",items_per_page);
        return returnPage;
    }
    /*Выдача информации о запрашиваемом по id артикуле
    * @return представление из /views*/
    @GetMapping("/show/{id}")
    public String show(Model model, @PathVariable("id") int id){
        model.addAttribute("lbl",labelService.findById(id));
        return "/show_card";
    }
}
