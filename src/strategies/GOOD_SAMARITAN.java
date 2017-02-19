package strategies;

import main.History;
import main.Variables;

/**
 * Created by dbrisingr on 17/02/2017.
 */
public class GOOD_SAMARITAN {

    public String calculate(History h) {
        int currentRound = h.getCurrentRound();
        int[][] roundScore = h.getRoundScore();

        boolean defectTwice;
        boolean alternatePairs;

        if (h.getCurrentRound() > 0) {
            String currentOpponentOutput = Variables.getOpponentOutput(roundScore, currentRound);
            String previousOpponentOutput = Variables.getOpponentOutput(roundScore, currentRound - 1);

            defectTwice = currentOpponentOutput.equals(Variables.DEFECT)
                    && previousOpponentOutput.equals(Variables.DEFECT);

            if (h.getCurrentRound() > 2) {

                String previousPreviousOpponentOutput = Variables.getOpponentOutput(roundScore, currentRound - 2);
                String previousPreviousPreviousOpponentOutput = Variables.getOpponentOutput(roundScore, currentRound - 3);

                alternatePairs = !currentOpponentOutput.equals(previousOpponentOutput)
                        && !previousOpponentOutput.equals(previousPreviousOpponentOutput)
                        && !previousPreviousOpponentOutput.equals(previousPreviousPreviousOpponentOutput);

                if (alternatePairs) return Variables.DEFECT;
            }

            if (defectTwice) return Variables.DEFECT;
        }

        return Variables.COOPERATE;
    }
}
