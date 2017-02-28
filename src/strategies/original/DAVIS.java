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

        String[][] scores = h.getMatchScore();
        String[] temp = new String[h.getNumberOfRounds()];
        int i = 0;
        for (String[] array : scores) {
            temp[i++] = array[1];
        }
        Set<String> VALUES = new HashSet<>(Arrays.asList(temp));
        if (VALUES.contains(Variables.DEFECT)) {
            return Variables.DEFECT;
        }else{
            return Variables.COOPERATE;
        }
    }
}
