package gui.controllers;

import gui.data_structures.ModeData;
import gui.data_structures.Observables;
import gui.data_structures.RankData;
import gui.data_structures.StrategyData;
import gui.event_handlers.CustomEventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.CommonFunctions;
import main.TournamentMode;
import main.Variables;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by dbrisingr on 09/02/2017.
 */
public class ControllerTournament implements Initializable {

    @FXML
    private Accordion tournamentAccordion;
    @FXML
    private TitledPane tournament_modeSelection;
    @FXML
    private ListView tournament_modeSelection_listView;
    @FXML
    private Button tournament_modeSelection_editButton;
    @FXML
    private TableView tournament_modeSelection_selectedModeSettings_tableView;
    @FXML
    private TableColumn tournament_modeSelection_selectedModeSettings_tableView_variablesColumn;
    @FXML
    private TableColumn tournament_modeSelection_selectedModeSettings_tableView_valuesColumn;
    @FXML
    private TitledPane tournament_tournamentEntriesSelection;
    @FXML
    private TableView tournament_tournamentEntriesSelection_tableView;
    @FXML
    private TableColumn tournament_tournamentEntriesSelection_tableView_strategyColumn;
    @FXML
    private TableColumn tournament_tournamentEntriesSelection_tableView_selectColumn;
    @FXML
    private Button tournament_tournamentEntriesSelection_selectOriginalButton;
    @FXML
    private Button tournament_tournamentEntriesSelection_editCustomStrategiesButton;
    @FXML
    private Button tournament_tournamentEntriesSelection_selectDeselectButton;
    @FXML
    private Button tournament_runSimulationButton;
    @FXML
    private Button tournament_stopSimulationButton;
    @FXML
    private Accordion resultsAccordion;
    @FXML
    private TitledPane tournament_rankTable;
    @FXML
    private TableView tournament_rankTable_tableView;
    @FXML
    private TableColumn tournament_rankTable_tableView_rankColumn;
    @FXML
    private TableColumn tournament_rankTable_tableView_entryColumn;
    @FXML
    private TableColumn tournament_rankTable_tableView_scoreColumn;
    @FXML
    private TitledPane tournament_payoff;
    @FXML
    private BarChart tournament_payoffGraph;
    //    private LineChart tournament_payoffGraph;
    @FXML
    private Button tournament_GoToAnalysisButton;
    @FXML
    private Button tournament_saveResultButton;

    private Observables observables;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tournamentAccordion.setExpandedPane(tournament_modeSelection);
        resultsAccordion.setExpandedPane(tournament_rankTable);

