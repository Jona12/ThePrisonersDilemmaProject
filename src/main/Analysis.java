package main;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dbrisingr on 15/02/2017.
 */
public class Analysis {

    private LinkedList<HashMap<String, History>> tournamentLinkedList;
    private LinkedList<History> randomLinkedList;

    public Analysis(LinkedList<HashMap<String, History>> tournamentLinkedList, LinkedList<History> randomLinkedList) {
        this.tournamentLinkedList = tournamentLinkedList;
        this.randomLinkedList = randomLinkedList;
    }

    public Analysis(LinkedList<HashMap<String, History>> tournamentLinkedList) {
        this.tournamentLinkedList = tournamentLinkedList;
    }

    public HashMap<String, Integer> fetchTournamentScores(boolean tournamentSum, boolean matchSum, boolean roundSum) {

        HashMap<String, Integer> finalScore = new HashMap<>();
        int score;
        if (randomLinkedList != null) {
            for (History history : randomLinkedList) {
                score = history.calculateMatchScores(tournamentSum, matchSum, roundSum);
                if (finalScore.get("RANDOM") == null) {
                    finalScore.put("RANDOM", score);
                } else {
                    finalScore.put("RANDOM", score + finalScore.get("RANDOM"));
                }
            }
        }
        for (HashMap<String, History> historyHashMap : tournamentLinkedList) {
            for (Map.Entry<String, History> entry : historyHashMap.entrySet()) {
                score = entry.getValue().calculateMatchScores(tournamentSum, matchSum, roundSum);
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
