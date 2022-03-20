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
    public void fillCloud() {
        game.fillCloud();
    }

    @Override
    public AssistantCard chooseCard() {
        return game.chooseCard();
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
    public Cloud chooseCloud() {
        return game.chooseCloud();
    }

    @Override
    public void moveStudentsFromCloud(Cloud chosenCloud) {
        game.moveStudentsFromCloud(chosenCloud);
    }

    @Override
    public void computeInfluence() {
        game.computeInfluence();
    }
}
