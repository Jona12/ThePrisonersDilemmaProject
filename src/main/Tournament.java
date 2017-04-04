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
    private LinkedHashMap<String, int[]> tournamentResult;

    private ArrayList<LinkedHashMap<String, int[]>> matchScoresArrayList;
    private LinkedList<HashMap<String, History>> tournamentScoresLinkedList;
    private LinkedList<History> randomHistoryLinkedList;

    private int numberOfRounds;
    private int repeat;
    private boolean twin;
    private boolean random;
    private int count;

    public Tournament(ArrayList<String> strategyArrayList, String mode) {

        this.strategyArrayList = strategyArrayList;
        modeHashMap = TournamentMode.getModesHashMap();

        numberOfRounds = (int) modeHashMap.get(mode).get(Variables.ROUNDS);
        repeat = (int) modeHashMap.get(mode).get(Variables.REPEAT);
        twin = (boolean) modeHashMap.get(mode).get(Variables.TWIN);
        random = (boolean) modeHashMap.get(mode).get(Variables.RANDOM);

        Variables.setScoreMatrix((int[]) modeHashMap.get(mode).get(Variables.SCORE_MATRIX));

        tournamentScoresLinkedList = new LinkedList<>();
        randomHistoryLinkedList = new LinkedList<>();
        tournamentResult = new LinkedHashMap<>();
        matchScoresArrayList = new ArrayList<>();
        count = 0;
    }

    public void executeMatches() {
        // run default and add result to linked list
        tournamentScoresLinkedList.add(runDefault());
        if (twin) {
            runTwin(tournamentScoresLinkedList.get(0), 0);
        }
        if (random) {
            randomHistoryLinkedList.add(runRandom(tournamentScoresLinkedList.get(0), 0));
        }
        matchScoresArrayList.add(tournamentResult);
        tournamentResult = new LinkedHashMap<>();
        if (repeat > 0) {
            for (int i = 0; i < repeat; i++) {
                tournamentScoresLinkedList.add(runDefault());
                if (twin) {
                    runTwin(tournamentScoresLinkedList.get(i + 1), i + 1);
                }
                if (random) {
                    randomHistoryLinkedList.add(runRandom(tournamentScoresLinkedList.get(0), i + 1));
                }
                matchScoresArrayList.add(tournamentResult);
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

    public ArrayList<LinkedHashMap<String, int[]>> getmatchScoresArrayList() {
        return matchScoresArrayList;
    }

    public LinkedList<HashMap<String, History>> gettournamentScoresLinkedList() {
        return tournamentScoresLinkedList;
    }

    public LinkedList<History> getrandomHistoryLinkedList() {
        return randomHistoryLinkedList;
    }
}
