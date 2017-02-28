package strategies.built_in;

import main.History;

/**
 * Created by dbrisingr on 27/02/2017.
 */
public class DOWNING_REVISED {

    public String calculate(History h) {
        return new DOWNING().calculateDowning(h, true);
    }
}
