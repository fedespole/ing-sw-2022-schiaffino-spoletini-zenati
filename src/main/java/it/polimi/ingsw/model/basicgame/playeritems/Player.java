package it.polimi.ingsw.model.basicgame.playeritems;

import it.polimi.ingsw.model.basicgame.*;

import java.io.Serializable;
import java.util.Objects;

public class Player implements Serializable {
    private final java.lang.String username;
    private int coins;
    private final Deck myDeck;
    private final SchoolBoard mySchoolBoard;
    private TEAM team;
    private AssistantCard chosenCard;
    private boolean hasCharacterCardBeenUsed;

    public Player(java.lang.String username) {
        this.username = username;
        this.myDeck = new Deck();
        this.mySchoolBoard = new SchoolBoard();
        this.coins = -1;
        this.hasCharacterCardBeenUsed = false;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Deck getMyDeck() {
        return myDeck;
    }

    public SchoolBoard getMySchoolBoard() {
        return mySchoolBoard;
    }

    public java.lang.String getUsername() {
        return username;
    }

    public TEAM getTeam() {
        return team;
    }

    public int getCoins() {
        return coins;
    }

    public void setTeam(TEAM team) {
        this.team = team;
    }

    public AssistantCard getChosenCard() {
        return chosenCard;
    }

    public void setChosenCard(AssistantCard chosenCard) {
        this.chosenCard = chosenCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return username.equals(player.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public boolean isHasCharacterCardBeenUsed() {
        return hasCharacterCardBeenUsed;
    }

    public void setHasCharacterCardBeenUsed(boolean hasCharacterCardBeenUsed) {
        this.hasCharacterCardBeenUsed = hasCharacterCardBeenUsed;
    }
}


