package ru.veselov.springstickers.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.veselov.springstickers.SpringStickers.model.LabelFactory;
import ru.veselov.springstickers.SpringStickers.model.LabelSticker;
import ru.veselov.springstickers.SpringStickers.model.Paper;

import java.io.IOException;
import java.util.HashMap;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class PaperTest {
    private Paper paper;
    private LabelSticker testSticker;
    private HashMap<Integer,LabelSticker> testMap;

    private Paper mockPaper;

    @Before
    public void setUpBefore(){
        paper=  new Paper();
        testSticker = LabelFactory.getLabel("Test","Test",
                "Test","Test","Test",1);
        testMap = new HashMap<>();
        testMap.put(1,testSticker);
        mockPaper = Mockito.mock(Paper.class);

    }
    @Test
    public void testSaveWeb() {
            /*Тестирование на нулл и на возможность чтения*/
            /*Проверяет были ли вызовы методов*/
            mockPaper.saveWeb();
            Mockito.verify(mockPaper,times(1)).saveWeb();

    }
}