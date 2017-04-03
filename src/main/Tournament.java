package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by dbrisingr on 06/02/2017.
 */
public class Tournament {

    private boolean cancel;

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    private ArrayList<String> strategyArrayList;
    private HashMap<String, HashMap<Object, Object>> modeHashMap;

    private LinkedList<HashMap<String, History>> tournamentLinkedList;
    private LinkedList<History> randomLinkedList;

    private int numberOfRounds;

    private int repeat;
    private boolean twin;
    private boolean random;

    private ArrayList<LinkedHashMap<String, int[]>> tournamentResultArray;
    private LinkedHashMap<String, int[]> tournamentResult;
    private int count;


    public Tournament(ArrayList<String> strategyArrayList, String mode) {

        this.strategyArrayList = strategyArrayList;
        modeHashMap = TournamentMode.getModesHashMap();

        numberOfRounds = (int) modeHashMap.get(mode).get(Variables.ROUNDS);
        repeat = (int) modeHashMap.get(mode).get(Variables.REPEAT);
        twin = (boolean) modeHashMap.get(mode).get(Variables.TWIN);
        random = (boolean) modeHashMap.get(mode).get(Variables.RANDOM);

        Variables.setScoreMatrix((int[]) modeHashMap.get(mode).get(Variables.SCORE_MATRIX));

        tournamentLinkedList = new LinkedList<>();
        randomLinkedList = new LinkedList<>();
        tournamentResult = new LinkedHashMap<>();
        tournamentResultArray = new ArrayList<>();
        count = 0;
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
        tournamentResultArray.add(tournamentResult);
        tournamentResult = new LinkedHashMap<>();
        if (repeat > 0) {
            for (int i = 0; i < repeat; i++) {
                tournamentLinkedList.add(runDefault());
                if (twin) {
                    runTwin(tournamentLinkedList.get(i + 1), i + 1);
                }
                if (random) {
                    randomLinkedList.add(runRandom(tournamentLinkedList.get(0), i + 1));
                }
                tournamentResultArray.add(tournamentResult);
                tournamentResult = new LinkedHashMap<>();
            }
        }
    }

    public HashMap<String, History> runDefault() {

        HashMap<String, History> historyHashMap = new HashMap<>();
        for (String strategy : strategyArrayList) {
            historyHashMap.put(strategy, new History(numberOfRounds, strategy));
        }
        Match match;
        String strategyOne, strategyTwo;
        loop:
        for (int i = 0; i < strategyArrayList.size(); i++) {
            for (int j = historyHashMap.size() - 1; j > i; j--) {
                if (cancel) {
                    break loop;
                }
                strategyOne = strategyArrayList.get(i);
                strategyTwo = strategyArrayList.get(j);

                String matchID = "#" + (count++) + "_" + strategyOne + "_vs._" + strategyTwo;
                match = new Match(historyHashMap.get(strategyOne), historyHashMap.get(strategyTwo), numberOfRounds);
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

            String matchID = "#" + (count++) + "_" + s + "_TWIN" + "_ITERATION_" + repeatIteration;
            match = new Match(historyHashMap.get(strategyOne), twinHistoryHashMap.get(strategyTwo), numberOfRounds);
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
                String matchID = "#" + (count++) + "_" + s + "_RAND" + "_ITERATION_" + repeatIteration;

//                match = new Match(matchID, historyHashMap.get(strategyOne), twinHistoryHashMap.get(strategyTwo), numberOfRounds);
                match = new Match(historyHashMap.get(strategyOne), randomHistory, numberOfRounds);
                match.runMatch();

                tournamentResult.put(matchID, match.getMatchResult());

            }
        }
        return randomHistory;
    }

    public ArrayList<LinkedHashMap<String, int[]>> getTournamentResultArray() {
        return tournamentResultArray;
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
}
