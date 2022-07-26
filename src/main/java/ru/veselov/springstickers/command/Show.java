package ru.veselov.springstickers.command;

import ru.veselov.springstickers.controllers.ControllerInt;
import ru.veselov.springstickers.model.LabelSticker;

import java.util.Map;

public class Show implements Command {
	private ControllerInt controller;
	public Show(ControllerInt fxController) {
		this.controller = fxController;
	}
	@Override
	public void execute() {
		Map<Integer, LabelSticker> map = controller.getModel().getMap();
		if(map.isEmpty()) {
			controller.sendMessage("Еще ничего не добавлено");
			return;
		}
		StringBuilder sb = new StringBuilder("Готово к печати:\n");
		
		//System.out.println("Готово к печати:");
		for(Map.Entry<Integer,LabelSticker> entry: map.entrySet()) {
			sb.append(String.format("Номер позиции %d : Датчик %s %s с номером %s%n", entry.getKey(), 
					entry.getValue().getName(),entry.getValue().getRange(),
					entry.getValue().getSerial()));
			/*System.out.printf("Номер позиции %d : Датчик %s %s с номером %s",entry.getKey(), 
					entry.getValue().getName(),entry.getValue().getRange(),
					entry.getValue().getSerial());	
			System.out.println();*/
		}
		controller.sendMessage(sb.toString());
		
	}

}