package ru.veselov.springstickers.SpringStickers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.veselov.springstickers.SpringStickers.model.SerialEntity;
@Repository
public interface SerialsRepository extends JpaRepository<SerialEntity, Integer> {

}
