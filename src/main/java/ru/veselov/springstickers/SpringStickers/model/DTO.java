package ru.veselov.springstickers.SpringStickers.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

//класс для передачи из формы в контроллер данных о позиции и артикулу
public class DTO {
    private int pos=1;
    private int art=1;
    public void setPos(int pos) {//сеттер для DTO
        this.pos = pos;
    }

    public void setArt(int art) {//сеттер для DTO
        this.art = art;
    }

    public void setSerial(String serial) {//сеттер для DTO
        this.serial = serial;
    }

    public void setMessage(String message) {//сеттер для DTO TODO проверить кажется всё таки этот не нужен
        this.message = message;
    }

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
