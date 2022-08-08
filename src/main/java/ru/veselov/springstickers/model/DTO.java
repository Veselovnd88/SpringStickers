package ru.veselov.springstickers.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

//класс для передачи из формы в контроллер данных о позиции и артикулу
public class DTO {

    private int pos=1;

    private int art=1;
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min=2,max=15,message = "Серийный номер должен быть от 2 до 15 цифр")
    private String serial;
    private String message="Пока ничего не добавлено";


    public String getMessage(){
        return message;
    }

    public String getSerial() {
        return serial;
    }

    public int getPos() {
        return pos;
    }

    public int getArt() {
        return art;
    }


}
