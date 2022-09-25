package ru.veselov.springstickers.SpringStickers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.veselov.springstickers.SpringStickers.model.LabelEntity;

import java.util.Optional;

@Repository
public interface LabelRepository extends JpaRepository<LabelEntity,Integer> {

    public Optional<LabelEntity> findByArticle(String article);
}
