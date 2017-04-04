package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dbrisingr on 06/02/2017.
 */
public class History implements Serializable {

    private static final long serialVersionUID = 42L;

    private HashMap<String, int[][]> selfMatchResults;
    private HashMap<String, int[][]> opponentMatchResults;

    private int currentRound;
    private int[][] integerMatchScore;

    private String selfName;
    private String currentOpponent;

    private int numberOfRounds;

    public History(int numberOfRounds, String selfName) {
        this.numberOfRounds = numberOfRounds;
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

    protected void setOpponentMatchResults(HashMap<String, int[][]> opponentMatchResults) {
        this.opponentMatchResults = opponentMatchResults;
    }

    protected void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public String[] getPreviousRoundScore() {
        return Variables.calculateRoundScoreString(integerMatchScore[currentRound - 1]);
    }

    public String[][] getMatchScore() {
        return Variables.calculateMatchScoreString(integerMatchScore);
    }

    protected void setIntegerMatchScore(int[][] matchScore) {
        this.integerMatchScore = matchScore;
        selfMatchResults.put(currentOpponent, matchScore);
    }

    public HashMap<String, int[][]> getSelfMatchResults() {
        return selfMatchResults;
    }

    public HashMap<String, int[][]> getOpponentMatchResults() {
        return opponentMatchResults;
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public String calculateAverage(HashMap<String, int[][]> hashMap, boolean self) {
        String toReturn = "";

        int defect = 0, cooperate = 0, index;
        if (self) {
            index = 0;
        } else {
            index = 1;
        }
        for (Map.Entry<String, int[][]> entry : hashMap.entrySet()) {
            int[][] value = entry.getValue();
            int c = 0, d = 0;
            for (int i = 0; i < value.length; i++) {
                if (Variables.calculateRoundScoreString(value[i])[index].equals(Variables.COOPERATE)) {
                    c++;
                } else {
                    d++;
                }
            }
            if (c >= d) {
                cooperate++;
            } else {
                defect++;
            }
        }
        if (cooperate >= defect) {
            toReturn = Variables.COOPERATE;
        } else {
            toReturn = Variables.DEFECT;
        }

        return toReturn;
    }

    public int calculateMatchScores(boolean tournamentSum, boolean maxMatchSum, boolean minMatchSum) {

        int sum = 0;
        int temp = 0;
        for (Map.Entry<String, int[][]> entry : selfMatchResults.entrySet()) {
            if (maxMatchSum && temp > sum) {
                sum = temp;
            } else if (minMatchSum && temp < sum) {
                sum = temp;
            }
            temp = 0;

            String opponentName = entry.getKey();
            int[][] actualRoundScore = entry.getValue();

            for (int i = 0; i < actualRoundScore.length; i++) {
                if (tournamentSum) {
                    sum += selfMatchResults.get(opponentName)[i][0];
                } else if (maxMatchSum) {
                    temp += selfMatchResults.get(opponentName)[i][0];
                }
            }
        }
        return sum;
    }
}
