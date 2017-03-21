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

    private History opponentHistory;
    private HashMap<String, int[][]> selfMatchResults;
    private HashMap<String, int[][]> opponentMatchResults;

    private int currentRound;
    private int[][] integerMatchScore;

    private String selfName;
    private String currentOpponent;

    private int numberOfRounds;

    private HashMap<String, Object> strategyVariables;

    public History(int numberOfRounds, String selfName) {
        this.numberOfRounds = numberOfRounds;
        this.selfName = selfName;
        selfMatchResults = new HashMap<>();
        strategyVariables = new HashMap<>();
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

    public HashMap<String, Object> getStrategyVariables() {
        return strategyVariables;
    }

    public void setStrategyVariables(HashMap<String, Object> strategyVariables) {
        this.strategyVariables = strategyVariables;
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
