package ru.veselov.springstickers.SpringStickers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.veselov.springstickers.SpringStickers.model.SerialEntity;

public interface SerialsRepository extends JpaRepository<Integer, SerialEntity> {

}
