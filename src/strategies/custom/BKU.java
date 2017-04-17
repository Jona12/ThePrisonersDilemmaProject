package strategies.custom;

import main.History;
import main.Variables;

/**
 * Created by dbrisingr on 11/04/2017.
 */
public class BKU {

    public String calculate(History h){
        if(h.getCurrentRound() == 0) return Variables.COOPERATE;
        else if(h.getCurrentRound() == 1) return Variables.DEFECT;
        else{
            if(h.getPreviousRoundScore()[0].equals(Variables.COOPERATE)){
                if(h.getMatchScore()[h.getCurrentRound()-2][0].equals(Variables.COOPERATE)){
                    return Variables.DEFECT;
                }
            }
                return Variables.COOPERATE;
        }
    }
}
