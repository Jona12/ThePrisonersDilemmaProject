package gui.data_structures;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by dbrisingr on 03/04/2017.
 */
public class MatchData {

    private final SimpleStringProperty matchID = new SimpleStringProperty("");
    private final SimpleIntegerProperty overallScore = new SimpleIntegerProperty();
    private final SimpleIntegerProperty score1 = new SimpleIntegerProperty();
    private final SimpleIntegerProperty score2 = new SimpleIntegerProperty();

    public MatchData(String matchID, int overallScore, int score1, int score2){
        setMatchID(matchID);
        setOverallScore(overallScore);
        setScore1(score1);
        setScore2(score2);
    }

    public void setMatchID(String matchID) {
        this.matchID.set(matchID);
    }

    public void setOverallScore(int overallScore){ this.overallScore.set(overallScore);}

    public void setScore1(int score1) {
        this.score1.set(score1);
    }

    public void setScore2(int score2) {
        this.score2.set(score2);
    }

    public SimpleStringProperty matchIDProperty() {
        return matchID;
    }

    public SimpleIntegerProperty overallScoreProperty(){return overallScore;}

    public SimpleIntegerProperty score1Property() {
        return score1;
    }

    public SimpleIntegerProperty score2Property() {
        return score2;
    }
}
