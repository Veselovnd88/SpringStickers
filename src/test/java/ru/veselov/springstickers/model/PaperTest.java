package ru.veselov.springstickers.model;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

public class PaperTest {
    private Paper paper;
    private LabelSticker testSticker;
    private HashMap<Integer,LabelSticker> testMap;
    @Before
    public void setUpBefore(){
        paper=  new Paper();
        testSticker = LabelFactory.getLabel("Test","Test",
                "Test","Test","Test",1);
        testMap = new HashMap<>();
        testMap.put(1,testSticker);
    }
    @Test
    public void testSaveWeb() {
        try {
            /*Тестирование на нулл и на возможность чтения*/
            Assert.assertNotNull(paper.saveWeb(testMap));
            Assert.assertNotEquals(paper.saveWeb(testMap).read(),-1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}