package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.common.events.fromServerEvents.TieEvent;
import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.common.events.fromServerEvents.VictoryEvent;
import it.polimi.ingsw.model.basicgame.STATUS;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.view.gui.Constants;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ActionSceneController extends GuiController{

    @FXML
    private Label phaseLabel;
    @FXML
    public ImageView background;

    public GridPane islandsPane;
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
        if(guiManager.getData().getNumPlayers()==3){
            Image image= new Image(GuiManager.class.getResource("/graphics/playerItems/schoolBoard/Plancia_DEF3.png").toString());
            Player2Board.setImage(image);
        }
        this.fillMyDiningRoomAction();
        this.fillOtherPlayersAction();
        this.fillIslands();

        if (guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername())) {
            phaseLabel.setText("Your turn, " + guiManager.getData().getStatusGame().getStatus().toString());

            if (guiManager.getData().getStatusGame().getStatus().equals(STATUS.ACTION_MOVESTUD)){
                //
            }
            else if(guiManager.getData().getStatusGame().getStatus().equals(STATUS.ACTION_MOVEMN)){
                //
            }
            else if(guiManager.getData().getStatusGame().getStatus().equals(STATUS.ACTION_CHOOSECLOUD)){
               //
            }

        }
        else {
            phaseLabel.setText(guiManager.getData().getCurrPlayer().getUsername() + "'s turn");
        }

    }

    private void fillMyDiningRoomAction(){
        for (Player player : guiManager.getData().getPlayers()) {
            if (player.getUsername().equals(guiManager.getOwner())) {
                super.fillMyDiningRoom(player, MyDiningRoom, MyEntrance, MyProfessors, MyTowers);
            }
        }
    }

    private void fillOtherPlayersAction(){
        int flag=0;
        GridPane entrance=Player1Entrance,diningroom=Player1DiningRoom,professors=Player1Professors,towers=Player1Towers;
        for(Player player:guiManager.getData().getPlayers()){
            if(!player.getUsername().equals(guiManager.getOwner())){
                if(flag!=0){
                    entrance=Player2Entrance;
                    diningroom=Player2DiningRoom;
                    professors=Player2Professors;
                    towers=Player2Towers;
                }
                super.fillOtherPlayers(entrance, diningroom, professors, towers, player);
                flag++;
            }
        }
    }

    private void fillIslands(){

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                if(!((j==1 || j==2)&&(i==1 || i==2))){
                    ImageView island = new ImageView(GuiManager.class.getResource("/graphics/pieces/island" + ((i % 3) + 1) + ".png").toString());
                    island.setPreserveRatio(true);
                    island.setFitWidth(80);
                    islandsPane.add(island, i, j);
                    islandsPane.toFront();
                }

            }
        }
    }

    @Override
    public void update(UpdatedDataEvent event) {

        if(guiManager.getData().getStatusGame().getStatus().equals(STATUS.PLANNING))
            Platform.runLater(() -> guiManager.setFXML(Constants.PLANNING_SCENE));
        else
            Platform.runLater(() -> guiManager.setFXML(Constants.ACTION_SCENE));

    }

    public void update(VictoryEvent event){

    }

    public void update(TieEvent event){

    }

}
