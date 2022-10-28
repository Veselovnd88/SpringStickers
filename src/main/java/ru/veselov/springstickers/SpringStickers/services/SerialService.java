package ru.veselov.springstickers.SpringStickers.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.veselov.springstickers.SpringStickers.model.LabelEntity;
import ru.veselov.springstickers.SpringStickers.model.LabelSticker;
import ru.veselov.springstickers.SpringStickers.model.SerialEntity;
import ru.veselov.springstickers.SpringStickers.repositories.SerialsRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@Transactional(readOnly = true)
public class SerialService {

    private final SerialsRepository repository;
    private final LabelService labelService;

    public SerialService(SerialsRepository repository, LabelService labelService) {
        this.repository = repository;
        this.labelService = labelService;
    }

    public List<SerialEntity> findAll(){
       return repository.findAll();
    }
    @Transactional
    public SerialEntity save(SerialEntity serialEntity){
        repository.save(serialEntity);
        return serialEntity;
    }

    @Transactional
    public void saveAll(List<LabelSticker> stickers){
        stickers.forEach(x->{
            SerialEntity serialEntity = new SerialEntity();
            Optional<LabelEntity> labelEntity = labelService.findByArticle(x.getArticle());
            if (labelEntity.isPresent()){
                 serialEntity.setLabel(labelEntity.get());
                 serialEntity.setNum(x.getSerial());
                 serialEntity.setCreatedAt(new Date());
                SerialEntity saved = repository.save(serialEntity);
                log.info("Серийный номер {} успешно сохранен в БД",saved.getNum());
            }
        });

    }


    public int getMaxPage(Pageable pageable) {
       return repository.findAll(pageable).getTotalPages();
    }

    public List<SerialEntity> findAll(Pageable pageable){
        return repository.findAll(pageable).getContent();
    }
}
