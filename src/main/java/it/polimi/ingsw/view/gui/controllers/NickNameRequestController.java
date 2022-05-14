package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.common.events.GameHandler;
import it.polimi.ingsw.common.events.fromClientEvents.PlayerAccessEvent;
import it.polimi.ingsw.common.events.fromServerEvents.RequestNumPlayersEvent;
import it.polimi.ingsw.view.gui.Constants;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.text.html.ImageView;
import java.util.Objects;

public class NickNameRequestController extends GuiController{

    @FXML
    public TextField nicknameTextField;
    @FXML
    public Label errorMessage;
    @FXML
    public Button playButton;   // slow button ON
    @FXML
    public ImageView image;


    private GuiManager guimanager;

    @FXML
    public void initialize(){
        guimanager = GuiManager.getInstance();
    }

    @FXML
    public void loginAction(ActionEvent actionEvent) {
        String chosenNickname= nicknameTextField.getText();
        if(!Objects.equals(chosenNickname, "")){
            this.guimanager.getClient().getClientEvs().add(new PlayerAccessEvent(this, chosenNickname,this.guimanager.getClient().getSocket().toString()));
            playButton.setDisable(true);
            errorMessage.setText(chosenNickname);
            Platform.runLater(() -> guimanager.setFXML(Constants.WAITING_ROOM_SCENE));
        }
        else{
            errorMessage.setText("Type a nickname");
        }
    }

}
