package ru.veselov.springstickers.model;


import ru.veselov.springstickers.exception.InterruptOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

public class MainModelLabel implements ModelLabel {
	private int pos;
	private int art;
	
	private  Map<Integer, LabelSticker> posLabels = new TreeMap<>();//мапа в которой хранится #Позиции: этикетка
	private final Paper paper = new Paper();//тут логика размещения этикеток на листе
	private String serial;

	public void setMap(Map<Integer, LabelSticker> newMap){
		this.posLabels=newMap;
	}

	@Override
	public InputStream save() throws InterruptOperationException {
		//сохранение в файл для последующей отправки пользователю
		try {
			 return paper.saveWeb(posLabels);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}