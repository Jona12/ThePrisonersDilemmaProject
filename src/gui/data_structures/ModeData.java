package gui.data_structures;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by dbrisingr on 13/02/2017.
 */
public class ModeData {

    private final SimpleStringProperty variable = new SimpleStringProperty("");
    private final SimpleStringProperty value = new SimpleStringProperty("");

    public ModeData(String variable, String value) {
        setVariable(variable);
        if(value.equals("false")){
            value = "No";
        }else if(value.equals("true")){
            value = "Yes";
        }
        setValue(value);
    }

    public void setVariable(String variable) {
        this.variable.set(variable);
    }

    public void setValue(String value) {
        if(value.equals("false")){
            this.value.set("No");
        }else if(value.equals("true")){
            this.value.set("Yes");
        }
        this.value.set(value);
    }

    public SimpleStringProperty variableProperty() {
        return variable;
    }

    public SimpleStringProperty valueProperty() {
        return value;
    }
}