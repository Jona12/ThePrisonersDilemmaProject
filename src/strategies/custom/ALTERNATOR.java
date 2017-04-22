package strategies.custom;

import main.History;
import main.Variables;

/**
 * Created by dbrisingr on 14/04/2017.
 */
public class ALTERNATOR {

    public String calculate(History h){
        if(h.getCurrentRound() == 0) return Variables.COOPERATE;
        if(h.getPreviousRoundScore()[0].equals(Variables.COOPERATE)) return Variables.DEFECT;
        return Variables.COOPERATE;
    }
}
