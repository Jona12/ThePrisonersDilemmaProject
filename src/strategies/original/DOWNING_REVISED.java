package strategies.original;

import main.History;
import main.Variables;

/**
 * Created by dbrisingr on 27/02/2017.
 */
public class DOWNING_REVISED {

    public String calculate(History h) {
        return calculateDowning(h, true);
    }

    public static String calculateDowning(History h, boolean isRevised) {
        int good, bad, nice1, nice2, total_C, total_D;
        good = 1;
        bad = nice1 = nice2 = total_C = total_D = 0;

        if (isRevised && h.getCurrentRound() == 0) {
            return Variables.COOPERATE;
        }
        if (!isRevised && h.getCurrentRound() < 2) {
            return Variables.DEFECT;
        }

        String[] previousScore = h.getPreviousRoundScore();
        if (previousScore[0].equals(Variables.DEFECT)) {
            if (previousScore[1].equals(Variables.COOPERATE)) {
                nice2 += 1;
            }
            total_D += 1;
            bad = nice2 / total_D;
        } else {
            if (previousScore[1].equals(Variables.COOPERATE)) {
                nice1 += 1;
            }
            total_C += 1;
            good = nice1 / total_C;
        }


        double c = 6.0 * good - 8.0 * bad - 2;
        double alt = 4.0 * good - 5.0 * bad - 1;
        String toReturn;

        if (c >= 0 && c >= alt) {
            toReturn = Variables.COOPERATE;
        } else if ((c >= 0 && c < alt) || (alt >= 0)) {
            toReturn = Variables.DEFECT;
        } else {
            toReturn = Variables.DEFECT;
        }
        return toReturn;
    }
}
