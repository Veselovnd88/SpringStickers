package ru.veselov.springstickers.SpringStickers.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LabelSticker extends AbstractLabel{


	protected static final int WIDTH = 810;//ширина
	protected static final int HEIGHT = 375;//высота
	protected BufferedImage bufferedImage;
	protected Graphics g;
	private String article;
	
	
	public LabelSticker(String article,String name, String range, String pinout, String manufacturer, String serial, int id) {
		this.name = name;
		this.range = range;
		this.pinout = pinout;
		this.serial = serial;
		this.manufacturer =manufacturer;
		this.id=id;
		this.article = article;
		this.bufferedImage = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		this.g = this.bufferedImage.getGraphics();
	    this.g.fillRect(0, 0, WIDTH, HEIGHT);
	}
	
	private void addSigns() {
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

			g.drawImage(ros.getScaledInstance(120, 120, Image.SCALE_DEFAULT),690,225,
					null);
			g.drawImage(eac.getScaledInstance(120, 120, Image.SCALE_DEFAULT), 690, 60,
					null);
			g.drawImage(manuf.getScaledInstance(240, 240, Image.SCALE_SMOOTH),0,90 ,
					null);
	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Image createImage() {
	      Font stringFont = new Font("Arial",Font.BOLD,39);//задаем стандартный шрифт
	      Font stringBold = new Font("Arial",Font.BOLD,45);//задаем жирный шрифт
	      this.g.setFont(stringFont);//установка обычного шрифта
	      this.g.setColor(Color.black);//установка цвета шрифта
	      this.g.drawString(range, 240,153);//рисуем диапазон
	      this.g.drawString(pinout, 240,216);//рисуем распиновку
	      this.g.drawString("SN: "+serial, 240,279);//рисуем серийный номер
	      this.g.setFont(stringBold);//устанавливаем жирный шрифт для рисования жирным
	      this.g.drawString(name, 240,90);//рисуем имя
	      this.g.drawString(super.manufacturer, 240,345);//рисуем строку производителя
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

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}
}
