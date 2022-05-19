package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.common.ANSIcolors.ANSI;
import it.polimi.ingsw.common.events.fromServerEvents.NotifyExceptionEvent;
import it.polimi.ingsw.common.events.fromServerEvents.RequestNumPlayersEvent;
import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.common.exceptions.*;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.controllers.GuiController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GuiManager extends View {

    private static GuiManager instance = null;//singleton
    private Client client;
    private Stage stage;
    private Scene currentScene;
    private GuiController currentController;
    private ExecutorService executor = Executors.newFixedThreadPool(128);


    private GuiManager(Client client) {
        super();
        this.client = client;
        instance = this;
    }

    public static GuiManager getInstance(Client client) {
        if (instance == null)
            return new GuiManager(client);
        else
            return instance;
    }

    public static GuiManager getInstance() {
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void gameSetUp() {
        Thread thread = new Thread("New Thread") {
            public void run() {
                GuiView.go();
            }
        };

        thread.start();
        //javafx.application.Application.launch(GuiView.class);
    }

    public void setFXML(String path) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GuiManager.class.getResource(path));
        Pane pane;
        try {
            pane = loader.load();
            currentScene.setRoot(pane);
            this.currentController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Client getClient() {
        return client;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }

    public void update(RequestNumPlayersEvent event) {
        super.update(event);
        Platform.runLater(() -> currentController.update(event)); //currentController is a NickNameRequestController
    }

    public void update(UpdatedDataEvent event) {
        super.update(event);
        Platform.runLater(() -> currentController.update(event));
    }

    public void update(NotifyExceptionEvent event) {
        String message = null;
        if (event.getException() instanceof InvalidUserNameException
                && ((InvalidUserNameException) event.getException()).getClientThatCausedEx().equals(this.client.getSocket().toString())) {
            message = Constants.INVALID_USERNAME_EXC;
            displayException(message);
            Platform.runLater(() -> this.setFXML(Constants.NICKNAME_SCENE));
        } else {
            if (getData().getCurrPlayer() != null) {
                if (getOwner() != null && getOwner().equals(getData().getCurrPlayer().getUsername())) {
                    if (event.getException() instanceof AlreadyUsedCardException || event.getException() instanceof NotAvailableCardException) {
                        message = Constants.ALREADY_USED_CARD_EXC;
                        update(new UpdatedDataEvent(this, this.getData()));
                    } else if (event.getException() instanceof StudentNotPresentException) {
                        message = Constants.STUDENT_NOT_PRESENT_EXC;
                        //moveStudent();
                    } else if (event.getException() instanceof NoMoreSpaceException) {
                        message = Constants.NO_MORE_SPACE_EXC;
                        update(new UpdatedDataEvent(this, this.getData()));
                    } else if (event.getException() instanceof InvalidIslandIndexException) {
                        message = Constants.INVALID_ISLAND_INDEX_EXC;
                        update(new UpdatedDataEvent(this, this.getData()));
                    } else if (event.getException() instanceof InvalidStepsException) {
                        message = Constants.INVALID_STEPS_EXC;
                        update(new UpdatedDataEvent(this, this.getData()));
                    } else if (event.getException() instanceof AbilityAlreadyUsedException) {
                        message = Constants.ABILITY_ALREADY_USED_EXC;
                        update(new UpdatedDataEvent(this, this.getData()));
                    } else if (event.getException() instanceof TooPoorException) {
                        message = Constants.TOO_POOR_EXC;
                        update(new UpdatedDataEvent(this, this.getData()));
                    } else if (event.getException() instanceof InvalidCharacterException) {
                        message = Constants.INVALID_CHARACTER_EXC;
                        update(new UpdatedDataEvent(this, this.getData()));
                    } else if (event.getException() instanceof InvalidNumStudentsException) {
                        message = Constants.INVALID_NUM_STUDENTS_EXC;
                        update(new UpdatedDataEvent(this, this.getData()));
                    } else if (event.getException() instanceof StudentNotPresentInCharacterException) {
                        message = Constants.STUDENT_NOT_PRESENT_IN_CHARACTER_EXC;
                        update(new UpdatedDataEvent(this, this.getData()));
                    } else if (event.getException() instanceof InvalidCharIslandIndexException) {
                        message = Constants.INVALID_CHAR_ISLAND_EXC;
                        update(new UpdatedDataEvent(this, this.getData()));
                    } else if (event.getException() instanceof CloudAlreadyChosenException) {
                        message = Constants.CLOUD_ALREADY_CHOSEN_EXCEPTION;
                        update(new UpdatedDataEvent(this, this.getData()));
                    } else if (event.getException() instanceof InvalidPhaseException) {
                        message = Constants.INVALID_PHASE_EXC;
                        update(new UpdatedDataEvent(this, this.getData()));
                    }
                    displayException(message);
                }
            }
        }
    }

    private void displayException(String message) {
        String finalMessage = message;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);
                Button okButton = new Button("Ok!");
                okButton.setOnMouseClicked(event -> {
                    dialogStage.close();
                });
                VBox vbox = new VBox(new Text(finalMessage), okButton);
                vbox.setAlignment(Pos.CENTER);
                vbox.setPadding(new Insets(30));
                vbox.setSpacing(15);
                dialogStage.setScene(new Scene(vbox));
                dialogStage.setTitle("Invalid Move!");
                dialogStage.setResizable(false);
                dialogStage.getIcons().add(new Image(GuiManager.class.getResource("/graphics/EriantysIntro.jpg").toString()));
                dialogStage.show();
            }
        });
    }

    public Stage getStage() {
        return stage;
    }

}

