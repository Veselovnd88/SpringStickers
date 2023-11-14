package ru.veselov.springstickers.springstickers.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.veselov.springstickers.springstickers.model.LabelSticker;
import ru.veselov.springstickers.springstickers.model.Paper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaperService {

    private final Paper paper;

    private final SaveToFileService saveToFileService;

    public void setPosLabels(Map<Integer, LabelSticker> posLabels) {
        this.paper.setPosLabels(posLabels);
    }

    public InputStream save() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = this.paper.saveWeb();
            InputStream pdfStream = saveToFileService.saveToPdf(byteArrayOutputStream);
            log.info("Image created and converted to pdf, created input stream");
            return pdfStream;

        } catch (IOException e) {
            log.error("Ошибка из формирования листа с размещенными этикетками");
            return null;
        }

    }
}
