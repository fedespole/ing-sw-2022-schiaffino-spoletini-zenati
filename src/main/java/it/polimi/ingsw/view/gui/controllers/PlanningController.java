package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.GuiManager;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.io.File;

public class PlanningController extends GuiController{

    public FlowPane AssistantCards;
    public ImageView AssistantCard0;

    @FXML
    public void initialize() {
        super.initialize();
        guiManager.getStage().setFullScreen(true);
        Image image = new Image(new File("@../graphics/playerItems/deck/assistantCards/Assistente (1).png").toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(0);
        AssistantCards.getChildren().add(imageView);
    }


    public void mouseOnCharacters(MouseEvent mouseEvent) {
        AssistantCards.alignmentProperty().setValue(Pos.TOP_CENTER);
    }


    public void mouseOffCharacters(MouseEvent mouseEvent) {
        AssistantCards.alignmentProperty().setValue(Pos.BOTTOM_CENTER);
    }
}
