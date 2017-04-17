package strategies.original;

import main.History;
import main.Variables;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by dbrisingr on 04/04/2017.
 */
public class NYDEGGER {

    public String calculate(History h) {
        int currentRound = h.getCurrentRound();
        if (currentRound < 2) {
            return new TIT_FOR_TAT().calculate(h);
        }
        if (currentRound == 2) {
            String[] firstRound = h.getMatchScore()[0];
            String[] secondRound = h.getMatchScore()[1];
            if (firstRound[0].equals(Variables.COOPERATE) && firstRound[1].equals(Variables.DEFECT)
                    && secondRound[0].equals(Variables.DEFECT) && secondRound[1].equals(Variables.COOPERATE)) {
                return Variables.DEFECT;
            } else {
                return Variables.COOPERATE;
            }
        }

        int x, y, z;
        x = calculateInteger(h, 1, 16);
        y = calculateInteger(h, 2, 4);
        z = calculateInteger(h, 3, 1);

        int a = x + y + z;
        int[] defect = {
                1, 6, 7, 17, 22, 23, 26, 29, 30, 31, 33, 38, 39, 45, 49, 54, 55, 58, 61
        };
        if (IntStream.of(defect).anyMatch(t -> t == a)) {
            return Variables.DEFECT;
        }
        return Variables.COOPERATE;
    }

    private int calculateInteger(History h, int previous, int weight) {
        int toReturn;

        String[][] check = new String[4][2];
        check[0][0] = Variables.COOPERATE;
        check[0][1] = Variables.COOPERATE;
        check[1][0] = Variables.COOPERATE;
        check[1][1] = Variables.DEFECT;
        check[2][0] = Variables.DEFECT;
        check[2][1] = Variables.COOPERATE;
        check[3][0] = Variables.DEFECT;
        check[3][1] = Variables.DEFECT;

        String[] compare;
        if (previous == 1) {
            compare = h.getPreviousRoundScore();
        } else if (previous == 2) {
            compare = h.getMatchScore()[h.getCurrentRound() - 2];
        } else {
            compare = h.getMatchScore()[h.getCurrentRound() - 3];
        }
        if (compare[0].equals(check[0][0]) && compare[1].equals(check[0][1])) {
            toReturn = 0;
        } else if (compare[0].equals(check[1][0]) && compare[1].equals(check[1][1])) {
            toReturn = 2 * weight;
        } else if (compare[0].equals(check[2][0]) && compare[1].equals(check[2][1])) {
            toReturn = weight;
        } else {
            toReturn = 3 * weight;
        }
        return toReturn;
    }

}
