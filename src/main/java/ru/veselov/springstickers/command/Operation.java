package ru.veselov.springstickers.command;

public enum Operation {
	INFO,
	CHOOSE,
	SHOW,
	SAVE,
	OPEN,
	EXIT;
	
	public static Operation getOpByOrdinal(Integer i) {
		switch(i) {
		case 0:
			return INFO;
		case 1:
			return CHOOSE;	
		case 2:
			return SHOW;
		case 3:
			return SAVE;
		case 4:
			return OPEN;
		case 5:
			return EXIT;
			
		}
		
	return null;
	

}
}
