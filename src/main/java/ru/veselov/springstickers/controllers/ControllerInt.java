package ru.veselov.springstickers.controllers;

import ru.veselov.springstickers.exception.InterruptOperationException;
import ru.veselov.springstickers.model.ModelLabel;

public interface ControllerInt {

	int onGetArt() throws InterruptOperationException;

	void onSave() throws InterruptOperationException;
	int onGetPos() throws InterruptOperationException;
	ModelLabel getModel();
	boolean onYesOrNo() throws InterruptOperationException;;
	String onReadSerial() throws InterruptOperationException;
	String sendMessage(String message);
	boolean checkForRewriting() throws InterruptOperationException; //метод проверяет есть ли в мапе эта позиция или нет и перезаписывает


	void onDelete();
}