package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by dbrisingr on 06/02/2017.
 */
public class Tournament {

    private ArrayList<String> strategyArrayList;
    private HashMap<String, HashMap<Object, Object>> modeHashMap;

    private LinkedList<HashMap<String, History>> tournamentLinkedList;
    private LinkedList<History> randomLinkedList;
//    private History randomHistory;

    private int numberOfRounds;

    private int repeat;
    private boolean twin;
    private boolean random;

    private HashMap<String, int[]> tournamentResult;
    private int count;


    public Tournament(ArrayList<String> strategyArrayList, String mode) {

        this.strategyArrayList = strategyArrayList;
        modeHashMap = Tournament.TournamentMode.getModesHashMap();

        numberOfRounds = (int) modeHashMap.get(mode).get(Variables.ROUNDS);
        repeat = (int) modeHashMap.get(mode).get(Variables.REPEAT);
        twin = (boolean) modeHashMap.get(mode).get(Variables.TWIN);
        random = (boolean) modeHashMap.get(mode).get(Variables.RANDOM);

        Variables.setScoreMatrix((int[]) modeHashMap.get(mode).get(Variables.SCORE_MATRIX));
//        historyHashMap = new HashMap<>();
//        for (String strategy : strategyArrayList) {
//            historyHashMap.put(strategy, new History(numberOfRounds, strategy));
//        }

        if (twin || random) {
//            twinHistoryHashMap = new HashMap<>();
//            randomHistory = new History(numberOfRounds, "RANDOM");
        }


        tournamentLinkedList = new LinkedList<>();
        randomLinkedList = new LinkedList<>();
        tournamentResult = new HashMap<>();
    }

    public void executeMatches() {
        // run default and add result to linked list
        tournamentLinkedList.add(runDefault());

        if (twin) {
            runTwin(tournamentLinkedList.get(0), 0);
        }
        if (random) {
            randomLinkedList.add(runRandom(tournamentLinkedList.get(0), 0));
        }
        if (repeat > 0) {
            for (int i = 0; i < repeat; i++) {
                tournamentLinkedList.add(runDefault());
                if (twin) {
                    runTwin(tournamentLinkedList.get(i + 1), i + 1);
                }
                if (random) {
                    randomLinkedList.add(runRandom(tournamentLinkedList.get(0), i + 1));
                }
            }
        }
    }

    public HashMap<String, History> runDefault() {

        HashMap<String, History> historyHashMap;
        historyHashMap = new HashMap<>();
        for (String strategy : strategyArrayList) {
            historyHashMap.put(strategy, new History(numberOfRounds, strategy));
        }
        Match match;
        String strategyOne, strategyTwo;

        count = 0;
//        for (String key : historyHashMap.keySet()) {
//            strategies[count] = key;
//            count++;
//        }

        count = 0;
        for (int i = 0; i < strategyArrayList.size(); i++) {
            for (int j = historyHashMap.size() - 1; j > i; j--) {
                strategyOne = strategyArrayList.get(i);
                strategyTwo = strategyArrayList.get(j);

                String matchID = "#" + (count++) + strategyOne + "_" + strategyTwo;
                match = new Match(matchID, historyHashMap.get(strategyOne), historyHashMap.get(strategyTwo), numberOfRounds);
                match.runMatch();
                tournamentResult.put(matchID, match.getMatchResult());
            }
        }

        return historyHashMap;
    }

//    public void runRepeat() {
//        Match match;
//        String strategyOne, strategyTwo;
//
//        count = 0;
//        for (int i = 0; i < strategies.length; i++) {
//            for (int j = historyHashMap.size() - 1; j > i; j--) {
//                strategyOne = strategies[i];
//                strategyTwo = strategies[j];
//
//                String matchID = "#" + (count++) + strategyOne + "_" + strategyTwo + "REPEAT";
//                match = new Match(matchID, historyHashMap.get(strategyOne), historyHashMap.get(strategyTwo), numberOfRounds);
//                match.runMatch();
//
////                historyHashMap.put(strategyOne, temp[0]);
////                historyHashMap.put(strategyTwo, temp[1]);
//
//                tournamentResult.put(matchID, match.getMatchResult());
//            }
//        }
//    }

