package it.polimi.ingsw.model.basicgame;
import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
import it.polimi.ingsw.model.basicgame.playeritems.Cloud;
import it.polimi.ingsw.model.basicgame.playeritems.Player;

import java.util.ArrayList;

public class BasicGame implements Game{
    private ArrayList<Player> players;
    private int numPlayers;
    private Bag bag;
    private ArrayList<ArrayList<Island>> islands;
    private ArrayList<Professor> professors;
    private int motherNature;
    private Player currPlayer;

    public BasicGame(int numPlayers) {
        this.numPlayers = numPlayers;
        this.bag = new Bag();
        this.islands = new ArrayList<ArrayList<Island>>();
        for(int i=0; i<12; i++){
            islands.add(new ArrayList<Island>());
            islands.get(i).add(new Island());
        }
        this.professors = new ArrayList<Professor>();
        for(COLOR color : COLOR.values()){
            professors.add(new Professor(color));
        }
    }

    @Override//finds the empty cloud and fills it
    public void fillCloud() {
        for(Cloud c:currPlayer.getMyCloud()){
            if(c.getStudents().size()==0){
                for(int i=0;i<3;i++)
                {
                    c.addStudent(this.bag);
                }
                if(numPlayers==3)//if there are three players,we add the fourth student
                    c.addStudent(this.bag);
            }
        }
    }

    @Override
    public AssistantCard chooseCard(int index) {
        for(AssistantCard ac:currPlayer.getMyDeck().getCards())
            if(ac.getValue() == index)
                currPlayer.getMyDeck().draw(ac);
        return null;
    }

    @Override
    public void moveStudentFromEntranceToDining(Student student) {
        currPlayer.getMySchoolBoard().removeStudentFromEntrance(student);
        currPlayer.getMySchoolBoard().addStudentToDining(student);
    }

    @Override
    public void moveStudentFromEntranceToIsland(Student student, Island chosenIsland){
        currPlayer.getMySchoolBoard().removeStudentFromEntrance(student);
        chosenIsland.addStudent(student);
    }

    @Override
    public void moveMother(int steps) {
        motherNature = (motherNature+steps) % this.islands.size();
        // fare controllo su maxSteps
    }

    @Override//from the index of the cloud,returns the cloud chosen by the player
    public Cloud chooseCloud(int cloud) {
        return currPlayer.getMyCloud().get(cloud);
    }

    @Override//moves all the students from the chosen cloud to the entrance of the school board
    public void moveStudentsFromCloud(Cloud chosenCloud) {
        if(numPlayers==2){//3 students in chosenCloud
            for(int i=0;i<3;i++) {
                currPlayer.getMySchoolBoard().addStudentToEntrance(chosenCloud.getStudents().get(i));
                chosenCloud.removeStudent(i);
            }
        }
        else//4 students in chosenCloud
            for(int i=0;i<4;i++) {
                currPlayer.getMySchoolBoard().addStudentToEntrance(chosenCloud.getStudents().get(i));
                chosenCloud.removeStudent(i);
            }
    }

    @Override
    public void computeInfluence() {
        int[] p = {0, 0, 0}; //if the players are 2, p3 remains 0
        int indexOfWinner = -1;
        int currTowerOwner = -1;
        for(Island island : this.islands.get(motherNature)){
            for(Student student:island.getStudents()){//counts every student from one island
                for(Professor professor:professors){
                    if(student.getColor()==professor.getColor() && professor.getOwner()!=null){
                        if(professor.getOwner().getUsername().equals(this.players.get(0).getUsername()))
                            p[0]++;
                        else if(professor.getOwner().getUsername().equals(this.players.get(1).getUsername()))
                            p[1]++;
                        else if(numPlayers==3)
                            p[2]++;
                    }
                }
            }
            if(island.getTower()!=null){//counts tower from one island
                if(island.getTower().getColor()==players.get(0).getTeam()) {
                    p[0]++;
                    currTowerOwner = 0;
                }
                else if(island.getTower().getColor()==players.get(1).getTeam()) {
                    p[1]++;
                    currTowerOwner = 1;
                }
                else if(numPlayers==3) {
                    p[2]++;
                    currTowerOwner = 2;
                }
            }
        }//calculate int influence
        int count = 0;
        int max = Math.max(Math.max(p[0], p[1]), p[2]);
        for(int i=0; i < 3; i++) {
            if(p[i]==max){
                indexOfWinner = i;
                count++;
            }
        }
        if (currTowerOwner == -1 && count < 2){
            for(Island island:this.islands.get(motherNature)){
                // Moves a tower from board to island
                island.setTower(players.get(indexOfWinner).getMySchoolBoard().removeTower());
            }
        }
        else if(currTowerOwner != indexOfWinner && count < 2){
            for(Island island:this.islands.get(motherNature)){
                // Moves a tower from island to board
                players.get(currTowerOwner).getMySchoolBoard().addTower(island.getTower());
                // Moves a tower from board to island
                island.setTower(players.get(indexOfWinner).getMySchoolBoard().removeTower());
            }
        }
    }

    @Override
    public void assignProfessor(COLOR color){

        // Check if professor is not owned
        if(professors.get(color.ordinal()).getOwner()==null){
            professors.get(color.ordinal()).setOwner(currPlayer);           // assignProf
        }
        // Compare influence of this player and last owner, if higher, the owner changes
        else{
            int influenceContender = currPlayer.getMySchoolBoard().getDiningRoom()[color.ordinal()].size();
            int influenceLastOwner = professors.get(color.ordinal()).getOwner().getMySchoolBoard().getDiningRoom()[color.ordinal()].size();
            if(influenceContender>influenceLastOwner){
                professors.get(color.ordinal()).setOwner(currPlayer);       // assignProf
            }
        }
    }

    @Override
    public int getMotherNature() {
        return motherNature;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public Bag getBag() {
        return bag;
    }

    public ArrayList<Professor> getProfessors() {
        return professors;
    }

    public ArrayList<ArrayList<Island>> getIslands() {
        return islands;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }

    public void setIslands(ArrayList<ArrayList<Island>> islands) {
        this.islands = islands;
    }

    public void setProfessors(ArrayList<Professor> professors) {
        this.professors = professors;
    }

    public void setMotherNature(int motherNature) {
        this.motherNature = motherNature;
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }
}
