package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.common.events.fromClientEvents.DrawAssistantCardEvent;
import it.polimi.ingsw.common.events.fromClientEvents.charactersEvents.*;
import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.model.basicgame.*;
import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.characters.*;
import it.polimi.ingsw.model.expertgame.characters.Character;
import it.polimi.ingsw.view.gui.Constants;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class PlanningController extends GuiController{
    public FlowPane AssistantCards;
    public GridPane MyDiningRoom;
    public GridPane MyEntrance;
    public GridPane MyProfessors;
    public GridPane MyTowers;
    public GridPane Player1DiningRoom;
    public GridPane Player1Professors;
    public GridPane Player1Entrance;
    public GridPane Player1Towers;
    public ImageView Player1AssistantCard;
    public GridPane Player2Entrance;
    public GridPane Player2Towers;
    public GridPane Player2DiningRoom;
    public ImageView Player2Board;
    public GridPane Player2Professors;
    public ImageView Player2AssistantCard;
    public Label Title;
    public ImageView ChosenCard;
    public GridPane islandsPane;
    public Label Player1Coins;
    public Label Player2Coins;
    public Label MyCoins;
    public TilePane clouds0Pane;
    public TilePane clouds1Pane;
    public TilePane clouds2Pane;
    public ImageView cloud2;
    public ImageView cloud0;
    public ImageView cloud1;

    @FXML
    @Override
    public void initialize() {
        super.initialize();
        if(guiManager.getData().getNumPlayers()==3){
            Image image= new Image(GuiManager.class.getResource("/graphics/playerItems/schoolBoard/Plancia_DEF3.png").toString());
            Player2Board.setImage(image);
        }
        if(guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername()))
            Title.setText("CHOOSE YOUR ASSISTANT CARD");
        else
            Title.setText(guiManager.getData().getCurrPlayer().getUsername() + "'s turn");

        this.fillMyDiningRoomPlanning();
        this.fillOtherPlayersPlanning();
        this.addAvailableAssistantCards();
        super.fillIslands(islandsPane, 150.0, 90.0, guiManager.getData().getIslands());
        super.fillCloud(clouds0Pane, 0);
        super.fillCloud(clouds1Pane, 1);
        if(guiManager.getData().getNumPlayers()==3){
            Image image= new Image(GuiManager.class.getResource("/graphics/pieces/cloud_card.png").toString());
            cloud2.setImage(image);
            super.fillCloud(clouds2Pane, 2);
        }

        if(guiManager.getData().isExpert())
            super.setCharacters();
    }

    public void mouseOnAssistants(MouseEvent mouseEvent) {
        if(guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername()))
            AssistantCards.getScene().setCursor(Cursor.HAND);
        AssistantCards.alignmentProperty().setValue(Pos.TOP_CENTER);
    }


    public void mouseOffAssistants(MouseEvent mouseEvent) {
        AssistantCards.alignmentProperty().setValue(Pos.BOTTOM_CENTER);
        if(AssistantCards.getScene()!=null)
            AssistantCards.getScene().setCursor(Cursor.DEFAULT);
    }

    private void addAvailableAssistantCards() {
        for (Player player : guiManager.getData().getPlayers()) {
            if(player.getUsername().equals(guiManager.getOwner())) {
                for (AssistantCard assistantCard : player.getMyDeck().getCards()) {
                    ImageView imageView = new ImageView(GuiManager.class.getResource("/graphics/playerItems/deck/assistantCards/Assistente ("+assistantCard.getValue()+").png").toString());
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(100);
                    imageView.setOnMouseEntered(this::mouseOnAssistants);
                    imageView.setOnMouseExited(this::mouseOffAssistants);
                    if(player.getUsername().equals(guiManager.getData().getCurrPlayer().getUsername()))
                        imageView.setOnMouseClicked(this::mouseClickedAssistant);
                    imageView.setId(Integer.toString(assistantCard.getValue()));
                    AssistantCards.getChildren().add(imageView);
                    AssistantCards.toFront();
                }
            }
        }
    }

    private void fillMyDiningRoomPlanning(){

        for (Player player : guiManager.getData().getPlayers()) {
            if (player.getUsername().equals(guiManager.getOwner())) {
                super.fillPlayerItems(MyEntrance, MyDiningRoom, MyProfessors, MyTowers, player, MyCoins);
                if(player.getChosenCard()!=null) {
                    Image image = new Image(GuiManager.class.getResource("/graphics/playerItems/deck/assistantCards/Assistente (" + player.getChosenCard().getValue() + ").png").toString());
                    ChosenCard.setImage(image);
                }
            }
        }
    }

    private void fillOtherPlayersPlanning(){
        int flag=0;
        GridPane entrance=Player1Entrance,diningroom=Player1DiningRoom,professors=Player1Professors,towers=Player1Towers;
        ImageView assistantCard=Player1AssistantCard;
        Label coins=Player1Coins;
        for(Player player:guiManager.getData().getPlayers()){
            if(!player.getUsername().equals(guiManager.getOwner())){
                if(flag!=0){
                    entrance=Player2Entrance;
                    diningroom=Player2DiningRoom;
                    professors=Player2Professors;
                    towers=Player2Towers;
                    assistantCard=Player2AssistantCard;
                    coins=Player2Coins;
                }
                super.fillPlayerItems(entrance, diningroom, professors, towers, player,coins);
                if(player.getChosenCard()!=null){
                    Image image= new Image(GuiManager.class.getResource("/graphics/playerItems/deck/assistantCards/Assistente ("+player.getChosenCard().getValue()+").png").toString());
                    assistantCard.setImage(image);
                }
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
}