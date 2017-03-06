package gui.event_handlers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.TournamentMode;
import main.Variables;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by dbrisingr on 06/03/2017.
 */
public class CustomStrategiesHandler implements EventHandler {

    private HashMap<Object, Object> data;

    private TextField win_value;
    private TextField lose_value;
    private TextField draw_c_value;
    private TextField draw_d_value;
    private TextField rounds_value;
    private ComboBox repeat_value;
    private ComboBox twin_value;
    private ComboBox random_value;


    public CustomStrategiesHandler(Object[] components) {
        TextField[] textFields = (TextField[]) components[0];

        win_value = textFields[0];
        lose_value = textFields[1];
        draw_c_value = textFields[2];
        draw_d_value = textFields[3];

        rounds_value = (TextField) components[1];
        repeat_value = (ComboBox) components[2];
        twin_value = (ComboBox) components[3];
        random_value = (ComboBox) components[4];
    }

    @Override
    public void handle(Event event) {
        HashMap<Object, Object> temp = new HashMap<>();

        String[] scoreMatrix = {
                win_value.getText(), lose_value.getText(), draw_c_value.getText(), draw_d_value.getText()
        };

        String twin;
        if (twin_value.getValue().toString().equals("Yes")) {
            twin = "" + true;
        } else {
            twin = "" + false;
        }
        String random;
        if (random_value.getValue().toString().equals("Yes")) {
            random = "" + true;
        } else {
            random = "" + false;
        }
        temp.put(Variables.SCORE_MATRIX, Arrays.toString(scoreMatrix));
        temp.put(Variables.ROUNDS, rounds_value.getText());
        temp.put(Variables.REPEAT, repeat_value.getValue().toString());
        temp.put(Variables.TWIN, twin);
        temp.put(Variables.RANDOM, random);

        TournamentMode.storeCustomModeData(temp);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Changes saved");
        alert.setHeaderText(null);
        alert.setContentText("Custom Mode settings have been successfully changed");
        alert.showAndWait();
    }
}
