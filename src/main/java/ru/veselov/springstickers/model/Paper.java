package ru.veselov.springstickers.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Paper {
	private final static int LABELWIDTH = LabelSticker.WIDTH;//ширина этикетки
	private final static int LABELHEIGHT = LabelSticker.HEIGHT;//высота этикетки
	private static final int LEFTEDGE = 59;//отступ слева от края листа
	private static final HashMap<Integer, List<Integer>> coordinates = new HashMap<>();//мапа с координатами и позициями
		
	private final BufferedImage myImage;
	private final Graphics g;

	
	static {//статик блок для инициализации координатами мапы
		for (int i=1; i<13;i++) {//13 - 12 позиций
			coordinates.put(i, new ArrayList<Integer>());
			if(i<5) {
				coordinates.get(i).add(LABELHEIGHT);
				if(i==1) {
					coordinates.get(i).add((LEFTEDGE));}//59 расстояние от левого края
				else {
					coordinates.get(i).add(16*(i-1)+LEFTEDGE+(i-1)*LABELWIDTH);//17 расстояние между этикетками
				}					
			}
			else if(i<9) {
				coordinates.get(i).add(LABELHEIGHT*2);
				if(i==5) {
					coordinates.get(i).add((LEFTEDGE));}
				else {
					coordinates.get(i).add(16*(i-5)+LEFTEDGE+(i-5)*LABELWIDTH);
				}	
			}
			else {
				coordinates.get(i).add(LABELHEIGHT*3);
				if(i==9) {
					coordinates.get(i).add((LEFTEDGE));}
				else {
					coordinates.get(i).add(16*(i-9)+LEFTEDGE+(i-9)*LABELWIDTH);
				}	
			}
		}
	}
	
	public Paper() {
		//высота
		int HEIGHT = 1754;
		// ширина в пикселях, размер взят для DPI 150
		int WIDTH = 1240;
		myImage = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);//создание листа с указанными размерами, здесь А4
		g = myImage.getGraphics();
	    g.setColor(Color.WHITE);
	    g.fillRect(0, 0, WIDTH, HEIGHT);
	}

	/*Метод размещает(рисует) переданную этикету по заданным координатам*/
	private void drawLabel(Image im, int x, int y) {
		g.drawImage(im, x, y, null);
	}
	//метод размещает все этикетки по координатам и рисует на данном Image
	private void drawAllImages(Map<Integer, LabelSticker> labels) {
		for(Map.Entry<Integer, LabelSticker> entry: labels.entrySet()) {
			int x = coordinates.get(entry.getKey()).get(1);
			int y = coordinates.get(entry.getKey()).get(0);
			drawLabel(entry.getValue().createImage(),x,y );//размещение всех этикеток на листе
		}
	}

	public InputStream saveWeb(Map<Integer, LabelSticker> labels) throws IOException {
		drawAllImages(labels);
		ByteArrayOutputStream baos =new ByteArrayOutputStream();
		ImageIO.write((BufferedImage) myImage,"jpg",baos);//пишем в созданный файл
		return new ByteArrayInputStream(baos.toByteArray());
	}


}