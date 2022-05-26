package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.GuiManager;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.lang.reflect.Method;

import static java.lang.Thread.sleep;

public class EndSceneController extends GuiController{

    @FXML
    public Label endMessage;

    @FXML
    @Override
    public void initialize() {
        super.initialize();

        if(guiManager.getOwner().equals(guiManager.getData().getWinner())) {
            endMessage.setText("YOU WON!");
            Media media = new Media(GuiManager.class.getResource("/graphics/WinningAudio.mp3").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
        }
        else if(guiManager.getData().getTiePlayers()!=null &&  guiManager.getData().getTiePlayers().contains(guiManager.getOwner())) {
            endMessage.setText("YOU WON!");
            Media media = new Media(GuiManager.class.getResource("/graphics/WinningAudio.mp3").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
        }
        else {
            if(guiManager.getData().getTiePlayers()!=null && guiManager.getData().getTiePlayers().size()>0)
                endMessage.setText("Players "+ guiManager.getData().getTiePlayers().get(0) + " and " + guiManager.getData().getTiePlayers().get(1) +" won!");
            else
                endMessage.setText("Player " + guiManager.getData().getWinner() + " won!");
            Media media = new Media(GuiManager.class.getResource("/graphics/LosingAudio.mp3").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
        }
    }
}
