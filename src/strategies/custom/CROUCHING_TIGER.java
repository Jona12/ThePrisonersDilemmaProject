package strategies.custom;

import main.History;
import main.Variables;

/**
 * Created by dbrisingr on 22/02/2017.
 */
public class CROUCHING_TIGER {

    public String calculate(History h) {
        if(h.getCurrentRound() == 0) return Variables.COOPERATE;
        double toCompare = (double) h.getCurrentRound() / h.getNumberOfRounds();
        if (toCompare <= 0.5) {
            return Variables.COOPERATE;
        } else {
            int defects = 0;
            for (int i = 0; i < h.getCurrentRound()-1; i++) {
                if (h.getMatchScore()[i][1].equals(Variables.DEFECT)) {
                    defects++;
                }
            }
            if (defects > (h.getNumberOfRounds() / 2) * 0.75) return Variables.DEFECT;
            return Variables.COOPERATE;
        }
    }
}
