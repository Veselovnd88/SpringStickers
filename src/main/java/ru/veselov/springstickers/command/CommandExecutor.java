package ru.veselov.springstickers.command;

import ru.veselov.springstickers.controllers.ControllerInt;
import ru.veselov.springstickers.exception.InterruptOperationException;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {


	public static final Map<Operation, Command> allCommands = new HashMap<>();

	
	private CommandExecutor() {		
	}
	
	
	public static void init(ControllerInt controller) {
		allCommands.put(Operation.CHOOSE, new ChooseArt(controller));
		allCommands.put(Operation.SHOW, new Show(controller));
		allCommands.put(Operation.SAVE, new Save(controller));
		allCommands.put(Operation.OPEN, new OpenFolder(controller));
		allCommands.put(Operation.EXIT, new Exit());

	}
	public static final void execute(Operation operation) throws InterruptOperationException {
		allCommands.get(operation).execute();
	}
	
	
	
	

}
