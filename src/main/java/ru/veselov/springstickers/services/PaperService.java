package ru.veselov.springstickers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.veselov.springstickers.model.LabelSticker;
import ru.veselov.springstickers.model.Paper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

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
            throw new RuntimeException(e);
        }
    }
}
