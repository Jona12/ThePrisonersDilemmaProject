package gui;

import gui.data_structures.ModeData;
import gui.data_structures.RankData;
import gui.data_structures.StrategyData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.Tournament;
import main.Variables;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
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
    private Label tournament_tournamentEntriesSelection_selectMoreLabel;
    @FXML
    private TableView tournament_tournamentEntriesSelection_tableView;
    @FXML
    private TableColumn tournament_tournamentEntriesSelection_tableView_strategyColumn;
    @FXML
    private TableColumn tournament_tournamentEntriesSelection_tableView_selectColumn;
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
    private LineChart tournament_payoffGraph;
    @FXML
    private Button tournament_GoToAnalysisButton;
    @FXML
    private Button tournament_saveResultButton;

    private Object[] objects = {
            tournamentAccordion, tournament_modeSelection, tournament_modeSelection_listView,
            tournament_modeSelection_editButton,
            tournament_modeSelection_selectedModeSettings_tableView,
            tournament_modeSelection_selectedModeSettings_tableView_variablesColumn,
            tournament_modeSelection_selectedModeSettings_tableView_valuesColumn, tournament_tournamentEntriesSelection,
            tournament_tournamentEntriesSelection_selectMoreLabel, tournament_tournamentEntriesSelection_tableView,
            tournament_tournamentEntriesSelection_tableView_strategyColumn,
            tournament_tournamentEntriesSelection_tableView_selectColumn, tournament_runSimulationButton,
            tournament_stopSimulationButton, resultsAccordion, tournament_rankTable, tournament_rankTable_tableView,
            tournament_rankTable_tableView_rankColumn, tournament_rankTable_tableView_entryColumn,
            tournament_rankTable_tableView_scoreColumn, tournament_payoff, tournament_payoffGraph,
            tournament_GoToAnalysisButton,
            tournament_saveResultButton
    };

    private Observables observables;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        observables = new Observables();
        CustomEventHandler customEventHandlerLauncher = new CustomEventHandler(getNodeHashMap());
        CustomEventHandler customEventHandlerSimulate = new CustomEventHandler("run_simulation", observables);

        tournamentAccordion.setExpandedPane(tournament_modeSelection);
//        tournament_modeSelection.setAnimated(false);
//        tournament_tournamentEntriesSelection.setAnimated(false);
        resultsAccordion.setExpandedPane(tournament_rankTable);
