package ru.veselov.springstickers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.veselov.springstickers.model.LabelEntity;
import ru.veselov.springstickers.repositories.LabelRepository;

import java.util.List;
import java.util.Optional;

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

    public List<LabelEntity> findAll(Pageable pageable){
        return labelRepository.findAll(pageable).getContent();
    }

    public int getMaxPage(Pageable pageable){
        return labelRepository.findAll(pageable).getTotalPages();
    }

    public LabelEntity findById(int id){
        Optional<LabelEntity> label = labelRepository.findById(id);
        return label.orElse(null);
    }


}
