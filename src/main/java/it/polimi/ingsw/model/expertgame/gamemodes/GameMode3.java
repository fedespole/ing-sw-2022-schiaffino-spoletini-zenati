package it.polimi.ingsw.model.expertgame.gamemodes;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;

public class GameMode3 extends ConcreteExpertGame {
    public GameMode3(ConcreteExpertGame concreteGame,int island){//island to call computeInfluence
        super(concreteGame);
        int real_motherNature=this.getGame().getMotherNature();//aggiungere metodo
        this.getGame().moveMother(island);
        this.getGame().computeInfluence();
        this.getGame().moveMother(real_motherNature);
    }
}
