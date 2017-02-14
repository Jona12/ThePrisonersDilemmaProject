package gui.data_structures;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by dbrisingr on 14/02/2017.
 */
public class StrategyData {

    private final SimpleStringProperty strategy = new SimpleStringProperty("");
    private final SimpleBooleanProperty select = new SimpleBooleanProperty(false);

    public StrategyData(String strategy, boolean select) {
        setStrategy(strategy);
        setSelect(select);
    }

    public void setStrategy(String strategy) {
        this.strategy.set(strategy);
    }

    public void setSelect(boolean select) {
        this.select.set(select);
    }

    public SimpleStringProperty strategyProperty() {
        return strategy;
    }

    public SimpleBooleanProperty selectProperty() {
        return select;
    }
}
