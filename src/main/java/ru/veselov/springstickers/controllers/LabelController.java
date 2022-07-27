package ru.veselov.springstickers.controllers;

import org.springframework.stereotype.Component;
import ru.veselov.springstickers.command.CommandExecutor;
import ru.veselov.springstickers.command.Operation;
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
        return 0;// not used
    }

    @Override
    public void onSave() throws InterruptOperationException {
        CommandExecutor.execute(Operation.SAVE);
    }

    @Override
    public int onGetPos() throws InterruptOperationException {
        return 0;//not used
    }

    @Override
    public ModelLabel getModel() {
        return this.modelLabel;
    }

    @Override
    public boolean onYesOrNo() throws InterruptOperationException {
        return false;
    }

    @Override
    public String onReadSerial() throws InterruptOperationException {
        return null;//not used
    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public boolean checkForRewriting() throws InterruptOperationException {
        return false;
    }

    @Override
    public void onDelete() {

    }
}
