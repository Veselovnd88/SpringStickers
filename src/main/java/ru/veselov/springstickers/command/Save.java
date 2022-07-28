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
		

		
	}

}