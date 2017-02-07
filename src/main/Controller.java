package main;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dbrisingr on 06/02/2017.
 */
public class Controller {


    public static void main(String[] args) {
        ArrayList<String> strategyArrayList = new ArrayList<>();
        strategyArrayList.add("ALWAYS_COOPERATE");
        strategyArrayList.add("ALWAYS_DEFECT");
        strategyArrayList.add("TIT_FOR_TAT");
//        strategyArrayList.add("RANDOM");

        Tournament tournament = new Tournament(strategyArrayList, Tournament.TournamentMode.MODE_ORIGINAL);
        tournament.executeMatches();
        tournament.printMatchScores(true, false, false);
        tournament.printTournamentScores();
//        tournament.getTournamentResult();
    }

}
