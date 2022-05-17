package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.Professor;
import it.polimi.ingsw.model.basicgame.TEAM;
import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.view.gui.Constants;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.io.File;

public class ActionSceneController extends GuiController{

    @FXML
    private Label phaseLabel;
    @FXML
    public ImageView background;
    @FXML
    private FlowPane boardsPane;
    @FXML
    private FlowPane islandsPane;
    public GridPane MyDiningRoom;
    public GridPane MyEntrance;
    public GridPane MyProfessors;
    public GridPane MyTowers;
    public GridPane Player1DiningRoom;
    public GridPane Player1Professors;
    public GridPane Player1Entrance;
    public GridPane Player1Towers;
    public GridPane Player2Entrance;
    public GridPane Player2Towers;
    public GridPane Player2DiningRoom;
    public ImageView Player2Board;
    public GridPane Player2Professors;

    @FXML
    public void initialize(){
        super.initialize();
        guiManager.getStage().setFullScreen(true);
        if(guiManager.getData().getNumPlayers()==3){
            Image image= new Image(GuiManager.class.getResource("/graphics/playerItems/schoolBoard/Plancia_DEF3.png").toString());
            Player2Board.setImage(image);
        }
    /*    this.fillMyDiningRoom();
        this.fillOtherPlayers();
        this.fillIslands();*/

    }

    @Override
    public void update(UpdatedDataEvent event) {

        phaseLabel.setText("Current phase is " + guiManager.getData().getStatusGame().toString());


    }

    private void fillMyDiningRoom(){
        for (int i = 0; i < guiManager.getData().getNumPlayers(); i++) {

            ImageView board = new ImageView(GuiManager.class.getResource("/graphics/playerItems/schoolBoard/Plancia_DEF3.png").toString());
            board.setFitWidth(250);
            board.setFitHeight(100);
            boardsPane.getChildren().add(board);
        }
    }

    private void fillOtherPlayers(){

    }

    private void fillIslands(){
        for (int i = 0; i < guiManager.getData().getIslands().size(); i++) {

            ImageView island = new ImageView(GuiManager.class.getResource("/graphics/pieces/island"+ (i%3)+1 +".png").toString());
            island.setPreserveRatio(true);
            island.setFitWidth(150);
            island.setFitHeight(0);
            islandsPane.getChildren().add(island);
        }
    }

}
