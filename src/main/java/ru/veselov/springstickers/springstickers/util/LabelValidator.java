package ru.veselov.springstickers.springstickers.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.veselov.springstickers.springstickers.entity.LabelEntity;
import ru.veselov.springstickers.springstickers.services.LabelService;

import java.util.Optional;
/*Кастомная валидация по артикулу
* Класс имплементирует интерфейс Validator
**/
@Component
public class LabelValidator implements Validator {
    private final LabelService labelService;
    @Autowired
    public LabelValidator(LabelService labelService) {
        this.labelService = labelService;
    }
    /*Метод проверяет класс который передается на валидацию*/
    @Override
    public boolean supports(Class<?> clazz) {
        return LabelEntity.class.equals(clazz);
    }
    /*Логика валидации
    * Делаем запрос к бд по заданному артикулу, если такое значение есть, то пишем в ошибки значения
    * Если нет, то всё в порядке и делаем возврат из функции*/
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
