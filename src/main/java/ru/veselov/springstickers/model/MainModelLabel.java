package ru.veselov.springstickers.model;


import org.springframework.stereotype.Component;
import ru.veselov.springstickers.exception.InterruptOperationException;

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
	public void save(String directory) throws InterruptOperationException {
		

		paper.placeAll(posLabels);
		paper.save(directory);
		
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