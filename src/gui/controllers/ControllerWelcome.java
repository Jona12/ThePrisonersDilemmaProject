package gui.controllers;

import gui.event_handlers.CustomEventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by dbrisingr on 08/02/2017.
 */
public class ControllerWelcome implements Initializable {

    @FXML
    private Button welcomeDilemmaButton;
    @FXML
    private Button welcomeIteratedButton;
    @FXML
    private Button welcomeAxelrodButton;
    @FXML
    private Button welcomeRunningTournamentButton;
    @FXML
    private Button welcomeCustomStrategiesButton;
    @FXML
    private Button welcomeAnalysisButton;
    @FXML
    private Button welcomeLaunchTournamentButton;
    @FXML
    private Button welcomeLaunchAnalysisButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        welcomeLaunchTournamentButton.getStyleClass().add("custom-button");
        welcomeLaunchAnalysisButton.getStyleClass().add("custom-button");

        CustomEventHandler customEventHandler = new CustomEventHandler(getNodeHashMap());
        welcomeLaunchTournamentButton.setOnAction(customEventHandler);
        welcomeAxelrodButton.setOnAction(customEventHandler);
        welcomeDilemmaButton.setOnAction(customEventHandler);
    }

    private HashMap<Node, Object[]> getNodeHashMap() {

        HashMap<Node, Object[]> nodeHashMap = new HashMap<>();

        Button[] keys = {
                welcomeLaunchTournamentButton, welcomeLaunchAnalysisButton, welcomeDilemmaButton,
                welcomeIteratedButton, welcomeAxelrodButton, welcomeRunningTournamentButton,
                welcomeCustomStrategiesButton, welcomeAnalysisButton,
        };
        String[][] values = new String[8][2];

        values[0][0] = "fxml/tournament.fxml";
        values[0][1] = "Tournament";

        values[1][0] = "fxml/analysis.fxml";
        values[1][1] = "Analysis";

        values[2][0] = "fxml/dilemma_concept.fxml";
        values[2][1] = "Prisoner's Dilemma Concepts";

        values[3][0] = "fxml/iterated_concept.fxml";
        values[3][1] = "Iterated Prisoner's Dilemma Concepts";

        values[4][0] = "fxml/axelrod_concept.fxml";
        values[4][1] = "Robert Axelrod's Original Tournament";

        values[5][0] = "fxml/custom_concept.fxml";
        values[5][1] = "Designing Custom Strategies";

        values[6][0] = "fxml/analysis_concept.fxml";
        values[6][1] = "Running A Tournament";

        values[7][0] = "fxml/run_tournament.fxml";
        values[7][1] = "Running A Tournament";

        int count = 0;
        for (String[] v : values) {
            nodeHashMap.put(keys[count++], v);
        }

        return nodeHashMap;
    }
}
