package ru.veselov.springstickers.springstickers.model;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class Paper {
    /*Ширина этикетки*/
    private static final int LABEL_WIDTH = LabelSticker.WIDTH;
    /*Высотка этикетки*/
    private static final int LABEL_HEIGHT = LabelSticker.HEIGHT;
    /*Отступ от левого края*/
    private static final int LEFT_EDGE = 180;//отступ слева от края листа
    /*Расстояние меджду этикетками*/
    private static final int BETWEEN = 45;
    /*Высота листа*/
    private static final int HEIGHT = 5262;
    /*Ширина листа*/
    private static final int WIDTH = 3720;

    public static final int FROM_ABOVE = 330;
    /*Хешмап с координатами и позициями, заполняется при создании объекта
     * Номер Позиции:  - список координат (x и y) */
    private final HashMap<Integer, List<Integer>> coordinates = new HashMap<>();//мапа с координатами и позициями

    /*Мапа с позициями и этикетками Позиция: этикетка*/
    private Map<Integer, LabelSticker> posLabels = new TreeMap<>();//мапа в которой хранится #Позиции: этикетка
    private final BufferedImage myImage;
    private final Graphics g;

    public Paper() {
        init();
        myImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = myImage.getGraphics();
        refresh();
    }

    /*Метод заполняет мапу coordinates значениями*/
    private void init() {
        for (int i = 1; i < 13; i++) {//13 - 12 позиций
            coordinates.put(i, new ArrayList<>());
            if (i < 5) {
                coordinates.get(i).add(FROM_ABOVE);
                if (i == 1) {
                    coordinates.get(i).add((LEFT_EDGE));
                }//59 расстояние от левого края
                else {
                    coordinates.get(i).add(BETWEEN * (i - 1) + LEFT_EDGE + (i - 1) * LABEL_WIDTH);//17 расстояние между этикетками
                }
            } else if (i < 9) {
                coordinates.get(i).add(LABEL_HEIGHT+FROM_ABOVE);
                if (i == 5) {
                    coordinates.get(i).add((LEFT_EDGE));
                } else {
                    coordinates.get(i).add(BETWEEN * (i - 5) + LEFT_EDGE + (i - 5) * LABEL_WIDTH);
                }
            } else {
                coordinates.get(i).add(LABEL_HEIGHT * 2+FROM_ABOVE);
                if (i == 9) {
                    coordinates.get(i).add((LEFT_EDGE));
                } else {
                    coordinates.get(i).add(BETWEEN * (i - 9) + LEFT_EDGE + (i - 9) * LABEL_WIDTH);
                }
            }
        }
    }

    /*Метод заливает форму белым цветом*/
    private void refresh() {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
    }

    /*Метод размещает(рисует) переданную этикетку по заданным координатам*/
    private void drawLabel(Image im, int x, int y) {
        g.drawImage(im, x, y, null);
    }

    /*Метод размещает все этикетки по координатам и рисует на данном Image*/
    private void drawAllImages() {
        for (Map.Entry<Integer, LabelSticker> entry : posLabels.entrySet()) {
            int x = coordinates.get(entry.getKey()).get(1);
            int y = coordinates.get(entry.getKey()).get(0);
            drawLabel(entry.getValue().createImage(), x, y);
        }
    }


    /*В этом методе размещаются все этикетки, Image пишется в Стрим Массива Байтов
     * @return Массив байтов ByteArrayInputStream для дальнейшего преобразования в другие потоки
     * после того как создали, обновляем файл (чтобы наши результаты не мешали другим пользователям)*/
    public ByteArrayOutputStream saveWeb() throws IOException {
        drawAllImages();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(myImage, "jpg", baos);
        refresh();
        return baos;
    }

    public Map<Integer, LabelSticker> getPosLabels() {
        return posLabels;
    }


    public void setPosLabels(Map<Integer, LabelSticker> posLabels) {
        this.posLabels = posLabels;
    }

}