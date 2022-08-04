package ru.veselov.springstickers.model;

public class LabelFactory {
	//Фабрика для создания этикетки
		private LabelFactory() {
		
	}
	
	public static LabelSticker getLabel(String name, String range, String pinout, String serial) {
		return new LabelSticker(name,range,pinout,serial);
	}//этот метод возвращает этикетку для укладки в мапу для печати( скорей всего можно убрать и использовать расширенный

	public static LabelSticker getLabel(String name, String range,
										String pinout, String manufacturer, String serial,int id) {
		return new LabelSticker(name,range,pinout,manufacturer, serial,id);
	}

}
