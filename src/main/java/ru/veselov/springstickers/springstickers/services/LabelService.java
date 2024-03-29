package ru.veselov.springstickers.springstickers.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.veselov.springstickers.springstickers.entity.LabelEntity;
import ru.veselov.springstickers.springstickers.repositories.LabelRepository;

import java.util.List;
import java.util.Optional;
@Slf4j
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void update(int id, LabelEntity labelEntity){
        labelEntity.setId(id);
        labelRepository.save(labelEntity);
        log.info("Обновлен артикул {}",labelEntity.getArticle());
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void deleteById(int id){
        labelRepository.deleteById(id);
        log.info("Удален артикул id={}",id);
    }

    public Optional<LabelEntity> findByArticle(String article) {
        return labelRepository.findByArticle(article);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void save(LabelEntity labelEntity){
        labelRepository.save(labelEntity);
        log.info("Сохранен артикул {}",labelEntity.getArticle());
    }
}
