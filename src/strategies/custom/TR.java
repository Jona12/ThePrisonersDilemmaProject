package strategies.custom;

import main.History;
import main.Variables;

/**
 * Created by dbrisingr on 27/02/2017.
 */
public class TR {

    public String calculate(History h) {
        if (h.getCurrentRound() == 0) return Variables.COOPERATE;
        if (h.getCurrentRound() == 1 && h.getPreviousRoundScore()[1].equals(Variables.COOPERATE)) {
            return Variables.COOPERATE;
        } else if (h.getCurrentRound() == 1) {
            return Variables.DEFECT;
        } else {
            if (h.getPreviousRoundScore()[0].equals(Variables.COOPERATE)) {
                return Variables.DEFECT;
            } else {
                return Variables.COOPERATE;
            }
        }
    }
}
