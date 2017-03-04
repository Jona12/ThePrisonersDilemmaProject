package main;

import java.util.ArrayList;
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

    public int calculateMatchScores(boolean tournamentSum, boolean matchSum, boolean roundSum) {

        ArrayList<String> toReturn = new ArrayList<>();
        int sum = 0;

        for (Map.Entry<String, int[][]> entry : selfMatchResults.entrySet()) {
            if (matchSum || roundSum) {
                sum = 0;
            }
            String opponentName = entry.getKey();
            if (matchSum || roundSum) {
                toReturn.add(selfName + " vs. " + opponentName);
            }
            int[][] actualRoundScore = entry.getValue();
            for (int i = 0; i < actualRoundScore.length; i++) {
                if (tournamentSum || matchSum) {
                    sum += selfMatchResults.get(opponentName)[i][0];
                } else if (roundSum) {
                    toReturn.add("round: " + i);
                    toReturn.add("" + selfMatchResults.get(opponentName)[i][0]);
                    toReturn.add("" + selfMatchResults.get(opponentName)[i][1]);
                }
            }
            if (matchSum) {
                toReturn.add(selfName + " match score: " + sum);
                toReturn.add("");
            }
        }

        if (tournamentSum) {
            toReturn.add(selfName + " tournament score: " + sum);
        }
        return sum;
    }

    public int[] calculateAverageRoundScores() {
        int[] averageScores = new int[numberOfRounds];
        int count = 0;
        for (Map.Entry<String, int[][]> entry : selfMatchResults.entrySet()) {
            for (int i = 0; i < numberOfRounds; i++) {
                averageScores[i] += entry.getValue()[i][0];
            }
        }
        for (int i = 0; i < numberOfRounds; i++) {
            averageScores[i] = averageScores[i] / selfMatchResults.size();
        }
        return averageScores;
    }
}
