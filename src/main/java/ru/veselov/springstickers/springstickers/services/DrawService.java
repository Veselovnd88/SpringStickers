package ru.veselov.springstickers.springstickers.services;

import ru.veselov.springstickers.springstickers.model.LabelSticker;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Map;

public interface DrawService {

    InputStream saveWeb(BufferedImage image);

    void setPosLabels(Map<Integer, LabelSticker> posLabels);

}
