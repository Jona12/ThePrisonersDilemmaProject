package strategies.original;

import main.History;
import main.StrategyFunctions;
import main.Variables;

/**
 * Created by dbrisingr on 28/02/2017.
 */
public class GROFMAN {

    public String calculate(History h) {
        int round = h.getCurrentRound();
        if (round < 2) {
            return Variables.COOPERATE;
        }
        if (round < 7) {
            return h.getPreviousRoundScore()[1];
        }
        if (h.getPreviousRoundScore()[0].equals(h.getPreviousRoundScore()[1])) {
            return Variables.COOPERATE;
        }
        return StrategyFunctions.randomChoice(2 / 7);
    }
}
