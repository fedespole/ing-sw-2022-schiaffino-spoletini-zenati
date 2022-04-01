package it.polimi.ingsw.model.basicgame;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
import it.polimi.ingsw.model.basicgame.playeritems.Player;

import java.util.ArrayList;
import java.util.Random;

public class BasicGame implements Game{
    private ArrayList<Player> players;
    private int numPlayers;
    private Bag bag;
    private ArrayList<ArrayList<Island>> islands;
    private ArrayList<Professor> professors;
    private int motherNature;
    private Player currPlayer;
    private final ArrayList<Cloud> clouds;


    public BasicGame(Player host) {
        this.bag = new Bag();
        this.islands = new ArrayList<ArrayList<Island>>();
        this.players = new ArrayList<Player>();
        this.clouds = new ArrayList<Cloud>();
        for(int i=0; i<12; i++){
            islands.add(new ArrayList<Island>());
            islands.get(i).add(new Island());
        }
        this.professors = new ArrayList<Professor>();
        for(COLOR color : COLOR.values()){
            professors.add(new Professor(color));
        }
        this.players.add(host);
    }

    @Override
    public void setUp() throws InvalidNumPlayerException {

        if(this.players.size()>3 || this.players.size()<2) throw new InvalidNumPlayerException();

        this.numPlayers=this.players.size();
        this.currPlayer=this.players.get(0);
        Random new_random= new Random();
        this.motherNature = new_random.nextInt(11);
        for(int i=0;i<12;i++){
            if(i!=this.motherNature && i!=(this.motherNature+6)%12)
                this.getIslands().get(i).get(0).addStudent(this.bag.removeStudent());
        }
        for(COLOR c : COLOR.values()) {
            for (int i = 0; i < 24; i++) {
                this.bag.getStudents().add(new Student(c));
            }
        }
        for(int i=0;i<numPlayers;i++){
            this.players.get(i).setTeam(TEAM.values()[i]);
            switch(numPlayers) {
                case 2:
                    for(int j=0;j<7;j++){
                        this.players.get(i).getMySchoolBoard().addStudentToEntrance(this.bag.removeStudent());
                    }
                    this.clouds.add(new Cloud());
                    this.clouds.add(new Cloud());

                    for (int j = 0; j < 8; j++)
                        this.players.get(i).getMySchoolBoard().addTower(new Tower(TEAM.values()[i]));

                    break;
                case 3:
                    for(int j=0;j<9;j++){
                        this.players.get(i).getMySchoolBoard().addStudentToEntrance(this.bag.removeStudent());
                    }
                    this.clouds.add(new Cloud());
                    this.clouds.add(new Cloud());
                    this.clouds.add(new Cloud());

                    for (int j = 0; j < 6; j++)
                        this.players.get(i).getMySchoolBoard().addTower(new Tower(TEAM.values()[i]));

                    break;
            }
        }
    }

    @Override//finds the empty cloud and fills it
    public void fillCloud() {

        for(Cloud c: clouds){

            switch (numPlayers){
                case 2:
                    c.addStudent(this.bag);
                    c.addStudent(this.bag);
                    c.addStudent(this.bag);
                    break;

                case 3:
                    c.addStudent(this.bag);
                    c.addStudent(this.bag);
                    c.addStudent(this.bag);
                    c.addStudent(this.bag);
                    break;
            }
        }
    }

    @Override
    public void chooseCard(int value){

        if(value<1 || value>10)
            throw new OutOfBoundCardSelectionException();

        for(AssistantCard ac:currPlayer.getMyDeck().getCards())
            if(ac.getValue() == value) {
                currPlayer.setChosenCard(currPlayer.getMyDeck().draw(ac));
                return;
            }

        throw new NotAvailableCardException();  //if the card has already been drawn
    }

    @Override
    public void moveStudentFromEntranceToDining(COLOR color) {
        currPlayer.getMySchoolBoard().addStudentToDining(currPlayer.getMySchoolBoard().removeStudentFromEntrance(color));
        assignProfessor(color);
    }

    @Override
    public void moveStudentFromEntranceToIsland(COLOR color, Island chosenIsland){
        chosenIsland.addStudent(currPlayer.getMySchoolBoard().removeStudentFromEntrance(color));
    }

    @Override
    public void moveMother(int steps) {
        if(steps < 0 || steps > currPlayer.getChosenCard().getSteps()) throw new InvalidStepsException();
        motherNature = (motherNature+steps) % this.islands.size();
    }

    @Override//from the index of the cloud,returns the cloud chosen by the player
    public Cloud chooseCloud(int cloud) {
        if(cloud < 0 || cloud > clouds.size()) throw new InvalidCloudIndexException();
        return this.clouds.get(cloud);
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
                        if(professor.getOwner()==this.players.get(0))
                            p[0]++;
                        else if(professor.getOwner()==this.players.get(1))
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
        this.mergeIslands();
    }

    @Override
    public void assignProfessor(COLOR color){

        // Check if professor is not owned
        if(professors.get(color.ordinal()).getOwner()==null){
            professors.get(color.ordinal()).setOwner(currPlayer);           // assignProf
            currPlayer.getMySchoolBoard().addProfessor(professors.get(color.ordinal()));
        }
        // Compare influence of this player and last owner, if higher, the owner changes
        else{
            int influenceContender = currPlayer.getMySchoolBoard().getDiningRoom()[color.ordinal()].size();
            int influenceLastOwner = professors.get(color.ordinal()).getOwner().getMySchoolBoard().getDiningRoom()[color.ordinal()].size();
            if(influenceContender>influenceLastOwner){
                professors.get(color.ordinal()).setOwner(currPlayer);       // assignProf
                currPlayer.getMySchoolBoard().addProfessor(professors.get(color.ordinal()).getOwner().getMySchoolBoard().removeProfessor( professors.get(color.ordinal())));   // assignProf
            }
        }
    }

    @Override
    public void mergeIslands(){
        if(this.islands.get(motherNature).get(0).getTower()==null)
            return;
        int prior,next;
        ArrayList<Island> removePrior = null;
        if(motherNature==0)
            prior=this.islands.size()-1;
        else
            prior=motherNature-1;
        if(motherNature==this.islands.size()-1)
            next=0;
        else
            next=motherNature+1;
        if(this.islands.get(prior).get(0).getTower()!=null && this.islands.get(prior).get(0).getTower().getColor().equals(this.islands.get(motherNature).get(0).getTower().getColor())){
            while(this.islands.get(prior).size()!=0){
                this.islands.get(motherNature).add(this.islands.get(prior).remove(0));
            }
            removePrior = this.islands.get(prior);
        }
        if(this.islands.get(next).get(0).getTower()!=null && this.islands.get(next).get(0).getTower().getColor().equals(this.islands.get(motherNature).get(0).getTower().getColor())){
            while(this.islands.get(next).size()!=0){
                this.islands.get(motherNature).add(this.islands.get(next).remove(0));
            }
            this.islands.remove(next);
        }

        if(removePrior!=null) this.islands.remove(removePrior);
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

    public ArrayList<Cloud> getClouds() {
        return clouds;
    }
}
