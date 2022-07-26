package ru.veselov.springstickers.model;

public class LabelFactory {
	
	private LabelFactory() {
		
	}
	
	public static LabelSticker getLabel(String name, String range, String pinout, String serial) {
		return new LabelSticker(name,range,pinout,serial);
	}

}
