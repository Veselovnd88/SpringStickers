package ru.veselov.springstickers.command;

import ru.veselov.springstickers.controllers.ControllerInt;
import ru.veselov.springstickers.exception.InterruptOperationException;
import ru.veselov.springstickers.model.LabelFactory;
import ru.veselov.springstickers.model.LabelSticker;

import java.util.Map;

public class ChooseArt implements Command {
	private ControllerInt controller;
	public ChooseArt(ControllerInt fxController) {
		this.controller = fxController;
	}

	@Override
	public  void execute() throws InterruptOperationException {

		int art = controller.onGetArt();
		int pos = controller.onGetPos();
		Map<Integer, LabelSticker> map = controller.getModel().getMap();
		String serial = controller.onReadSerial();
		if(serial==null) {
			return;
		}
		if(!controller.checkForRewriting())
		{
			return;//Проверка есть ли позиция или нет
		}

		String name="";
		String range="";
		String pinout="";
		
		if(art==1) {
			name = "ADZ-SML-20.11";
			range = "1 MPa    0.5...5V";
			pinout = "1+, 2-, 3 Out, 4 Gehause";}
		if(art==2) {
			name = "ADZ-SML-20.11";
			range = "10 bar    0.5...5V";
			pinout = "1+, 2-, 3 Out, 4 Gehause";
		}
		if (art==3) {
			name = "ADZ-SML-20.11";
			range = "6 bar    0.5...5V";
			pinout = "3+, 2-, 1 Out";
		}
		if (art==4) {
			name = "ADZ-SML-20.11";
			range = "0.6 MPa    0.5...5V";
			pinout = "1+, 2-, 3 Out, 4 Gehause";
		}

			LabelSticker lab = LabelFactory.getLabel(name,range,pinout,serial);
			controller.getModel().getMap().put(pos, lab);	
			controller.sendMessage(String.format("Размещена позиция %s %s %s"//output to chosen source
					,pos, map.get(pos).getName(), map.get(pos).getSerial()));
	}

}