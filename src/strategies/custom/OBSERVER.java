package strategies.custom;

import main.History;
import main.Variables;
import strategies.original.DOWNING_REVISED;
import strategies.original.TIT_FOR_TAT;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dbrisingr on 14/04/2017.
 */
public class OBSERVER {

    public String calculate(History h){
        HashMap<String, int[][]> opponentScore = h.getOpponentMatchScores();
        double maxDefects = 0;
        for(Map.Entry<String, int[][]> entry : opponentScore.entrySet()){
            int defects = 0;
            int[][] value = entry.getValue();
            String[][] stringValue = Variables.calculateMatchScoreString(value);
            for(String[] x : stringValue){
                if(x[0].equals(Variables.DEFECT)) defects++;
            }
            if(maxDefects < defects) maxDefects = defects;
        }
        double toCompare = maxDefects / 200;
        if(toCompare > 0.5){
            return new TIT_FOR_TAT().calculate(h);
        }
        else{
            return new DOWNING_REVISED().calculate(h);
        }
    }
}
