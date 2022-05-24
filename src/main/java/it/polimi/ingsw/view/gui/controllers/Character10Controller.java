package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.common.events.fromClientEvents.charactersEvents.UseCharacter10Event;
import it.polimi.ingsw.common.events.fromClientEvents.charactersEvents.UseCharacter7Event;
import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.Professor;
import it.polimi.ingsw.model.basicgame.TEAM;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Character10Controller extends GuiController{
    public GridPane EntranceGrid;
    public GridPane ProfessorGrid;
    public GridPane TowersGrid;
    public GridPane DiningRoomGrid;
    public TilePane BufferFromDiningRoom;
    public TilePane BufferFromEntrance;
    public ImageView closeButton;
    public ImageView checkButton;

    ArrayList<Integer> colorsFromEntrance;
    ArrayList<Integer> colorsFromDiningRoom;

    @Override
    public void initialize() {
        colorsFromEntrance= new ArrayList<>();
        colorsFromDiningRoom= new ArrayList<>();
        super.initialize();
        for(Player player:guiManager.getData().getPlayers()) {
            if (player.getUsername().equals(guiManager.getOwner())) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < player.getMySchoolBoard().getDiningRoom()[i].size(); j++) {
                        ImageView imageView = new ImageView(GuiManager.class.getResource("/graphics/pieces/student_" + COLOR.values()[i].toString().toLowerCase() + ".png").toString());
                        imageView.setPreserveRatio(true);
                        imageView.setFitWidth(60);
                        imageView.setId(i+" "+ j);
                        if (guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername())) {
                            imageView.setOnMouseEntered(this::mouseOnGeneric);
                            imageView.setOnMouseExited(this::mouseOffGeneric);
                            imageView.setOnMouseClicked(this::studentFromDiningRoomClicked);
                        }
                        DiningRoomGrid.add(imageView, j, i);
                    }
                }
                for (int i = 0; i < player.getMySchoolBoard().getEntrance().size(); i++) {
                    COLOR color = player.getMySchoolBoard().getEntrance().get(i).getColor();
                    ImageView imageView = new ImageView(GuiManager.class.getResource("/graphics/pieces/student_" + color.toString().toLowerCase() + ".png").toString());
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(60);
                    if (guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername())) {
                        imageView.setOnMouseEntered(this::mouseOnGeneric);
                        imageView.setOnMouseExited(this::mouseOffGeneric);
                        imageView.setOnMouseClicked(this::studentFromEntranceClicked);
                    }
                    imageView.setId(color.ordinal() + "   " + i);
                    if (i % 2 == 0)
                        EntranceGrid.add(imageView, 1, i / 2);
                    else
                        EntranceGrid.add(imageView, 0, i / 2 + 1);
                }
                for (Professor professor : player.getMySchoolBoard().getProfessors()) {
                    COLOR color = professor.getColor();
                    ImageView imageView = new ImageView(GuiManager.class.getResource("/graphics/pieces/teacher_" + color.toString().toLowerCase() + ".png").toString());
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(60);
                    imageView.setRotate(29.7);
                    ProfessorGrid.add(imageView, 0, color.ordinal());
                }
                for (int i = 0; i < player.getMySchoolBoard().getTowers().size(); i++) {
                    TEAM team = player.getTeam();
                    ImageView imageView = new ImageView(GuiManager.class.getResource("/graphics/pieces/" + team.toString().toLowerCase() + "_tower.png").toString());
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(150);
                    if (i % 2 == 0)
                        TowersGrid.add(imageView, 0, i / 2);
                    else
                        TowersGrid.add(imageView, 1, i / 2);
                }
            }
        }
        if(guiManager.getOwner().equals(guiManager.getData().getCurrPlayer().getUsername())){
            checkButton.setOnMouseEntered(this::mouseOnGeneric);
            checkButton.setOnMouseExited(this::mouseOffGeneric);
            checkButton.setOnMouseClicked(this::checkButtonClicked);
        }
    }

    private void studentFromEntranceClicked(MouseEvent mouseEvent) {
        if(colorsFromEntrance.size()==2)
            return;
        int color= Integer.parseInt(String.valueOf(((ImageView)mouseEvent.getSource()).getId().charAt(0)));
        ((ImageView)mouseEvent.getSource()).setImage(null);
        colorsFromEntrance.add(color);
        ImageView imageView= new ImageView(GuiManager.class.getResource("/graphics/pieces/student_"+COLOR.values()[color].toString().toLowerCase()+".png").toString());
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(60);
        BufferFromEntrance.getChildren().add(imageView);
        if(colorsFromEntrance.size()==2)
            lastStudentAdded();
    }

    private void studentFromDiningRoomClicked(MouseEvent mouseEvent) {
        if (colorsFromDiningRoom.size() == 2)
            return;
        int color = Integer.parseInt(String.valueOf(((ImageView) mouseEvent.getSource()).getId().charAt(0)));
        ((ImageView) mouseEvent.getSource()).setImage(null);
        colorsFromDiningRoom.add(color);
        ImageView imageView = new ImageView(GuiManager.class.getResource("/graphics/pieces/student_" + COLOR.values()[color].toString().toLowerCase() + ".png").toString());
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(60);
        BufferFromDiningRoom.getChildren().add(imageView);
        if (colorsFromDiningRoom.size() == 2)
            lastStudentAdded();
    }
    public void lastStudentAdded(){
        if(colorsFromDiningRoom.size()==colorsFromEntrance.size() ) {
            ArrayList<Integer> result=new ArrayList<>();
            for(int i=0;i<colorsFromDiningRoom.size();i++){
                result.add(colorsFromDiningRoom.get(i));
                result.add(colorsFromEntrance.get(i));
            }
            this.guiManager.getClient().getClientEvs().add(new UseCharacter10Event(this, result));
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

    public void closeButtonClicked(MouseEvent mouseEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void checkButtonClicked(MouseEvent mouseEvent){
        lastStudentAdded();
    }
}
