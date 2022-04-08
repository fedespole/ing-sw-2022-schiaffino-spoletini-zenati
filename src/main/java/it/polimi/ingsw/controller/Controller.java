package it.polimi.ingsw.controller;

import it.polimi.ingsw.common.events.*;
import it.polimi.ingsw.common.events.charactersEvents.*;
import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;
import it.polimi.ingsw.model.expertgame.characters.*;
import it.polimi.ingsw.model.expertgame.characters.Character;
import it.polimi.ingsw.model.expertgame.gamemodes.GameMode2;
import it.polimi.ingsw.model.expertgame.gamemodes.GameMode6;
import it.polimi.ingsw.model.expertgame.gamemodes.GameMode8;
import it.polimi.ingsw.model.expertgame.gamemodes.GameMode9;

import java.util.ArrayList;
import java.util.EventListener;

public class Controller implements EventListener {
    private Game game;

    public Game getGame() {
    return game;
  }


    public void update(PlayerAccessEvent event){
        game.getPlayers().add(new Player(event.getUsername()));
    }

    public void update(StartGameEvent event){
        if(event.isExpert()){
            game = new ConcreteExpertGame(game);
        }
        game.setUp();
    }

    public void update(DrawAssistantCardEvent event){
        game.chooseCard(event.getIndex());
    }


    public void update(MoveStudentToDiningEvent event){
        game.moveStudentFromEntranceToDining(COLOR.values()[event.getColorIndex()]);
    }

    public void update(MoveStudentToIslandEvent event){
        game.moveStudentFromEntranceToIsland(COLOR.values()[event.getColorIndex()],game.getIslands().get(event.getIslandIndex()).get(0));
    }

    public void update(MoveMotherEvent event){
        game.moveMother(event.getIndex());
    }

    public void update(ChooseCloudEvent event){
        game.chooseCloud(event.getIndex());
        if((game instanceof GameMode2) || (game instanceof GameMode6) || (game instanceof GameMode8) || (game instanceof GameMode9)){
            game = new ConcreteExpertGame((ConcreteExpertGame)game );
        }
    }

    public void update(UseCharacter1Event event){
        ConcreteExpertGame currGame = (ConcreteExpertGame)game;
        for(Character character : currGame.getCharacters()){
            if(character instanceof Character1) {
                ((Character1)character).useAbility(game,event.getIndexStudent(),game.getIslands().get(event.getIndexIsland()));
                break;
            }
        }
    }

    public void update(UseCharacter2Event event){
        ConcreteExpertGame currGame = (ConcreteExpertGame)game;
        for(Character character : currGame.getCharacters()){
            if(character instanceof Character2) {
                game = ((Character2)character).useAbility(game);
                break;
            }
        }
    }

    public void update(UseCharacter3Event event){
        ConcreteExpertGame currGame = (ConcreteExpertGame)game;
        for(Character character : currGame.getCharacters()){
            if(character instanceof Character3) {
                ((Character3)character).useAbility(game,event.getIndexIsland());
                break;
            }
        }
    }

    public void update(UseCharacter4Event event){
        ConcreteExpertGame currGame = (ConcreteExpertGame)game;
        for(Character character : currGame.getCharacters()){
            if(character instanceof Character4) {
                ((Character4)character).useAbility(game.getPlayers().get(event.getIndexPlayer()));
                break;
            }
        }
    }

    public void update(UseCharacter5Event event){
        ConcreteExpertGame currGame = (ConcreteExpertGame)game;
        for(Character character : currGame.getCharacters()){
            if(character instanceof Character5) {
                ((Character5)character).useAbility(game,game.getIslands().get(event.getIndexIsland()));
                break;
            }
        }
    }

    public void update(UseCharacter6Event event){
        ConcreteExpertGame currGame = (ConcreteExpertGame)game;
        for(Character character : currGame.getCharacters()){
            if(character instanceof Character6) {
                game = ((Character6)character).useAbility(game);
                break;
            }
        }
    }

    public void update(UseCharacter7Event event){
        ConcreteExpertGame currGame = (ConcreteExpertGame)game;
        ArrayList<COLOR> colors = new ArrayList<>();
        for(Integer i: event.getIndexesColors()){
            colors.add(COLOR.values()[i]);
        }
        for(Character character : currGame.getCharacters()){
            if(character instanceof Character7) {
                ((Character7)character).useAbility(game,colors);
                break;
            }
        }
    }

    public void update(UseCharacter8Event event){
        ConcreteExpertGame currGame = (ConcreteExpertGame)game;
        for(Character character : currGame.getCharacters()){
            if(character instanceof Character8) {
                game = ((Character8)character).useAbility(game);
                break;
            }
        }
    }

    public void update(UseCharacter9Event event){
        ConcreteExpertGame currGame = (ConcreteExpertGame)game;
        for(Character character : currGame.getCharacters()){
            if(character instanceof Character9) {
                game = ((Character9)character).useAbility(game,COLOR.values()[event.getIndexColor()]);
                break;
            }
        }
    }

    public void update(UseCharacter10Event event){
        ConcreteExpertGame currGame = (ConcreteExpertGame)game;
        ArrayList<COLOR> colors = new ArrayList<>();
        for(Integer i: event.getIndexesColors()){
            colors.add(COLOR.values()[i]);
        }
        for(Character character : currGame.getCharacters()){
            if(character instanceof Character10) {
                ((Character10)character).useAbility(game,colors);
                break;
            }
        }
    }

    public void update(UseCharacter11Event event){
        ConcreteExpertGame currGame = (ConcreteExpertGame)game;
        for(Character character : currGame.getCharacters()){
            if(character instanceof Character11) {
                ((Character11)character).useAbility(game, event.getIndexStudent());
                break;
            }
        }
    }

    public void update(UseCharacter12Event event){
        ConcreteExpertGame currGame = (ConcreteExpertGame)game;
        for(Character character : currGame.getCharacters()){
            if(character instanceof Character12) {
                ((Character12)character).useAbility(game, COLOR.values()[event.getIndexColor()]);
                break;
            }
        }
    }
}
