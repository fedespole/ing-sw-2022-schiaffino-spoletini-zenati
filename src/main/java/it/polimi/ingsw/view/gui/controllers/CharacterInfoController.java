package it.polimi.ingsw.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Displays the detailed description of the characters' abilities.
 */
public class CharacterInfoController extends GuiController{

    @FXML
    public ImageView c1;
    @FXML
    public ImageView c2;
    @FXML
    public ImageView c3;
    @FXML
    public ImageView c4;
    @FXML
    public ImageView c5;
    @FXML
    public ImageView c6;
    @FXML
    public ImageView c7;
    @FXML
    public ImageView c8;
    @FXML
    public ImageView c9;
    @FXML
    public ImageView c10;
    @FXML
    public ImageView c11;
    @FXML
    public ImageView c12;
    @FXML
    public Label characterEffectText;
    public ImageView closeButton;

    public void textOff(MouseEvent mouseEvent) {
        characterEffectText.setText("");
    }

    public void c1On(MouseEvent mouseEvent) {
        characterEffectText.setText("Take 1 Student from this card and place it on an Island of your choice. Then, draw a new student from the Bag and place it on this card.");
    }


    public void c2On(MouseEvent mouseEvent) {
        characterEffectText.setText("During this turn, you take control of any number of Professors even if you have the same number of Students as the player who currently controls them");
    }

    public void c3On(MouseEvent mouseEvent) {
        characterEffectText.setText("Choose an lsland and resolve the Island as if Mother Nature had ended her movement there. Mother Nature will still move and the Island where she ends her movement will also be resolved.");
    }

    public void c4On(MouseEvent mouseEvent) {
        characterEffectText.setText("You may move Mother Nature up to 2 additional islands than is indicated by the Assistant card you've played.");
    }


    public void c5On(MouseEvent mouseEvent) {
        characterEffectText.setText("Place a No Entry tile on an island of your choice. The first time Mother Nature ends her movement there, put the No Entry tile back onto this card DO NOT calculate influence on that Island, or place any Towers.");
    }


    public void c6On(MouseEvent mouseEvent) {
        characterEffectText.setText("When resolving a Conquering on an Island, towers do not count towards influence.");
    }

    public void c7On(MouseEvent mouseEvent) {
        characterEffectText.setText("You may take up to 3 Students from this card and replace them with the same number of Students from your Entrance.");
    }

    public void c8On(MouseEvent mouseEvent) {
        characterEffectText.setText("During the influence calculation this turn, you count as having 2 more influence.");
    }

    public void c9On(MouseEvent mouseEvent) {
        characterEffectText.setText("Choose a color of Student: during the influence calculation this turn, that color adds no influence.");
    }

    public void c10On(MouseEvent mouseEvent) {
        characterEffectText.setText("You may exchange up to 2 Students between your Entrance and your Dining Room.");
    }

    public void c11On(MouseEvent mouseEvent) {
        characterEffectText.setText("Take 1 Student from this card and place it in your Dining Room. Then, draw a new student from the Bag and place it on this card.");
    }

    public void c12On(MouseEvent mouseEvent) {
        characterEffectText.setText("Choose a type of Student: every player (including yourself) must return 3 Students of that type from their Dining Room to the bag. If any player has fewer than 3 Students of that type, return as many Students as they have.");
    }

    public void closeButtonClicked(MouseEvent mouseEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void closeButtonOn(MouseEvent mouseEvent) {
        closeButton.getScene().setCursor(Cursor.HAND);
    }

    public void closeButtonOff(MouseEvent mouseEvent) {
        closeButton.getScene().setCursor(Cursor.DEFAULT);
    }
}
