package strategies.custom;

import main.History;
import main.Variables;

/**
 * Created by dbrisingr on 14/04/2017.
 */
public class FIVE {

    public String calculate(History h){
        if(h.getCurrentRound() % 5 == 0){
            return Variables.COOPERATE;
        }
        return Variables.DEFECT;
    }
}
