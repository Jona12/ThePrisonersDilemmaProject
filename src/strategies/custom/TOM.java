package strategies.custom;

import main.History;
import main.Variables;

/**
 * Created by dbrisingr on 12/04/2017.
 */
public class TOM {

    public String calculate(History h){
        if(h.getCurrentRound() == 0) return Variables.DEFECT;
        int defects = 0;
        for(int i = 0; i < h.getMatchScore().length; i++){
            if(h.getMatchScore()[i][1].equals(Variables.DEFECT)) defects++;
        }
        if (defects > h.getMatchScore().length * 0.1) return Variables.DEFECT;
        return Variables.COOPERATE;
    }
}
