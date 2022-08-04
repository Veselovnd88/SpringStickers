package ru.veselov.springstickers.model;

import ru.veselov.springstickers.exception.InterruptOperationException;

import java.io.File;
import java.util.Map;

public interface ModelLabel {
	void setMap(Map<Integer,LabelSticker> newMap);

	File save(String directory) throws InterruptOperationException;
}
