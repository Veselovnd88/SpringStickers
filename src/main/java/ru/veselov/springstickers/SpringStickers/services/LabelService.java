package ru.veselov.springstickers.SpringStickers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.veselov.springstickers.SpringStickers.model.LabelEntity;
import ru.veselov.springstickers.SpringStickers.repositories.LabelRepository;

import java.time.LocalDateTime;
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
    @Transactional
    public void update(int id, LabelEntity labelEntity){
        labelEntity.setId(id);
        labelEntity.setCreatedAt(LocalDateTime.now());
        labelRepository.save(labelEntity);
    }
    @Transactional
    public void deleteById(int id){
        labelRepository.deleteById(id);
    }

    public Optional<LabelEntity> findByArticle(String article) {
        return labelRepository.findByArticle(article);
    }

    @Transactional
    public void save(LabelEntity labelEntity){
        labelEntity.setCreatedAt(LocalDateTime.now());
        labelRepository.save(labelEntity);
    }
}
