package strategies.custom;

import main.History;
import main.Variables;


/**
 * Created by dbrisingr on 14/04/2017.
 */
public class REV_TFT {

    public String calculate(History h){
        if(h.getCurrentRound() == 0) return Variables.DEFECT;
        return h.getPreviousRoundScore()[1];
    }
}
