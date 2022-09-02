package ru.veselov.springstickers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.veselov.springstickers.model.LabelEntity;
import ru.veselov.springstickers.repositories.LabelRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LabelService {
    private final LabelRepository labelRepository;

    @Autowired
    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }


    public List<LabelEntity> findAll(){
        return labelRepository.findAll();
    }
}
