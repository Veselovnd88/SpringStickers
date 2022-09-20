package ru.veselov.springstickers.SpringStickers.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.veselov.springstickers.SpringStickers.model.LabelEntity;
import ru.veselov.springstickers.SpringStickers.services.LabelService;

import java.util.Optional;
@Component
public class LabelValidator implements Validator {
    private final LabelService labelService;
    @Autowired
    public LabelValidator(LabelService labelService) {
        this.labelService = labelService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return LabelEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LabelEntity labelEntity= (LabelEntity) target;
        Optional<LabelEntity> optionalLabel = labelService.findByArticle(labelEntity.getArticle());
        if(optionalLabel.isEmpty()){
            return;
        }
        errors.rejectValue("article","","Такой артикул уже существует");

    }
}
