package ru.veselov.springstickers.model;

import java.awt.Image;

public abstract class AbstractLabel {
	
	protected String name;
	protected String range;
	protected String pinout;
	protected String serial;
	protected final String MANUFACTURER = "ADZ NAGANO GmbH";
	
	public abstract Image createImage();

}
