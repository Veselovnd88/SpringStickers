package ru.veselov.springstickers.command;

import ru.veselov.springstickers.exception.InterruptOperationException;

public interface Command {
	public void execute() throws InterruptOperationException;
}
