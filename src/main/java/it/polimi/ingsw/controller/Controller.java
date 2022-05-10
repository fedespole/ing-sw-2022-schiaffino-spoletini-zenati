package it.polimi.ingsw.controller;

import it.polimi.ingsw.common.events.*;
import it.polimi.ingsw.common.events.fromClientEvents.*;
import it.polimi.ingsw.common.events.fromClientEvents.charactersEvents.*;
import it.polimi.ingsw.common.events.fromServerEvents.*;
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
import it.polimi.ingsw.view.ViewData;

import java.util.*;

public class Controller implements EventListener {
    private Game game;
    private boolean hasCardBeenUsed;   //true if a CharacterCard has been used in this turn
    private int numOfMoveStudent;      //counts how many student the currPlayer has moved

    private HashMap<java.lang.String,Boolean> disconnectedPlayers;

    public Controller(Game game) {
        this.game = game;
        this.hasCardBeenUsed = false;
        GameHandler.addEventListener(this);
        this.numOfMoveStudent = 0;
        disconnectedPlayers = new HashMap<>();
    }

    public Game getGame() {
        return game;
    }

    public void update(PlayerAccessEvent event) {
        if (game.getStatusGame().getStatus().equals(STATUS.SETUP)) {
            checkSetUpPhase();
            for (Player player : game.getPlayers()) {
                if (player.getUsername().equals(event.getUsername())) {
                    GameHandler.calls(new NotifyExceptionEvent(this, new InvalidUserNameException(event.getClient())));
                    return;
                }
            }
            Player newPlayer = new Player(event.getUsername());
            if (getGame().getPlayers().size() != 0)
                GameHandler.calls(new NewPlayerCreatedEvent(this, newPlayer.getUsername()));
            else
                GameHandler.calls(new RequestNumPlayersEvent(this, newPlayer.getUsername()));
            game.getPlayers().add(newPlayer);
            if (getGame().getPlayers().size() == getGame().getNumPlayers()) {
                game.setUp();
                GameHandler.calls(new UpdatedDataEvent(this, game.getData()));//return updated version of a ViewData object
            }
        }
    }

    public void update(SelectedGameSetUpEvent event){
        checkSetUpPhase();

        getGame().setNumPlayers(event.getNumPlayers());
        if (event.isExpert()) {
            game = new ConcreteExpertGame(game);
        }
    }

    public void update(DrawAssistantCardEvent event) {
        int chosenValue = event.getValue();

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
        if(!allowedToPutIt && alreadyUsedCard) {
            GameHandler.calls(new NotifyExceptionEvent(this, new AlreadyUsedCardException()));
            return;
        }

        try {
            game.chooseCard(chosenValue);
            GameHandler.calls(new UpdatedDataEvent(this,game.getData()));//return updated version of a ViewData object
        } catch (NotAvailableCardException e) {
            GameHandler.calls(new NotifyExceptionEvent(this, e));
            return;
        }
        checkDisconnection();
    }

    public void update(MoveStudentToDiningEvent event) {
        checkActionMoveStudentPhase();

        if (game.getCurrPlayer().getMySchoolBoard().getDiningRoom()[event.getColorIndex()].size() >= 10) {
            GameHandler.calls(new NotifyExceptionEvent(this, new NoMoreSpaceException()));
            return;
        }
        try {
            game.moveStudentFromEntranceToDining(COLOR.values()[event.getColorIndex()]);
            GameHandler.calls(new UpdatedDataEvent(this,game.getData()));//return updated version of a ViewData object
        } catch (StudentNotPresentException e) {
            GameHandler.calls(new NotifyExceptionEvent(this, e));
            return;
        }
        numOfMoveStudent++;
        if(((numOfMoveStudent==3) && getGame().getNumPlayers()==2) || ((numOfMoveStudent==4) && getGame().getNumPlayers()==3)){
            this.getGame().getStatusGame().setStatus(STATUS.ACTION_MOVEMN);
            this.numOfMoveStudent = 0;  //resets the counter
        }
    }


    public void update(MoveStudentToIslandEvent event) {
        checkActionMoveStudentPhase();

        if (event.getIslandIndex() < 0 || event.getIslandIndex() > game.getIslands().size()) {
            GameHandler.calls(new NotifyExceptionEvent(this, new InvalidIslandIndexException()));
            return;
        }
        try {
            game.moveStudentFromEntranceToIsland(COLOR.values()[event.getColorIndex()], game.getIslands().get(event.getIslandIndex()).get(0));
            GameHandler.calls(new UpdatedDataEvent(this,game.getData()));//return updated version of a ViewData object
        } catch (StudentNotPresentException e) {
            GameHandler.calls(new NotifyExceptionEvent(this, e));
            return;
        }
        numOfMoveStudent++;
        if(((numOfMoveStudent==3) && getGame().getNumPlayers()==2) || ((numOfMoveStudent==4) && getGame().getNumPlayers()==3)){
            this.getGame().getStatusGame().setStatus(STATUS.ACTION_MOVEMN);
            this.numOfMoveStudent = 0;  //resets the counter
        }
    }

