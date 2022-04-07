package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.common.exceptions.InvalidIslandIndexException;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.Island;
import it.polimi.ingsw.model.basicgame.Professor;
import it.polimi.ingsw.model.basicgame.Student;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;

public class Character3 extends Character{

    public Character3(){
        setCost(3);
        setId(3);
        setHasBeenUsed(false);
    }

    public void useAbility(Game game, int destination) {

        ConcreteExpertGame currGame = (ConcreteExpertGame) game;

        if(destination < 0 || destination > currGame.getIslands().size()) throw new InvalidIslandIndexException();
        playerPayment(currGame.getCurrPlayer());
        changeCost();

        int[] p = {0, 0, 0}; //if the players are 2, p3 remains 0
        int indexOfWinner = -1;
        int currTowerOwner = -1;
        for (Island island : currGame.getIslands().get(destination)) {
            for (Student student : island.getStudents()) {//counts every student from one island
                for (Professor professor : currGame.getProfessors()) {
                    if (student.getColor() == professor.getColor() && professor.getOwner() != null) {
                        if (professor.getOwner() == currGame.getPlayers().get(0))
                            p[0]++;
                        else if (professor.getOwner() == currGame.getPlayers().get(1))
                            p[1]++;
                        else if (currGame.getNumPlayers() == 3)
                            p[2]++;
                    }
                }
            }
            if (island.getTower() != null) {//counts tower from one island
                if (island.getTower().getColor() == currGame.getPlayers().get(0).getTeam()) {
                    p[0]++;
                    currTowerOwner = 0;
                } else if (island.getTower().getColor() == currGame.getPlayers().get(1).getTeam()) {
                    p[1]++;
                    currTowerOwner = 1;
                } else if (currGame.getNumPlayers() == 3) {
                    p[2]++;
                    currTowerOwner = 2;
                }
            }
        }//calculate int influence
        int count = 0;
        int max = Math.max(Math.max(p[0], p[1]), p[2]);
        for (int i = 0; i < 3; i++) {
            if (p[i] == max) {
                indexOfWinner = i;
                count++;
            }
        }
        if (currTowerOwner == -1 && count < 2) {
            for (Island island : currGame.getIslands().get(destination)) {
                // Moves a tower from board to island
                island.setTower(currGame.getPlayers().get(indexOfWinner).getMySchoolBoard().removeTower());
            }
        } else if (currTowerOwner != indexOfWinner && count < 2) {
            for (Island island : currGame.getIslands().get(destination)) {
                // Moves a tower from island to board
                currGame.getPlayers().get(currTowerOwner).getMySchoolBoard().addTower(island.getTower());
                // Moves a tower from board to island
                island.setTower(currGame.getPlayers().get(indexOfWinner).getMySchoolBoard().removeTower());
            }
        }
        currGame.mergeIslands();
    }
}


