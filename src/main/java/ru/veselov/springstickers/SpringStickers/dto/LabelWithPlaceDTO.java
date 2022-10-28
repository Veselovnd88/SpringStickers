package ru.veselov.springstickers.SpringStickers.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import org.springframework.stereotype.Component;
@Data
//класс для передачи из формы в контроллер данных о позиции и артикулу
public class LabelWithPlaceDTO {
    private int pos=1;
    private int art=1;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min=2,max=15,message = "Серийный номер должен быть от 2 до 15 цифр")
    private String serial;
    private String message="Пока ничего не добавлено";


}
