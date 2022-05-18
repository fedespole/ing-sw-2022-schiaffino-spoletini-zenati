package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.common.events.fromClientEvents.DrawAssistantCardEvent;
import it.polimi.ingsw.common.events.fromClientEvents.charactersEvents.*;
import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.model.basicgame.*;
import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
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


    @FXML
    @Override
    public void initialize() {
        super.initialize();
        ArrayList<Character> characters= new ArrayList<>();
        guiManager.getData().setCharacters(characters);
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
        super.fillIslands(islandsPane, 130.0, 80.0);

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
        ((ImageView) mouseEvent.getSource()).getScene().setCursor(Cursor.HAND);
    }

    public void mouseOffGeneric(MouseEvent mouseEvent){
        ((ImageView) mouseEvent.getSource()).getScene().setCursor(Cursor.DEFAULT);
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
                //TODO Character1 studenti
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
                //todo character 11
            }
            case 12:{
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
                super.fillMyDiningRoom(player, MyDiningRoom, MyEntrance, MyProfessors, MyTowers,MyCoins);
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
                super.fillOtherPlayers(entrance, diningroom, professors, towers, player,coins);
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
            imageView.setOnMouseClicked(this::mouseClickedCharacter);
            imageView.setId(Integer.toString(character.getId()));
            Characters.getChildren().add(imageView);
        }
        ImageView infoButton = new ImageView(GuiManager.class.getResource("/graphics/infoButton.png").toString());
        infoButton.setPreserveRatio(true);
        infoButton.setFitWidth(60);
        infoButton.setId("infoButton");
        infoButton.setOnMousePressed(this::infoButtonClicked);
        Characters.getChildren().add(0, infoButton);
    }

    private void infoButtonClicked(MouseEvent mouseEvent){
        //create new stage and load it with CharacterInfoScene
        /*Parent root = null;
        try{
          root = FXMLLoader.load(GuiManager.class.getResource(Constants.CHARACTER_INFO));
        } catch(IOException e){};
        Scene scene = new Scene(root, 1360, 765);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.setTitle("Characters' Infos");
        newStage.show();*/
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
            ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+COLOR.values()[i].toString().toLowerCase()+".png").toString());
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
            String id = character+" "+student.getColor().ordinal()+" "+i;
            System.out.println(id);
          //  imageView.setOnMouseClicked(this::mouseClickedColorPopup);
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
    @Override
    public void update(UpdatedDataEvent event) {
        if(guiManager.getData().getStatusGame().getStatus().equals(STATUS.PLANNING))
            Platform.runLater(() -> guiManager.setFXML(Constants.PLANNING_SCENE));
        else
            Platform.runLater(() -> guiManager.setFXML(Constants.ACTION_SCENE));
    }
    private Image generateImage(double red, double green, double blue, double opacity) {
        WritableImage img = new WritableImage(1, 1);
        PixelWriter pw = img.getPixelWriter();

        Color color = Color.color(red/255, green/255, blue/255, opacity/255);
        pw.setColor(0, 0, color);
        return img ;
    }
}