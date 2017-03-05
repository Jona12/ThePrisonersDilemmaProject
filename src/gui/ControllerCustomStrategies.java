package gui;

import gui.data_structures.Observables;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by dbrisingr on 04/03/2017.
 */
public class ControllerCustomStrategies implements Initializable {

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

    private Observables observables;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setObservables(Observables observables) {
        this.observables = observables;
    }


}
