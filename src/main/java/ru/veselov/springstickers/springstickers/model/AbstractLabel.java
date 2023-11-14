package ru.veselov.springstickers.springstickers.model;

import java.awt.Image;

public abstract class AbstractLabel {
	
	protected String name;
	protected String range;
	protected String pinout;
	protected String serial;
	protected String manufacturer = "ADZ NAGANO GmbH";
	protected int id;
	public abstract Image createImage();

}
