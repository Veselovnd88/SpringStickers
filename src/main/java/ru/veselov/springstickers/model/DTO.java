package ru.veselov.springstickers.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

//класс для передачи из формы в контроллер данных о позиции и артикулу
public class DTO {
    @NotEmpty
    @Min(value=1)
    @Max(value=12)
    private int pos=1;
    @NotEmpty
    @Min(value = 1)
    @Max(value =4)
    private int art=1;
    @NotEmpty
    @Size(min=2,max=13,message = "Серийный номер должен быть от 2 до 13 цифр")
    private String serial;
    private String message="Пока ничего не добавлено";


    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message=message;
    }
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    private String task="Разместить";

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getArt() {
        return art;
    }

    public void setArt(int art) {
        this.art = art;
    }



}
