package gui.controllers;

import gui.data_structures.Observables;
import gui.event_handlers.CustomModeHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.TournamentMode;
import main.Variables;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by dbrisingr on 04/03/2017.
 */
public class ControllerCustomMode implements Initializable {

    @FXML
    private TextField win_value;
    @FXML
    private TextField lose_value;
    @FXML
    private TextField draw_c_value;
    @FXML
    private TextField draw_d_value;
    @FXML
    private TextField rounds_value;
    @FXML
    private ComboBox repeat_value;
    @FXML
    private ComboBox twin_value;
    @FXML
    private ComboBox random_value;
    @FXML
    private Button save_custom_mode;

    private int repeat;
    private boolean twin;
    private boolean random;

    private Observables observables;
    private ObservableList<String> repeatList;
    private ObservableList<String> twinList;
    private ObservableList<String> randomList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        repeatList = FXCollections.observableArrayList();
        twinList = FXCollections.observableArrayList();
        randomList = FXCollections.observableArrayList();
    }

    public void setObservables(Observables observables) {
        this.observables = observables;

        setupView();
        populateFields();
        setDefaultComboBox();
    }

    private void setupView() {
        TextField[] textFields = {
                win_value, lose_value, draw_c_value, draw_d_value
        };
        Object[] objects = {textFields, rounds_value, repeat_value, twin_value,
                random_value};
        CustomModeHandler customModeHandler = new CustomModeHandler(objects, observables);
        save_custom_mode.setOnAction(customModeHandler);
    }

    private void setDefaultComboBox() {
        String[] repeats = {"0", "1", "2", "3", "4", "5"};
        repeatList.addAll(repeats);
        String[] values = {"Yes", "No"};
        twinList.addAll(values);
        randomList.addAll(values);

        repeat_value.setItems(repeatList);
        twin_value.setItems(twinList);
        random_value.setItems(randomList);

        repeat_value.setValue(repeat);
        if (twin) {
            twin_value.setValue("Yes");
        } else {
            twin_value.setValue("No");
        }

        if (random) {
            random_value.setValue("Yes");
        } else {
            random_value.setValue("No");
        }
    }

    private void populateFields() {
        HashMap<String, HashMap<Object, Object>> modeHashMap = TournamentMode.getModesHashMap();
        int[] scoreMatrix = (int[]) modeHashMap.get(TournamentMode.MODE_CUSTOM).get(Variables.SCORE_MATRIX);
        int numberOfRounds = (int) modeHashMap.get(TournamentMode.MODE_CUSTOM).get(Variables.ROUNDS);
        repeat = (int) modeHashMap.get(TournamentMode.MODE_CUSTOM).get(Variables.REPEAT);
        twin = (boolean) modeHashMap.get(TournamentMode.MODE_CUSTOM).get(Variables.TWIN);
        random = (boolean) modeHashMap.get(TournamentMode.MODE_CUSTOM).get(Variables.RANDOM);

        win_value.setText("" + scoreMatrix[0]);
        lose_value.setText("" + scoreMatrix[1]);
        draw_c_value.setText("" + scoreMatrix[2]);
        draw_d_value.setText("" + scoreMatrix[3]);
        rounds_value.setText("" + numberOfRounds);
//
//        String finalMatrix = "WIN: " + scoreMatrix[0] + "\r\n" + "LOSE: " + scoreMatrix[1] +
//                "\r\n" + "DRAW_C: " + scoreMatrix[2] + "\r\n" + "DRAW_D: " + scoreMatrix[3];

//        HashMap<Object, Object> temp = new HashMap<>();

//        String roundsValue = String.valueOf(numberOfRounds);
//        String scoreMatrixValue = Arrays.toString(scoreMatrix);
//        String repeatValue = String.valueOf(repeat);
//        String twinValue = String.valueOf(twin);
//        String randomValue = String.valueOf(random);
//
//        temp.put(Variables.ROUNDS, roundsValue);
//        temp.put(Variables.SCORE_MATRIX, scoreMatrixValue);
//        temp.put(Variables.REPEAT, repeatValue);
//        temp.put(Variables.TWIN, twinValue);
//        temp.put(Variables.RANDOM, randomValue);

//        TournamentMode.storeCustomModeData(temp);
//        if(observables.getMode().equals(TournamentMode.MODE_CUSTOM)){
//            ObservableList<ModeData> modeDataObservableList = observables.getModeData();
//            modeDataObservableList.clear();
//            modeDataObservableList.add(new ModeData(Variables.SCORE_MATRIX, finalMatrix));
//            modeDataObservableList.add(new ModeData(Variables.ROUNDS, "" + numberOfRounds));
//            modeDataObservableList.add(new ModeData(Variables.REPEAT, "" + repeat));
//            modeDataObservableList.add(new ModeData(Variables.TWIN, "" + twin));
//            modeDataObservableList.add(new ModeData(Variables.RANDOM, "" + random));
//        }
    }


}
