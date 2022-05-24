package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.common.events.fromClientEvents.charactersEvents.UseCharacter1Event;
import it.polimi.ingsw.common.events.fromClientEvents.charactersEvents.UseCharacter7Event;
import it.polimi.ingsw.common.events.fromClientEvents.charactersEvents.UseCharacter9Event;
import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.Professor;
import it.polimi.ingsw.model.basicgame.TEAM;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.characters.Character;
import it.polimi.ingsw.model.expertgame.characters.Character7;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Character7Controller extends GuiController{
    public TilePane CharacterGrid;
    public GridPane EntranceGrid;
    public GridPane DiningRoomGrid;
    public GridPane TowersGrid;
    public GridPane ProfessorGrid;
    public TilePane BufferEntrance;
    public TilePane BufferCharacter;
    public ImageView checkButton;
    public ImageView closeButton;
    ArrayList<Integer> colorsFromCharacter;

    ArrayList<Integer> colorsFromEntrance;
    @Override
    public void initialize() {
        colorsFromEntrance=new ArrayList<>();
        colorsFromCharacter=new ArrayList<>();
        super.initialize();
        for(Player player:guiManager.getData().getPlayers()){
            if(player.getUsername().equals(guiManager.getOwner())){
                for (int i=0;i<5;i++){
                    for(int j=0;j<player.getMySchoolBoard().getDiningRoom()[i].size();j++){
                        ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+ COLOR.values()[i].toString().toLowerCase()+".png").toString());
                        imageView.setPreserveRatio(true);
                        imageView.setFitWidth(50);
                        DiningRoomGrid.add(imageView,j,i);
                    }
                }
                for(int i=0;i<player.getMySchoolBoard().getEntrance().size();i++){
                    COLOR color=player.getMySchoolBoard().getEntrance().get(i).getColor();
                    ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+color.toString().toLowerCase()+".png").toString());
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(50);
                    if(guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername())){
                        imageView.setOnMouseEntered(this::mouseOnGeneric);
                        imageView.setOnMouseExited(this::mouseOffGeneric);
                        imageView.setOnMouseClicked(this::studentFromEntranceClicked);
                    }
                    imageView.setId(color.ordinal()+ "  "+ i);
                    if(i%2==0)
                        EntranceGrid.add(imageView,1,i/2);
                    else
                        EntranceGrid.add(imageView,0,i/2+1);
                }
                for(Professor professor: player.getMySchoolBoard().getProfessors()){
                    COLOR color=professor.getColor();
                    ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/teacher_"+color.toString().toLowerCase()+".png").toString());
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(50);
                    imageView.setRotate(29.7);
                    ProfessorGrid.add(imageView,0,color.ordinal());
                }
                for(int i=0;i<player.getMySchoolBoard().getTowers().size();i++){
                    TEAM team=player.getTeam();
                    ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/"+team.toString().toLowerCase()+"_tower.png").toString());
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(120);
                    if(i%2==0)
                        TowersGrid.add(imageView,0,i/2);
                    else
                        TowersGrid.add(imageView,1,i/2);
                }
            }
        }
        Character7 character7=null;
        for(Character character:guiManager.getData().getCharacters())
            if(character instanceof  Character7){
                character7=(Character7) character;
                break;
            }
        for(int i=0;i<character7.getStudents().size();i++){
            COLOR color=character7.getStudents().get(i).getColor();
            ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+color.toString().toLowerCase()+".png").toString());
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(50);
            imageView.setId(color.ordinal()+" "+i);
            if(guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername())){
                imageView.setOnMouseEntered(this::mouseOnGeneric);
                imageView.setOnMouseExited(this::mouseOffGeneric);
                imageView.setOnMouseClicked(this::studentFromCharacterClicked);
            }
            CharacterGrid.getChildren().add(imageView);
        }
        if(guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername())){
            checkButton.setOnMouseEntered(this::mouseOnGeneric);
            checkButton.setOnMouseExited(this::mouseOffGeneric);
            checkButton.setOnMouseClicked(this::checkButtonClicked);
        }
    }

    public void closeButtonClicked(MouseEvent mouseEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void checkButtonClicked(MouseEvent mouseEvent){
        lastStudentAdded();
    }

    public void studentFromCharacterClicked(MouseEvent mouseEvent){
        if(colorsFromCharacter.size()==3)
            return;
        int color= Integer.parseInt(String.valueOf(((ImageView)mouseEvent.getSource()).getId().charAt(0)));
        ((ImageView)mouseEvent.getSource()).setImage(null);
        colorsFromCharacter.add(color);
        ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+COLOR.values()[color].toString().toLowerCase()+".png").toString());
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(50);
        BufferCharacter.getChildren().add(imageView);
        if(colorsFromCharacter.size()==3)
            lastStudentAdded();
    }

    public void studentFromEntranceClicked (MouseEvent mouseEvent){
        if(colorsFromEntrance.size()==3)
            return;
        int color= Integer.parseInt(String.valueOf(((ImageView)mouseEvent.getSource()).getId().charAt(0)));
        ((ImageView)mouseEvent.getSource()).setImage(null);
        colorsFromEntrance.add(color);
        ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+COLOR.values()[color].toString().toLowerCase()+".png").toString());
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(50);
        BufferEntrance.getChildren().add(imageView);
        if(colorsFromEntrance.size()==3)
            lastStudentAdded();
    }

    public void lastStudentAdded(){
        if(colorsFromCharacter.size()==colorsFromEntrance.size() ) {
            ArrayList<Integer> result=new ArrayList<>();
            for(int i=0;i<colorsFromCharacter.size();i++){
                result.add(colorsFromEntrance.get(i));
                result.add(colorsFromCharacter.get(i));
            }
            this.guiManager.getClient().getClientEvs().add(new UseCharacter7Event(this, result));
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }
    }
    public void mouseOnGeneric(MouseEvent mouseEvent){
        ((Node)mouseEvent.getSource()).getScene().setCursor(Cursor.HAND);
    }

    public void mouseOffGeneric(MouseEvent mouseEvent){
        ((Node)mouseEvent.getSource()).getScene().setCursor(Cursor.DEFAULT);
    }
}
