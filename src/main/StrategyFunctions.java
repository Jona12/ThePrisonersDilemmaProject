package main;

import main.Variables;

import java.util.Random;

/**
 * Created by dbrisingr on 28/02/2017.
 */
public class StrategyFunctions {

    public static String randomChoice(float p) {
        if (p == 0) {
            return Variables.DEFECT;
        }
        if (p == 1) {
            return Variables.COOPERATE;
        }
        if (new Random().nextInt(100) < p) {
            return Variables.COOPERATE;
        } else {
            return Variables.DEFECT;
        }
    }
}
