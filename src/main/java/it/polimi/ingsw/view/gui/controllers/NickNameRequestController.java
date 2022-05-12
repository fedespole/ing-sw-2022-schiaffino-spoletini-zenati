package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.common.events.GameHandler;
import it.polimi.ingsw.common.events.fromClientEvents.PlayerAccessEvent;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class NickNameRequestController extends GuiController{

    @FXML
    public TextField nicknameTextField;
    @FXML
    public Label errorMessage;
    @FXML
    public Button playButton;

    private GuiManager guimanager;

    @FXML
    public void initialize(){
        guimanager = GuiManager.getInstance();
    }

    @FXML
    public void loginAction(ActionEvent actionEvent) {
        String chosenNickname= nicknameTextField.getText();
        if(chosenNickname!=null){
        GameHandler.calls(new PlayerAccessEvent(this, chosenNickname,this.guimanager.getClient().getSocket().toString()));
        playButton.setDisable(true);
        }
        else{
            errorMessage.setText("Type a nickname");
        }
    }
}
