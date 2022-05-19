package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.common.events.fromClientEvents.MoveStudentToIslandEvent;
import it.polimi.ingsw.common.events.fromServerEvents.RequestNumPlayersEvent;
import it.polimi.ingsw.common.events.fromServerEvents.TieEvent;
import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.common.events.fromServerEvents.VictoryEvent;
import it.polimi.ingsw.model.basicgame.*;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GuiController {
    public GuiManager guiManager;

    @FXML
    public void initialize(){
        guiManager = GuiManager.getInstance();
    }

    public void fillPlayerItems(GridPane entrance, GridPane diningroom, GridPane professors, GridPane towers, Player player, Label coins) {
        for (int i=0;i<5;i++){
            for(int j=0;j<player.getMySchoolBoard().getDiningRoom()[i].size();j++){
                ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+ COLOR.values()[i].toString().toLowerCase()+".png").toString());
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
                    fillElementsOnIsland(islandsPane, islands.get(idCounter), i, j, idCounter);
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

    public void fillElementsOnIsland(GridPane islandsPane, ArrayList<Island> island, int i, int j, int idCounter){

        FlowPane elemPane = new FlowPane();
        islandsPane.add(elemPane, j, i);

        for(int x=0;x<island.get(0).getStudents().size();x++){
            COLOR color = island.get(0).getStudents().get(x).getColor();
            ImageView imageViewS = new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+color.toString().toLowerCase()+".png").toString());
            imageViewS.setPreserveRatio(true);
            imageViewS.setFitWidth(20);
            elemPane.getChildren().add(imageViewS);
        }

        if(island.get(0).getTower()!=null) {
            TEAM team = island.get(0).getTower().getColor();
            ImageView imageViewT = new ImageView(GuiManager.class.getResource("/graphics/pieces/" + team.toString().toLowerCase() + "_tower.png").toString());
            imageViewT.setPreserveRatio(true);
            imageViewT.setFitWidth(60);
            elemPane.getChildren().add(imageViewT);
        }

        if(guiManager.getData().getMotherNature()==idCounter){
            ImageView imageViewM = new ImageView(GuiManager.class.getResource("/graphics/pieces/mother_nature.png").toString());
            imageViewM.setPreserveRatio(true);
            imageViewM.setFitWidth(60);
            elemPane.getChildren().add(imageViewM);
            imageViewM.toFront();
        }

        if(island.get(0).isNoEntry()){
            ImageView imageViewN = new ImageView(GuiManager.class.getResource("/graphics/pieces/deny_island_icon.png").toString());
            imageViewN.setPreserveRatio(true);
            imageViewN.setFitWidth(60);
            elemPane.getChildren().add(imageViewN);
            imageViewN.toFront();
        }

        if(guiManager.getData().getStatusGame().getStatus().equals(STATUS.ACTION_MOVESTUD)) {
            //island able to receive dragOver
            elemPane.setOnDragOver(event -> {
                System.out.println("onDragOver");
                elemPane.getScene().setCursor(Cursor.NONE);
                if (event.getGestureSource() != elemPane) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            });

            elemPane.setOnDragEntered(event -> {
                        System.out.println("onDragEntered");
                        if (event.getGestureSource() != elemPane) {
                            elemPane.setOpacity(0.5);
                        }
                        event.consume();
                    }
            );

            elemPane.setOnDragExited(event -> {
                elemPane.getScene().setCursor(Cursor.DEFAULT);
                elemPane.setOpacity(1);
                event.consume();
            });

            elemPane.setOnDragDropped(event -> {

                System.out.println(event.getDragboard().getString());
                String color = event.getDragboard().getString();
                int a = Integer.parseInt(color);
                this.guiManager.getClient().getClientEvs().add(new MoveStudentToIslandEvent(this, a, idCounter));
                event.setDropCompleted(true);
                elemPane.getScene().setCursor(Cursor.DEFAULT);
                event.consume();
            });

            elemPane.setOnDragDone(event -> {
                System.out.println("onDragDone");
                event.consume();
            });
        }

        elemPane.alignmentProperty().setValue(Pos.CENTER);
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
