package ru.veselov.springstickers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.veselov.springstickers.model.LabelEntity;

@Repository
public interface LabelRepository extends JpaRepository<LabelEntity,Integer> {



}
