package ru.veselov.springstickers.model;


import ru.veselov.springstickers.exception.InterruptOperationException;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class MainModelLabel implements ModelLabel {
	private int pos;
	private int art;
	
	
	private final Map<Integer, LabelSticker> posLabels = new TreeMap<>();
	private final Paper paper = new Paper();//тут логика размещения этикеток на листе
	private String serial;


	@Override
	public Paper getPaper() {
		return this.paper;
	}
	
	
	
	@Override
	public Map<Integer, LabelSticker> getMap(){
		return posLabels;
	}

	@Override
	public File save(String directory) throws InterruptOperationException {
		

		paper.placeAll(posLabels);
		try {
			 return paper.saveWeb();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
	@Override
	public int getPos() {
		return pos;
	}
	@Override
	public void setPos(int pos) {
		this.pos = pos;
	}

	@Override
	public void setSerial(String serial) {
		this.serial = serial;
	}

	@Override
	public String getSerial() {
		return this.serial;
	}

	@Override
	public int getArt() {
		return art;
	}
	@Override
	public void setArt(int art) {
		this.art = art;
	}

	


}