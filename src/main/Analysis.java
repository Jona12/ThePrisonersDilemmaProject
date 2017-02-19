package main;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dbrisingr on 15/02/2017.
 */
public class Analysis {

    public HashMap<String, History> getHistoryHashMap() {
        return historyHashMap;
    }

    private HashMap<String, History> historyHashMap;

    public Analysis(HashMap<String, History> historyHashMap){
        this.historyHashMap = historyHashMap;
    }

    public HashMap<String, Integer> fetchTournamentScores(boolean tournamentSum, boolean matchSum, boolean roundSum) {

        HashMap<String, Integer> finalScore = new HashMap<>();
        int score;
        for (Map.Entry<String, History> entry : historyHashMap.entrySet()) {
            score = entry.getValue().calculateMatchScores(tournamentSum, matchSum, roundSum);
            finalScore.put(entry.getKey(), score);
        }
        finalScore = (HashMap<String, Integer>) sortByValue(finalScore);
        return finalScore;
    }

//    public static <K, V extends Comparable<? super V>> Map<K, V>
//    sortByValue(Map<K, V> map) {
//        List<Map.Entry<K, V>> list =
//                new LinkedList<Map.Entry<K, V>>(map.entrySet());
//        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
//            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
//                return (o2.getValue()).compareTo(o1.getValue());
//            }
//        });
//
//        Map<K, V> result = new LinkedHashMap<K, V>();
//        for (Map.Entry<K, V> entry : list) {
//            result.put(entry.getKey(), entry.getValue());
//        }
//        return result;
//    }

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
