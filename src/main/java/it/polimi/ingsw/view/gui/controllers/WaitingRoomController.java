package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.common.events.fromServerEvents.RequestNumPlayersEvent;
import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.model.basicgame.STATUS;
import it.polimi.ingsw.view.gui.Constants;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class WaitingRoomController extends GuiController{

    @FXML
    public void initialize(){
        super.initialize();
    }

    @Override
    public void update(RequestNumPlayersEvent event) {
        if (event.getUsername().equals(guiManager.getOwner())){
            Platform.runLater(() -> guiManager.setFXML(Constants.GAME_SETTINGS_SCENE));
        }
    }

    @Override
    public void update(UpdatedDataEvent event) {
        if(event.getViewData().getStatusGame().getStatus().equals(STATUS.PLANNING))
            Platform.runLater(() -> guiManager.setFXML(Constants.PLANNING_SCENE));
        else
            Platform.runLater(() -> guiManager.setFXML(Constants.ACTION_SCENE));
    }
}