    public void runTwin(HashMap<String, History> historyHashMap, int repeatIteration) {
        Match match;
        String strategyOne, strategyTwo;

        HashMap<String, History> twinHistoryHashMap = new HashMap<>();
        for (String strategy : strategyArrayList) {
            History history = new History(numberOfRounds, (strategy));
            twinHistoryHashMap.put(strategy, history);
        }
        for (String s : strategyArrayList) {
            strategyOne = s;
            strategyTwo = s;

            String matchID = "#" + (count++) + s + "_TWIN" + repeatIteration;
            match = new Match(matchID, historyHashMap.get(strategyOne), twinHistoryHashMap.get(strategyTwo), numberOfRounds);
            match.runMatch();

            tournamentResult.put(matchID, match.getMatchResult());
        }
    }

    public History runRandom(HashMap<String, History> historyHashMap, int repeatIteration) {
        Match match;
        String strategyOne, strategyTwo = "RANDOM";

        History randomHistory = new History(numberOfRounds, "RANDOM");
        for (String s : strategyArrayList) {
            strategyOne = s;
            if (!s.equals(strategyTwo)) {
                String matchID = "#" + (count++) + s + "_RAND" + repeatIteration;

//                match = new Match(matchID, historyHashMap.get(strategyOne), twinHistoryHashMap.get(strategyTwo), numberOfRounds);
                match = new Match(matchID, historyHashMap.get(strategyOne), randomHistory, numberOfRounds);
                match.runMatch();

                tournamentResult.put(matchID, match.getMatchResult());

            }
        }
        return randomHistory;
    }

    public LinkedList<HashMap<String, History>> getTournamentLinkedList() {
        return tournamentLinkedList;
    }

    public LinkedList<History> getRandomLinkedList() {
        return randomLinkedList;
    }

    public HashMap<String, int[]> getTournamentResult() {
        return tournamentResult;
    }


    public static class TournamentMode {

        public final static String MODE_ORIGINAL = "ORIGINAL";
        public final static String MODE_NO_RANDOM = "NO_RANDOM";
        public final static String MODE_NO_TWIN = "NO_TWIN";
        public final static String MODE_NO_RANDOM_NO_TWIN = "NO_RANDOM_NO_TWIN";
        public final static String MODE_ORIGINAL_WITH_REPEAT = "ORIGINAL MODE, WITH 1 REPEAT";
        public final static String MODE_CUSTOM = "CUSTOM MODE";

        private static HashMap<String, HashMap<Object, Object>> modesHashMap;

        private static String[] originalStrategies = {
                "TIT_FOR_TAT (original)", "DAVIS (original)", "DOWNING_REVISED (original)", "FELD (original)",
                "GROFMAN (original)", "JOSS (original)", "NYDEGGER (original)", "SHUBIK (original)",
                "TULLOCK (original)", "UNKNOWN (original)"
        };

        private static final String[] originalModesArray = {
                MODE_ORIGINAL, MODE_NO_RANDOM, MODE_NO_TWIN, MODE_NO_RANDOM_NO_TWIN, MODE_ORIGINAL_WITH_REPEAT, MODE_CUSTOM
        };

        public static HashMap<Object, Object> getVariables(String mode) {
            return modesHashMap.get(mode);
        }

        public static HashMap<String, HashMap<Object, Object>> getModesHashMap() {

            modesHashMap = new HashMap<>();

            for(String s : originalModesArray){
                modesHashMap.put(s, prepareTempHashMap(s));
            }
            modesHashMap.remove(MODE_CUSTOM);

            HashMap<Object, Object> tempHashMap = loadTempMap();
            modesHashMap.put(MODE_CUSTOM, tempHashMap);

            return modesHashMap;
        }

