package ru.veselov.springstickers.SpringStickers.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.veselov.springstickers.SpringStickers.model.LabelEntity;
import ru.veselov.springstickers.SpringStickers.model.LabelSticker;
import ru.veselov.springstickers.SpringStickers.model.SerialEntity;
import ru.veselov.springstickers.SpringStickers.repositories.SerialsRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SerialService {

    private final SerialsRepository repository;

    public SerialService(SerialsRepository repository) {
        this.repository = repository;
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

        });

    }





}
