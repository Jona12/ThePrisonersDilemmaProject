package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Created by dbrisingr on 21/01/2017.
 */
public class Variables {

    public final static String COOPERATE = "COOPERATE";
    public final static String DEFECT = "DEFECT";

    public static int WIN_SCORE;
    public static int LOSE_SCORE;
    public static int DRAW_C_SCORE;
    public static int DRAW_D_SCORE;

    public final static String STRATEGY = "Selected strategy name";
    public final static String STRATEGIES = "Strategies included";
    public final static String REPEAT = "Repeat entire tournament?";
    public final static String ROUNDS = "Number of total rounds";
    public final static String CURRENT_ROUND = "current round";
    public final static String ENTRIES = "number of rounds";
    public final static String RANDOM = "Is the RANDOM strategy included?";
    public final static String TWIN = "Will twin strategies be included?";
    public final static String SCORE_MATRIX = "Score matrix";

    public final static String PAST_SELF_ROUNDS = "PAST SELF ROUNDS";
    public final static String PAST_SELF_MATCHES = "PAST SELF MATCHES";
    public final static String PAST_OPPONENT_ROUNDS = "PAST OPPONENT ROUNDS";
    public final static String PAST_OPPONENT_MATCHES = "PAST OPPONENT MATCHES";

    private String[] modeValuesArray = {
            STRATEGIES, REPEAT, ROUNDS, CURRENT_ROUND, ENTRIES, RANDOM, TWIN, SCORE_MATRIX, PAST_SELF_ROUNDS, PAST_SELF_MATCHES,
            PAST_OPPONENT_ROUNDS, PAST_OPPONENT_MATCHES
    };


    private String[] getVariablesArray() {
        return modeValuesArray;
    }

    public static String[] calculateRoundScoreString(int[] input) {

        int score1 = input[0];
        int score2 = input[1];
        String[] output = new String[2];

        if (score1 == DRAW_C_SCORE && score2 == DRAW_C_SCORE) {
            output[0] = COOPERATE;
            output[1] = COOPERATE;

        } else if (score1 == LOSE_SCORE && score2 == WIN_SCORE) {
            output[0] = COOPERATE;
            output[1] = DEFECT;

        } else if (score1 == WIN_SCORE && score2 == LOSE_SCORE) {
            output[0] = DEFECT;
            output[1] = COOPERATE;

        } else {
            output[0] = DEFECT;
            output[1] = DEFECT;
        }

        return output;
    }

    public static String[][] calculateMatchScoreString(int[][] input) {

        String[][] output = new String[input.length][2];

        for (int i = 0; i < input.length; i++) {
            output[i] = calculateRoundScoreString(input[i]);
        }

        return output;
    }

    public static int[][] calculateScoreFromOutput(String[] input, int totalRounds) {
        int[][] output = new int[totalRounds][2];


        return output;
    }

    public static void setScoreMatrix(int[] scoreMatrix) {
        WIN_SCORE = scoreMatrix[0];
        LOSE_SCORE = scoreMatrix[1];
        DRAW_C_SCORE = scoreMatrix[2];
        DRAW_D_SCORE = scoreMatrix[3];
    }
}
