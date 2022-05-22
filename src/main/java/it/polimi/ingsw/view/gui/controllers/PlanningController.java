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
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
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
    public ImageView CharacterBack;
    public FlowPane Characters;
    public GridPane islandsPane;
    public Label Player1Coins;
    public Label Player2Coins;
    public Label MyCoins;
    public AnchorPane CharactersAnchorPane;
    public FlowPane cloudsPane;

    private int colorPressed;

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

        if(guiManager.getData().isExpert())
            this.setCharacters();
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

    public void mouseOffCharacters(MouseEvent mouseEvent){
        Characters.setVisible(false);
        CharacterBack.setVisible(true);
        Characters.getScene().setCursor(Cursor.DEFAULT);
    }

    public void mouseOnGeneric(MouseEvent mouseEvent){
        ((Node) mouseEvent.getSource()).getScene().setCursor(Cursor.HAND);
    }

    public void mouseOffGeneric(MouseEvent mouseEvent){
        ((Node) mouseEvent.getSource()).getScene().setCursor(Cursor.DEFAULT);
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
                studentsPopup(1);
                break;
            }
            case 2:{
                if(guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername()))
                    this.guiManager.getClient().getClientEvs().add(new UseCharacter2Event(this));
                break;
            }
            case 3:{
                islandPopup(3);
                break;
            }
            case 4:{
                // exception pop up -> in planning non la puoi selezionare
                break;
            }
            case 5:{
                noEntriesPopup();
                break;
            }
            case 6:{
                if(guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername()))
                    this.guiManager.getClient().getClientEvs().add(new UseCharacter6Event(this));
                break;
            }
            case 7:{
                //todo character7
                break;
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
                break;
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

    public void mouseClickedAssistant(MouseEvent mouseEvent){
        if(guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername())) {
            ((ImageView) mouseEvent.getSource()).setOpacity(0);
            int valueCard = Integer.parseInt(((ImageView) mouseEvent.getSource()).getId());
            this.guiManager.getClient().getClientEvs().add(new DrawAssistantCardEvent(this, valueCard));
        }
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
                this.colorPressed=color;
                islandPopup(1);
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

    public void mouseClickedCharacterIsland(MouseEvent mouseEvent){
        int islandIndex= Integer.parseInt(((Node)mouseEvent.getSource()).getId());
        int character = Integer.parseInt(((Node)mouseEvent.getSource()).getParent().getId());
        switch(character){
            case 1:{
                this.guiManager.getClient().getClientEvs().add(new UseCharacter1Event(this, colorPressed,islandIndex));
                colorPressed=-1;
                break;
            }
            case 3:{
                this.guiManager.getClient().getClientEvs().add(new UseCharacter3Event(this,islandIndex));
                break;
            }
            case 5:{
                this.guiManager.getClient().getClientEvs().add(new UseCharacter5Event(this,islandIndex));
                break;
            }
        }
        ((Stage)((Node) mouseEvent.getSource()).getScene().getWindow()).close();
    }
    public void mouseClickedNoEntriesPopup(MouseEvent mouseEvent){
        islandPopup(5);
        ((Stage)((ImageView) mouseEvent.getSource()).getScene().getWindow()).close();
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

    private void setCharacters(){
        this.CharacterBack.setImage(new Image(GuiManager.class.getResource("/graphics/characters/Personaggi_retro.jpg").toString()));
        this.CharacterBack.setOnMouseClicked(this::mouseClickedBackCharacter);
        this.CharacterBack.setOnMouseEntered(this::mouseOnGeneric);
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
        GridPane colors=new GridPane();
        colors.addRow(0);
        colors.addRow(1);
        colors.addColumn(0);
        colors.addColumn(1);
        System.out.println((colors.getLayoutX()));
        System.out.println(colors.getLayoutY());
        int index=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<2 && index<5;j++) {
                ImageView imageView = new ImageView(GuiManager.class.getResource("/graphics/pieces/student_" + COLOR.values()[index].toString().toLowerCase() + ".png").toString());
                String id = character + " " + index;
                System.out.println(id);
                imageView.setOnMouseClicked(this::mouseClickedColorPopup);
                imageView.setId(id);
                imageView.setOnMouseEntered(this::mouseOnGeneric);
                imageView.setOnMouseExited(this::mouseOffGeneric);
                colors.add(imageView,j,i);
                index++;
            }
        }
        colors.setPadding(new Insets(25));
        colors.setAlignment(Pos.CENTER);
        colors.setStyle("-fx-background-color:WHITE");
        colors.setAlignment(Pos.CENTER);
        colors.setHgap(10);
        colors.setVgap(10);
        //set scene
        newStage.setResizable(false);
        newStage.setScene(new Scene(colors));
        newStage.show();
    }

    private void islandPopup(int c){
        Stage newStage = new Stage();
        newStage.setTitle("Character "+c+" Island Selection");
       GridPane islands= new GridPane();
        fillIslands(islands,150.0, 90.0, guiManager.getData().getIslands());
        islands.setId(Integer.toString(c));
        for(Node island: islands.getChildren()){
            island.setOnMouseEntered(this::mouseOnGeneric);
            island.setOnMouseExited(this::mouseOffGeneric);
            island.setOnMouseClicked(this::mouseClickedCharacterIsland);
        }
        islands.setStyle("-fx-background-color:WHITE");
        newStage.setResizable(false);
        newStage.setScene(new Scene(islands));
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
        GridPane colors=new GridPane();
        colors.addRow(0);
        colors.addRow(1);
        colors.addColumn(0);
        colors.addColumn(1);
        if(students.size()>4)
            colors.addColumn(2);
        Student student;
        int index=0;
        for(int i=0;i<students.size()/2;i++) {
            for (int j = 0; j < 2 && index < students.size(); j++) {
                student = students.get(index);
                ImageView imageView = new ImageView(GuiManager.class.getResource("/graphics/pieces/student_" + student.getColor().toString().toLowerCase() + ".png").toString());
                String id = c + " " + student.getColor().ordinal() + " " + index;
                imageView.setId(id);
                if (guiManager.getData().getCurrPlayer().getUsername().equals(guiManager.getOwner())) {
                    imageView.setOnMouseEntered(this::mouseOnGeneric);
                    imageView.setOnMouseExited(this::mouseOffGeneric);
                    imageView.setOnMouseClicked(this::mouseClickedStudentPopup);
                }
                colors.add(imageView, j, i);
                index++;
            }
        }
        colors.setHgap(10);
        colors.setVgap(10);
        colors.setPadding(new Insets(25));
        colors.setAlignment(Pos.CENTER);
        colors.setStyle("-fx-background-color:WHITE");
        colors.setAlignment(Pos.CENTER);
        //set scene
        newStage.setResizable(false);
        newStage.setScene(new Scene(colors));
        newStage.show();
    }

    private void noEntriesPopup(){
        Stage newStage = new Stage();
        newStage.setTitle("Entries");
        GridPane grid=new GridPane();
        grid.addRow(0);
        grid.addRow(1);
        grid.addColumn(0);
        grid.addColumn(1);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color:WHITE");
        grid.setAlignment(Pos.CENTER);
        Character5 character5=null;
        for(Character character:guiManager.getData().getCharacters()){
            if(character instanceof Character5)
                character5=(Character5) character;
        }
        for(int i=0;i<character5.getNoEntries()/2;i++){
            for(int j=0;j<character5.getNoEntries()/2;j++){
                ImageView imageView = new ImageView(GuiManager.class.getResource("/graphics/pieces/deny_island_icon.png").toString());
                if (guiManager.getData().getCurrPlayer().getUsername().equals(guiManager.getOwner())) {
                    imageView.setOnMouseEntered(this::mouseOnGeneric);
                    imageView.setOnMouseExited(this::mouseOffGeneric);
                    imageView.setOnMouseClicked(this::mouseClickedNoEntriesPopup);
                }
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(40);
                grid.add(imageView,j,i);
            }
        }
        //set scene
        newStage.setResizable(false);
        newStage.setScene(new Scene(grid));
        newStage.show();
    }

    @Override
    public void update(UpdatedDataEvent event) {
        if(guiManager.getData().getStatusGame().getStatus().equals(STATUS.PLANNING))
            Platform.runLater(() -> guiManager.setFXML(Constants.PLANNING_SCENE));
        else
            Platform.runLater(() -> guiManager.setFXML(Constants.ACTION_SCENE));
    }
}