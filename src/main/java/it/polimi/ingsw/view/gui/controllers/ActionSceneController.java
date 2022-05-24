package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.common.events.fromClientEvents.*;
import it.polimi.ingsw.common.events.fromClientEvents.charactersEvents.*;
import it.polimi.ingsw.common.events.fromServerEvents.TieEvent;
import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.common.events.fromServerEvents.VictoryEvent;
import it.polimi.ingsw.model.basicgame.*;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.characters.*;
import it.polimi.ingsw.model.expertgame.characters.Character;
import it.polimi.ingsw.view.gui.Constants;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ActionSceneController extends GuiController{

    @FXML
    private Label phaseLabel;
    public TilePane clouds0Pane;
    public TilePane clouds1Pane;
    public TilePane clouds2Pane;
    public ImageView cloud2;
    public ImageView cloud0;
    public ImageView cloud1;
    public GridPane islandsPane;
    public GridPane MyDiningRoom;
    public GridPane MyEntrance;
    public GridPane MyProfessors;
    public GridPane MyTowers;
    public ImageView Player1Board;
    public GridPane Player1DiningRoom;
    public GridPane Player1Professors;
    public GridPane Player1Entrance;
    public GridPane Player1Towers;
    public GridPane Player2Entrance;
    public GridPane Player2Towers;
    public GridPane Player2DiningRoom;
    public ImageView Player2Board;
    public GridPane Player2Professors;
    public ImageView CharacterBack;
    public FlowPane Characters;
    public Label Player1Coins;
    public Label Player2Coins;
    public Label MyCoins;
    public AnchorPane CharactersAnchorPane;

    @FXML
    @Override
    public void initialize(){
        super.initialize();
        if(guiManager.getData().getNumPlayers()==3){
            Image image= new Image(GuiManager.class.getResource("/graphics/playerItems/schoolBoard/Plancia_DEF3.png").toString());
            Player2Board.setImage(image);
        }

        this.fillMyDiningRoomAction();
        this.fillOtherPlayersAction();
        super.fillIslands(islandsPane, 190.0, 140.0, guiManager.getData().getIslands());
        super.fillCloud(clouds0Pane, 0);
        super.fillCloud(clouds1Pane, 1);
        if(guiManager.getData().getNumPlayers()==3){
            Image image= new Image(GuiManager.class.getResource("/graphics/pieces/cloud_card.png").toString());
            cloud2.setImage(image);
            super.fillCloud(clouds2Pane, 2);
        }

        if(guiManager.getData().isExpert())
            super.setCharacters();

        if (guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername())) {
            //Mydining able to receive drag if i'm currPlayer
            if (guiManager.getData().getStatusGame().getStatus().equals(STATUS.ACTION_MOVESTUD)){
                phaseLabel.setText("Your turn, move a student.");
                MyDiningRoom.setOnDragOver(event -> {

                    MyDiningRoom.getScene().setCursor(Cursor.NONE);
                    if (event.getGestureSource() != MyDiningRoom) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }
                    event.consume();
                });

                MyDiningRoom.setOnDragEntered(event->{
                    if (event.getGestureSource() != MyDiningRoom) {
                        MyDiningRoom.setOpacity(0.5);
                    }
                    event.consume();
                });

                MyDiningRoom.setOnDragExited(event -> {
                    MyDiningRoom.getScene().setCursor(Cursor.DEFAULT);
                    MyDiningRoom.setOpacity(1);
                    event.consume();
                });

                MyDiningRoom.setOnDragDropped(event -> {
                    String color = event.getDragboard().getString();
                    int a = Integer.parseInt(color);
                    this.guiManager.getClient().getClientEvs().add(new MoveStudentToDiningEvent(this, a));
                    event.setDropCompleted(true);
                    MyDiningRoom.getScene().setCursor(Cursor.DEFAULT);
                    event.consume();
                });

                MyDiningRoom.setOnDragDone(event -> {
                    event.consume();
                });
            }

            else if(guiManager.getData().getStatusGame().getStatus().equals(STATUS.ACTION_MOVEMN)){

                phaseLabel.setText("Click on island to move Mother Nature");

            }

            else if(guiManager.getData().getStatusGame().getStatus().equals(STATUS.ACTION_CHOOSECLOUD)){
                phaseLabel.setText("Choose a Cloud");
                clouds0Pane.toFront();
                clouds1Pane.toFront();
                //cloud0 onMouse
                clouds0Pane.setOnMouseEntered(event -> {
                    guiManager.getStage().getScene().setCursor(Cursor.HAND);
                });
                clouds0Pane.setOnMouseExited(event -> {
                    guiManager.getStage().getScene().setCursor(Cursor.DEFAULT);
                });
                clouds0Pane.setOnMouseClicked(event -> {
                    this.guiManager.getClient().getClientEvs().add(new ChooseCloudEvent(this, 0));
                });

                //cloud1 onMouse
                clouds1Pane.setOnMouseEntered(event -> {
                    guiManager.getStage().getScene().setCursor(Cursor.HAND);
                });
                clouds1Pane.setOnMouseExited(event -> {
                    guiManager.getStage().getScene().setCursor(Cursor.DEFAULT);
                });
                clouds1Pane.setOnMouseClicked(event -> {
                    this.guiManager.getClient().getClientEvs().add(new ChooseCloudEvent(this, 1));
                });

                if(guiManager.getData().getNumPlayers()==3){
                    clouds2Pane.toFront();
                    clouds2Pane.setOnMouseEntered(event -> {
                        guiManager.getStage().getScene().setCursor(Cursor.HAND);
                    });
                    clouds2Pane.setOnMouseExited(event -> {
                        guiManager.getStage().getScene().setCursor(Cursor.DEFAULT);
                    });
                    clouds2Pane.setOnMouseClicked(event -> {
                        this.guiManager.getClient().getClientEvs().add(new ChooseCloudEvent(this, 2));
                    });
                }

            }

        }
        else {
            phaseLabel.setText(guiManager.getData().getCurrPlayer().getUsername() + "'s turn");
        }

    }

    private void fillMyDiningRoomAction(){
        for (Player player : guiManager.getData().getPlayers()) {
            if (player.getUsername().equals(guiManager.getOwner())) {
                for (int i=0;i<5;i++){
                    for(int j=0;j<player.getMySchoolBoard().getDiningRoom()[i].size();j++){
                        ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+ COLOR.values()[i].toString().toLowerCase()+".png").toString());
                        imageView.setPreserveRatio(true);
                        imageView.setFitWidth(20);
                        MyDiningRoom.add(imageView,j,i);
                    }
                }
                for(int i=0;i<player.getMySchoolBoard().getEntrance().size();i++){
                    COLOR color=player.getMySchoolBoard().getEntrance().get(i).getColor();
                    ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+color.toString().toLowerCase()+".png").toString());
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(20);
                    //students draggable if I'm currPlayer
                    if(guiManager.getData().getStatusGame().getStatus().equals(STATUS.ACTION_MOVESTUD) &&  guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername())) {
                        imageView.setOnMouseEntered(event -> {
                            imageView.getScene().setCursor(Cursor.HAND);
                        });
                        imageView.setOnMouseExited(event -> {
                            imageView.getScene().setCursor(Cursor.HAND);
                        });
                        imageView.setOnDragDetected(mouseEvent -> {
                            Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
                            ClipboardContent content = new ClipboardContent();
                            content.putImage(imageView.getImage());
                            int a = COLOR.valueOf(color.toString()).ordinal();
                            String c = Integer.toString(a);
                            content.putString(c);
                            db.setContent(content);
                            mouseEvent.consume();
                        });
                    }

                    if(i%2==0)
                        MyEntrance.add(imageView,1,i/2);
                    else
                        MyEntrance.add(imageView,0,i/2+1);
                }
                for(Professor professor: player.getMySchoolBoard().getProfessors()){
                    COLOR color=professor.getColor();
                    ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/teacher_"+color.toString().toLowerCase()+".png").toString());
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(20);
                    imageView.setRotate(29.7);
                    MyProfessors.add(imageView,0,color.ordinal());
                }
                for(int i=0;i<player.getMySchoolBoard().getTowers().size();i++){
                    TEAM team=player.getTeam();
                    ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/"+team.toString().toLowerCase()+"_tower.png").toString());
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(60);
                    if(i%2==0)
                        MyTowers.add(imageView,0,i/2);
                    else
                        MyTowers.add(imageView,1,i/2);
                }
                if(guiManager.getData().isExpert() && MyCoins!=null)
                    MyCoins.setText("COINS: "+player.getCoins());

                MyDiningRoom.toFront();
                MyEntrance.toFront();
                MyProfessors.toFront();
                MyTowers.toFront();
            }
        }
    }

    private void fillOtherPlayersAction(){
        int flag=0;
        GridPane entrance=Player1Entrance,diningroom=Player1DiningRoom,professors=Player1Professors,towers=Player1Towers;
        Label coins=Player1Coins;
        for(Player player:guiManager.getData().getPlayers()){
            if(!player.getUsername().equals(guiManager.getOwner())){
                if(flag!=0){
                    entrance=Player2Entrance;
                    diningroom=Player2DiningRoom;
                    professors=Player2Professors;
                    towers=Player2Towers;
                    coins=Player2Coins;
                    Player2DiningRoom.toFront();
                    Player2Entrance.toFront();
                    Player2Professors.toFront();
                    Player2Towers.toFront();
                    Player2Coins.toFront();
                }
                super.fillPlayerItems(entrance, diningroom, professors, towers, player,coins);
                Player1DiningRoom.toFront();
                Player1Entrance.toFront();
                Player1Professors.toFront();
                Player1Towers.toFront();
                Player1Coins.toFront();;
                flag++;
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

    @Override
    public void update(VictoryEvent event){
        Platform.runLater(() -> guiManager.setFXML(Constants.END_SCENE));
    }

    @Override
    public void update(TieEvent event){
        Platform.runLater(() -> guiManager.setFXML(Constants.END_SCENE));
    }

}
