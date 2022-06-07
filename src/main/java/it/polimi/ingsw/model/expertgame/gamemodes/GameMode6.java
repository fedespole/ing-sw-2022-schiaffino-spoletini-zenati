package it.polimi.ingsw.model.expertgame.gamemodes;

import it.polimi.ingsw.common.events.GameHandler;
import it.polimi.ingsw.common.events.fromServerEvents.VictoryEvent;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.Island;
import it.polimi.ingsw.model.basicgame.Professor;
import it.polimi.ingsw.model.basicgame.Student;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;

public class GameMode6 extends ConcreteExpertGame {
    public GameMode6(ConcreteExpertGame concreteExpertGame) {
        super(concreteExpertGame);
    }

    @Override
    public void computeInfluence() {
        int[] p = {0, 0, 0}; //if the players are 2, p3 remains 0
        int indexOfWinner = -1;
        int currTowerOwner = -1;
        for (Island island : this.getIslands().get(getMotherNature())) {
            for (Student student : island.getStudents()) {//counts every student from one island
                for (Professor professor : this.getProfessors()) {
                    if (student.getColor() == professor.getColor() && professor.getOwner() != null) {
                        if (professor.getOwner() == this.getPlayers().get(0))
                            p[0]++;
                        else if (professor.getOwner() == this.getPlayers().get(1))
                            p[1]++;
                        else if (this.getNumPlayers() == 3)
                            p[2]++;
                    }
                }
            }
            if (island.getTower() != null) {//save currTowerOwner
                if (island.getTower().getColor() == this.getPlayers().get(0).getTeam()) {
                    currTowerOwner = 0;
                } else if (island.getTower().getColor() == this.getPlayers().get(1).getTeam()) {
                    currTowerOwner = 1;
                } else if (this.getNumPlayers() == 3) {
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
            for (Island island : this.getIslands().get(this.getMotherNature())) {
                // Moves a tower from board to island
                island.setTower(this.getPlayers().get(indexOfWinner).getMySchoolBoard().removeTower());
            }
        } else if (currTowerOwner != indexOfWinner && count < 2) {
            for (Island island : this.getIslands().get(getMotherNature())) {
                // Moves a tower from island to board
                this.getPlayers().get(currTowerOwner).getMySchoolBoard().addTower(island.getTower());
                // Moves a tower from board to island
                island.setTower(this.getPlayers().get(indexOfWinner).getMySchoolBoard().removeTower());
            }
        }
        this.mergeIslands();

        if(this.getIslands().size()==3) checkWinner();

        if(getCurrPlayer().getMySchoolBoard().getTowers().size()==0){
            GameHandler.calls(new VictoryEvent(this, getCurrPlayer().getUsername()));
        }

    }
}