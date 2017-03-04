package strategies.original;

import main.History;
import main.StrategyFunctions;
import main.Variables;

/**
 * Created by dbrisingr on 28/02/2017.
 */
public class JOSS {

    public String calculate(History h) {
        float probability = 0.9f;

        if (h.getCurrentRound() > 0) {
            if (h.getPreviousRoundScore()[1].equals(Variables.COOPERATE)) {
                return StrategyFunctions.randomChoice(probability);
            }
        }
        return new TIT_FOR_TAT().calculate(h);
    }
}
