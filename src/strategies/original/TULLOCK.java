package strategies.original;

import main.CommonFunctions;
import main.History;
import main.Variables;

/**
 * Created by dbrisingr on 04/04/2017.
 */
public class TULLOCK {

    public String calculate(History h) {
        if (h.getCurrentRound() < 11) {
            return Variables.COOPERATE;
        }

        int cooperate_count = 0;
        for (int i = h.getCurrentRound() - 1; i > h.getCurrentRound() - 11; i--) {
            if (h.getMatchScore()[i][1].equals(Variables.COOPERATE)) {
                cooperate_count++;
            }
        }
        int prop_cooperate = cooperate_count / 11;
        double prob_cooperate = Math.max(0, prop_cooperate - 0.1);
        return CommonFunctions.randomChoice(Double.doubleToLongBits(prob_cooperate));
    }
}
