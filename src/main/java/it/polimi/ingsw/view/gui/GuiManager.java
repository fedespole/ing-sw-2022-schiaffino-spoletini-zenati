/*
package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.controllers.GuiController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class GuiManager extends View {

    private static GuiManager instance = null;//singleton
    private Client client;
    private Stage stage;
    private Scene currentScene;
    private GuiController currentController;

    private GuiManager(Client client){
        super();
        this.client = client;
        instance = this;
    }

    public static GuiManager getInstance(Client client){
        if(instance == null)
            return new GuiManager(client);
        else
            return instance;
    }
    public static GuiManager getInstance(){
            return instance;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void gameSetUp(){
        GuiView.main(null);
    }

    public void setFXML(String path) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GuiManager.class.getResource(path));
        Pane pane;
            this.currentController = loader.getController();
            boolean isMaximized = stage.isMaximized();
            boolean isFullScreen = stage.isFullScreen();
            if (!isFullScreen && !isMaximized) {
                stage.setMinWidth(0);
                stage.setMinHeight(0);
                stage.sizeToScene();
                stage.setMinWidth(stage.getWidth());
                stage.setMinHeight(stage.getHeight());
            }

    }
    public Client getClient() {
        return client;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }
}
*/
