package ru.veselov.springstickers.model;

import org.junit.Assert;
import org.junit.Test;
import ru.veselov.springstickers.springstickers.model.LabelFactory;


public class LabelFactoryTest {
    @Test
    public void testGetLabel() {
        Assert.assertNotNull(LabelFactory.getLabel(null,null,null,null,null,null,-1));
    }
}