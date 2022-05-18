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
    public Label characterEffectetext;

    public void c1On(MouseEvent mouseEvent) {
    }

    public void textOff(MouseEvent mouseEvent) {
        characterEffectetext.setText("");
    }

    public void c2On(MouseEvent mouseEvent) {
    }

    public void c12On(MouseEvent mouseEvent) {
    }
}
