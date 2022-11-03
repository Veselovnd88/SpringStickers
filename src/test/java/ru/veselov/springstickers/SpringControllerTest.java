package ru.veselov.springstickers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.veselov.springstickers.SpringStickers.SpringStickersApplication;
import ru.veselov.springstickers.SpringStickers.services.SerialService;
import ru.veselov.springstickers.SpringStickers.controllers.SpringLabelController;
import ru.veselov.springstickers.SpringStickers.model.LabelEntity;
import ru.veselov.springstickers.SpringStickers.model.LabelSticker;
import ru.veselov.springstickers.SpringStickers.services.LabelService;
import ru.veselov.springstickers.SpringStickers.services.PaperService;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

import static org.mockito.Mockito.when;

/*Тестирование класса контроллера
* Если не указать ContextConfiguration - то спринг не сможет найти этот класс, т.к. он лежит не в той же
* директории где и Мейн класс*/

@WebMvcTest(value = SpringLabelController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = SpringStickersApplication.class)
public class SpringControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LabelService labelService;

    @MockBean
    private PaperService paperService;

    @MockBean
    private LabelEntity labelEntity;

    @MockBean
    private SerialService serialService;

    @Test
    public void testMainPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Добро пожаловать")));

    }

    @Test
    public void testShowPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/show")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("show"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Вернуться на главную")));

    }

/*Тест проверяет доступность страницы с карточкой номенклатуры
    * Перед запуском - устанавливаем поведение LabelService через Mockito - чтобы он возвращал корректный объект и у нас не
    * ломался таймлиф
    * Устанавливаем моки секьюрити контекста, так как в представлении у нас есть правило которое зависит от
    * роли пользователя*/

    @Test
    public void testShowCardPage() throws Exception {
        int id=1;
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(labelService.findById(id)).thenReturn(labelEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/show/{id}",id)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("show_card"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Вернуться")));

    }
/*Проверка перехода по ссылке /download
    * 1: Для случая, когда ничего не добавлено
    * 2: Для случае, когда есть мапа, но не сформировался инпутстрим
    * 3: Для корректного случая, когда есть добавленные позиции и правильно отработал метод paperService.save() */

    @Test
    public void testDownloadPageNoData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/download")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Ничего")));
    }

    @Test
    public void testDownloadPageNotNullMap() throws Exception {
        HashMap<Integer,LabelSticker> test = new HashMap<>();
        test.put(1,null);
        mockMvc.perform(MockMvcRequestBuilders.get("/download").flashAttr("map",test))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Проблема")));
    }

    @Test
    public void testDownloadPageNotNullMapNotNullInputStream() throws Exception {
        HashMap<Integer,LabelSticker> test = new HashMap<>();
        test.put(1,null);
        when(paperService.save()).thenReturn(new ByteArrayInputStream(new byte[1]));
        mockMvc.perform(MockMvcRequestBuilders.get("/download").flashAttr("map",test))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("image/jpg"));
    }

}

