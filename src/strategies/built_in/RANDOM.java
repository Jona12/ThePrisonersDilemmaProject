package strategies.built_in;

import main.History;
import main.Variables;
import java.util.Random;

/**
 * Created by dbrisingr on 06/02/2017.
 */
public class RANDOM {

    public String calculate(History h){
        if(new Random().nextInt(100) < 50){
            return Variables.COOPERATE;
        }else{
            return Variables.DEFECT;
        }

    }
}