//        tournament_rankTable.setAnimated(false);
//        tournament_payoff.setAnimated(false);

        tournament_runSimulationButton.setOnAction(customEventHandlerSimulate);

        setListViews();
        setSelectedModeColumns();
        setTournamentEntriesColumns();
        setRankColumns();
        setPayoffGraph();
    }

    private void setListViews() {

        tournament_modeSelection_listView.setItems(observables.getOriginalModesData());
        tournament_modeSelection_listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                HashMap<String, HashMap<String, Object>> modeHashMap = Tournament.TournamentMode.getModesHashMap();

                int[] scoreMatrix = (int[]) modeHashMap.get(newValue).get(Variables.SCORE_MATRIX);
                String finalMatrix = "WIN: " + scoreMatrix[0] + "\r\n" + "LOSE: " + scoreMatrix[1] +
                        "\r\n" + "DRAW_C: " + scoreMatrix[2] + "\r\n" + "DRAW_D: " + scoreMatrix[3];

                int numberOfRounds = (int) modeHashMap.get(newValue).get(Variables.ROUNDS);
                boolean repeat = (boolean) modeHashMap.get(newValue).get(Variables.REPEAT);
                boolean twin = (boolean) modeHashMap.get(newValue).get(Variables.TWIN);
                boolean random = (boolean) modeHashMap.get(newValue).get(Variables.RANDOM);

                ObservableList<ModeData> modeData = observables.getModeData();
                modeData.clear();

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
        ArrayList<String> strategies = getStrategies();

        for (String s : strategies) {
            strategyData.add(new StrategyData(s.substring(0, s.indexOf(".java")), false));
        }

        tournament_tournamentEntriesSelection_tableView.setItems(observables.getStrategyData());
    }

    private void setRankColumns() {

        tournament_rankTable_tableView_entryColumn.setPrefWidth(300);

        tournament_rankTable_tableView_rankColumn.setCellValueFactory(new PropertyValueFactory<RankData, Integer>("rank"));
        tournament_rankTable_tableView_entryColumn.setCellValueFactory(new PropertyValueFactory<RankData, String>("entry"));
        tournament_rankTable_tableView_scoreColumn.setCellValueFactory(new PropertyValueFactory<RankData, Integer>("score"));
        tournament_rankTable_tableView.setItems(observables.getRankData());

    }

    private void setPayoffGraph() {

        tournament_payoffGraph.setCreateSymbols(false);
        tournament_payoffGraph.getXAxis().setLabel("Rounds");
        tournament_payoffGraph.getYAxis().setLabel("Average Payoff");
        tournament_payoffGraph.setData(observables.getGraphData());
        NumberAxis x = (NumberAxis) tournament_payoffGraph.getXAxis();
        x.setTickUnit(100);

    }

    private ArrayList<String> getStrategies() {
        File currentDir = new File("."); // Read current file location
        File srcDir;
        File strategiesDir;
        ArrayList<String> strategies = new ArrayList<>();
        try {
            srcDir = new File(currentDir.getCanonicalFile(), "src"); // Construct the target directory file with the right parent directory
            strategiesDir = new File(srcDir, "strategies");

            DirectoryStream<Path> dirStream = Files.newDirectoryStream(strategiesDir.toPath());
            for (Path p : dirStream) {
                if (!p.toFile().isDirectory()) {
                    strategies.add(p.getFileName().toString());
                }
//            if (p.toFile().isDirectory()) {
//                listDirectoryAndFiles(p);
//            }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strategies;
    }

//    private HashMap<String, Object> getStringHashMap() {
//        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
//        ArrayList<String> stringArrayList = new ArrayList<>(Arrays.asList(objects.toString()));
//        int count = 0;
//        for (String s : stringArrayList) {
//            stringObjectHashMap.put(s, objects[count++]);
//        }
//        return stringObjectHashMap;
//    }

    private HashMap<Node, Object[]> getNodeHashMap() {

        HashMap<Node, Object[]> nodeHashMap = new HashMap<>();

        Node[] keys = {
                tournament_runSimulationButton, tournament_stopSimulationButton, tournament_GoToAnalysisButton, tournament_saveResultButton
        };
        Object[][] values = new String[4][2];

        //tournament_runSimulationButton
        values[0][0] = "tournament_rankTable_tableView";
        values[0][1] = "tournament_rankTable_tableView_rankColumn";

        //tournament_stopSimulationButton
        values[1][0] = "fxml/analysis.fxml";
        values[1][1] = "Analysis";

        //tournament_GoToAnalysisButton
        values[2][0] = "fxml/analysis.fxml";
        values[2][1] = "Analysis";

        //tournament_saveResultButton
        values[3][0] = "fxml/analysis.fxml";
        values[3][1] = "Analysis";

        int count = 0;
        for (Object[] v : values) {
            nodeHashMap.put(keys[count++], v);
        }

        return nodeHashMap;
    }

    public class Observables {

        String mode = "";
        ObservableList<ModeData> modeData = FXCollections.observableArrayList();
        ObservableList<RankData> rankData = FXCollections.observableArrayList();
        ObservableList<StrategyData> strategyData = FXCollections.observableArrayList();
        ObservableList<String> originalModesData = FXCollections.observableArrayList(Tournament.TournamentMode.getOriginalModes());
        ObservableList<XYChart.Series> graphData = FXCollections.observableArrayList();

        public Observables() {
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode1) {
            mode = mode1;
        }

        public void setModeData(ObservableList<ModeData> modeData1) {
            modeData = modeData1;
        }

        public ObservableList<ModeData> getModeData() {
            return modeData;
        }

        public void setRankData(ObservableList<RankData> rankData1) {
            rankData = rankData1;
        }

        public ObservableList<RankData> getRankData() {
            return rankData;
        }

        public void setStrategyData(ObservableList<StrategyData> strategyData1) {
            strategyData = strategyData1;
        }

        public ObservableList<StrategyData> getStrategyData() {
            return strategyData;
        }

        public void setOriginalModesData(ObservableList<String> originalModesData1) {
            originalModesData = originalModesData1;
        }

        public ObservableList<String> getOriginalModesData() {
            return originalModesData;
        }

        public void setGraphData(ObservableList<XYChart.Series> graphData1) {
            graphData = graphData1;
        }

        public ObservableList<XYChart.Series> getGraphData() {
            return graphData;
        }
    }
}
