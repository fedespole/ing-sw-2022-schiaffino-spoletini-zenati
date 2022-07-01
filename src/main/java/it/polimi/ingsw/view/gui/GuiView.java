
package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This is a JavaFX Application class that creates a new stage with the first scene
 */
public class GuiView extends Application {


    @Override
    public void start(Stage stage) {

        stage.setScene(new Scene(new Pane()));
        stage.sizeToScene();
        stage.setResizable(false);
        GuiManager.getInstance().setStage(stage);
        GuiManager.getInstance().setCurrentScene(stage.getScene());
        GuiManager.getInstance().setFXML(Constants.NICKNAME_SCENE);
        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
        stage.setTitle("Eriantys");
        stage.getIcons().add(new Image(GuiManager.class.getResource("/graphics/EriantysIntro.jpg").toString()));
        stage.setOnCloseRequest((windowEvent) -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void go() {
        launch(null);
    }
}

