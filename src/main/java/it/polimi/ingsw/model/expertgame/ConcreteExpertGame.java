package it.polimi.ingsw.model.expertgame;

import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
import it.polimi.ingsw.model.basicgame.BasicGame;
import it.polimi.ingsw.model.basicgame.playeritems.Cloud;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.characters.Character;

public class ConcreteExpertGame extends ExpertGameDecorator {
    private BasicGame game;
    private Character[] characters;

    public ConcreteExpertGame(BasicGame game) {
        this.game = game;
        for(Player player : game.getPlayers()){
            player.setCoins(0);
        }
        //qui vengono scelti tre personaggi a caso
    }

    @Override
    public void fillCloud(Player player) {
        game.fillCloud(player);
    }

    @Override
    public AssistantCard chooseCard(int index,Player player) {
        return game.chooseCard(index,player);
    }

    @Override
    public void movestudentsfromEntrance() {
        game.movestudentsfromEntrance();
    }

    @Override
    public void moveMother(int steps) {
        game.moveMother(steps);
    }

    @Override
    public Cloud chooseCloud(Player player,int cloud) {
        return game.chooseCloud(player,cloud);
    }

    @Override
    public void moveStudentsFromCloud(Player player,Cloud chosenCloud) {
        game.moveStudentsFromCloud(player,chosenCloud);
    }

    @Override
    public void computeInfluence() {
        game.computeInfluence();
    }
}
