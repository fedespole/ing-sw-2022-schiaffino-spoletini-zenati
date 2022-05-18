package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.common.events.fromServerEvents.RequestNumPlayersEvent;
import it.polimi.ingsw.common.events.fromServerEvents.TieEvent;
import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.common.events.fromServerEvents.VictoryEvent;
import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.Island;
import it.polimi.ingsw.model.basicgame.Professor;
import it.polimi.ingsw.model.basicgame.TEAM;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.view.gui.Constants;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GuiController {
    public GuiManager guiManager;

    @FXML
    public void initialize(){
        guiManager = GuiManager.getInstance();
    }

    public void fillMyDiningRoom(Player player, GridPane myDiningRoom, GridPane myEntrance, GridPane myProfessors, GridPane myTowers,Label myCoins) {
        for (int i=0;i<5;i++){
            for(int j=0;j<player.getMySchoolBoard().getDiningRoom()[i].size();j++){
                ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+ COLOR.values()[i].toString().toLowerCase()+".png").toString());
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(20);
                myDiningRoom.add(imageView,i,j);
            }
        }
        for(int i=0;i<player.getMySchoolBoard().getEntrance().size();i++){
            COLOR color=player.getMySchoolBoard().getEntrance().get(i).getColor();
            ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+color.toString().toLowerCase()+".png").toString());
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(20);
            if(i%2==0)
                myEntrance.add(imageView,1,i/2);
            else
                myEntrance.add(imageView,0,i/2+1);
        }
        for(Professor professor: player.getMySchoolBoard().getProfessors()){
            COLOR color=professor.getColor();
            ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/teacher_"+color.toString().toLowerCase()+".png").toString());
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(20);
            imageView.setRotate(29.7);
            myProfessors.add(imageView,0,color.ordinal());
        }
        for(int i=0;i<player.getMySchoolBoard().getTowers().size();i++){
            TEAM team=player.getTeam();
            ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/"+team.toString().toLowerCase()+"_tower.png").toString());
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(60);
            if(i%2==0)
                myTowers.add(imageView,0,i/2);
            else
                myTowers.add(imageView,1,i/2);
        }
        if(guiManager.getData().isExpert() && myCoins!=null)
            myCoins.setText("COINS: "+player.getCoins());
    }

    public void fillOtherPlayers(GridPane entrance, GridPane diningroom, GridPane professors, GridPane towers, Player player, Label coins) {
        for(int i=0;i<5;i++){
            player.getMySchoolBoard().addProfessor(new Professor(COLOR.values()[i]));
        }
        for (int i=0;i<5;i++){
            for(int j=0;j<player.getMySchoolBoard().getDiningRoom()[i].size();j++){
                ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+COLOR.values()[i].toString().toLowerCase()+".png").toString());
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(20);
                diningroom.add(imageView,i,j);
            }
        }
        for(int i=0;i<player.getMySchoolBoard().getEntrance().size();i++){
            COLOR color=player.getMySchoolBoard().getEntrance().get(i).getColor();
            ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+color.toString().toLowerCase()+".png").toString());
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(20);
            if(i%2==0)
                entrance.add(imageView,1,i/2);
            else
                entrance.add(imageView,0,i/2+1);
        }
        for(Professor professor: player.getMySchoolBoard().getProfessors()){
            COLOR color=professor.getColor();
            ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/teacher_"+color.toString().toLowerCase()+".png").toString());
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(20);
            imageView.setRotate(29.7);
            professors.add(imageView,0,color.ordinal());
        }
        for(int i=0;i<player.getMySchoolBoard().getTowers().size();i++){
            TEAM team=player.getTeam();
            ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/"+team.toString().toLowerCase()+"_tower.png").toString());
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(60);
            if(i%2==0)
                towers.add(imageView,0,i/2);
            else
                towers.add(imageView,1,i/2);
        }
        if(guiManager.getData().isExpert() && coins!=null)
            coins.setText("COINS: "+player.getCoins());
    }

    // Based on islands array dimension, manually displays a different gridpane layout
    public void fillIslands(GridPane islandsPane, double sizeW, double sizeH, ArrayList<ArrayList<Island>> islands){
        // ID are progressive, counting from left to right and from top to bottom
        int idCounter = 0;

        for (int i = 0; i < 4 && idCounter < islands.size(); i++) {
            for (int j = 0; j < 6 && idCounter < islands.size(); j++) {

                if (!(((j == 1 || j == 2 || j == 3 || j == 4) && (i == 1 || i == 2))) && !((i == 0 || i == 3) && (j == 0 || j == 5))) {
                    ImageView islandNode;
           //         if(islands.get(idCounter).size()==1) {
                        islandNode = new ImageView(GuiManager.class.getResource("/graphics/pieces/island" + ((j % 3) + 1) + ".png").toString());
             //       }
            //        else{
            //            islandNode = new ImageView(GuiManager.class.getResource("/graphics/pieces/island" + ((j % 3) + 1) + ".png").toString());
             //       }
                    islandNode.setPreserveRatio(true);
                    islandNode.setFitWidth(sizeW);
                    islandNode.setFitHeight(sizeH);
                    islandsPane.add(islandNode, j, i);
                    islandNode.setId(Integer.toString(idCounter));
                    idCounter++;
                    if (j == 0 || j == 5) {
                        if (i == 1) GridPane.setValignment(islandNode, VPos.BOTTOM);
                        else GridPane.setValignment(islandNode, VPos.TOP);
                    } else GridPane.setValignment(islandNode, VPos.CENTER);
                    GridPane.setHalignment(islandNode, HPos.CENTER);
                    islandsPane.toFront();
                }

            }
        }
    }

    public void update(RequestNumPlayersEvent event) {
    }
    public void update(UpdatedDataEvent event){
    }
    public void update(VictoryEvent event){
    }
    public void update(TieEvent event){
    }
}
