package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.common.events.fromClientEvents.SelectedGameSetUpEvent;
import it.polimi.ingsw.view.gui.Constants;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;

public class GameSettingsController extends GuiController{
    @FXML
    public RadioButton button2;
    @FXML
    public RadioButton button3;
    @FXML
    public RadioButton buttonBasic;
    @FXML
    public RadioButton buttonExpert;
    @FXML
    public ImageView background;
    @FXML
    public Button buttonPlay;
    @FXML
    public Label nickNameLabel;

    private boolean is3players;
    private boolean isExpert;


    @FXML
    public void initialize(){
        super.initialize();
        String nickName = guiManager.getOwner();
        nickNameLabel.setText("YOUR CHOSEN NICKNAME IS: " + nickName);
    }

    public void button2Selected(ActionEvent actionEvent) {
        is3players = false;
    }

    public void button3Selected(ActionEvent actionEvent) {
        is3players = true;
    }

    public void buttonBasicSelected(ActionEvent actionEvent) {
        isExpert = false;
    }

    public void buttonExpertSelected(ActionEvent actionEvent) {
        isExpert = true;
    }

    public void buttonPlaySelected(ActionEvent actionEvent) {
        guiManager.getClient().getClientEvs().add(new SelectedGameSetUpEvent(guiManager.getOwner(), is3players ? 3 : 2, isExpert));
        buttonPlay.setDisable(true);
        Platform.runLater(() -> guiManager.setFXML(Constants.WAITING_ROOM_SCENE));
    }
}
