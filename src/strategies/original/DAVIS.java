package strategies.original;

import main.History;
import main.Variables;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dbrisingr on 28/02/2017.
 */
public class DAVIS {

    public String calculate(History h) {

        int numberOfCooperates = 10;

        if (h.getCurrentRound() < numberOfCooperates) {
            return Variables.COOPERATE;
        }

        return new GRUDGER().calculate(h);
    }
}
