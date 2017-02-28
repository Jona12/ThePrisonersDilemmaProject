package strategies.original;

import main.History;
import main.StrategyFunctions;

/**
 * Created by dbrisingr on 28/02/2017.
 */
public class FELD {

    public String calculate(History h){
        float start_coop_prob = 1.0f;
        float end_coop_prob = 0.5f;
        int rounds_of_decay = 200;

        float diff = (end_coop_prob - start_coop_prob);
        float slope = diff / rounds_of_decay;
        int rounds = h.getNumberOfRounds();

        float cooperation_prob = Math.max(start_coop_prob + slope * rounds, end_coop_prob);

        return StrategyFunctions.randomChoice(cooperation_prob);
    }
}
