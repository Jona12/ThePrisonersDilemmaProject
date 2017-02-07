package strategies;

import main.History;
import main.Variables;

/**
 * Created by dbrisingr on 06/02/2017.
 */
public class ALWAYS_COOPERATE {

    public String calculate(History h){
        return Variables.COOPERATE;
    }
}
