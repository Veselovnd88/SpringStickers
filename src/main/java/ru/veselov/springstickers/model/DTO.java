package ru.veselov.springstickers.model;
//класс для передачи из формы в контроллер данных о позиции и артикулу
public class DTO {
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

    private int pos=1;
    private int art=1;


}