    public void update(MoveMotherEvent event) {
        checkActionMoveMotherPhase();

        if (event.getIndex() < 0 || event.getIndex() > game.getCurrPlayer().getChosenCard().getSteps()) {
            GameHandler.calls(new NotifyExceptionEvent(this, new InvalidStepsException()));
            return;
        }
        game.moveMother(event.getIndex());
        GameHandler.calls(new UpdatedDataEvent(this,game.getData()));//return updated version of a ViewData object
    }

    public void update(ChooseCloudEvent event) {
        checkActionChooseCloudPhase();
        if (game.getClouds().get(event.getIndex()).getStudents().size()==0){
            GameHandler.calls(new NotifyExceptionEvent(this, new CloudAlreadyChosenException()));
            return;
        }
        game.chooseCloud(event.getIndex());
        GameHandler.calls(new UpdatedDataEvent(this,game.getData()));//return updated version of a ViewData object
        if ((game instanceof GameMode2) || (game instanceof GameMode6) || (game instanceof GameMode8) || (game instanceof GameMode9)) {
            game = new ConcreteExpertGame((ConcreteExpertGame) game);
        }
        checkDisconnection();
    }

    public void update(UseCharacter1Event event) {
        for (Character character : ((ConcreteExpertGame)game).getCharacters()) {
            if (character instanceof Character1) {
                if(checkAbility(character))
                    return;
                ((Character1) character).useAbility(game, event.getIndexColor(), game.getIslands().get(event.getIndexIsland()));
                ViewData data = game.getData();
                data.setIndexCharacterUsed(1);
                GameHandler.calls(new UpdatedDataEvent(this,data));//return updated version of a ViewData object
                return;
            }
        }
        GameHandler.calls(new NotifyExceptionEvent(this, new InvalidCharacterException()));
    }

    public void update(UseCharacter2Event event) {
        ConcreteExpertGame currGame = (ConcreteExpertGame) game;
        for (Character character : currGame.getCharacters()) {
            if (character instanceof Character2) {
                if(checkAbility(character))
                    return;
                game = ((Character2) character).useAbility(game);
                ViewData data = game.getData();
                data.setIndexCharacterUsed(2);
                GameHandler.calls(new UpdatedDataEvent(this,data));//return updated version of a ViewData object
                return;
            }
        }
        GameHandler.calls(new NotifyExceptionEvent(this, new InvalidCharacterException()));
    }

    public void update(UseCharacter3Event event) {
        ConcreteExpertGame currGame = (ConcreteExpertGame) game;
        for (Character character : currGame.getCharacters()) {
            if (character instanceof Character3) {
                if(checkAbility(character))
                    return;
                ((Character3) character).useAbility(game, event.getIndexIsland());
                ViewData data = game.getData();
                data.setIndexCharacterUsed(3);
                GameHandler.calls(new UpdatedDataEvent(this,data));//return updated version of a ViewData object
                return;
            }
        }
        GameHandler.calls(new NotifyExceptionEvent(this, new InvalidCharacterException()));
    }

    public void update(UseCharacter4Event event) {
        ConcreteExpertGame currGame = (ConcreteExpertGame) game;
        for (Character character : currGame.getCharacters()) {
            if (character instanceof Character4) {
                if(checkAbility(character))
                    return;
                ((Character4) character).useAbility(game.getCurrPlayer());
                ViewData data = game.getData();
                data.setIndexCharacterUsed(4);
                GameHandler.calls(new UpdatedDataEvent(this,data));//return updated version of a ViewData object
                return;
            }
        }
        GameHandler.calls(new NotifyExceptionEvent(this, new InvalidCharacterException()));
    }

    public void update(UseCharacter5Event event) {
        ConcreteExpertGame currGame = (ConcreteExpertGame) game;
        for (Character character : currGame.getCharacters()) {
            if (character instanceof Character5) {
                if(checkAbility(character))
                    return;
                ((Character5) character).useAbility(game, game.getIslands().get(event.getIndexIsland()));
                ViewData data = game.getData();
                data.setIndexCharacterUsed(5);
                GameHandler.calls(new UpdatedDataEvent(this,data));//return updated version of a ViewData object
                return;
            }
        }
        GameHandler.calls(new NotifyExceptionEvent(this, new InvalidCharacterException()));
    }

