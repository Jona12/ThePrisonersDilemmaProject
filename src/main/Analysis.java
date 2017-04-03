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

    private ArrayList<LinkedHashMap<String, int[]>> tournamentResultArray;
    private LinkedList<HashMap<String, History>> tournamentLinkedList;
    private LinkedList<History> randomLinkedList;

    public ArrayList<LinkedHashMap<String, int[]>> getTournamentResultArray() {
        return tournamentResultArray;
    }

    public LinkedList<HashMap<String, History>> getTournamentLinkedList() {
        return tournamentLinkedList;
    }

    public LinkedList<History> getRandomLinkedList() {
        return randomLinkedList;
    }

    public Analysis(LinkedList<HashMap<String, History>> tournamentLinkedList, ArrayList<LinkedHashMap<String, int[]>> tournamentResultArray, LinkedList<History> randomLinkedList) {
        this.tournamentLinkedList = tournamentLinkedList;
        this.tournamentResultArray = tournamentResultArray;
        this.randomLinkedList = randomLinkedList;
    }

    public Analysis(ArrayList<LinkedHashMap<String, int[]>> tournamentResultArray, LinkedList<HashMap<String, History>> tournamentLinkedList) {
        this.tournamentResultArray = tournamentResultArray;
        this.tournamentLinkedList = tournamentLinkedList;
    }

    public HashMap<String, Integer> fetchAverageScores() {
        HashMap<String, Integer> averageScores = new HashMap<>();

        String temp;
        HashMap<String, ArrayList<Integer>> hashMap = new HashMap<>();
        int[] ints;
        for (HashMap<String, int[]> stringHashMap : tournamentResultArray) {
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
//            System.out.println(s);
            for (int i : entry.getValue()) {
//                System.out.println(i);
                totalScore += i;
            }
//            System.out.println(count);
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
        if (randomLinkedList != null) {
            for (History history : randomLinkedList) {
                score = history.calculateMatchScores(tournamentSum, maxMatchSum, minMatchSum);
                if (finalScore.get("RANDOM") == null) {
                    finalScore.put("RANDOM", score);
                } else {
                    finalScore.put("RANDOM", score + finalScore.get("RANDOM"));
                }
            }
        }
        for (HashMap<String, History> historyHashMap : tournamentLinkedList) {
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
