package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.common.events.fromClientEvents.DrawAssistantCardEvent;
import it.polimi.ingsw.common.events.fromClientEvents.MoveStudentToDiningEvent;
import it.polimi.ingsw.common.events.fromClientEvents.MoveStudentToIslandEvent;
import it.polimi.ingsw.common.events.fromClientEvents.charactersEvents.*;
import it.polimi.ingsw.common.events.fromServerEvents.TieEvent;
import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.common.events.fromServerEvents.VictoryEvent;
import it.polimi.ingsw.model.basicgame.*;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.characters.Character;
import it.polimi.ingsw.model.expertgame.characters.Character1;
import it.polimi.ingsw.model.expertgame.characters.Character11;
import it.polimi.ingsw.model.expertgame.characters.Character7;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
        MyDiningRoom.setOnDragOver(event -> {

                System.out.println("onDragOver");
                MyDiningRoom.getScene().setCursor(Cursor.NONE);
                if (event.getGestureSource() != MyDiningRoom) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
        });

        MyDiningRoom.setOnDragEntered(event->{
            System.out.println("onDragEntered");
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
            System.out.println(event.getDragboard().getString());
            String color = event.getDragboard().getString();
            int a = Integer.parseInt(color);
            this.guiManager.getClient().getClientEvs().add(new MoveStudentToDiningEvent(this, a));
            event.setDropCompleted(true);
            MyDiningRoom.getScene().setCursor(Cursor.DEFAULT);
            event.consume();
        });

        MyDiningRoom.setOnDragDone(event -> {
            System.out.println("onDragDone");
            event.consume();
        });

        this.fillMyDiningRoomAction();
        this.fillOtherPlayersAction();
        super.fillIslands(islandsPane, 190.0, 140.0, guiManager.getData().getIslands());

        if(guiManager.getData().isExpert())
            this.setCharacters();

        if (guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername())) {

            if (guiManager.getData().getStatusGame().getStatus().equals(STATUS.ACTION_MOVESTUD)){
                phaseLabel.setText("Your turn, move a student.");
            }
            else if(guiManager.getData().getStatusGame().getStatus().equals(STATUS.ACTION_MOVEMN)){
                phaseLabel.setText("Your turn, move mother nature (clockwise).");
            }
            else if(guiManager.getData().getStatusGame().getStatus().equals(STATUS.ACTION_CHOOSECLOUD)){
                phaseLabel.setText("Your turn, choose a cloud.");
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
                        MyDiningRoom.add(imageView,i,j);
                    }
                }
                for(int i=0;i<player.getMySchoolBoard().getEntrance().size();i++){
                    COLOR color=player.getMySchoolBoard().getEntrance().get(i).getColor();
                    ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+color.toString().toLowerCase()+".png").toString());
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(20);
                    imageView.setOnMouseEntered(event->{
                        imageView.getScene().setCursor(Cursor.HAND);
                    });
                    imageView.setOnMouseExited(event->{
                        imageView.getScene().setCursor(Cursor.HAND);
                    });

                    imageView.setOnDragDetected(mouseEvent -> {
                        System.out.println("onDragDetected");
                        Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
                        ClipboardContent content = new ClipboardContent();
                        content.putImage(imageView.getImage());
                        int a = COLOR.valueOf(color.toString()).ordinal();
                        String c = Integer.toString(a);
                        content.putString(c);
                        db.setContent(content);
                        mouseEvent.consume();
                    });

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

    private void setCharacters(){
        this.CharacterBack.setImage(new Image(GuiManager.class.getResource("/graphics/characters/Personaggi_retro.jpg").toString()));
        this.CharacterBack.setOnMouseClicked(this::mouseClickedBackCharacter);
        this.CharacterBack.setOnMouseEntered(this::mouseOnGeneric);
        this.CharacterBack.setOnMouseExited(this::mouseOffGeneric);
        this.CharacterBack.toFront();
        this.Characters.setVisible(false);
        this.Characters.setOnMouseExited(this::mouseOffCharacters);
        for(Character character: guiManager.getData().getCharacters()){
            ImageView imageView = new ImageView(GuiManager.class.getResource("/graphics/characters/Character"+character.getId()+".jpg").toString());
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(100);
            System.out.println(character.getId());
            imageView.setOnMouseClicked(this::mouseClickedCharacter);
            imageView.setId(Integer.toString(character.getId()));
            Characters.getChildren().add(imageView);
        }
        ImageView infoButton = new ImageView(GuiManager.class.getResource("/graphics/infoButton.png").toString());
        infoButton.setPreserveRatio(true);
        infoButton.setFitWidth(60);
        infoButton.setId("infoButton");
        infoButton.setOnMousePressed(this::infoButtonClicked);
        Characters.getChildren().add( 0,infoButton);
    }

    public void mouseOffCharacters(MouseEvent mouseEvent){
        Characters.setVisible(false);
        CharacterBack.setVisible(true);
        Characters.getScene().setCursor(Cursor.DEFAULT);
    }

    public void mouseOnCharacterBack(MouseEvent mouseEvent){
        Characters.getScene().setCursor(Cursor.HAND);
    }

    public void mouseClickedBackCharacter(MouseEvent mouseEvent){
        Characters.setVisible(true);
        CharacterBack.setVisible(false);
        Characters.getScene().setCursor(Cursor.HAND);
    }

    public void mouseClickedCharacter(MouseEvent mouseEvent){
        int valueChar=Integer.parseInt(((ImageView) mouseEvent.getSource()).getId());
        switch(valueChar){
            case 1:{
                // studentsPopup(1);
                break;
            }
            case 2:{
                if(guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername()))
                    this.guiManager.getClient().getClientEvs().add(new UseCharacter2Event(this));
                break;
            }
            case 3:{
                //TODO Character3
            }
            case 4:{
                if(guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername()))
                    this.guiManager.getClient().getClientEvs().add(new UseCharacter4Event(this));
                break;
            }
            case 5:{

            }
            case 6:{
                if(guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername()))
                    this.guiManager.getClient().getClientEvs().add(new UseCharacter6Event(this));
                break;
            }
            case 7:{
                //todo character7
            }
            case 8:{
                if(guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername()))
                    this.guiManager.getClient().getClientEvs().add(new UseCharacter8Event(this));
                break;
            }
            case 9:{
                if(guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername()))
                    colorPopup(9);
                break;
            }
            case 10:{
                //todo character10
            }
            case 11:{
                studentsPopup(11);
                break;
            }
            case 12:{
                if(guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername()))
                    colorPopup(12);
                break;
            }
        }
        Characters.setVisible(false);
        CharacterBack.setVisible(true);
        Characters.getScene().setCursor(Cursor.DEFAULT);
    }

    public void mouseClickedColorPopup(MouseEvent mouseEvent){//0 for character 9,1 for character 12
        int character =Integer.parseInt(String.valueOf(((ImageView) mouseEvent.getSource()).getId().charAt(0)));
        int color =Integer.parseInt(String.valueOf(((ImageView) mouseEvent.getSource()).getId().charAt(2)));
        switch(character){
            case 0:{
                this.guiManager.getClient().getClientEvs().add(new UseCharacter9Event(this, color));
                break;
            }
            case 1:{
                this.guiManager.getClient().getClientEvs().add(new UseCharacter12Event(this, color));
                break;
            }
        }
        ((Stage)((ImageView) mouseEvent.getSource()).getScene().getWindow()).close();
    }

    public void mouseClickedStudentPopup(MouseEvent mouseEvent) {//0 for character 11
        int character =Integer.parseInt(String.valueOf(((ImageView) mouseEvent.getSource()).getId().charAt(0)));
        int color =Integer.parseInt(String.valueOf(((ImageView) mouseEvent.getSource()).getId().charAt(2)));
        switch(character){
            case 1:{
                //TODO SCEGLI ISOLA
                break;
            }
            case 7:{
                //TODO SCEGLI STUDENTI
                break;
            }
            case 0:{
                this.guiManager.getClient().getClientEvs().add(new UseCharacter11Event(this, color));
                break;
            }
        }
        ((Stage)((ImageView) mouseEvent.getSource()).getScene().getWindow()).close();
    }

    public void mouseOnGeneric(MouseEvent mouseEvent){
        ((ImageView) mouseEvent.getSource()).getScene().setCursor(Cursor.HAND);
    }

    public void mouseOffGeneric(MouseEvent mouseEvent){
        ((ImageView) mouseEvent.getSource()).getScene().setCursor(Cursor.DEFAULT);
    }

    private void infoButtonClicked(MouseEvent mouseEvent){
        //create new stage and load it with CharacterInfoScene
        Stage s = new Stage();
        Parent parent = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GuiManager.class.getResource(Constants.CHARACTER_INFO_SCENE));
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent,1360,765);
        s.setTitle("Info");
        s.setScene(scene);
        s.setResizable(false);
        s.show();
    }

    private void colorPopup(int character){//we send 0 if the character is 9,1 if the character is 12
        Stage newStage = new Stage();
        newStage.setTitle("Character"+character+" Selection");
        Label title= new Label("Choose a Color: ");
        if(character==9)
            character=0;
        else if(character==12)
            character=1;
        //create Pane
        VBox colors=new VBox(title);
        for(int i=0;i<5;i++){
            ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+ COLOR.values()[i].toString().toLowerCase()+".png").toString());
            String id = character+" "+i;
            System.out.println(id);
            imageView.setOnMouseClicked(this::mouseClickedColorPopup);
            imageView.setId(id);
            imageView.setOnMouseEntered(this::mouseOnGeneric);
            imageView.setOnMouseExited(this::mouseOffGeneric);
            colors.getChildren().add(imageView);
        }
        colors.setSpacing(15);
        colors.setPadding(new Insets(25));
        colors.setAlignment(Pos.CENTER);
        colors.setStyle("-fx-background-color:WHITE");
        colors.setAlignment(Pos.CENTER);
        //set scene
        newStage.setResizable(false);
        newStage.setScene(new Scene(colors));
        newStage.show();
    }

    private void studentsPopup(int c){//we send 0 if the character is 11
        Stage newStage = new Stage();
        newStage.setTitle("Character"+c+" Selection");
        Label title= new Label("Choose a Student: ");
        Character character=null;
        ArrayList<Student> students= null;
        for(int i=0;i<3;i++)
            if(guiManager.getData().getCharacters().get(i).getId()==c){
                character=guiManager.getData().getCharacters().get(i);
                break;
            }
        if(c==11)
            c=0;
        switch(c) {
            case 1: {
                students = ((Character1) character).getStudents();
                break;
            }
            case 7: {
                students = ((Character7) character).getStudents();
                break;
            }
            case 0:{
                students = ((Character11)character).getStudents();
                break;
            }
        }
        VBox colors=new VBox(title);
        Student student;
        for(int i=0;i<students.size();i++){
            student=students.get(i);
            ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+student.getColor().toString().toLowerCase()+".png").toString());
            String id = c+" "+student.getColor().ordinal()+" "+i;
            imageView.setId(id);
            if(guiManager.getData().getCurrPlayer().getUsername().equals(guiManager.getOwner())) {
                imageView.setOnMouseEntered(this::mouseOnGeneric);
                imageView.setOnMouseExited(this::mouseOffGeneric);
                imageView.setOnMouseClicked(this::mouseClickedStudentPopup);
            }
            colors.getChildren().add(imageView);
        }
        colors.setSpacing(15);
        colors.setPadding(new Insets(25));
        colors.setAlignment(Pos.CENTER);
        colors.setStyle("-fx-background-color:WHITE");
        colors.setAlignment(Pos.CENTER);
        //set scene
        newStage.setResizable(false);
        newStage.setScene(new Scene(colors));
        newStage.show();
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
