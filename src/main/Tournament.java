package main;

import org.omg.CORBA.INITIALIZE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dbrisingr on 06/02/2017.
 */
public class Tournament {

    private ArrayList<String> strategyArrayList;
    private HashMap<String, HashMap<String, Object>> modeHashMap;
    private HashMap<String, History> historyHashMap;
    private HashMap<String, History> twinHistoryHashMap;

    private int numberOfRounds;

    private boolean repeat;
    private boolean twin;
    private boolean random;

    private HashMap<String, int[]> tournamentResult;

    private String[] strategies;

    private int count;


    public Tournament(ArrayList<String> strategyArrayList, String mode) {

        this.strategyArrayList = strategyArrayList;
        modeHashMap = Tournament.TournamentMode.getMode(mode);

        numberOfRounds = (int) modeHashMap.get(mode).get(Variables.ROUNDS);
        repeat = (boolean) modeHashMap.get(mode).get(Variables.REPEAT);
        twin = (boolean) modeHashMap.get(mode).get(Variables.TWIN);
        random = (boolean) modeHashMap.get(mode).get(Variables.RANDOM);

        Variables.setScoreMatrix((int[]) modeHashMap.get(mode).get(Variables.SCORE_MATRIX));
        historyHashMap = new HashMap<>();
        for (String strategy : strategyArrayList) {
            historyHashMap.put(strategy, new History(numberOfRounds, strategy));
        }

        if (twin || random) {
            twinHistoryHashMap = new HashMap<>();
        }


        tournamentResult = new HashMap<>();
        strategies = new String[historyHashMap.size()];
    }

    public void executeMatches() {
        runDefault();
        if (twin) {
            runTwin();
        }
        if (random) {
            runRandom();
        }
    }

    public void runDefault() {
        Match match;
        String strategyOne, strategyTwo;

        count = 0;
        for (String key : historyHashMap.keySet()) {
            strategies[count] = key;
            count++;
        }

        count = 0;
        for (int i = 0; i < strategies.length; i++) {
            for (int j = historyHashMap.size() - 1; j > i; j--) {
                strategyOne = strategies[i];
                strategyTwo = strategies[j];

                String matchID = "#" + (count++) + strategyOne + "_" + strategyTwo;
                match = new Match(matchID, historyHashMap.get(strategyOne), historyHashMap.get(strategyTwo), numberOfRounds);
                match.runMatch();

//                historyHashMap.put(strategyOne, temp[0]);
//                historyHashMap.put(strategyTwo, temp[1]);

                tournamentResult.put(matchID, match.getMatchResult());
            }
        }
    }

    public void runTwin() {
        Match match;
        String strategyOne, strategyTwo;

        for (String strategy : strategyArrayList) {
            History history = new History(numberOfRounds, (strategy));
            twinHistoryHashMap.put(strategy, history);
        }

        for (String s : strategies) {
            strategyOne = s;
            strategyTwo = s;

            String matchID = "#" + (count++) + s + "_TWIN";

            match = new Match(matchID, historyHashMap.get(strategyOne), twinHistoryHashMap.get(strategyTwo), numberOfRounds);
            match.runMatch();

            tournamentResult.put(matchID, match.getMatchResult());
        }
    }

    public void runRandom() {
        Match match;
        String strategyOne, strategyTwo;

        twinHistoryHashMap.put("RANDOM", new History(numberOfRounds, "RANDOM"));

        for( String s : strategies){
            strategyOne = s;
            strategyTwo = "RANDOM";

            String matchID = "#" + (count++) + s + "_RAND";

            match = new Match(matchID, historyHashMap.get(strategyOne), twinHistoryHashMap.get(strategyTwo), numberOfRounds);
            match.runMatch();

            tournamentResult.put(matchID, match.getMatchResult());
        }

    }

    public HashMap<String, int[]> getTournamentResult() {
        return tournamentResult;
    }

    public void printMatchScores(boolean tournamentSum, boolean matchSum, boolean roundSum) {

//        HashMap<String, Integer> finalScore = new HashMap<>();
        for (Map.Entry<String, History> entry : historyHashMap.entrySet()) {
            entry.getValue().calculateMatchScores(tournamentSum, matchSum, roundSum);
        }
    }

    public static class TournamentMode {

        public final static String MODE_ORIGINAL = "ORIGINAL";
        public final static String MODE_ORIGINAL_V2 = "ORIGINAL_V2";
        public final static String MODE_NO_RANDOM = "NO_RANDOM";
        public final static String MODE_NO_TWIN = "NO_TWIN";
        public final static String MODE_NO_RANDOM_NO_TWIN = "NO_RANDOM_NO_TWIN";
        public final static String MODE_ORIGINAL_WITH_REPEAT = "ORIGINAL MODE WITH 2X THE ROUNDS";
        public final static String MODE_CUSTOM = "CUSTOM MODE";

        private static HashMap<String, HashMap<String, Object>> modesHashMap;

        private static String[] originalModesArray = {
                MODE_ORIGINAL, MODE_ORIGINAL_V2, MODE_NO_RANDOM, MODE_NO_TWIN, MODE_NO_RANDOM_NO_TWIN, MODE_ORIGINAL_WITH_REPEAT, MODE_CUSTOM
        };

        public static HashMap<String, HashMap<String, Object>> getMode(String mode) {

            modesHashMap = new HashMap<>();
            HashMap<String, Object> tempTable = new HashMap<>();

            int[] scoreMatrix = new int[4];
            switch (mode) {
                case MODE_ORIGINAL:

                    scoreMatrix[0] = 5;
                    scoreMatrix[1] = 0;
                    scoreMatrix[2] = 3;
                    scoreMatrix[3] = 1;
                    String[] strategiesTemp = {
                            "TIT FOR TAT"
                    };

                    tempTable.put(Variables.STRATEGIES, strategiesTemp);
                    tempTable.put(Variables.ROUNDS, 200);
                    tempTable.put(Variables.ENTRIES, 16);
                    tempTable.put(Variables.REPEAT, false);
                    tempTable.put(Variables.TWIN, true);
                    tempTable.put(Variables.RANDOM, true);
                    tempTable.put(Variables.SCORE_MATRIX, scoreMatrix);

                    modesHashMap.put(MODE_ORIGINAL, tempTable);
                    break;

                case MODE_ORIGINAL_V2:

                    break;

                case MODE_NO_RANDOM:

                    break;

                case MODE_NO_TWIN:
                    break;
                case MODE_NO_RANDOM_NO_TWIN:
                    break;
                case MODE_ORIGINAL_WITH_REPEAT:
                    break;
                case MODE_CUSTOM:
                    break;
                case "":
                    System.out.println("mode not valid");
                    break;
                default:

                    break;
            }

            return modesHashMap;
        }

        public static String[] getOriginalModes() {
            return originalModesArray;
        }

    }
}
