package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.common.events.fromServerEvents.RequestNumPlayersEvent;
import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.Professor;
import it.polimi.ingsw.model.basicgame.TEAM;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class GuiController {
    public GuiManager guiManager;

    @FXML
    public void initialize(){
        guiManager = GuiManager.getInstance();
    }

    public void fillMyDiningRoom(Player player, GridPane myDiningRoom, GridPane myEntrance, GridPane myProfessors, GridPane myTowers) {
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
    }

    public void fillOtherPlayers(GridPane entrance, GridPane diningroom, GridPane professors, GridPane towers, Player player) {
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
    }

    public void update(RequestNumPlayersEvent event) {
    }
    public void update(UpdatedDataEvent event){

    }
}
