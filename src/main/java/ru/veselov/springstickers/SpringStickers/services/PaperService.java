package ru.veselov.springstickers.SpringStickers.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.veselov.springstickers.SpringStickers.model.Paper;
import ru.veselov.springstickers.SpringStickers.model.LabelSticker;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
@Slf4j
@Service
public class PaperService {

    private final Paper paper;
    @Autowired
    public PaperService(Paper paper) {
        this.paper = paper;
    }

    public void setPosLabels(Map<Integer, LabelSticker> posLabels){
        this.paper.setPosLabels(posLabels);
    }

    public InputStream save(){
        try {
            return this.paper.saveWeb();
        } catch (IOException e) {
            log.error("Ошибка из формирования листа с размещенными этикетками");
            return null;
        }

    }
}
