package it.polimi.ingsw.model.basicgame;
import it.polimi.ingsw.common.events.*;
import it.polimi.ingsw.common.exceptions.*;
import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
import it.polimi.ingsw.model.basicgame.playeritems.Player;

import java.util.ArrayList;
import java.util.Random;

public class BasicGame implements Game{
    private final ArrayList<Player> players;
    private int numPlayers;
    private final Bag bag;
    private final ArrayList<ArrayList<Island>> islands;
    private final ArrayList<Professor> professors;
    private int motherNature;
    private Player currPlayer;
    private final ArrayList<Cloud> clouds;
    private final StatusGame statusGame;
    private boolean lastRound;

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
        statusGame=new StatusGame();
        lastRound=false;
    }

    @Override
    public void setUp() throws InvalidNumPlayerException {

        if(this.players.size()>3 || this.players.size()<2) throw new InvalidNumPlayerException();

        this.numPlayers=this.players.size();
        if(this.numPlayers==2){
            this.currPlayer=this.players.get(new Random().nextInt(2));
        }
        else {
            this.currPlayer = this.players.get(new Random().nextInt(3));
        }
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
        fillClouds();
    }

    @Override// fills all the clouds
    public void fillClouds() {


        if (numPlayers==2 && (this.getBag().getStudents().size() < 6)) lastRound = true;
        else if (numPlayers==3 && (this.getBag().getStudents().size() < 12)) lastRound = true;
        else {
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
        if(this.statusGame.getStatus()==STATUS.ACTION) {
            // Last method of action phase, this player will be the first to draw the card in the next round
            this.currPlayer = this.statusGame.getOrder().get(0);
            this.statusGame.getOrder().clear();
        }
        this.statusGame.setStatus(STATUS.PLANNING);
    }

    @Override
    public void chooseCard(int value){

        if(value<1 || value>10)
            throw new OutOfBoundCardSelectionException();

        for(AssistantCard ac:currPlayer.getMyDeck().getCards()) {
            if (ac.getValue() == value) {
                currPlayer.setChosenCard(currPlayer.getMyDeck().draw(ac));

                // Set the order in action phase
                this.statusGame.addSort(currPlayer);

                if (this.statusGame.getOrder().size() == numPlayers) {
                    this.statusGame.setStatus(STATUS.ACTION);
                    currPlayer = this.statusGame.getOrder().get(0);
                } else {
                    // This is the last method of the planning phase of one player
                    currPlayer = this.players.get((this.players.indexOf(currPlayer) + 1) % this.players.size());
                }

                return;
            }
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

        // Every time motherNature stops on an island, this method checks the influence
        computeInfluence();

        // After the last player of the round moves motherNature,
        //      the winner is checked if is last round,
        //      otherwise the clouds are filled for a new round
        if(statusGame.getOrder().get(statusGame.getOrder().size()-1)==currPlayer){
            if(lastRound) checkWinner();
        }

    }

    @Override//from the index of the cloud,returns the cloud chosen by the player
    public void chooseCloud(int cloud) {
        if(cloud < 0 || cloud >= clouds.size()) throw new InvalidCloudIndexException();

        moveStudentsFromCloud(this.clouds.get(cloud));

        // After the last player of the round moves motherNature,
        //      the clouds are filled for a new round
        if(statusGame.getOrder().get(statusGame.getOrder().size()-1)==currPlayer){
            fillClouds();
        }
        // If not last player, action phase continues in order
        else{
            currPlayer = this.statusGame.getOrder().get(this.statusGame.getOrder().indexOf(currPlayer) + 1);
        }
    }

    @Override//moves all the students from the chosen cloud to the entrance of the school board
    public void moveStudentsFromCloud(Cloud chosenCloud) {
        if(numPlayers==2){//3 students in chosenCloud
            for(int i=0;i<3;i++) {
                currPlayer.getMySchoolBoard().addStudentToEntrance(chosenCloud.getStudents().get(0));
                chosenCloud.removeStudent(0);
            }
        }
        else//4 students in chosenCloud
            for(int i=0;i<4;i++) {
                currPlayer.getMySchoolBoard().addStudentToEntrance(chosenCloud.getStudents().get(0));
                chosenCloud.removeStudent(0);
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

        if(this.getIslands().size()==3) checkWinner();

        if(currPlayer.getMySchoolBoard().getTowers().size()==0) new VictoryEvent(this, getCurrPlayer());
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
                currPlayer.getMySchoolBoard().addProfessor(professors.get(color.ordinal()).getOwner().getMySchoolBoard().removeProfessor( professors.get(color.ordinal())));   // assignProf
                professors.get(color.ordinal()).setOwner(currPlayer);       // assignProf
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

    public void checkWinner(){

        int minTowers=9; // in 3 player maxTower is 8, so 9 is a ceiling for the minSort
        Player winner = null;

        for(Player player : this.players){

            // Updates player with less towers in board, so more towers placed on islands
            if(minTowers > player.getMySchoolBoard().getTowers().size()){
                winner = player;
                minTowers = player.getMySchoolBoard().getTowers().size();
            }
            // if two players have the same number of towers placed, checks number of owned professors
            else if(minTowers == player.getMySchoolBoard().getTowers().size()){
                assert winner != null;
                // if player has more professors, wins.
                if(winner.getMySchoolBoard().getProfessors().size()<player.getMySchoolBoard().getProfessors().size())
                    winner = player;
                //TODO manca il pareggio (quando hanno sia torri che prof uguali)
            }

        }

        new VictoryEvent(this, winner);
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

    @Override
    public StatusGame getStatusGame() {
        return statusGame;
    }
}
