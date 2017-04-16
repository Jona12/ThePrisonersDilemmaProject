package strategies.custom;

import main.History;
import main.Variables;

/**
 * Created by dbrisingr on 17/02/2017.
 */
public class GOOD_SAMARITAN {
    public String calculate(History h) {
        int currentRound = h.getCurrentRound();
        boolean defectTwice, alternatePairs;
        if (h.getCurrentRound() > 1) {
            String currentOpponentOutput = h.getPreviousRoundScore()[1];
            String previousOpponentOutput = h.getMatchScore()[currentRound - 2][1];
            defectTwice = currentOpponentOutput.equals(Variables.DEFECT)
                    && previousOpponentOutput.equals(Variables.DEFECT);
            if (h.getCurrentRound() > 3) {
                String previousPreviousOpponentOutput = h.getMatchScore()[currentRound - 3][1];
                String previousPreviousPreviousOpponentOutput = h.getMatchScore()[currentRound - 4][1];
                alternatePairs = currentOpponentOutput.equals(previousPreviousOpponentOutput)
                        && previousOpponentOutput.equals(previousPreviousPreviousOpponentOutput);
                if (alternatePairs) return Variables.DEFECT;
            }
            if (defectTwice) return Variables.DEFECT;
        }
        return Variables.COOPERATE;
    }
}