    public void update(UseCharacter6Event event) {
        ConcreteExpertGame currGame = (ConcreteExpertGame) game;
        for (Character character : currGame.getCharacters()) {
            if (character instanceof Character6) {
                if(checkAbility(character))
                    return;
                game = ((Character6) character).useAbility(game);
                ViewData data = game.getData();
                data.setIndexCharacterUsed(6);
                GameHandler.calls(new UpdatedDataEvent(this,data));//return updated version of a ViewData object
                return;
            }
        }
        GameHandler.calls(new NotifyExceptionEvent(this, new InvalidCharacterException()));
    }

    public void update(UseCharacter7Event event) {
        ConcreteExpertGame currGame = (ConcreteExpertGame) game;
        ArrayList<COLOR> colors = new ArrayList<>();
        for (Integer i : event.getIndexesColors()) {
            colors.add(COLOR.values()[i]);
        }
        for (Character character : currGame.getCharacters()) {
            if (character instanceof Character7) {
                if(checkAbility(character))
                    return;
                ((Character7) character).useAbility(game, colors);
                ViewData data = game.getData();
                data.setIndexCharacterUsed(7);
                GameHandler.calls(new UpdatedDataEvent(this,data));//return updated version of a ViewData object
                return;
            }
        }
        GameHandler.calls(new NotifyExceptionEvent(this, new InvalidCharacterException()));
    }

    public void update(UseCharacter8Event event) {
        ConcreteExpertGame currGame = (ConcreteExpertGame) game;
        for (Character character : currGame.getCharacters()) {
            if (character instanceof Character8) {
                if(checkAbility(character))
                    return;
                game = ((Character8) character).useAbility(game);
                ViewData data = game.getData();
                data.setIndexCharacterUsed(8);
                GameHandler.calls(new UpdatedDataEvent(this,data));//return updated version of a ViewData object
                return;
            }
        }
        GameHandler.calls(new NotifyExceptionEvent(this, new InvalidCharacterException()));
    }

    public void update(UseCharacter9Event event) {
        ConcreteExpertGame currGame = (ConcreteExpertGame) game;
        for (Character character : currGame.getCharacters()) {
            if (character instanceof Character9) {
                if(checkAbility(character))
                    return;
                game = ((Character9) character).useAbility(game, COLOR.values()[event.getIndexColor()]);
                ViewData data = game.getData();
                data.setIndexCharacterUsed(9);
                GameHandler.calls(new UpdatedDataEvent(this,data));//return updated version of a ViewData object
                return;
            }
        }
        GameHandler.calls(new NotifyExceptionEvent(this, new InvalidCharacterException()));
    }

    public void update(UseCharacter10Event event) {
        ConcreteExpertGame currGame = (ConcreteExpertGame) game;
        ArrayList<COLOR> colors = new ArrayList<>();
        for (Integer i : event.getIndexesColors()) {
            colors.add(COLOR.values()[i]);
        }
        for (Character character : currGame.getCharacters()) {
            if (character instanceof Character10) {
                if(checkAbility(character))
                    return;
                ((Character10) character).useAbility(game, colors);
                ViewData data = game.getData();
                data.setIndexCharacterUsed(10);
                GameHandler.calls(new UpdatedDataEvent(this,data));//return updated version of a ViewData object
                return;
            }
        }
        GameHandler.calls(new NotifyExceptionEvent(this, new InvalidCharacterException()));
    }

    public void update(UseCharacter11Event event) {
        ConcreteExpertGame currGame = (ConcreteExpertGame) game;
        for (Character character : currGame.getCharacters()) {
            if (character instanceof Character11) {
                if(checkAbility(character))
                    return;
                ((Character11) character).useAbility(game, event.getIndexColor());
                ViewData data = game.getData();
                data.setIndexCharacterUsed(11);
                GameHandler.calls(new UpdatedDataEvent(this,data));//return updated version of a ViewData object
                return;
            }
        }
        GameHandler.calls(new NotifyExceptionEvent(this, new InvalidCharacterException()));
    }

    public void update(UseCharacter12Event event) {
        ConcreteExpertGame currGame = (ConcreteExpertGame) game;
        for (Character character : currGame.getCharacters()) {
            if (character instanceof Character12) {
                if(checkAbility(character))
                    return;
                ((Character12) character).useAbility(game, COLOR.values()[event.getIndexColor()]);
                ViewData data = game.getData();
                data.setIndexCharacterUsed(12);
                GameHandler.calls(new UpdatedDataEvent(this,data));//return updated version of a ViewData object
                return;
            }
        }
        GameHandler.calls(new NotifyExceptionEvent(this, new InvalidCharacterException()));
    }

