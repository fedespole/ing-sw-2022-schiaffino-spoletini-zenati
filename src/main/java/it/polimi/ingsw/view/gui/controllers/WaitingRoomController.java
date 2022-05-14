package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.common.events.fromServerEvents.RequestNumPlayersEvent;
import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.view.gui.Constants;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class WaitingRoomController extends GuiController{

    private GuiManager guimanager;

    @FXML
    public void initialize(){
        guimanager = GuiManager.getInstance();
    }

    @Override
    public void update(RequestNumPlayersEvent event) {
        if (event.getUsername().equals(guimanager.getOwner())){
            Platform.runLater(() -> guimanager.setFXML(Constants.GAME_SETTINGS_SCENE));
        }
    }

    @Override
    public void update(UpdatedDataEvent event) {
        Platform.runLater(() -> guimanager.setFXML(Constants.PLANNING_SCENE));
    }
}
