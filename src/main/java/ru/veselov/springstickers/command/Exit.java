package ru.veselov.springstickers.command;

import ru.veselov.springstickers.exception.InterruptOperationException;

public class Exit implements Command {

	@Override
	public void execute() throws InterruptOperationException {
		throw new InterruptOperationException();
		
	}

}
