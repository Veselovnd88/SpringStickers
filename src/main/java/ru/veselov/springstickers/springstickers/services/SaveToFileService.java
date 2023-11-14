package ru.veselov.springstickers.springstickers.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public interface SaveToFileService {

    InputStream saveToPdf(ByteArrayOutputStream imageStream);

}
