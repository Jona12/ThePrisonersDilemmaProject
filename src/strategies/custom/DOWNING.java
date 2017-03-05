package strategies.custom;

import main.History;
import strategies.original.DOWNING_REVISED;

/**
 * Created by dbrisingr on 27/02/2017.
 */
public class DOWNING {

    public String calculate(History h) {
        return DOWNING_REVISED.calculateDowning(h, false);
    }
}
