package strategies.custom;

import main.History;

/**
 * Created by dbrisingr on 22/02/2017.
 */
public class TLK {

    public String calculate(History h){
        if (h.getCurrentRound()== 0) { return "COOPERATE"; }
        else { return "DEFECT"; }
    }
}
