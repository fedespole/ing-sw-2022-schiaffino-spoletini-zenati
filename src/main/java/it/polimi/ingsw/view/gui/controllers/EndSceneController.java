package it.polimi.ingsw.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EndSceneController extends GuiController{

    @FXML
    public Label endMessage;

    @FXML
    @Override
    public void initialize() {
        super.initialize();

        if(guiManager.getOwner().equals(guiManager.getData().getWinner()))
            endMessage.setText("YOU WON!");
        else if(guiManager.getData().getTiePlayers().contains(guiManager.getOwner()))
            endMessage.setText("YOU WON!");
        else {
            if(guiManager.getData().getTiePlayers().size()>0)
                endMessage.setText("Players "+ guiManager.getData().getTiePlayers().get(0) + " and " + guiManager.getData().getTiePlayers().get(1) +" won!");
            else
                endMessage.setText("Player " + guiManager.getData().getWinner() + " won!");
        }
    }
}
