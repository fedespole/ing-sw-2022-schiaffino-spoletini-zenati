package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.common.events.fromClientEvents.DrawAssistantCardEvent;
import it.polimi.ingsw.common.events.fromClientEvents.MoveMotherEvent;
import it.polimi.ingsw.common.events.fromClientEvents.MoveStudentToIslandEvent;
import it.polimi.ingsw.common.events.fromClientEvents.charactersEvents.*;
import it.polimi.ingsw.common.events.fromServerEvents.RequestNumPlayersEvent;
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
import javafx.event.Event;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class GuiController {

    public GuiManager guiManager;
    private int colorPressed;
    public ImageView CharacterBack;
    public FlowPane Characters;
    public FlowPane coinsPlayer0;
    public FlowPane coinsPlayer1;
    public FlowPane coinsPlayer2;

    public ImageView Coin1;

    public ImageView Coin2;

    public ImageView Coin0;


    @FXML
    public void initialize(){
        guiManager = GuiManager.getInstance();
    }

    public void fillPlayerItems(GridPane entrance, GridPane diningroom, GridPane professors, GridPane towers, Player player, FlowPane coins) {
        for (int i=0;i<5;i++){
            for(int j=0;j<player.getMySchoolBoard().getDiningRoom()[i].size();j++){
                ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+ COLOR.values()[i].toString().toLowerCase()+".png").toString());
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(20);
                diningroom.add(imageView,j,i);
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
        for(int i = 0; i < player.getCoins() ; i++) {
            if (guiManager.getData().isExpert() && coins != null) {
                ImageView imageView = new ImageView(GuiManager.class.getResource("/graphics/pieces/Moneta_base.png").toString());
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(40);
                imageView.setOpacity(1);
                if(!guiManager.getData().getStatusGame().getStatus().equals(STATUS.PLANNING)){
                FlowPane.setMargin(imageView, new Insets(0.0,5.0, 80.0, 0.0));
                }
                coins.getChildren().add(imageView);
            }
        }
        if(player.getChosenCard()!=null) {
            if(!guiManager.getData().getStatusGame().getStatus().equals(STATUS.PLANNING)) {
                ImageView imageView = new ImageView(GuiManager.class.getResource("/graphics/playerItems/deck/assistantCards/Assistente (" + player.getChosenCard().getValue() + ").png").toString());
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(70);
                coins.getChildren().add(imageView);
            }
        }
    }

    // Based on islands array dimension, manually displays a different gridpane layout
    public void fillIslands(GridPane islandsPane, double sizeW, double sizeH, ArrayList<ArrayList<Island>> islands){

        int idCounter = -1;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {

                if (!(((j == 1 || j == 2 || j == 3 || j == 4) && (i == 1 || i == 2))) && !((i == 0 || i == 3) && (j == 0 || j == 5))) {

                    // Setting island count clockwise
                    if(i==0){
                        idCounter++;
                        ImageView islandNode = allocateIsland(islandsPane, idCounter,  sizeW, sizeH, i, j, islands);
                        // Layout
                        GridPane.setValignment(islandNode, VPos.CENTER);
                        GridPane.setHalignment(islandNode, HPos.CENTER);
                    }
                    else if(i==1){
                        if(j==0){
                            if(islands.size() == 12) {
                                idCounter = 11;
                                ImageView islandNode = allocateIsland(islandsPane, idCounter,  sizeW, sizeH, i, j, islands);
                                GridPane.setValignment(islandNode, VPos.BOTTOM);
                                GridPane.setHalignment(islandNode, HPos.CENTER);
                            }
                        }
                        else{
                            if(islands.size() > 4) {
                                idCounter = 4;
                                ImageView islandNode = allocateIsland(islandsPane, idCounter,  sizeW, sizeH, i, j, islands);
                                GridPane.setValignment(islandNode, VPos.BOTTOM);
                                GridPane.setHalignment(islandNode, HPos.CENTER);
                            }
                        }
                    }
                    else if(i==2){
                        if(j==0){
                            if(islands.size() > 10){
                                idCounter = 10;
                                ImageView islandNode = allocateIsland(islandsPane, idCounter,  sizeW, sizeH, i, j, islands);
                                GridPane.setValignment(islandNode, VPos.TOP);
                                GridPane.setHalignment(islandNode, HPos.CENTER);
                            }
                        }
                        else{
                            if(islands.size() > 5){
                                idCounter = 5;
                                ImageView islandNode = allocateIsland(islandsPane, idCounter,  sizeW, sizeH, i, j, islands);
                                GridPane.setValignment(islandNode, VPos.TOP);
                                GridPane.setHalignment(islandNode, HPos.CENTER);
                            }
                        }
                    }
                    else{
                        if(islands.size() > (10-j)) {
                            idCounter = 10 - j;
                            ImageView islandNode = allocateIsland(islandsPane, idCounter,  sizeW, sizeH, i, j, islands);
                            GridPane.setValignment(islandNode, VPos.CENTER);
                            GridPane.setHalignment(islandNode, HPos.CENTER);
                        }
                    }
                }
            }
        }
    }

    private ImageView allocateIsland(GridPane islandsPane, int idCounter, double sizeW, double sizeH, int i, int j, ArrayList<ArrayList<Island>> islands){

        ImageView islandNode;
        if(islands.get(idCounter).size()==1) {
            islandNode = new ImageView(GuiManager.class.getResource("/graphics/pieces/island" + ((j % 3) + 1) + ".png").toString());
            islandNode.setFitWidth(sizeW);
            islandNode.setFitHeight(sizeH);
        }
        else if(islands.get(idCounter).size()==2){
            islandNode = new ImageView(GuiManager.class.getResource("/graphics/pieces/merge2.png").toString());
            islandNode.setFitWidth(sizeW+5);
            islandNode.setFitHeight(sizeH+2);
        }
        else if(islands.get(idCounter).size()==3){
            islandNode = new ImageView(GuiManager.class.getResource("/graphics/pieces/merge3.png").toString());
            islandNode.setFitWidth(sizeW+5);
            islandNode.setFitHeight(sizeH+2);
        }
        else if(islands.get(idCounter).size()==4){
            islandNode = new ImageView(GuiManager.class.getResource("/graphics/pieces/merge4.png").toString());
            islandNode.setFitWidth(sizeW+5);
            islandNode.setFitHeight(sizeH+2);
        }
        else if(islands.get(idCounter).size()==5){
            islandNode = new ImageView(GuiManager.class.getResource("/graphics/pieces/merge5.png").toString());
            islandNode.setFitWidth(sizeW+5);
            islandNode.setFitHeight(sizeH+2);
        }
        else if(islands.get(idCounter).size()==6){
            islandNode = new ImageView(GuiManager.class.getResource("/graphics/pieces/merge6.png").toString());
            islandNode.setFitWidth(sizeW+5);
            islandNode.setFitHeight(sizeH+2);
        }
        else{
            islandNode = new ImageView(GuiManager.class.getResource("/graphics/pieces/merge7.png").toString());
            islandNode.setFitWidth(sizeW+5);
            islandNode.setFitHeight(sizeH+2);
        }

        islandNode.setPreserveRatio(true);
        islandNode.setId(Integer.toString(idCounter));
        islandsPane.add(islandNode, j, i);
        fillElementsOnIsland(islandsPane, islands.get(idCounter), i, j, idCounter);
        return islandNode;
    }


    public void fillElementsOnIsland(GridPane islandsPane, ArrayList<Island> island, int i, int j, int idCounter){

        TilePane elemPane = new TilePane();
        islandsPane.add(elemPane, j, i);
        elemPane.setId(Integer.toString(idCounter));
        for (Island subIsland : island) {
            for (int x = 0; x < subIsland.getStudents().size(); x++) {
                COLOR color = subIsland.getStudents().get(x).getColor();
                ImageView imageViewS = new ImageView(GuiManager.class.getResource("/graphics/pieces/student_" + color.toString().toLowerCase() + ".png").toString());
                imageViewS.setPreserveRatio(true);
                imageViewS.setFitWidth(20);
                elemPane.getChildren().add(imageViewS);
            }
        }
        for (Island subIsland : island) {
            if (subIsland.getTower() != null) {
                TEAM team = subIsland.getTower().getColor();
                ImageView imageViewT = new ImageView(GuiManager.class.getResource("/graphics/pieces/" + team.toString().toLowerCase() + "_tower.png").toString());
                imageViewT.setPreserveRatio(true);
                imageViewT.setFitWidth(40);
                elemPane.getChildren().add(imageViewT);
            }
        }
        for (Island subIsland : island) {
            if (subIsland.isNoEntry()) {
                ImageView imageViewN = new ImageView(GuiManager.class.getResource("/graphics/pieces/deny_island_icon.png").toString());
                imageViewN.setPreserveRatio(true);
                imageViewN.setFitWidth(60);
                elemPane.getChildren().add(imageViewN);
                imageViewN.toFront();
            }
        }

        if(guiManager.getData().getMotherNature()==idCounter){
            ImageView imageViewM = new ImageView(GuiManager.class.getResource("/graphics/pieces/mother_nature.png").toString());
            imageViewM.setPreserveRatio(true);
            imageViewM.setFitWidth(40);
            elemPane.getChildren().add(imageViewM);
            imageViewM.toFront();
        }

        if(guiManager.getData().getStatusGame().getStatus().equals(STATUS.ACTION_MOVESTUD) && guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername())) {

            // Island able to receive dragOver if I'm currPlayer
            elemPane.setOnDragOver(event -> {
                elemPane.getScene().setCursor(Cursor.NONE);
                if (event.getGestureSource() != elemPane) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            });

            elemPane.setOnDragEntered(event -> {
                if (event.getGestureSource() != elemPane) {
                    elemPane.setOpacity(0.5);
                }
                event.consume();
            });

            elemPane.setOnDragExited(event -> {
                elemPane.getScene().setCursor(Cursor.DEFAULT);
                elemPane.setOpacity(1);
                event.consume();
            });

            elemPane.setOnDragDropped(event -> {

                String color = event.getDragboard().getString();
                int a = Integer.parseInt(color);
                this.guiManager.getClient().getClientEvs().add(new MoveStudentToIslandEvent(this, a, idCounter));
                event.setDropCompleted(true);
                elemPane.getScene().setCursor(Cursor.DEFAULT);
                event.consume();
            });

            elemPane.setOnDragDone(Event::consume);

        }
        else if(guiManager.getData().getStatusGame().getStatus().equals(STATUS.ACTION_MOVEMN) && guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername())){
            elemPane.setOnMouseClicked(event->{
                int steps = (idCounter + guiManager.getData().getIslands().size() - guiManager.getData().getMotherNature())%guiManager.getData().getIslands().size();
                this.guiManager.getClient().getClientEvs().add(new MoveMotherEvent(this, steps));
            });
            elemPane.setOnMouseEntered(event->{
                guiManager.getStage().getScene().setCursor(Cursor.HAND);
            });
            elemPane.setOnMouseExited(event->{
                guiManager.getStage().getScene().setCursor(Cursor.DEFAULT);
            });
        }
        // TODO : il tilePane scala solo in base alla grandezza del gridPane dietro, GridPane setFillWidth(Node child, Boolean value) fa scegliere le colonne del tilePane ma sposta la posizione
        elemPane.setPrefSize(5, 5);
        elemPane.setHgap(1);
        elemPane.setVgap(1);
        elemPane.setPrefColumns(3);
        elemPane.alignmentProperty().setValue(Pos.CENTER);
        elemPane.toFront();
    }

    public void fillCloud(TilePane cloudPane, int i){

        Cloud c = guiManager.getData().getClouds().get(i);
        for(int x=0;x< c.getStudents().size();x++){
            COLOR color = c.getStudents().get(x).getColor();
            ImageView imageViewS = new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+color.toString().toLowerCase()+".png").toString());
            imageViewS.setPreserveRatio(true);
            imageViewS.setFitWidth(20);
            cloudPane.getChildren().add(imageViewS);
        }

        cloudPane.setHgap(10);
        cloudPane.setVgap(10);
        cloudPane.setPrefRows(2);
        cloudPane.alignmentProperty().setValue(Pos.CENTER);

    }

    public void setCharacters(){
        this.Coin0.setImage(new Image(GuiManager.class.getResource("/graphics/pieces/Moneta_base.png").toString()));
        this.Coin1.setImage(new Image(GuiManager.class.getResource("/graphics/pieces/Moneta_base.png").toString()));
        this.Coin2.setImage(new Image(GuiManager.class.getResource("/graphics/pieces/Moneta_base.png").toString()));
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
        Characters.getChildren().add( 0,infoButton);
        Coin2.setVisible(false);
        Coin1.setVisible(false);
        Coin0.setVisible(false);
    }

    public void mouseOffCharacters(MouseEvent mouseEvent){
        Characters.setVisible(false);
        CharacterBack.setVisible(true);
        Coin2.setVisible(false);
        Coin1.setVisible(false);
        Coin0.setVisible(false);
        Characters.getScene().setCursor(Cursor.DEFAULT);
    }

    public void mouseClickedBackCharacter(MouseEvent mouseEvent){
        Characters.setVisible(true);
        CharacterBack.setVisible(false);
        Coin2.setVisible(guiManager.getData().getCharacters().get(0).isHasBeenUsed());
        Coin2.setVisible(guiManager.getData().getCharacters().get(1).isHasBeenUsed());
        Coin2.setVisible(guiManager.getData().getCharacters().get(2).isHasBeenUsed());
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
                if(guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername())) {
                    if (guiManager.getData().getStatusGame().getStatus().equals(STATUS.PLANNING)) {
                        Stage dialogStage = new Stage();
                        dialogStage.initModality(Modality.WINDOW_MODAL);
                        Button okButton = new Button("Ok!");
                        okButton.setOnMouseClicked(event -> {
                            dialogStage.close();
                        });
                        VBox vbox = new VBox(new Text("You can't use this character during the planning phase"), okButton);
                        vbox.setAlignment(Pos.CENTER);
                        vbox.setPadding(new Insets(30));
                        vbox.setSpacing(15);
                        dialogStage.setScene(new Scene(vbox));
                        dialogStage.setTitle("Invalid Move!");
                        dialogStage.setResizable(false);
                        dialogStage.getIcons().add(new Image(GuiManager.class.getResource("/graphics/EriantysIntro.jpg").toString()));
                        dialogStage.show();
                    } else {
                        this.guiManager.getClient().getClientEvs().add(new UseCharacter4Event(this));
                    }
                }
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
                Stage s = new Stage();
                Parent parent = null;
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(GuiManager.class.getResource(Constants.CHARACTER7_SCENE));
                    parent = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(parent);
                s.setTitle("Character 7");
                s.setScene(scene);
                s.setResizable(false);
                s.show();
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
                Stage s = new Stage();
                Parent parent = null;
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(GuiManager.class.getResource(Constants.CHARACTER10_SCENE));
                    parent = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(parent);
                s.setTitle("Character 10");
                s.setScene(scene);
                s.setResizable(false);
                s.show();
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
        Coin2.setVisible(false);
        Coin1.setVisible(false);
        Coin0.setVisible(false);
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
        int index=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<2 && index<5;j++) {
                ImageView imageView = new ImageView(GuiManager.class.getResource("/graphics/pieces/student_" + COLOR.values()[index].toString().toLowerCase() + ".png").toString());
                String id = character + " " + index;
                imageView.setOnMouseClicked(this::mouseClickedColorPopup);
                imageView.setId(id);
                imageView.setOnMouseEntered(event->{
                    ((Node)event.getSource()).getScene().setCursor(Cursor.HAND);
                });
                imageView.setOnMouseExited(event->{
                    ((Node)event.getSource()).getScene().setCursor(Cursor.DEFAULT);
                });
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
        fillIslands(islands,210.0, 160.0, guiManager.getData().getIslands());
        islands.setPrefWidth(1200);
        islands.setPrefHeight(700);
        islands.setId(Integer.toString(c));
        islands.setAlignment(Pos.CENTER);
        for(Node island: islands.getChildren()){
            island.setOnMouseEntered(event->{
                ((Node)event.getSource()).getScene().setCursor(Cursor.HAND);
            });
            island.setOnMouseExited(event->{
                ((Node)event.getSource()).getScene().setCursor(Cursor.DEFAULT);
            });
            island.setOnMouseClicked(this::mouseClickedCharacterIsland);
        }
        AnchorPane background =new AnchorPane();
        background.setPrefWidth(1200);
        background.setPrefHeight(700);
        ImageView image =new ImageView(GuiManager.class.getResource("/graphics/provasfondo.png").toString());
        image.setX(0);
        image.setY(0);
        image.setPreserveRatio(false);
        image.setFitWidth(1200);
        image.setFitHeight(700);
        image.setOpacity(0.9);
        background.getChildren().add(image);
        background.getChildren().add(islands);
        islands.setAlignment(Pos.CENTER);
        newStage.setResizable(false);
        newStage.setScene(new Scene(background));
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
                    imageView.setOnMouseEntered(event->{
                        ((Node)event.getSource()).getScene().setCursor(Cursor.HAND);
                    });
                    imageView.setOnMouseExited(event->{
                        ((Node)event.getSource()).getScene().setCursor(Cursor.DEFAULT);
                    });
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
                    imageView.setOnMouseEntered(event->{
                        ((Node)event.getSource()).getScene().setCursor(Cursor.HAND);
                    });
                    imageView.setOnMouseExited(event->{
                        ((Node)event.getSource()).getScene().setCursor(Cursor.DEFAULT);
                    });
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


    public void mouseOnGeneric(MouseEvent mouseEvent){
        guiManager.getStage().getScene().setCursor(Cursor.HAND);
    }

    public void mouseOffGeneric(MouseEvent mouseEvent){
        guiManager.getStage().getScene().setCursor(Cursor.DEFAULT);
    }

    public void update(RequestNumPlayersEvent event) {
    }
    public void update(UpdatedDataEvent event){
    }

    public void update(VictoryEvent event){
        Platform.runLater(() -> guiManager.setFXML(Constants.END_SCENE));
    }

    public void update(TieEvent event){
        Platform.runLater(() -> guiManager.setFXML(Constants.END_SCENE));
    }

}