        setOnAction();
        setListViews();
        setSelectedModeColumns();
        setTournamentEntriesColumns();
        setRankColumns();
        setPayoffGraph();
    }

    private void setOnAction() {
        observables = new Observables();
        CustomEventHandler customEventHandlerLauncher = new CustomEventHandler(getNodeHashMap());
        CustomEventHandler customEventHandler = new CustomEventHandler(observables);
        CustomEventHandler customEventHandler1 = new CustomEventHandler(getNodeHashMap(), observables);

        tournament_modeSelection_editButton.setOnAction(customEventHandler1);
        tournament_tournamentEntriesSelection_editCustomStrategiesButton.setOnAction(customEventHandler1);
        tournament_GoToAnalysisButton.setOnAction(customEventHandlerLauncher);

        tournament_tournamentEntriesSelection_selectOriginalButton.setOnAction(customEventHandler);
        tournament_tournamentEntriesSelection_selectDeselectButton.setOnAction(customEventHandler);
        tournament_runSimulationButton.setOnAction(customEventHandler);
        tournament_stopSimulationButton.setOnAction(customEventHandler);

        tournament_saveResultButton.setOnAction(customEventHandler);
    }

    private void setListViews() {

        tournament_modeSelection_listView.setItems(observables.getOriginalModesData());
        tournament_modeSelection_listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                String finalMatrix;
                int numberOfRounds;
                int repeat;
                boolean twin;
                boolean random;
                ObservableList<ModeData> modeData = observables.getModeData();
                modeData.clear();

                if (newValue.equals(TournamentMode.MODE_CUSTOM)) {
                    HashMap<Object, Object> modeHashMap = TournamentMode.loadCustomModeData();

                    int[] scoreMatrix = (int[]) modeHashMap.get(Variables.SCORE_MATRIX);
                    finalMatrix = "WIN: " + scoreMatrix[0] + "\r\n" + "LOSE: " + scoreMatrix[1] +
                            "\r\n" + "DRAW_C: " + scoreMatrix[2] + "\r\n" + "DRAW_D: " + scoreMatrix[3];

                    numberOfRounds = (int) modeHashMap.get(Variables.ROUNDS);
                    repeat = (int) modeHashMap.get(Variables.REPEAT);
                    twin = (boolean) modeHashMap.get(Variables.TWIN);
                    random = (boolean) modeHashMap.get(Variables.RANDOM);
                } else {
                    HashMap<String, HashMap<Object, Object>> modeHashMap = TournamentMode.getModesHashMap();

                    int[] scoreMatrix = (int[]) modeHashMap.get(newValue).get(Variables.SCORE_MATRIX);
                    finalMatrix = "WIN: " + scoreMatrix[0] + "\r\n" + "LOSE: " + scoreMatrix[1] +
                            "\r\n" + "DRAW_C: " + scoreMatrix[2] + "\r\n" + "DRAW_D: " + scoreMatrix[3];

                    numberOfRounds = (int) modeHashMap.get(newValue).get(Variables.ROUNDS);
                    repeat = (int) modeHashMap.get(newValue).get(Variables.REPEAT);
                    twin = (boolean) modeHashMap.get(newValue).get(Variables.TWIN);
                    random = (boolean) modeHashMap.get(newValue).get(Variables.RANDOM);
                }

                observables.setMode((String) newValue);
                modeData.add(new ModeData(Variables.SCORE_MATRIX, finalMatrix));
                modeData.add(new ModeData(Variables.ROUNDS, "" + numberOfRounds));
                modeData.add(new ModeData(Variables.REPEAT, "" + repeat));
                modeData.add(new ModeData(Variables.TWIN, "" + twin));
                modeData.add(new ModeData(Variables.RANDOM, "" + random));
                observables.setModeData(modeData);
            }
        });
    }

    private void setSelectedModeColumns() {

        tournament_modeSelection_selectedModeSettings_tableView_variablesColumn.setPrefWidth(300);
        tournament_modeSelection_selectedModeSettings_tableView_valuesColumn.setPrefWidth(100);

        tournament_modeSelection_selectedModeSettings_tableView_variablesColumn.setCellValueFactory(new PropertyValueFactory<ModeData, String>("variable"));
        tournament_modeSelection_selectedModeSettings_tableView_valuesColumn.setCellValueFactory(new PropertyValueFactory<ModeData, String>("value"));
        tournament_modeSelection_selectedModeSettings_tableView.setItems(observables.getModeData());
    }

    private void setTournamentEntriesColumns() {

        tournament_tournamentEntriesSelection_tableView.setEditable(true);
        tournament_tournamentEntriesSelection_tableView_selectColumn.setEditable(true);

        tournament_tournamentEntriesSelection_tableView_strategyColumn.setPrefWidth(300);
        tournament_tournamentEntriesSelection_tableView_strategyColumn.setCellValueFactory(new PropertyValueFactory<StrategyData, String>("strategy"));
        tournament_tournamentEntriesSelection_tableView_selectColumn.setCellValueFactory(new PropertyValueFactory<StrategyData, String>("select"));

        tournament_tournamentEntriesSelection_tableView_selectColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new CheckBoxTableCell<StrategyData, Boolean>() {
                    {
                        setEditable(true);
                        setAlignment(Pos.CENTER);
                    }

                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        if (!empty) {
                            TableRow row = getTableRow();

                            if (row != null) {
                                int rowNo = row.getIndex();
                                TableView.TableViewSelectionModel sm = getTableView().getSelectionModel();
                                if (item) {
//                                    if (tournament_tournamentEntriesSelection.isExpanded()) {}
                                    sm.select(rowNo);
                                } else {

                                    sm.clearSelection(rowNo);
                                }
                            }
                        }

                        super.updateItem(item, empty);
                    }
                };

            }
        });

        ObservableList<StrategyData> strategyData = observables.getStrategyData();
        ArrayList<String> strategies = CommonFunctions.getStrategies(true, false);
        strategies.remove("RANDOM (built-in)");

        for (String s : strategies) {
            strategyData.add(new StrategyData(s, false));
        }

        tournament_tournamentEntriesSelection_tableView.setItems(strategyData);
    }

    private void setRankColumns() {

        tournament_rankTable_tableView_entryColumn.setPrefWidth(300);

        tournament_rankTable_tableView_rankColumn.setCellValueFactory(new PropertyValueFactory<RankData, Integer>("rank"));
        tournament_rankTable_tableView_entryColumn.setCellValueFactory(new PropertyValueFactory<RankData, String>("entry"));
        tournament_rankTable_tableView_scoreColumn.setCellValueFactory(new PropertyValueFactory<RankData, Integer>("score"));
        tournament_rankTable_tableView.setItems(observables.getRankData());

    }

    private void setPayoffGraph() {

        tournament_payoffGraph.setTitle("Total Scores of Strategies");
        tournament_payoffGraph.getXAxis().setLabel("Strategy Names");
        tournament_payoffGraph.getYAxis().setLabel("Overall Score");
        tournament_payoffGraph.setLegendVisible(false);
        tournament_payoffGraph.setAnimated(false);
        tournament_payoffGraph.setData(observables.getGraphData());
//        NumberAxis x = (NumberAxis) tournament_payoffGraph.getXAxis();
//        x.setTickUnit(100);

        tournament_payoffGraph.setLegendVisible(false);
        tournament_payoffGraph.setData(observables.getGraphData());

    }

    private HashMap<Node, Object[]> getNodeHashMap() {

        HashMap<Node, Object[]> nodeHashMap = new HashMap<>();

        Node[] keys = {
                tournament_modeSelection_editButton, tournament_tournamentEntriesSelection_editCustomStrategiesButton, tournament_GoToAnalysisButton
        };
        Object[][] values = new String[3][2];

        //tournament_modeSelection_editButton
        values[0][0] = "fxml/edit_custom_mode.fxml";
        values[0][1] = "Mode Customisation";

        //tournament_tournamentEntriesSelection_editCustomStrategiesButton
        values[1][0] = "fxml/strategies.fxml";
        values[1][1] = "Custom Strategies";

        //tournament_GoToAnalysisButton
        values[2][0] = "fxml/analysis.fxml";
        values[2][1] = "Analysis";

        int count = 0;
        for (Object[] v : values) {
            nodeHashMap.put(keys[count++], v);
        }

        return nodeHashMap;
    }
}
