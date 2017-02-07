package main;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dbrisingr on 06/02/2017.
 */
public class History {

    private History opponentHistory;
    private HashMap<String, int[][]> selfMatchResults;
    private HashMap<String, int[][]> opponentMatchResults;

    private int currentRound;
    private int[][] roundScore;

    private String selfName;
    private String currentOpponent;

    public History(int numberOfRounds, String selfName) {
        this.selfName = selfName;
        selfMatchResults = new HashMap<>();
    }

    public String getSelfName() {
        return selfName;
    }

    public String getCurrentOpponent(){
        return currentOpponent;
    }

    protected void setCurrentOpponent(String currentOpponent){
        this.currentOpponent = currentOpponent;
    }

    protected void setCurrentRound(int currentRound){
        this.currentRound = currentRound;
    }

    public int getCurrentRound(){
        return currentRound;
    }

    public int[][] getRoundScore() {
        return roundScore;
    }

    protected void setRoundScore(int[][] roundScore){
        selfMatchResults.put(currentOpponent, roundScore);
        if(selfName.equals("COOPERATE")){
//            System.out.println(currentOpponent);
//            System.out.println(selfMatchResults.get(currentOpponent)[currentRound][0]);
//        System.out.println(selfMatchResults.get(currentOpponent)[currentRound][1]);
        }

    }

    public HashMap<String, int[][]> getSelfMatchResults(){
        return selfMatchResults;
    }

    public HashMap<String, int[][]> getOpponentMatchResults(){
        return opponentMatchResults;
    }

    public void calculateMatchScores(){
        String toReturn = selfName;

        System.out.println(selfName+" vs.: ");
        for(Map.Entry entry : selfMatchResults.entrySet()){
            System.out.println(entry.getKey());
            int[][] value = (int[][]) entry.getValue();
            for(int i=0; i < value.length; i++){
                System.out.println(value[i][0]);
//                System.out.println(value[i][1]);
            }
        }
    }

}
