package ru.veselov.springstickers.controllers;

import ru.veselov.springstickers.model.MainModelLabel;
import ru.veselov.springstickers.model.ModelLabel;

//это не должно быть компонент
public class LabelController implements ControllerInt{
    ModelLabel modelLabel;
public LabelController(){
        this.modelLabel=new MainModelLabel();
    }

    @Override
    public ModelLabel getModel() {
        return this.modelLabel;
    }

}
