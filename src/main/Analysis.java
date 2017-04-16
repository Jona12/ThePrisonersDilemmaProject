package main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dbrisingr on 15/02/2017.
 */
public class Analysis {

    private LinkedList<LinkedHashMap<String, int[]>> matchScoresLinkedList;
    private LinkedList<HashMap<String, History>> tournamentScoresLinkedList;
    private LinkedList<History> randomHistoryLinkedList;

    public LinkedList<LinkedHashMap<String, int[]>> getMatchScoresLinkedList() {
        return matchScoresLinkedList;
    }

    public LinkedList<HashMap<String, History>> getTournamentScoresLinkedList() {
        return tournamentScoresLinkedList;
    }

    public LinkedList<History> getRandomHistoryLinkedList() {
        return randomHistoryLinkedList;
    }

    public Analysis(LinkedList<HashMap<String, History>> tournamentScoresLinkedList, LinkedList<LinkedHashMap<String, int[]>> matchScoresLinkedList, LinkedList<History> randomHistoryLinkedList) {
        this.tournamentScoresLinkedList = tournamentScoresLinkedList;
        this.matchScoresLinkedList = matchScoresLinkedList;
        this.randomHistoryLinkedList = randomHistoryLinkedList;
    }

    public Analysis(LinkedList<LinkedHashMap<String, int[]>> matchScoresLinkedList, LinkedList<HashMap<String, History>> tournamentScoresLinkedList) {
        this.matchScoresLinkedList = matchScoresLinkedList;
        this.tournamentScoresLinkedList = tournamentScoresLinkedList;
    }

    public HashMap<String, Integer> fetchAverageScores() {
        HashMap<String, Integer> averageScores = new HashMap<>();

        String temp;
        HashMap<String, ArrayList<Integer>> hashMap = new HashMap<>();
        int[] ints;
        for (HashMap<String, int[]> stringHashMap : matchScoresLinkedList) {
            for (Map.Entry<String, int[]> entry : stringHashMap.entrySet()) {
                temp = entry.getKey();
                ints = entry.getValue();
                String[] strings = fixMatchStrings(temp);

                if (strings[0].equals(strings[1])) {
                    if (hashMap.containsKey(strings[0])) {
                        ArrayList<Integer> toPut = hashMap.get(strings[0]);
                        toPut.add(ints[0]);
                    } else {
                        ArrayList<Integer> toPut = new ArrayList<>();
                        toPut.add(ints[0]);
                        hashMap.put(strings[0], toPut);
                    }
                } else {
                    for (int i = 0; i < strings.length; i++) {
                        if (hashMap.containsKey(strings[i])) {
                            ArrayList<Integer> toPut = hashMap.get(strings[i]);
                            toPut.add(ints[i]);
                        } else {
                            ArrayList<Integer> toPut = new ArrayList<>();
                            toPut.add(ints[i]);
                            hashMap.put(strings[i], toPut);
                        }
                    }
                }
            }
        }

        for (Map.Entry<String, ArrayList<Integer>> entry : hashMap.entrySet()) {
            String s = entry.getKey();
            int count = entry.getValue().size();
            int totalScore = 0;
            for (int i : entry.getValue()) {
                totalScore += i;
            }
            int averageScore = totalScore / count;
            averageScores.put(s, averageScore);
        }

        averageScores = (HashMap<String, Integer>) sortByValue(averageScores);
        return averageScores;
    }

    public static String[] fixMatchStrings(String matchID) {
        String[] strings = new String[2];
        String strategy1, strategy2;

        if (matchID.contains("_vs._")) {
            strategy1 = matchID.substring(matchID.indexOf("_") + 1, matchID.indexOf("_vs._"));
            strategy2 = matchID.substring(matchID.indexOf("._") + 2);
        } else if (matchID.contains("_RAND")) {
            strategy1 = matchID.substring(matchID.indexOf("_") + 1, matchID.indexOf("_RAND"));
            strategy2 = "RANDOM";
        } else {
            strategy1 = matchID.substring(matchID.indexOf("_") + 1, matchID.indexOf("_TWIN"));
            strategy2 = strategy1;
        }
        strings[0] = strategy1;
        strings[1] = strategy2;

        return strings;
    }

    public HashMap<String, Integer> fetchTournamentScores(boolean tournamentSum, boolean maxMatchSum, boolean minMatchSum) {

        HashMap<String, Integer> finalScore = new HashMap<>();
        int score;
        if (randomHistoryLinkedList != null) {
            for (History history : randomHistoryLinkedList) {
                score = history.calculateMatchScores(tournamentSum, maxMatchSum, minMatchSum);
                if (finalScore.get("RANDOM") == null) {
                    finalScore.put("RANDOM", score);
                } else {
                    finalScore.put("RANDOM", score + finalScore.get("RANDOM"));
                }
            }
        }
        for (HashMap<String, History> historyHashMap : tournamentScoresLinkedList) {
            for (Map.Entry<String, History> entry : historyHashMap.entrySet()) {
                score = entry.getValue().calculateMatchScores(tournamentSum, maxMatchSum, minMatchSum);
                if (finalScore.get(entry.getKey()) == null) {
                    finalScore.put(entry.getKey(), score);
                } else {
                    finalScore.put(entry.getKey(), score + finalScore.get(entry.getKey()));
                }
            }
        }
        finalScore = (HashMap<String, Integer>) sortByValue(finalScore);
        return finalScore;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
