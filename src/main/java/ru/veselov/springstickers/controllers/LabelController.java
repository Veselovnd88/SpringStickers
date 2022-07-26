package ru.veselov.springstickers.controllers;

import org.springframework.stereotype.Component;
import ru.veselov.springstickers.exception.InterruptOperationException;
import ru.veselov.springstickers.model.MainModelLabel;
import ru.veselov.springstickers.model.ModelLabel;

@Component
public class LabelController implements ControllerInt{
    ModelLabel modelLabel;

    public LabelController(){
        this.modelLabel=new MainModelLabel();
    }
    @Override
    public int onGetArt() throws InterruptOperationException {
        return 0;
    }

    @Override
    public void onSave() throws InterruptOperationException {

    }

    @Override
    public int onGetPos() throws InterruptOperationException {
        return 0;
    }

    @Override
    public ModelLabel getModel() {
        return null;
    }

    @Override
    public boolean onYesOrNo() throws InterruptOperationException {
        return false;
    }

    @Override
    public String onReadSerial() throws InterruptOperationException {
        return null;
    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public boolean checkForRewriting() throws InterruptOperationException {
        return false;
    }
}
