package ru.veselov.springstickers.model;
//класс для передачи из формы в контроллер данных о позиции и артикулу
public class DTO {

    private int pos=1;
    private int art=1;
    private String serial="";
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
