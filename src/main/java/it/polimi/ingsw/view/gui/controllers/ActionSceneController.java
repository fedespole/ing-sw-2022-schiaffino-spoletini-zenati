package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.common.events.fromServerEvents.TieEvent;
import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.common.events.fromServerEvents.VictoryEvent;
import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.Professor;
import it.polimi.ingsw.model.basicgame.STATUS;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.io.File;

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
        guiManager.getStage().setFullScreen(true);
        if(guiManager.getData().getNumPlayers()==3){
            Image image= new Image(GuiManager.class.getResource("/graphics/playerItems/schoolBoard/Plancia_DEF3.png").toString());
            Player2Board.setImage(image);
        }
        this.fillMyDiningRoom();
        this.fillOtherPlayers();
        this.fillIslands();

        if (guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername())) {
            phaseLabel.setText("Your turn, " + guiManager.getData().getStatusGame().toString());

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

    private void fillMyDiningRoom(){
        for (Player player : guiManager.getData().getPlayers()) {
            if (player.getUsername().equals(guiManager.getOwner())) {
                for(int i=0;i<5;i++){
                    player.getMySchoolBoard().addProfessor(new Professor(COLOR.values()[i]));
                }
                for (int i=0;i<5;i++){
                    for(int j=0;j<player.getMySchoolBoard().getDiningRoom()[i].size();j++){
                        ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+COLOR.values()[i].toString().toLowerCase()+".png").toString());
                        imageView.setPreserveRatio(true);
                        imageView.setFitHeight(35);
                        imageView.setFitWidth(33);
                        MyDiningRoom.add(imageView,i,j);
                    }
                }
                for(int i=0;i<player.getMySchoolBoard().getEntrance().size();i++){
                    COLOR color=player.getMySchoolBoard().getEntrance().get(i).getColor();
                    ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+color.toString().toLowerCase()+".png").toString());
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(35);
                    imageView.setFitWidth(33);
                    if(i%2==0)
                        MyEntrance.add(imageView,1,i/2);
                    else
                        MyEntrance.add(imageView,0,i/2+1);
                }
                for(Professor professor: player.getMySchoolBoard().getProfessors()){
                    COLOR color=professor.getColor();
                    ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/teacher_"+color.toString().toLowerCase()+".png").toString());
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(33);
                    imageView.setRotate(29.7);
                    MyProfessors.add(imageView,0,color.ordinal());
                }
                for(int i=0;i<player.getMySchoolBoard().getTowers().size();i++){
                    TEAM team=player.getTeam();
                    ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/"+team.toString().toLowerCase()+"_tower.png").toString());
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(80);
                    if(i%2==0)
                        MyTowers.add(imageView,0,i/2);
                    else
                        MyTowers.add(imageView,1,i/2);
                }
            }
        }
    }

    private void fillOtherPlayers(){
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
                for(int i=0;i<5;i++){
                    player.getMySchoolBoard().addProfessor(new Professor(COLOR.values()[i]));
                }
                for (int i=0;i<5;i++){
                    for(int j=0;j<player.getMySchoolBoard().getDiningRoom()[i].size();j++){
                        ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+COLOR.values()[i].toString().toLowerCase()+".png").toString());
                        imageView.setPreserveRatio(true);
                        //             imageView.setFitHeight(35);
                        imageView.setFitWidth(33);
                        diningroom.add(imageView,i,j);
                    }
                }
                for(int i=0;i<player.getMySchoolBoard().getEntrance().size();i++){
                    COLOR color=player.getMySchoolBoard().getEntrance().get(i).getColor();
                    ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+color.toString().toLowerCase()+".png").toString());
                    imageView.setPreserveRatio(true);
                    // imageView.setFitHeight(35);
                    imageView.setFitWidth(33);
                    if(i%2==0)
                        entrance.add(imageView,1,i/2);
                    else
                        entrance.add(imageView,0,i/2+1);
                }
                for(Professor professor: player.getMySchoolBoard().getProfessors()){
                    COLOR color=professor.getColor();
                    ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/teacher_"+color.toString().toLowerCase()+".png").toString());
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(33);
                    imageView.setRotate(29.7);
                    professors.add(imageView,0,color.ordinal());
                }
                for(int i=0;i<player.getMySchoolBoard().getTowers().size();i++){
                    TEAM team=player.getTeam();
                    ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/"+team.toString().toLowerCase()+"_tower.png").toString());
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(80);
                    if(i%2==0)
                        towers.add(imageView,0,i/2);
                    else
                        towers.add(imageView,1,i/2);
                }
                if(player.getChosenCard()!=null){
                    Image image= new Image(GuiManager.class.getResource("/graphics/playerItems/deck/assistantCards/Assistente ("+player.getChosenCard().getValue()+").png").toString());
                }
                flag++;
            }
        }
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
