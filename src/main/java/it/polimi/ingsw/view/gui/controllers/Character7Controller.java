package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.basicgame.playeritems.Player;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import javax.swing.text.html.ImageView;

public class Character7Controller extends GuiController{
    public GridPane CharacterGrid;
    public GridPane EntranceGrid;
    public GridPane DiningRoomGrid;
    public GridPane TowersGrid;
    public GridPane ProfessorGrid;

    @Override
    public void initialize() {
        super.initialize();
        for(Player player:guiManager.getData().getPlayers()){
            if(player.getUsername().equals(guiManager.getOwner())){
                fillPlayerItems(EntranceGrid, DiningRoomGrid, ProfessorGrid, TowersGrid,  player, null);
            }
        }
    }
}
