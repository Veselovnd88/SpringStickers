package ru.veselov.springstickers.controllers;

import org.springframework.stereotype.Component;
import ru.veselov.springstickers.command.CommandExecutor;
import ru.veselov.springstickers.command.Operation;
import ru.veselov.springstickers.exception.InterruptOperationException;
import ru.veselov.springstickers.model.MainModelLabel;
import ru.veselov.springstickers.model.ModelLabel;

@Component
public class LabelController implements ControllerInt{//вот это должно помещаться в новый тред
    //при гет запросе вся это история должна создаваться в новом треде
    ModelLabel modelLabel;


    public LabelController(){
        this.modelLabel=new MainModelLabel();
        CommandExecutor.init(this);

    }
    @Override
    public int onGetArt() throws InterruptOperationException {
        return modelLabel.getArt();
    }

    @Override
    public void onSave() throws InterruptOperationException {
        CommandExecutor.execute(Operation.SAVE);
    }

    @Override
    public int onGetPos() throws InterruptOperationException {
        return modelLabel.getPos();
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

        return modelLabel.getSerial();
    }

    @Override
    public String sendMessage(String message) {
        return message;
    }

    @Override
    public boolean checkForRewriting() throws InterruptOperationException {
        return true;
    }

    @Override
    public void onDelete(int pos) {
        getModel().getMap().remove(pos);
    }
}
