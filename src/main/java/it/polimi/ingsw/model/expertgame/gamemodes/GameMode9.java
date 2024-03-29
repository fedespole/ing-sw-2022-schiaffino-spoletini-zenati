package it.polimi.ingsw.model.expertgame.gamemodes;
import it.polimi.ingsw.common.events.GameHandler;
import it.polimi.ingsw.common.events.fromServerEvents.VictoryEvent;
import it.polimi.ingsw.model.basicgame.*;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;
/**
 * This class extends the ConcreteExpertGame and changes the rules of the turn when the character 9 is called.
 */
public class GameMode9 extends ConcreteExpertGame {
    COLOR color;

    public GameMode9(ConcreteExpertGame concreteExpertGame, COLOR color) {
        super(concreteExpertGame);
        this.color=color;
    }

    @Override
    public void computeInfluence() {
        int[] p = {0, 0, 0}; //if the players are 2, p3 remains 0
        int indexOfWinner = -1;
        int currTowerOwner = -1;
        for (Island island : this.getIslands().get(getMotherNature())) {
            for (Student student : island.getStudents()) {//counts every student from one island
                for (Professor professor : this.getProfessors()) {
                    if (student.getColor() == professor.getColor() && professor.getOwner() != null &&  professor.getColor()!=color) {
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
                    p[0]++;
                    currTowerOwner = 0;
                } else if (island.getTower().getColor() == this.getPlayers().get(1).getTeam()) {
                    p[1]++;
                    currTowerOwner = 1;
                } else if (this.getNumPlayers() == 3) {
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