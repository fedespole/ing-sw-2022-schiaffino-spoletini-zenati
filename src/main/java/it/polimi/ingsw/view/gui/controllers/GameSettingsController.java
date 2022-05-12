package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.common.events.fromClientEvents.SelectedGameSetUpEvent;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.Locale;

public class GameSettingsController extends GuiController{
    @FXML
    public Button button2;
    @FXML
    public Button button3;
    @FXML
    public Button buttonBasic;
    @FXML
    public ImageView background;
    @FXML
    public Button buttonPlay;
    @FXML
    public Button buttonExpert;
    @FXML
    public Label nickNameLabel;

    private boolean is3players;
    private boolean isExpert;

    private GuiManager guimanager;

    @FXML
    public void initialize(){
        guimanager = GuiManager.getInstance();
        String nickName = guimanager.getOwner();
        nickNameLabel.setText("YOUR CHOSEN NICKNAME IS: " + nickName);
    }

    public void button2Selected(ActionEvent actionEvent) {
        button3.setDefaultButton(false);
        is3players = false;
    }

    public void button3Selected(ActionEvent actionEvent) {
        button2.setDefaultButton(false);
        is3players = true;
    }

    public void buttonBasicSelected(ActionEvent actionEvent) {
        buttonExpert.setDefaultButton(false);
        isExpert = false;
    }

    public void buttonExpertSelected(ActionEvent actionEvent) {
        buttonBasic.setDefaultButton(false);
        isExpert = true;
    }

    public void buttonPlaySelected(ActionEvent actionEvent) {
        guimanager.getClient().getClientEvs().add(new SelectedGameSetUpEvent(guimanager.getOwner(), is3players ? 3 : 2, isExpert));
        buttonPlay.setDisable(true);
    }
}
