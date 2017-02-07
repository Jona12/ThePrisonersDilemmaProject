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

    public String getCurrentOpponent() {
        return currentOpponent;
    }

    protected void setCurrentOpponent(String currentOpponent) {
        this.currentOpponent = currentOpponent;
    }

    protected void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int[][] getRoundScore() {
        return roundScore;
    }

    protected void setRoundScore(int[][] roundScore) {
        this.roundScore = roundScore;
        selfMatchResults.put(currentOpponent, roundScore);

//        ROUND SCORE WORKS
//        System.out.println(selfName+" vs. "+currentOpponent);
//        System.out.println("round: "+currentRound);
//        System.out.println(roundScore[currentRound][0]);
//        System.out.println(roundScore[currentRound][1]);

//        ROUND SCORE WORKS TOO
//        System.out.println(selfName+" vs. "+currentOpponent);
//        System.out.println("round: "+currentRound);
//        System.out.println(selfMatchResults.get(currentOpponent)[currentRound][0]);
//        System.out.println(selfMatchResults.get(currentOpponent)[currentRound][1]);
    }

    public HashMap<String, int[][]> getSelfMatchResults() {
        return selfMatchResults;
    }

    public HashMap<String, int[][]> getOpponentMatchResults() {
        return opponentMatchResults;
    }

    public void calculateMatchScores(boolean tournamentSum, boolean matchSum, boolean roundSum) {

        int sum = 0;

        for (Map.Entry<String, int[][]> entry : selfMatchResults.entrySet()) {
            if (matchSum || roundSum) {
                sum = 0;
            }
            String opponentName = entry.getKey();
            if (matchSum || roundSum) {
                System.out.println(selfName + " vs. " + opponentName);
            }
            int[][] actualRoundScore = entry.getValue();
            for (int i = 0; i < actualRoundScore.length; i++) {
                if (tournamentSum || matchSum) {
                    sum += selfMatchResults.get(opponentName)[i][0];
                } else if (roundSum) {
                    System.out.println("round: " + i);
                    System.out.println(selfMatchResults.get(opponentName)[i][0]);
                    System.out.println(selfMatchResults.get(opponentName)[i][1]);
                }
            }
            if (matchSum) {
                System.out.println(selfName + " match score: " + sum);
                System.out.println();
            }
        }

        if (tournamentSum) {
            System.out.println(selfName + " tournament score: " + sum);
        }
    }
}
