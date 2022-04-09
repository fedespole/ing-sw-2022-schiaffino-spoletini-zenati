package it.polimi.ingsw.controller;

import it.polimi.ingsw.common.events.*;
import it.polimi.ingsw.common.events.charactersEvents.*;
import it.polimi.ingsw.common.exceptions.*;
import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.STATUS;
import it.polimi.ingsw.model.basicgame.playeritems.Deck;
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
    private boolean hasCardBeenUsed;

    public Game getGame() {
        return game;
    }

    //TODO inizializzare il booleano a false nel controller

    public void update(PlayerAccessEvent event) {
        checkSetUpPhase();
        for (Player player : game.getPlayers()) {
            if (player.getUsername().equals(event.getUsername()))
                throw new InvalidUserNameException();
        }
        if (game.getPlayers().size() > 2) {
            throw new TooManyPlayersException();
        }
        game.getPlayers().add(new Player(event.getUsername()));
    }

    public void update(StartGameEvent event) {
        checkSetUpPhase();
        if (game.getPlayers().size() < 2) {
            throw new TooLittlePlayersException();
        }
        if (event.isExpert()) {
            game = new ConcreteExpertGame(game);
        }
        game.setUp();
    }

    public void update(DrawAssistantCardEvent event) {
        if (event.getIndex() < 1 || event.getIndex() > 10)
            throw new OutOfBoundCardSelectionException();
        checkPlanningPhase();
        Deck deck = game.getCurrPlayer().getMyDeck();
        for (Player p : game.getStatusGame().getOrder()) {
            if (p.getChosenCard().getSteps() == deck.getCards().get(event.getIndex()).getSteps())
                if (deck.getCards().size() > 1) {
                    throw new AlreadyUsedCard();
                }
        }
        try {
            game.chooseCard(event.getIndex());
        } catch (NotAvailableCardException e) {
        }
    }


    public void update(MoveStudentToDiningEvent event) {
        checkActionPhase();
        if (!event.getSource().equals(game.getCurrPlayer()))
            throw new InvalidPlayerException();
        if (event.getColorIndex() < 0 || event.getColorIndex() > COLOR.values().length)
            throw new InvalidColorException();
        if (game.getCurrPlayer().getMySchoolBoard().getDiningRoom()[event.getColorIndex()].size() >= 10) {
            throw new NoMoreSpaceException();
        }
        try {
            game.moveStudentFromEntranceToDining(COLOR.values()[event.getColorIndex()]);
        } catch (StudentNotPresentException e) {
        }
    }


    public void update(MoveStudentToIslandEvent event) {
        checkActionPhase();
        if (!event.getSource().equals(game.getCurrPlayer()))
            throw new InvalidPlayerException();
        if (event.getIslandIndex() < 0 || event.getIslandIndex() > game.getIslands().size())
            throw new InvalidIslandIndexException();
        if (event.getColorIndex() < 0 || event.getColorIndex() > COLOR.values().length)
            throw new InvalidColorException();
        try {
            game.moveStudentFromEntranceToIsland(COLOR.values()[event.getColorIndex()], game.getIslands().get(event.getIslandIndex()).get(0));
        } catch (StudentNotPresentException e) {
        }
    }

    public void update(MoveMotherEvent event) {
        checkActionPhase();
        if (!event.getSource().equals(game.getCurrPlayer()))
            throw new InvalidPlayerException();
        if (event.getIndex() < 0 || event.getIndex() > game.getCurrPlayer().getChosenCard().getSteps())
            throw new InvalidStepsException();
        game.moveMother(event.getIndex());
    }

    public void update(ChooseCloudEvent event) {
        checkActionPhase();
        if (!event.getSource().equals(game.getCurrPlayer()))
            throw new InvalidPlayerException();
        if (event.getIndex() < 0 || event.getIndex() >= game.getClouds().size())
            throw new InvalidCloudIndexException();
        game.chooseCloud(event.getIndex());
        if ((game instanceof GameMode2) || (game instanceof GameMode6) || (game instanceof GameMode8) || (game instanceof GameMode9)) {
            game = new ConcreteExpertGame((ConcreteExpertGame) game);
        }
    }

    //TODO CharacterAbilityException dentro i character?
    public void update(UseCharacter1Event event) {
        ConcreteExpertGame currGame = (ConcreteExpertGame) game;
        for (Character character : currGame.getCharacters()) {
            if (character instanceof Character1) {
                checkAbility(character);
                ((Character1) character).useAbility(game, event.getIndexStudent(), game.getIslands().get(event.getIndexIsland()));
                return;
            }
        }
        throw new InvalidCharacterException();
    }

    public void update(UseCharacter2Event event) {
        ConcreteExpertGame currGame = (ConcreteExpertGame) game;
        for (Character character : currGame.getCharacters()) {
            if (character instanceof Character2) {
                checkAbility(character);
                game = ((Character2) character).useAbility(game);
                return;
            }
        }
        throw new InvalidCharacterException();
    }

    public void update(UseCharacter3Event event) {
        ConcreteExpertGame currGame = (ConcreteExpertGame) game;
        for (Character character : currGame.getCharacters()) {
            if (character instanceof Character3) {
                checkAbility(character);
                ((Character3) character).useAbility(game, event.getIndexIsland());
                return;
            }
        }
        throw new InvalidCharacterException();
    }

    public void update(UseCharacter4Event event) {
        ConcreteExpertGame currGame = (ConcreteExpertGame) game;
        for (Character character : currGame.getCharacters()) {
            if (character instanceof Character4) {
                checkAbility(character);
                ((Character4) character).useAbility(game.getPlayers().get(event.getIndexPlayer()));
                return;
            }
        }
        throw new InvalidCharacterException();
    }

    public void update(UseCharacter5Event event) {
        ConcreteExpertGame currGame = (ConcreteExpertGame) game;
        for (Character character : currGame.getCharacters()) {
            if (character instanceof Character5) {
                checkAbility(character);
                ((Character5) character).useAbility(game, game.getIslands().get(event.getIndexIsland()));
                return;
            }
        }
        throw new InvalidCharacterException();
    }

    public void update(UseCharacter6Event event) {
        ConcreteExpertGame currGame = (ConcreteExpertGame) game;
        for (Character character : currGame.getCharacters()) {
            if (character instanceof Character6) {
                checkAbility(character);
                game = ((Character6) character).useAbility(game);
                return;
            }
        }
        throw new InvalidCharacterException();
    }

    public void update(UseCharacter7Event event) {
        ConcreteExpertGame currGame = (ConcreteExpertGame) game;
        ArrayList<COLOR> colors = new ArrayList<>();
        for (Integer i : event.getIndexesColors()) {
            colors.add(COLOR.values()[i]);
        }
        for (Character character : currGame.getCharacters()) {
            if (character instanceof Character7) {
                checkAbility(character);
                ((Character7) character).useAbility(game, colors);
                return;
            }
        }
        throw new InvalidCharacterException();
    }

    public void update(UseCharacter8Event event) {
        ConcreteExpertGame currGame = (ConcreteExpertGame) game;
        for (Character character : currGame.getCharacters()) {
            if (character instanceof Character8) {
                checkAbility(character);
                game = ((Character8) character).useAbility(game);
                return;
            }
        }
        throw new InvalidCharacterException();
    }

    public void update(UseCharacter9Event event) {
        ConcreteExpertGame currGame = (ConcreteExpertGame) game;
        for (Character character : currGame.getCharacters()) {
            if (character instanceof Character9) {
                checkAbility(character);
                game = ((Character9) character).useAbility(game, COLOR.values()[event.getIndexColor()]);
                return;
            }
        }
        throw new InvalidCharacterException();
    }

    public void update(UseCharacter10Event event) {
        ConcreteExpertGame currGame = (ConcreteExpertGame) game;
        ArrayList<COLOR> colors = new ArrayList<>();
        for (Integer i : event.getIndexesColors()) {
            colors.add(COLOR.values()[i]);
        }
        for (Character character : currGame.getCharacters()) {
            if (character instanceof Character10) {
                checkAbility(character);
                ((Character10) character).useAbility(game, colors);
                return;
            }
        }
        throw new InvalidCharacterException();
    }

    public void update(UseCharacter11Event event) {
        ConcreteExpertGame currGame = (ConcreteExpertGame) game;
        for (Character character : currGame.getCharacters()) {
            if (character instanceof Character11) {
                checkAbility(character);
                ((Character11) character).useAbility(game, event.getIndexStudent());
                return;
            }
        }
        throw new InvalidCharacterException();
    }

    public void update(UseCharacter12Event event) {
        ConcreteExpertGame currGame = (ConcreteExpertGame) game;
        for (Character character : currGame.getCharacters()) {
            if (character instanceof Character12) {
                checkAbility(character);
                ((Character12) character).useAbility(game, COLOR.values()[event.getIndexColor()]);
                return;
            }
        }
        throw new InvalidCharacterException();
    }

    private void checkAbility(Character c) {
        //checks if a card has been used in this turn
        if (hasCardBeenUsed)
            throw new AbilityAlreadyUsedException();
        //check if the player can pay the character
        if (game.getCurrPlayer().getCoins() < c.getCost())
            throw new TooPoorException();
        this.hasCardBeenUsed = true;
    }

    private void checkSetUpPhase() {
        if (game.getStatusGame().getStatus().equals(STATUS.SETUP))
            throw new InvalidPhaseException();
    }

    private void checkPlanningPhase() {
        if (game.getStatusGame().getStatus().equals(STATUS.PLANNING))
            throw new InvalidPhaseException();
    }

    private void checkActionPhase() {
        if (game.getStatusGame().getStatus().equals(STATUS.ACTION))
            throw new InvalidPhaseException();
    }

}

