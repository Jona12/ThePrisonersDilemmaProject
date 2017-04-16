package main;

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

    private int[] matchScore;

    private int[][] currentScoreStrategyOne;
    private int[][] currentScoreStrategyTwo;

    private int currentScoreOne;
    private int currentScoreTwo;

    public Match(History historyStrategyOne, History historyStrategyTwo, int noOfRounds) {

        this.noOfRounds = noOfRounds;
        this.historyStrategyOne = historyStrategyOne;
        this.historyStrategyTwo = historyStrategyTwo;
        this.strategyNameOne = historyStrategyOne.getSelfName();
        this.strategyNameTwo = historyStrategyTwo.getSelfName();
        this.winScore = Variables.WIN_SCORE;
        this.loseScore = Variables.LOSE_SCORE;
        this.draw_c_Score = Variables.DRAW_C_SCORE;
        this.draw_d_Score = Variables.DRAW_D_SCORE;

        currentScoreOne = 0;
        currentScoreTwo = 0;
        matchScore = new int[2];

        currentScoreStrategyOne = new int[noOfRounds][2];
        currentScoreStrategyTwo = new int[noOfRounds][2];
    }

    public void runMatch() {
        StrategyManager strategyOne;
        StrategyManager strategyTwo;

        historyStrategyOne.setOpponentMatchScores(historyStrategyTwo.getSelfMatchScores());
        historyStrategyTwo.setOpponentMatchScores(historyStrategyOne.getSelfMatchScores());

        historyStrategyOne.setOpponentName(strategyNameTwo);
        historyStrategyTwo.setOpponentName(strategyNameOne);

        try {
            for (int i = 0; i < noOfRounds; i++) {

                strategyOne = new StrategyManager(historyStrategyOne);
                strategyTwo = new StrategyManager(historyStrategyTwo);

                historyStrategyOne.setCurrentRound(i);
                historyStrategyTwo.setCurrentRound(i);

                String resultStrategyOne = strategyOne.readStrategyClass();
                String resultStrategyTwo = strategyTwo.readStrategyClass();

                calculateRoundScore(resultStrategyOne, resultStrategyTwo);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        matchScore[0] = currentScoreOne;
        matchScore[1] = currentScoreTwo;

        historyStrategyOne.setOpponentName(null);
        historyStrategyTwo.setOpponentName(null);

    }

    public void calculateRoundScore(String resultStrategyOne, String resultStrategyTwo) {

        int currentRound = historyStrategyOne.getCurrentRound();

        if (resultStrategyOne.equals(Variables.COOPERATE) && resultStrategyTwo.equals(Variables.COOPERATE)) {

            currentScoreStrategyOne[currentRound][0] = draw_c_Score;
            currentScoreStrategyOne[currentRound][1] = draw_c_Score;

            currentScoreStrategyTwo[currentRound][0] = draw_c_Score;
            currentScoreStrategyTwo[currentRound][1] = draw_c_Score;

        } else if (resultStrategyOne.equals(Variables.COOPERATE) && resultStrategyTwo.equals(Variables.DEFECT)) {

            currentScoreStrategyOne[currentRound][0] = loseScore;
            currentScoreStrategyOne[currentRound][1] = winScore;

            currentScoreStrategyTwo[currentRound][0] = winScore;
            currentScoreStrategyTwo[currentRound][1] = loseScore;

        } else if (resultStrategyOne.equals(Variables.DEFECT) && resultStrategyTwo.equals(Variables.COOPERATE)) {

            currentScoreStrategyOne[currentRound][0] = winScore;
            currentScoreStrategyOne[currentRound][1] = loseScore;

            currentScoreStrategyTwo[currentRound][0] = loseScore;
            currentScoreStrategyTwo[currentRound][1] = winScore;
        } else {
            currentScoreStrategyOne[currentRound][0] = draw_d_Score;
            currentScoreStrategyOne[currentRound][1] = draw_d_Score;

            currentScoreStrategyTwo[currentRound][0] = draw_d_Score;
            currentScoreStrategyTwo[currentRound][1] = draw_d_Score;
        }

        historyStrategyOne.setCurrentMatchScore(currentScoreStrategyOne);
        currentScoreOne += currentScoreStrategyOne[currentRound][0];
        historyStrategyTwo.setCurrentMatchScore(currentScoreStrategyTwo);
        currentScoreTwo += currentScoreStrategyOne[currentRound][1];

    }

    public int[] getMatchScore() {
        return matchScore;
    }
}
