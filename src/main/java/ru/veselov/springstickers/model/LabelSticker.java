package ru.veselov.springstickers.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LabelSticker extends AbstractLabel{
	//TODO - увеличить DPI

	protected static final int WIDTH = 270;//ширина
	protected static final int HEIGHT = 125;//высота
	BufferedImage bufferedImage;
	Graphics g;
	
	
	public LabelSticker(String name, String range, String pinout, String manufacturer, String serial, int id) {
		this.name = name;
		this.range = range;
		this.pinout = pinout;
		this.serial = serial;
		this.manufacturer =manufacturer;
		this.id=id;
		this.bufferedImage = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		this.g = this.bufferedImage.getGraphics();
	    this.g.fillRect(0, 0, WIDTH, HEIGHT);
	}
	
	public void addSigns() {
		try {
			
			BufferedImage eac = ImageIO.read(LabelSticker.class.getResourceAsStream("/EAC.png"));
			BufferedImage ros = ImageIO.read(LabelSticker.class.getResourceAsStream("/reestr.png"));
			BufferedImage manuf=null;
			if(manufacturer.equals("ADZ NAGANO GmbH")){
				manuf = ImageIO.read(LabelSticker.class.getResourceAsStream("/adz.png"));

			}
			else{
				manuf = ImageIO.read(LabelSticker.class.getResourceAsStream("/all-imp.png"));
			}

			g.drawImage(ros.getScaledInstance(40, 40, Image.SCALE_DEFAULT),230,75,null);
			g.drawImage(eac.getScaledInstance(40, 40, Image.SCALE_DEFAULT), 230, 20, null);

			g.drawImage(manuf.getScaledInstance(80, 80, Image.SCALE_SMOOTH),0,30 , null);
	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Image createImage() {
	      Font stringFont = new Font("TrueType",Font.BOLD,13);//задаем стандартный шрифт
	      Font stringBold = new Font("TrueType",Font.BOLD,15);//задаем жирный шрифт
			Font small = new Font("TrueType", Font.PLAIN,12);
	      this.g.setFont(stringFont);//установка обычного шрифта
	      this.g.setColor(Color.black);//установка цвета шрифта
		  g.setFont(small);
	      this.g.drawString(range, 80,51);//рисуем диапазон
		  g.setFont(stringFont);
	      this.g.drawString(pinout, 80,72);//рисуем распиновку
	      this.g.drawString("SN: "+serial, 80,93);//рисуем серийный номер
		  this.g.drawString(super.manufacturer, 80,115);//рисуем строку производителя
	      this.g.setFont(stringBold);//устанавливаем жирный шрифт для рисования жирным
	      this.g.drawString(name, 80,30);//рисуем имя

	      addSigns();//добавляем значки
	      return this.bufferedImage;//возвращаем наше изображение
	}
	
	public String getSerial() {
		return this.serial;
	}
	public String getName() {
		return this.name;
	}
	public String getRange() {
		return this.range;
	}
	public int getId(){
		return this.id;
	}
	public String getPinout(){
		return this.pinout;
	}

	public String getManufacturer(){
		return this.manufacturer;
	}
}
