package it.polimi.ingsw.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CharacterInfoController extends GuiController{

    @FXML
    public ImageView c1;
    public ImageView c2;
    public ImageView c3;
    public ImageView c4;
    public ImageView c5;
    public ImageView c6;
    public ImageView c7;
    public ImageView c8;
    public ImageView c9;
    public ImageView c10;
    public ImageView c11;
    public ImageView c12;
    public Label effectText;

    public void textOff(MouseEvent mouseEvent) {
        effectText.setText("");
    }

    public void c1On(MouseEvent mouseEvent) {
        effectText.setText("Take 1 Student from this card and place it on an Island of your choice. Then, draw a new student from the Bag and place it on this card.");
    }


    public void c2On(MouseEvent mouseEvent) {
        effectText.setText("During this turn, you take control of any number of Professors even if you have the same number of Students as the player who currently controls them");
    }

    public void c3On(MouseEvent mouseEvent) {
        effectText.setText("Choose an lsland and resolve the Island as if Mother Nature had ended her movement there. Mother Nature will still move and the Island where she ends her movement will also be resolved.");
    }

    public void c4On(MouseEvent mouseEvent) {
        effectText.setText("You may move Mother Nature up to 2 additional islands than is indicated by the Assistant card you've played.");
    }


    public void c5On(MouseEvent mouseEvent) {
        effectText.setText("Place a No Entry tile on an island of your choice. The first time Mother Nature ends her movement there, put the No Entry tile back onto this card DO NOT calculate influence on that Island, or place any Towers.");
    }


    public void c6On(MouseEvent mouseEvent) {
        effectText.setText("When resolving a Conquering on an Island, towers do not count towards influence.");
    }

    public void c7On(MouseEvent mouseEvent) {
        effectText.setText("You may take up to 3 Students from this card and replace them with the same number of Students from your Entrance.");
    }

    public void c8On(MouseEvent mouseEvent) {
        effectText.setText("During the influence calculation this turn, you count as having 2 more influence.");
    }

    public void c9On(MouseEvent mouseEvent) {
        effectText.setText("Choose a color of Student: during the influence calculation this turn, that color adds no influence.");
    }

    public void c10On(MouseEvent mouseEvent) {
        effectText.setText("You may exchange up to 2 Students between your Entrance and your Dining Room.");
    }

    public void c11On(MouseEvent mouseEvent) {
        effectText.setText("Take 1 Student from this card and place it in your Dining Roam. Then, draw a new student from the Bag and place it on this card.");
    }

    public void c12On(MouseEvent mouseEvent) {
        effectText.setText("Choose a type of Student: every player (including yourself) must return 3 Students of that type from their Dining Room to the bag. If any player has fewer than 3 Students of that type, return as many Students as they have.");
    }

}