    public void update(ClientDisconnectedEvent event){
        this.disconnectedPlayers.put(event.getUsername(),false);
        System.out.println("DISCONNECTED:"+event.getUsername()+" "+this.disconnectedPlayers.get(event.getUsername()));
        this.checkDisconnection();
    }

    private boolean checkAbility(Character c) {
        //checks if a card has been used in this turn
        if (hasCardBeenUsed) {
            GameHandler.calls(new NotifyExceptionEvent(this, new AbilityAlreadyUsedException()));
            return true;
        }
        //check if the player can pay the character
        if (game.getCurrPlayer().getCoins() < c.getCost()){
            GameHandler.calls(new NotifyExceptionEvent(this, new TooPoorException()));
            return true;
        }
        this.hasCardBeenUsed = true;
        return false;
    }

    private void checkSetUpPhase() {
        if (!game.getStatusGame().getStatus().equals(STATUS.SETUP))
            GameHandler.calls(new NotifyExceptionEvent(this, new InvalidPhaseException()));
    }

    private void checkPlanningPhase() {
        if (!game.getStatusGame().getStatus().equals(STATUS.PLANNING))
            GameHandler.calls(new NotifyExceptionEvent(this, new InvalidPhaseException()));
    }

    private void checkActionMoveStudentPhase() {
        if (!game.getStatusGame().getStatus().equals(STATUS.ACTION_MOVESTUD))
            GameHandler.calls(new NotifyExceptionEvent(this, new InvalidPhaseException()));
    }
    private void checkActionMoveMotherPhase() {
        if (!game.getStatusGame().getStatus().equals(STATUS.ACTION_MOVEMN))
            GameHandler.calls(new NotifyExceptionEvent(this, new InvalidPhaseException()));
    }
    private void checkActionChooseCloudPhase(){
        if (!game.getStatusGame().getStatus().equals(STATUS.ACTION_CHOOSECLOUD))
            GameHandler.calls(new NotifyExceptionEvent(this, new InvalidPhaseException()));
    }

    private void checkDisconnection() {
        if (this.disconnectedPlayers.size() >= this.game.getNumPlayers() - 1 && !this.disconnectedPlayers.containsValue(true)) {
            //fare exception x unico client rimasto
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if(disconnectedPlayers.size() >= game.getNumPlayers() - 1 && !disconnectedPlayers.containsValue(true)) {
                        String winner = null;
                        for (Player player : game.getPlayers()) {
                            if (!disconnectedPlayers.containsKey(player.getUsername())) {
                                winner = player.getUsername();
                                break;
                            }
                        }
                        if(winner!=null)
                            GameHandler.calls(new VictoryEvent(this, winner));
                        else {
                            System.out.println("GAME ENDED WITHOUT WINNERS");
                        }
                    }
                }
            }, 45*1000);

        }else if (this.disconnectedPlayers.containsKey(game.getCurrPlayer().getUsername())) {
            if (game.getStatusGame().getStatus().equals(STATUS.PLANNING)) {
                if (this.disconnectedPlayers.get(game.getCurrPlayer().getUsername())) {
                    this.disconnectedPlayers.remove(game.getCurrPlayer().getUsername());
                    System.out.println("PLAYER BACK IN THE GAME:"+game.getCurrPlayer().getUsername());
                    return;
                }
                Random random = new Random();
                int int_random = random.nextInt(game.getCurrPlayer().getMyDeck().getCards().size());
                System.out.println("COMPUTER CHOSE "+game.getCurrPlayer().getMyDeck().getCards().get(int_random).getValue() +" FOR "+game.getCurrPlayer().getUsername());
                this.update(new DrawAssistantCardEvent(this, game.getCurrPlayer().getMyDeck().getCards().get(int_random).getValue()));
            } else if (game.getStatusGame().getStatus().equals(STATUS.ACTION_MOVESTUD)) {
                System.out.println(game.getCurrPlayer().getUsername() + " TURN SKIPPED");
                if (game.getStatusGame().getOrder().indexOf(game.getCurrPlayer()) != game.getStatusGame().getOrder().size() - 1) {
                    game.setCurrPlayer(game.getStatusGame().getOrder().get(game.getStatusGame().getOrder().indexOf(game.getCurrPlayer()) + 1));
                } else {
                    game.fillClouds();
                }
            }
            checkDisconnection();
        }
    }
    public HashMap<java.lang.String, Boolean> getDisconnectedPlayers() {
        return disconnectedPlayers;
    }
}

