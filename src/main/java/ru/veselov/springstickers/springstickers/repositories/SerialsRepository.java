package ru.veselov.springstickers.springstickers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.veselov.springstickers.springstickers.entity.SerialEntity;
@Repository
public interface SerialsRepository extends JpaRepository<SerialEntity, Integer> {

}
