package strategies.original;

import main.History;
import main.StrategyFunctions;

import java.util.Random;

/**
 * Created by dbrisingr on 28/02/2017.
 */
public class UNKNOWN {

    public String calculate(History h){
        int upperBound = 7;
        int lowerBound = 3;

        float r = new Random().nextInt(upperBound-lowerBound) + lowerBound;

        return StrategyFunctions.randomChoice(r);
    }
}
