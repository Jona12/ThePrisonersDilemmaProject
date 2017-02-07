package strategies;

import main.History;
import main.Variables;

import static main.Variables.calculateOutputFromScore;

/**
 * Created by dbrisingr on 06/02/2017.
 */
public class TIT_FOR_TAT {

    public String calculate(History h){

//        System.out.println("hi");
        if(h.getCurrentRound()!=0){
            int[][] x = h.getRoundScore();
            String[] previousScores = Variables.calculateOutputFromScore(h.getRoundScore()[h.getCurrentRound()-1]);
//            System.out.println("opponent "+h.getCurrentOpponent()+" with score "+previousScores[1]);
            if(previousScores[1].equals(Variables.COOPERATE)){
                return Variables.COOPERATE;
            }else{
                return Variables.DEFECT;
            }
        }
        return Variables.COOPERATE;
    }
}
