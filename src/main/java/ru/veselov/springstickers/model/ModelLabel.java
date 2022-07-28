package ru.veselov.springstickers.model;

import ru.veselov.springstickers.exception.InterruptOperationException;

import java.io.File;
import java.util.Map;

public interface ModelLabel {
	Map<Integer, LabelSticker> getMap();
	Paper getPaper();
	File save(String directory) throws InterruptOperationException;
	int getArt();
	int getPos();
	void setArt(int art);
	void setPos(int pos);
	void setSerial(String serial);
	String getSerial();
	

}
