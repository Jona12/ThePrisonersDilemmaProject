package gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by dbrisingr on 12/03/2017.
 */
public class ControllerAnalysis implements Initializable {
    @FXML
    private Accordion accordion;
    @FXML
    private TitledPane titled_match;
    @FXML
    private TitledPane titled_strategy;
    @FXML
    private TitledPane titled_analysis;
    @FXML
    private Label match_high;
    @FXML
    private Label match_low;
    @FXML
    private Label match_average;
    @FXML
    private Label strategy_high;
    @FXML
    private Label strategy_low;
    @FXML
    private Label strategy_average;
    @FXML
    private Label analysis_nice;
    @FXML
    private Label analysis_forgive;
    @FXML
    private Label analysis_king;
    @FXML
    private StackedBarChart match_graph;
    @FXML
    private StackedBarChart strategy_graph;
    @FXML
    private TableView analysis_table;
    @FXML
    private TableColumn rank;
    @FXML
    private TableColumn entry;
    @FXML
    private TableColumn score;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accordion.setExpandedPane(titled_match);

    }

    private void calculateMatchData(){}
    private void calculateMatchGraph(){}

    private void calculateStrategyData(){}
    private void calculateStrategyGraph(){}

    private void calculateAnalysisData(){}
    private void calculateAnalysisGraph(){}
}
