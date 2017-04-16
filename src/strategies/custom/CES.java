package strategies.custom;

import main.History;
import main.Variables;

/**
 * Created by dbrisingr on 22/02/2017.
 */
public class CES {

    public String calculate(History h) {
        if (h.getCurrentRound() < 2) {
            return Variables.DEFECT;
        } else if (h.getPreviousRoundScore()[1].equals(Variables.COOPERATE)) {
            return Variables.COOPERATE;
        } else if (h.getCurrentRound() > h.getNumberOfRounds() * 0.8) {
            return Variables.DEFECT;
        } else {
            String average = h.calculateAverage(h.getOpponentMatchScores(), true);
            if (average.equals(Variables.COOPERATE)) return Variables.COOPERATE;
            return Variables.DEFECT;
        }
    }
}
