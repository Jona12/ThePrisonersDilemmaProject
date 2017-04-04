package strategies.original;

import main.History;
import main.Variables;

/**
 * Created by dbrisingr on 04/04/2017.
 */
public class GRUDGER {

    public String calculate(History h){

        if(h.getCurrentRound() == 0) return Variables.COOPERATE;
        loop:
        for(int i = 0; i < h.getMatchScore().length; i++){
            if(h.getMatchScore()[i][1].equals(Variables.DEFECT)){
                return Variables.DEFECT;
            }
        }
        return Variables.COOPERATE;
    }
}
