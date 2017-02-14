package gui.data_structures;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by dbrisingr on 09/02/2017.
 */
public class RankData {

    private final SimpleIntegerProperty rank = new SimpleIntegerProperty();
    private final SimpleStringProperty entry = new SimpleStringProperty("");
    private final SimpleIntegerProperty score = new SimpleIntegerProperty();

    public RankData(int rank, String entry, int score){
        setRank(rank);
        setEntry(entry);
        setScore(score);
    }

    public void setRank(int rank) {
        this.rank.set(rank);
    }

    public void setEntry(String entry) {
        this.entry.set(entry);
    }

    public void setScore(int score) {
        this.score.set(score);
    }

    public SimpleIntegerProperty rankProperty() {
        return rank;
    }

    public SimpleStringProperty entryProperty() {
        return entry;
    }

    public SimpleIntegerProperty scoreProperty() {
        return score;
    }
}
