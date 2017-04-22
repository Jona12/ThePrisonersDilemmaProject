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

    private HashMap<String, int[][]> selfMatchScores;
    private HashMap<String, int[][]> opponentMatchScores;

    private int currentRound;
    private int[][] currentMatchScore;

    private String selfName;
    private String opponentName;

    private int numberOfRounds;
    private int repeat;

    public History(int numberOfRounds, String selfName, int repeat) {
        this.numberOfRounds = numberOfRounds;
        this.selfName = selfName;
        selfMatchScores = new HashMap<>();
        this.repeat = repeat;
    }

    public String getSelfName() {
        return selfName;
    }

    public String getopponentName() {
        return opponentName;
    }

    protected void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    protected void setOpponentMatchScores(HashMap<String, int[][]> opponentMatchScores) {
        this.opponentMatchScores = opponentMatchScores;
    }

    protected void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public String[] getPreviousRoundScore() {
        return Variables.calculateRoundScoreString(currentMatchScore[currentRound - 1]);
    }

    public String[][] getMatchScore() {
        return Variables.calculateMatchScoreString(currentMatchScore);
    }

    protected void setCurrentMatchScore(int[][] matchScore) {
        this.currentMatchScore = matchScore;
        selfMatchScores.put(opponentName, matchScore);
    }

    public HashMap<String, int[][]> getSelfMatchScores() {
        return selfMatchScores;
    }

    public HashMap<String, int[][]> getOpponentMatchScores() {
        return opponentMatchScores;
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public String calculateAverage(HashMap<String, int[][]> hashMap, boolean self) {
        String toReturn;

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
        for (Map.Entry<String, int[][]> entry : selfMatchScores.entrySet()) {
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
                    sum += selfMatchScores.get(opponentName)[i][0];
                } else if (maxMatchSum) {
                    temp += selfMatchScores.get(opponentName)[i][0];
                }
            }
        }
        return sum;
    }
}