        public static String[] getOriginalModes() {
            return originalModesArray;
        }

        public static String[] getOriginalStrategies() {
            return originalStrategies;
        }

        private static HashMap<Object, Object> prepareTempHashMap(String mode) {
            HashMap<Object, Object> tempHashMap = new HashMap<>();

            int numberOfRounds = 200;
            int numberOfRepeats = 0;
            boolean twin = true;
            boolean random = true;

            int[] scoreMatrix = new int[4];
            scoreMatrix[0] = 5;
            scoreMatrix[1] = 0;
            scoreMatrix[2] = 3;
            scoreMatrix[3] = 1;

            switch (mode) {
                case MODE_ORIGINAL:
                    break;
                case MODE_NO_RANDOM:
                    random = false;
                    break;
                case MODE_NO_TWIN:
                    twin = false;
                    break;
                case MODE_NO_RANDOM_NO_TWIN:
                    random = false;
                    twin = false;
                    break;
                case MODE_ORIGINAL_WITH_REPEAT:
                    numberOfRepeats = 1;
            }

            tempHashMap.put(Variables.ROUNDS, numberOfRounds);
            tempHashMap.put(Variables.SCORE_MATRIX, scoreMatrix);
            tempHashMap.put(Variables.REPEAT, numberOfRepeats);
            tempHashMap.put(Variables.TWIN, twin);
            tempHashMap.put(Variables.RANDOM, random);

            return tempHashMap;
        }

        private static Properties storeTempMap() {

            int[] scoreMatrix = new int[4];
            scoreMatrix[0] = 5;
            scoreMatrix[1] = 0;
            scoreMatrix[2] = 3;
            scoreMatrix[3] = 1;

            String roundsValue = String.valueOf(20);
            String scoreMatrixValue = Arrays.toString(scoreMatrix);
            String repeatValue = String.valueOf(1);
            String twinValue = String.valueOf(true);
            String randomValue = String.valueOf(true);

            HashMap<Object, Object> temp = new HashMap<>();

            temp.put(Variables.ROUNDS, roundsValue);
            temp.put(Variables.SCORE_MATRIX, scoreMatrixValue);
            temp.put(Variables.REPEAT, repeatValue);
            temp.put(Variables.TWIN, twinValue);
            temp.put(Variables.RANDOM, randomValue);

            Properties properties = new Properties();
            properties.putAll(temp);

            try {
                properties.store(new FileOutputStream("src/main/mode_data/data.properties"), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return properties;
        }

        private static HashMap<Object, Object> loadTempMap() {
            Properties properties = new Properties();

            try {
                properties.load(new FileInputStream("src/main/mode_data/data.properties"));
            } catch (IOException e) {
                properties = storeTempMap();
//                e.printStackTrace();
            }

            String arr = properties.getProperty(Variables.SCORE_MATRIX);
            String[] items = arr.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
            int[] results = new int[items.length];
            for (int i = 0; i < items.length; i++) {
                results[i] = Integer.parseInt(items[i]);
            }

            int roundsValue = Integer.valueOf(properties.getProperty(Variables.ROUNDS));
            int[] scoreMatrixValue = results;
            int repeatValue = Integer.valueOf(properties.getProperty(Variables.REPEAT));
            boolean twinValue = Boolean.valueOf(properties.getProperty(Variables.TWIN));
            boolean randomValue = Boolean.valueOf(properties.getProperty(Variables.RANDOM));

            properties.put(Variables.ROUNDS, roundsValue);
            properties.put(Variables.SCORE_MATRIX, scoreMatrixValue);
            properties.put(Variables.REPEAT, repeatValue);
            properties.put(Variables.TWIN, twinValue);
            properties.put(Variables.RANDOM, randomValue);

            HashMap<Object, Object> tempHashMap = new HashMap<>(properties);


            return tempHashMap;
        }
    }
}
