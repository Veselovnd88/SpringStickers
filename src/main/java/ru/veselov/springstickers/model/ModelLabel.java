package ru.veselov.springstickers.model;

import ru.veselov.springstickers.exception.InterruptOperationException;

import java.io.InputStream;
import java.util.Map;

public interface ModelLabel {
	void setMap(Map<Integer,LabelSticker> newMap);

	InputStream save(String directory) throws InterruptOperationException;
}
