package main;

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

    public final static String STRATEGIES = "STRATEGIES INCLUDED";
    public final static String REPEAT = "NUMBER OF ROUND REPEATS";
    public final static String ROUNDS = "NUMBER OF ROUNDS";
    public final static String CURRENT_ROUND = "CURRENT ROUND";
    public final static String ENTRIES= "NUMBER OF ENTRIES";
    public final static String RANDOM = "RANDOM";
    public final static String TWIN = "TWIN";
    public final static String SCORE_MATRIX = "SCORE MATRIX";

    public final static String PAST_SELF_ROUNDS = "PAST SELF ROUNDS";
    public final static String PAST_SELF_MATCHES = "PAST SELF MATCHES";
    public final static String PAST_OPPONENT_ROUNDS = "PAST OPPONENT ROUNDS";
    public final static String PAST_OPPONENT_MATCHES = "PAST OPPONENT MATCHES";

    private String[] modeValuesArray = {
            STRATEGIES, REPEAT, ROUNDS, CURRENT_ROUND, ENTRIES, RANDOM, TWIN, SCORE_MATRIX, PAST_SELF_ROUNDS, PAST_SELF_MATCHES,
            PAST_OPPONENT_ROUNDS, PAST_OPPONENT_MATCHES
    };


    private String[] getVariablesArray(){
        return modeValuesArray;
    }

    public static String[] calculateOutputFromScore(int[] input){

        int score1 = input[0]; int score2 = input[1];
        String[] output = new String[2];

        if(score1 == DRAW_C_SCORE && score2 == DRAW_C_SCORE){
            output[0] = COOPERATE; output[1] = COOPERATE;

        }else if(score1 == LOSE_SCORE && score2 == WIN_SCORE){
            output[0] = COOPERATE; output[1] = DEFECT;

        }else if(score1 == WIN_SCORE && score2 == LOSE_SCORE){
            output[0] = DEFECT; output[1] = COOPERATE;

        }else{
            output[0] = DEFECT; output[1] = DEFECT;
        }

        return output;
    }

    public static void setScoreMatrix(int[] scoreMatrix){
        WIN_SCORE = scoreMatrix[0];
        LOSE_SCORE = scoreMatrix[1];
        DRAW_C_SCORE = scoreMatrix[2];
        DRAW_D_SCORE = scoreMatrix[3];
    }
}
