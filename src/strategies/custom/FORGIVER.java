package strategies.custom;

import main.History;
import main.Variables;

/**
 * Created by dbrisingr on 14/04/2017.
 */
public class FORGIVER {

    public String calculate(History h){

        if(h.getCurrentRound() < 3) return Variables.COOPERATE;

        String[] p1 = h.getPreviousRoundScore();
        String[] p2 = h.getMatchScore()[h.getCurrentRound()-2];
        String[] p3 = h.getMatchScore()[h.getCurrentRound()-3];

        String s1 = p1[1];
        String s2 = p2[1];
        String s3 = p3[1];

        int defects = 0;
        if(s1.equals(Variables.DEFECT)) defects++;
        if(s2.equals(Variables.DEFECT)) defects++;
        if(s3.equals(Variables.DEFECT)) defects++;

        if(defects > 2) return Variables.DEFECT;
        return Variables.COOPERATE;
    }
}
