package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.common.events.fromServerEvents.RequestNumPlayersEvent;
import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.fxml.FXML;

public class GuiController {
    public GuiManager guiManager;

    @FXML
    public void initialize(){
        guiManager = GuiManager.getInstance();
    }
    public void update(RequestNumPlayersEvent event) {
    }
    public void update(UpdatedDataEvent event){

    }
}
