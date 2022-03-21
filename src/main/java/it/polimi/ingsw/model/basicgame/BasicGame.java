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
    public void fillCloud(Player player) {
        for(Cloud c:player.getMyCloud()){
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
    public AssistantCard chooseCard(int index,Player player) {
        for(AssistantCard ac:player.getMyDeck().getCards())
            if(ac.getValue() == index)
                player.getMyDeck().draw(ac);
        return null;
    }

    @Override
    public void movestudentsfromEntrance() {
//dobbiamo decidere come fare arrivare in input la destinazione e se far muovere tutti gli studenti con una sola chiamata a metodo o ogni studente con la propria
    }

    @Override
    public void moveMother(int steps) {
        motherNature = (motherNature+steps) % this.islands.size();
    }

    @Override//from the index of the cloud,returns the cloud chosen by the player
    public Cloud chooseCloud(Player player,int cloud) {
        return player.getMyCloud().get(cloud);
    }

    @Override//moves all the students from the chosen cloud to the entrance of the school board
    public void moveStudentsFromCloud(Player player,Cloud chosenCloud) {
        if(numPlayers==2){//3 students in chosenCloud
            for(int i=0;i<3;i++) {
                player.getMySchoolBoard().addStudentToEntrance(chosenCloud.getStudents().get(i));
                chosenCloud.removeStudent(i);
            }
        }
        else//4 students in chosenCloud
            for(int i=0;i<4;i++) {
                player.getMySchoolBoard().addStudentToEntrance(chosenCloud.getStudents().get(i));
                chosenCloud.removeStudent(i);
            }
    }

    @Override
    public void computeInfluence() {
        int p0=0,p1=0,p2=0;//if the players are 2,p3 remains 0
        int max=0;
        for(Island island:this.islands.get(motherNature)){
            for(Student student:island.getStudents()){//counts every student from one island
                for(Professor professor:professors){
                    if(student.getColor()==professor.getColor() && professor.getOwner()!=null){
                        if(professor.getOwner().getUsername().equals(this.players.get(0).getUsername()))
                            p0++;
                        else if(professor.getOwner().getUsername().equals(this.players.get(1).getUsername()))
                            p1++;
                        else if(numPlayers==3)
                            p2++;
                    }
                    break;
                }
            }
            if(island.getTower()!=null){//counts tower from one island
                if(island.getTower().getColor()==players.get(0).getTeam())
                    p0++;
                else if(island.getTower().getColor()==players.get(1).getTeam())
                    p1++;
                else if(numPlayers==3)
                    p2++;
            }
        }//calculate int influence
     /*   if(this.islands.get(motherNature).get(0).getTower()!=null){
            for(int i=0;i<2;i++){
                if(this.players.get(i).getTeam()==this.islands.get(motherNature).get(0).getTower().getColor()){
                    if(i==0)
                        max=p0;
                    else if(i==1)
                        max=p1;
                    break;
                }
            }
        }*///manca algoritmo per verifica influenza,ho gia calcolato p0,p1,p2
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getNumPlayers() {
        return numPlayers;
    }
}
