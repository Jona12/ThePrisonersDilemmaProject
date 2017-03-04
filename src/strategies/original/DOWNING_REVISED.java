package strategies.original;

import main.History;
import strategies.custom.DOWNING;

/**
 * Created by dbrisingr on 27/02/2017.
 */
public class DOWNING_REVISED {

    public String calculate(History h) {
        return new DOWNING().calculateDowning(h, true);
    }
}
