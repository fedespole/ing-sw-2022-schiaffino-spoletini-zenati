package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.view.gui.Constants;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.io.File;

public class ActionSceneController extends GuiController{

    private GuiManager guimanager;
    @FXML
    private Label phaseLabel;
    @FXML
    public ImageView background;
    @FXML
    private FlowPane boardsPane;
    @FXML
    private FlowPane islandsPane;

    @FXML
    public void initialize(){
        guimanager = GuiManager.getInstance();

        for (int i = 0; i < guimanager.getData().getIslands().size(); i++) {

            ImageView island = new ImageView();
            island.setFitWidth(150);
            island.setFitHeight(0);
            island.setImage(new Image(new File("@../graphics/pieces/island1.png").toURI().toString()));
            islandsPane.getChildren().add(island);

        }

        for (int i = 0; i < guimanager.getData().getNumPlayers(); i++) {

            ImageView board = new ImageView();
            board.setFitWidth(250);
            board.setFitHeight(100);
            board.setImage(new Image(new File("@../graphics/.png").toURI().toString()));
            boardsPane.getChildren().add(board);
        }


    }


    @Override
    public void update(UpdatedDataEvent event) {

        phaseLabel.setText("Current phase is " + guimanager.getData().getStatusGame().toString());


    }


}
