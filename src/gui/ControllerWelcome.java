package gui;

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
    private Button welcomeIteratedButton;
    @FXML
    private Button welcomeAxelrodButton;
    @FXML
    private Button welcomeStrategiesButton;
    @FXML
    private Button welcomeCustomStrategiesButton;
    @FXML
    private Button welcomeTournamentButton;
    @FXML
    private Button welcomeAnalysisButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        CustomEventHandler customEventHandler = new CustomEventHandler(getNodeHashMap());
        welcomeTournamentButton.setOnAction(customEventHandler);
        welcomeAxelrodButton.setOnAction(customEventHandler);
    }

    private HashMap<Node, Object[]> getNodeHashMap() {

        HashMap<Node, Object[]> nodeHashMap = new HashMap<>();

        Button[] keys = {
                welcomeIteratedButton, welcomeAxelrodButton, welcomeStrategiesButton, welcomeCustomStrategiesButton,
                welcomeTournamentButton, welcomeAnalysisButton
        };
        String[][] values = new String[6][2];

        values[0][0] = "fxml/iterated.fxml";
        values[0][1] = "Iterated Prisoner's Dilemma Concepts";

        values[1][0] = "fxml/axelrod.fxml";
        values[1][1] = "Axelrod's Tournament";

        values[2][0] = "fxml/strategies.fxml";
        values[2][1] = "Strategies";

        values[3][0] = "fxml/custom_strategies.fxml";
        values[3][1] = "Custom Strategies";

        values[4][0] = "fxml/tournament.fxml";
        values[4][1] = "Tournament";

        values[5][0] = "fxml/analysis.fxml";
        values[5][1] = "Analysis";

        int count = 0;
        for (String[] v : values) {
            nodeHashMap.put(keys[count++], v);
        }

        return nodeHashMap;
    }
}
