package it.polimi.ingsw.controller;

import it.polimi.ingsw.common.events.*;
import it.polimi.ingsw.common.events.charactersEvents.*;
import it.polimi.ingsw.common.events.fromClientEvents.*;
import it.polimi.ingsw.common.events.fromServerEvents.NewPlayerCreatedEvent;
import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.common.exceptions.*;
import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.STATUS;
import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
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

    public Controller(Game game) {
        this.game = game;
        this.hasCardBeenUsed = false;
        GameHandler.addEventListener(this);
    }

    public Game getGame() {
        return game;
    }


    public void update(PlayerAccessEvent event) {
        checkSetUpPhase();

        for (Player player : game.getPlayers()) {
            if (player.getUsername().equals(event.getUsername()))
                throw new InvalidUserNameException();
        }
        Player newPlayer = new Player(event.getUsername());
        game.getPlayers().add(newPlayer);
        if(getGame().getPlayers().size()!=0)
            GameHandler.calls(new NewPlayerCreatedEvent(this, newPlayer));
        else
            GameHandler.calls(new RequestNumPlayersEvent(this,newPlayer));
        if(getGame().getPlayers().size() == getGame().getNumPlayers())
            game.setUp();
    }

    public void update(SelectedGameSetUpEvent event){
        checkSetUpPhase();

        if(event.getNumPlayers()<2 || event.getNumPlayers()>3){
            throw new InvalidNumPlayersException();
        }
        getGame().setNumPlayers(event.getNumPlayers());
        if (event.isExpert()) {
            game = new ConcreteExpertGame(game);
        }
    }

    public void update(DrawAssistantCardEvent event) {
        int chosenValue = event.getValue();

        if (chosenValue < 1 || chosenValue > 10)
            throw new OutOfBoundCardSelectionException();

        checkPlanningPhase();

        boolean alreadyUsedCard = false;
        Deck deck = getGame().getCurrPlayer().getMyDeck();
        ArrayList<AssistantCard> cards = new ArrayList<AssistantCard>();
        for(Player p: getGame().getStatusGame().getOrder()){
            //save in "cards" all played cards
            cards.add(p.getChosenCard());
            if(p.getChosenCard().getValue()==chosenValue && !p.equals(game.getCurrPlayer()))
                //put alreadUsedCard true if someone has already played a card with same chosenValue
                alreadyUsedCard = true;
        }
        boolean allowedToPutIt = deck.getCards().containsAll(cards) && cards.size() == deck.getCards().size(); //my deck has only cards that were played
        if(!allowedToPutIt && alreadyUsedCard)
            throw new AlreadyUsedCardException();

        try {
            game.chooseCard(chosenValue);
            GameHandler.calls(new UpdatedDataEvent(this,game.getData()));//return updated version of a ViewData object
        } catch (NotAvailableCardException e) {
            throw new NotAvailableCardException();
        }
    }


    public void update(MoveStudentToDiningEvent event) {
        checkActionPhase();
        if (!event.getSource().equals(game.getCurrPlayer()))
            throw new InvalidPlayerException();
        if (event.getColorIndex() < 0)
            throw new InvalidColorException();
        if (game.getCurrPlayer().getMySchoolBoard().getDiningRoom()[event.getColorIndex()].size() >= 10) {
            throw new NoMoreSpaceException();
        }
        try {
            game.moveStudentFromEntranceToDining(COLOR.values()[event.getColorIndex()]);
            GameHandler.calls(new UpdatedDataEvent(this,game.getData()));//return updated version of a ViewData object
        } catch (StudentNotPresentException e) {
            throw new StudentNotPresentException();
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
            GameHandler.calls(new UpdatedDataEvent(this,game.getData()));//return updated version of a ViewData object
        } catch (StudentNotPresentException e) {
            throw new StudentNotPresentException();
        }
    }

    public void update(MoveMotherEvent event) {
        checkActionPhase();
        if (!event.getSource().equals(game.getCurrPlayer()))
            throw new InvalidPlayerException();
        if (event.getIndex() < 0 || event.getIndex() > game.getCurrPlayer().getChosenCard().getSteps())
            throw new InvalidStepsException();
        game.moveMother(event.getIndex());
        GameHandler.calls(new UpdatedDataEvent(this,game.getData()));//return updated version of a ViewData object
    }

    public void update(ChooseCloudEvent event) {
        checkActionPhase();
        if (!event.getSource().equals(game.getCurrPlayer()))
            throw new InvalidPlayerException();
        if (event.getIndex() < 0 || event.getIndex() >= game.getClouds().size())
            throw new InvalidCloudIndexException();
        game.chooseCloud(event.getIndex());
        GameHandler.calls(new UpdatedDataEvent(this,game.getData()));//return updated version of a ViewData object
        if ((game instanceof GameMode2) || (game instanceof GameMode6) || (game instanceof GameMode8) || (game instanceof GameMode9)) {
            game = new ConcreteExpertGame((ConcreteExpertGame) game);
        }
    }

    public void update(UseCharacter1Event event) {
        for (Character character : ((ConcreteExpertGame)game).getCharacters()) {
            if (character instanceof Character1) {
                checkAbility(character);
                ((Character1) character).useAbility(game, event.getIndexStudent(), game.getIslands().get(event.getIndexIsland()));
                GameHandler.calls(new UpdatedDataEvent(this,game.getData()));//return updated version of a ViewData object
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
                GameHandler.calls(new UpdatedDataEvent(this,game.getData()));//return updated version of a ViewData object
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
                GameHandler.calls(new UpdatedDataEvent(this,game.getData()));//return updated version of a ViewData object
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
                GameHandler.calls(new UpdatedDataEvent(this,game.getData()));//return updated version of a ViewData object
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
                GameHandler.calls(new UpdatedDataEvent(this,game.getData()));//return updated version of a ViewData object
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
                GameHandler.calls(new UpdatedDataEvent(this,game.getData()));//return updated version of a ViewData object
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
                GameHandler.calls(new UpdatedDataEvent(this,game.getData()));//return updated version of a ViewData object
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
                GameHandler.calls(new UpdatedDataEvent(this,game.getData()));//return updated version of a ViewData object
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
                GameHandler.calls(new UpdatedDataEvent(this,game.getData()));//return updated version of a ViewData object
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
        if (!game.getStatusGame().getStatus().equals(STATUS.SETUP))
            throw new InvalidPhaseException();
    }

    private void checkPlanningPhase() {
        if (!game.getStatusGame().getStatus().equals(STATUS.PLANNING))
            throw new InvalidPhaseException();
    }

    private void checkActionPhase() {
        if (!game.getStatusGame().getStatus().equals(STATUS.ACTION))
            throw new InvalidPhaseException();
    }

}

