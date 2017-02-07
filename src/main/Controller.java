package main;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dbrisingr on 06/02/2017.
 */
public class Controller {


    public static void main(String[] args){
        ArrayList<String> strategyArrayList = new ArrayList<>();
        strategyArrayList.add("COOPERATE");
        strategyArrayList.add("DEFECT");
        strategyArrayList.add("TRIAL");
//        strategyArrayList.add("RANDOM");

        Tournament tournament = new Tournament(strategyArrayList, Tournament.TournamentMode.MODE_ORIGINAL);
        tournament.executeMatches();
        tournament.printScore();
        tournament.printTournamentResult();
//        tournament.getTournamentResult();
    }

}
