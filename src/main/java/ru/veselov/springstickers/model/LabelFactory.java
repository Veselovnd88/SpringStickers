package ru.veselov.springstickers.model;

public class LabelFactory {
	//Фабрика для создания этикетки
		private LabelFactory() {
		
	}
	
	public static LabelSticker getLabel(String name, String range,
										String pinout, String manufacturer, String serial,int id) {
		return new LabelSticker(name,range,pinout,manufacturer, serial,id);
		//этот второй метод для создания этикеток после выгрузки из бд для выдачи на отображение
	}

}
