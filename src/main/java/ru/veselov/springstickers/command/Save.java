package ru.veselov.springstickers.command;

import ru.veselov.springstickers.controllers.ControllerInt;
import ru.veselov.springstickers.exception.InterruptOperationException;

public class Save implements Command {
	private ControllerInt controller;

	
	public Save(ControllerInt controller) {
		this.controller = controller;
		}
	@Override
	public void execute() throws InterruptOperationException {
		
		String windows = "c:\\StickersADZ";
		String linux = "/home/nikolay/StickersADZ";
		String os = System.getProperty("os.name").toLowerCase();
		
		
		
		if(this.controller.getModel().getMap().isEmpty()) {
			controller.sendMessage("Список для печати пустой, печатать нечего");
			return;
		}//проверка на пустую мапу, если ничего не добавлено
		if(os.startsWith("windows")){
			controller.getModel().save(windows);
		controller.sendMessage("Файл сохранен в директорию c:\\StickersADZ");}
		else {
			controller.getModel().save(linux);
			controller.sendMessage("Для "+ System.getProperty("os.name")+" не реализовано");
		}
		
	}

}