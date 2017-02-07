package main;

import java.util.HashMap;

/**
 * Created by dbrisingr on 06/02/2017.
 */
public class Match {

    private int noOfRounds;
    private String strategyNameOne;
    private String strategyNameTwo;

    private int winScore;
    private int loseScore;
    private int draw_c_Score;
    private int draw_d_Score;

    private History historyStrategyOne;
    private History historyStrategyTwo;

    private int[] matchResult;

    private int[][] tempOne;
    private int[][] tempTwo;

    private int currentScoreOne;
    private int currentScoreTwo;

    public Match(String matchID, History historyStrategyOne, History historyStrategyTwo, int noOfRounds) {

        this.noOfRounds = noOfRounds;
        this.historyStrategyOne = historyStrategyOne;
        this.historyStrategyTwo = historyStrategyTwo;
        this.strategyNameOne = historyStrategyOne.getSelfName();
        this.strategyNameTwo = historyStrategyTwo.getSelfName();
//        this.matchName = matchID + ": " + strategyName + " vs. " + strategyNameTwo;
        this.winScore = Variables.WIN_SCORE;
        this.loseScore = Variables.LOSE_SCORE;
        this.draw_c_Score = Variables.DRAW_C_SCORE;
        this.draw_d_Score = Variables.DRAW_D_SCORE;

        currentScoreOne = 0;
        currentScoreTwo = 0;
        matchResult = new int[2];

        tempOne = new int[noOfRounds][2]; tempTwo = new int[noOfRounds][2];
    }

    public void runMatch() {
        StrategyManager strategyOne;
        StrategyManager strategyTwo;

        historyStrategyOne.setCurrentOpponent(strategyNameTwo);
        historyStrategyTwo.setCurrentOpponent(strategyNameOne);

        historyStrategyOne.setRoundScore(tempOne);
        historyStrategyTwo.setRoundScore(tempTwo);

        try {
            for (int i = 0; i < noOfRounds; i++) {
                strategyOne = new StrategyManager(historyStrategyOne);
                strategyTwo = new StrategyManager(historyStrategyTwo);
                String resultStrategyOne = strategyOne.readStrategyClass();
                String resultStrategyTwo = strategyTwo.readStrategyClass();

                historyStrategyOne.setCurrentRound(i);
                historyStrategyTwo.setCurrentRound(i);

                calculateRoundScore(resultStrategyOne, resultStrategyTwo);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        matchResult[0] = currentScoreOne;
        matchResult[1] = currentScoreTwo;

        historyStrategyOne.setCurrentOpponent(null);
        historyStrategyTwo.setCurrentOpponent(null);

    }

    public void calculateRoundScore(String resultStrategyOne, String resultStrategyTwo) {

        int currentRound = historyStrategyOne.getCurrentRound();

        if (resultStrategyOne.equals(Variables.COOPERATE) && resultStrategyTwo.equals(Variables.COOPERATE)) {

            tempOne[currentRound][0] = draw_c_Score;
            tempOne[currentRound][1] = draw_c_Score;

            tempTwo[currentRound][0] = draw_c_Score;
            tempTwo[currentRound][1] = draw_c_Score;

        } else if (resultStrategyOne.equals(Variables.COOPERATE) && resultStrategyTwo.equals(Variables.DEFECT)) {

            tempOne[currentRound][0] = loseScore;
            tempOne[currentRound][1] = winScore;

            tempTwo[currentRound][0] = winScore;
            tempTwo[currentRound][1] = loseScore;

        } else if (resultStrategyOne.equals(Variables.DEFECT) && resultStrategyTwo.equals(Variables.COOPERATE)) {

            tempOne[currentRound][0] = winScore;
            tempOne[currentRound][1] = loseScore;

            tempTwo[currentRound][0] = loseScore;
            tempTwo[currentRound][1] = winScore;
        } else {
            tempOne[currentRound][0] = draw_d_Score;
            tempOne[currentRound][1] = draw_d_Score;

            tempTwo[currentRound][0] = draw_d_Score;
            tempTwo[currentRound][1] = draw_d_Score;
        }

//        System.out.println(strategyNameOne);
//        System.out.println(tempOne[currentRound][0]);
//        System.out.println(tempOne[currentRound][1]);
//        System.out.println();
//        System.out.println(strategyNameTwo);
//        System.out.println(tempTwo[currentRound][0]);
//        System.out.println(tempTwo[currentRound][1]);
//        System.out.println();

        historyStrategyOne.setRoundScore(tempOne);
        currentScoreOne += tempOne[currentRound][0];
        historyStrategyTwo.setRoundScore(tempTwo);
        currentScoreTwo += tempOne[currentRound][1];

    }

    public int[] getMatchResult() {
        return matchResult;
    }
}
