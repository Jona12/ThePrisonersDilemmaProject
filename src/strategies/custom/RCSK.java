package strategies.custom;

import main.History;
import main.Variables;

/**
 * Created by dbrisingr on 14/04/2017.
 */
public class RCSK {

    public String calculate(History h){
        if(h.getCurrentRound() < 2) return Variables.COOPERATE;
        int count = 0;
        String[] p1 = h.getPreviousRoundScore();
        String[] p2 = h.getMatchScore()[h.getCurrentRound()-2];
        if(p1[1].equals(Variables.DEFECT) && p2[1].equals(Variables.DEFECT)) return Variables.DEFECT;
        return Variables.COOPERATE;
    }
}
