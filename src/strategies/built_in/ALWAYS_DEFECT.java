package strategies.built_in;

import main.History;
import main.Variables;

/**
 * Created by dbrisingr on 06/02/2017.
 */
public class ALWAYS_DEFECT {

    public String calculate(History h){
        return Variables.DEFECT;
    }
}
