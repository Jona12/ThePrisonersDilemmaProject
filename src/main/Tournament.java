package main;

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

    private LinkedList<LinkedHashMap<String, int[]>> matchScoresLinkedList;
    private LinkedList<HashMap<String, History>> tournamentScoresLinkedList;
    private LinkedList<History> randomHistoryLinkedList;

    private int numberOfRounds;
    private int repeat;
    private boolean twin;
    private boolean random;
    private int count;

    public Tournament(ArrayList<String> strategyArrayList, String mode) {

        this.strategyArrayList = strategyArrayList;
        modeHashMap = TournamentMode.generateModesHashMap();

        numberOfRounds = (int) modeHashMap.get(mode).get(Variables.ROUNDS);
        repeat = (int) modeHashMap.get(mode).get(Variables.REPEAT);
        twin = (boolean) modeHashMap.get(mode).get(Variables.TWIN);
        random = (boolean) modeHashMap.get(mode).get(Variables.RANDOM);

        Variables.setScoreMatrix((int[]) modeHashMap.get(mode).get(Variables.SCORE_MATRIX));

        tournamentScoresLinkedList = new LinkedList<>();
        randomHistoryLinkedList = new LinkedList<>();
        tournamentResult = new LinkedHashMap<>();
        matchScoresLinkedList = new LinkedList<>();
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
        matchScoresLinkedList.add(tournamentResult);
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
                matchScoresLinkedList.add(tournamentResult);
                tournamentResult = new LinkedHashMap<>();
            }
        }
    }

    public HashMap<String, History> runDefault() {

        HashMap<String, History> historyHashMap = new HashMap<>();
        for (String strategy : strategyArrayList) {
            historyHashMap.put(strategy, new History(numberOfRounds, strategy, repeat));
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
                tournamentResult.put(matchID, match.getMatchScore());
            }
        }

        return historyHashMap;
    }

    public void runTwin(HashMap<String, History> historyHashMap, int repeatIteration) {
        Match match;
        String strategyOne, strategyTwo;

        HashMap<String, History> twinHistoryHashMap = new HashMap<>();
        for (String strategy : strategyArrayList) {
            History history = new History(numberOfRounds, strategy, repeat);
            twinHistoryHashMap.put(strategy, history);
        }
        for (String s : strategyArrayList) {
            strategyOne = s;
            strategyTwo = s;

            String matchID = "#" + (count++) + "_" + s + "_TWIN" + "_ITERATION_" + repeatIteration;
            match = new Match(historyHashMap.get(strategyOne), twinHistoryHashMap.get(strategyTwo), numberOfRounds);
            match.runMatch();

            tournamentResult.put(matchID, match.getMatchScore());
        }
    }

    public History runRandom(HashMap<String, History> historyHashMap, int repeatIteration) {
        Match match;
        String strategyOne, strategyTwo = "RANDOM";

        History randomHistory = new History(numberOfRounds, "RANDOM", repeat);
        for (String s : strategyArrayList) {
            strategyOne = s;
            if (!s.equals(strategyTwo)) {
                String matchID = "#" + (count++) + "_" + s + "_RAND" + "_ITERATION_" + repeatIteration;


                match = new Match(historyHashMap.get(strategyOne), randomHistory, numberOfRounds);
                match.runMatch();

                tournamentResult.put(matchID, match.getMatchScore());

            }
        }
        return randomHistory;
    }

    public LinkedList<LinkedHashMap<String, int[]>> getMatchScoresLinkedList() {
        return matchScoresLinkedList;
    }

    public LinkedList<HashMap<String, History>> getTournamentScoresLinkedList() {
        return tournamentScoresLinkedList;
    }

    public LinkedList<History> getrandomHistoryLinkedList() {
        return randomHistoryLinkedList;
    }
}
