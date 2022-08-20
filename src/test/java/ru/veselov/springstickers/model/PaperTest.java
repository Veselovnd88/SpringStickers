package ru.veselov.springstickers.model;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

public class PaperTest {
    private Paper paper;
    @Before
    public void setUpBefore(){
        paper=  new Paper();
    }
    @Test
    public void testSaveWeb() {
        try {
            Assert.assertNotNull(paper.saveWeb());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}