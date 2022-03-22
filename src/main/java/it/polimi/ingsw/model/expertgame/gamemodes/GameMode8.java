package it.polimi.ingsw.model.expertgame.gamemodes;
import it.polimi.ingsw.model.basicgame.Island;
import it.polimi.ingsw.model.basicgame.Professor;
import it.polimi.ingsw.model.basicgame.Student;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;


public class GameMode8 extends ConcreteExpertGame {
    Player player;

    public GameMode8(ConcreteExpertGame concreteExpertGame, Player player) {
        super(concreteExpertGame);
        this.player=player;
    }

    @Override
    public void computeInfluence() {
        int[] p = {0, 0, 0}; //if the players are 2, p3 remains 0
        for(int i=0;i<this.getGame().getPlayers().size();i++){
            if(this.getGame().getPlayers().get(i).equals(player)){
                p[i]+=2;
                break;
            }
        }
        int indexOfWinner = -1;
        int currTowerOwner = -1;
        for(Island island:this.getGame().getIslands().get(this.getGame().getMotherNature())) {
            for (Student student : island.getStudents()) {//counts every student from one island
                for (Professor professor : this.getGame().getProfessors()) {
                    if (student.getColor() == professor.getColor() && professor.getOwner() != null) {
                        if (professor.getOwner().getUsername().equals(this.getGame().getPlayers().get(0).getUsername()))
                            p[0]++;
                        else if (professor.getOwner().getUsername().equals(this.getGame().getPlayers().get(1).getUsername()))
                            p[1]++;
                        else if (this.getGame().getNumPlayers() == 3)
                            p[2]++;
                    }
                }
            }
            if(island.getTower()!=null){//counts tower from one island
                if(island.getTower().getColor()==this.getGame().getPlayers().get(0).getTeam()) {
                    p[0]++;
                    currTowerOwner = 0;
                }
                else if(island.getTower().getColor()==this.getGame().getPlayers().get(1).getTeam()) {
                    p[1]++;
                    currTowerOwner = 1;
                }
                else if(this.getGame().getNumPlayers()==3) {
                    p[2]++;
                    currTowerOwner = 2;
                }
            }//calculate int influence
        }
        int count = 0;
        int max = Math.max(Math.max(p[0], p[1]), p[2]);
        for(int i=0; i < 3; i++) {
            if(p[i]==max){
                indexOfWinner = i;
                count++;
            }
        }
        if (currTowerOwner == -1 && count < 2){
            for(Island island:this.getGame().getIslands().get(this.getGame().getMotherNature())){
                // Moves a tower from board to island
                island.setTower(this.getGame().getPlayers().get(indexOfWinner).getMySchoolBoard().removeTower());
            }
        }
        else if(currTowerOwner != indexOfWinner && count < 2){
            for(Island island:this.getGame().getIslands().get(this.getGame().getMotherNature())){
                // Moves a tower from island to board
                this.getGame().getPlayers().get(currTowerOwner).getMySchoolBoard().addTower(island.getTower());
                // Moves a tower from board to island
                island.setTower(this.getGame().getPlayers().get(indexOfWinner).getMySchoolBoard().removeTower());
            }
        }
    }
}
