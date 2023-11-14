package ru.veselov.springstickers.springstickers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.veselov.springstickers.springstickers.entity.LabelEntity;

import java.util.Optional;

@Repository
public interface LabelRepository extends JpaRepository<LabelEntity,Integer> {

    Optional<LabelEntity> findByArticle(String article);
}
