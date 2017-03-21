package gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import main.Analysis;
import main.CommonFunctions;
import main.History;
import main.Variables;

import java.net.URL;
import java.util.*;

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
    private BarChart strategy_graph;
    @FXML
    private TableView analysis_table;
    @FXML
    private TableColumn rank;
    @FXML
    private TableColumn entry;
    @FXML
    private TableColumn score;

    private String result;
    private Analysis analysis;

    private LinkedList<HashMap<String, History>> tournamentLinkedList;
    private HashMap<String, int[]> tournamentResult;
    private LinkedList<History> randomLinkedList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titled_match.setAnimated(true);
        titled_strategy.setAnimated(true);
        titled_analysis.setAnimated(true);

        accordion.setExpandedPane(titled_match);
    }

    public void setResult(String result) {
        this.result = result;
        setupView();
        calculateMatchData();
        calculateStrategyData();
    }

    private void setupView() {
        ArrayList<Object> arrayList = CommonFunctions.loadResult(result);

        tournamentLinkedList = (LinkedList<HashMap<String, History>>) arrayList.get(0);
        tournamentResult = (HashMap<String, int[]>) arrayList.get(1);
        randomLinkedList = (LinkedList<History>) arrayList.get(2);

        analysis = new Analysis(tournamentLinkedList, tournamentResult, randomLinkedList);
    }

    private void calculateMatchData() {
        setMatch(calculateMatch(true), true);
        setMatch(calculateMatch(false), false);
        calculateMatchAverage();
        calculateMatchGraph();

    }

    private String[] calculateMatch(boolean high) {
        String matchID = "";

        int score;
        if (high) {
            score = 0;
        } else {
            score = 99999999;
        }

        int score1;
        int score2;

        for (Map.Entry<String, int[]> entry : tournamentResult.entrySet()) {
            int temp = 0;
            int[] current = entry.getValue();
            for (int i : current) {
                temp += i;
            }
            if (high && temp > score) {
                score = temp;
                matchID = entry.getKey();
            } else if (!high && temp < score) {
                score = temp;
                matchID = entry.getKey();
            }
        }

        String[] strings = fixMatchStrings(matchID);

        int[] scores = tournamentResult.get(matchID);
        score1 = scores[0];
        score2 = scores[1];

        String[] objects = {
                matchID.substring(0, matchID.indexOf("_")), Integer.toString(score), strings[0], Integer.toString(score1),
                strings[1], Integer.toString(score2)
        };
        return objects;

    }

    private void setMatch(String[] strings, boolean high) {

        String temp;
        if (high) {
            temp = match_high.getText();
        } else {
            temp = match_low.getText();
        }

        temp = temp.replace("[matchID]", strings[0]);
        temp = temp.replace("[score]", strings[1]);
        temp = temp.replace("[strategy1]", strings[2]);
        temp = temp.replace("[score1]", strings[3]);
        temp = temp.replace("[strategy2]", strings[4]);
        temp = temp.replace("[score2]", strings[5]);

        if (high) {
            match_high.setText(temp);
        } else {
            match_low.setText(temp);
        }
    }

    private void calculateMatchAverage() {

        int count = 0;
        int sum = 0;
        for (Map.Entry<String, int[]> entry : tournamentResult.entrySet()) {
            int[] current = entry.getValue();
            for (int i : current) {
                sum += i;
            }
            count++;
        }
        int score = sum / count;

        String average = match_average.getText();
        average = average.replace("[score]", Integer.toString(score));
        match_average.setText(average);
    }

    private String[] fixMatchStrings(String matchID) {
        String[] strings = new String[2];
        String strategy1, strategy2;

        if (matchID.contains("_vs._")) {
            strategy1 = matchID.substring(matchID.indexOf("_") + 1, matchID.indexOf("_vs._"));
            strategy2 = matchID.substring(matchID.indexOf("._") + 2);
        } else if (matchID.contains("_RAND")) {
            strategy1 = matchID.substring(matchID.indexOf("_") + 1, matchID.indexOf("_RAND"));
            strategy2 = "RANDOM";
        } else {
            strategy1 = matchID.substring(matchID.indexOf("_") + 1, matchID.indexOf("_TWIN"));
            strategy2 = strategy1;
        }
        strings[0] = strategy1;
        strings[1] = strategy2;

        return strings;
    }

    private void calculateMatchGraph() {

        ArrayList<String> stringArrayList = new ArrayList<>();
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        ArrayList<Integer> integerArrayList2 = new ArrayList<>();

        for (Map.Entry<String, int[]> entry : tournamentResult.entrySet()) {

            String toAdd = entry.getKey().substring(0, entry.getKey().indexOf("_"));
            stringArrayList.add(toAdd);

            integerArrayList.add(entry.getValue()[0]);
            integerArrayList2.add(entry.getValue()[1]);

        }

        XYChart.Series series = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();

        match_graph.setTitle("Match Scores Sample");
        match_graph.getXAxis().setLabel("Match Number");
        match_graph.getYAxis().setLabel("Overall Score");
        match_graph.setAnimated(true);
        series.setName("Strategy 1 Score");
        series2.setName("Strategy 2 Score");

        for (int i = 0; i < 10; i++) {
            series.getData().add(new XYChart.Data<>(stringArrayList.get(i), integerArrayList.get(i)));
            series2.getData().add(new XYChart.Data<>(stringArrayList.get(i), integerArrayList2.get(i)));
        }
        match_graph.getData().clear();
        match_graph.getData().addAll(series, series2);
    }

    private void calculateStrategyData() {
        calculateStrategy(true);
        calculateStrategyAverage(calculateStrategy(false));
        calculateStrategyGraph();

    }

    private int calculateStrategy(boolean high) {

        String s;
        int matchScore1;
        if (high) {
            matchScore1 = 0;
            s = strategy_high.getText();
        } else {
            matchScore1 = 999999;
            s = strategy_low.getText();
        }

        HashMap<String, Integer> hashMap = analysis.fetchTournamentScores(true,
                false, false);

        String strategy1 = "";

        int score1 = 0;

        int count = 0;

        int strategySum = 0;

        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            if (high && count == 0) {
                strategy1 = entry.getKey();
                score1 = entry.getValue();
            } else if (!high && count == hashMap.size() - 1) {
                strategy1 = entry.getKey();
                score1 = entry.getValue();
            }
            strategySum += entry.getValue();
            count++;
        }

        int score = strategySum / count;


        LinkedList<HashMap<String, History>> linkedList = analysis.getTournamentLinkedList();
        int defect = 0;
        int cooperate = 0;
        for (HashMap<String, History> historyHashMap : linkedList) {
            History h = historyHashMap.get(strategy1);
            HashMap<String, int[][]> stringHashMap = h.getSelfMatchResults();
            for (int[][] entry : stringHashMap.values()) {
                String[][] strings = Variables.calculateMatchScoreString(entry);
                for (String[] array : strings) {
                    for (String string : array) {
                        if (string.equals(Variables.COOPERATE)) {
                            cooperate++;
                        } else {
                            defect++;
                        }
                    }
                }
            }
        }

        String outcome1, outcome2;
        if (cooperate >= defect) {
            outcome1 = "cooperates";
            outcome2 = "defects";
        } else {
            outcome1 = "defects";
            outcome2 = "cooperates";
        }

        String matchID = "";
        int matchScore2 = 0;
        for (Map.Entry<String, int[]> entry : tournamentResult.entrySet()) {
            if (entry.getKey().contains(strategy1)) {
                if (high) {
                    if (entry.getValue()[0] > matchScore1) {
                        matchID = entry.getKey().substring(0, entry.getKey().indexOf("_"));
                        matchScore1 = entry.getValue()[0];
                        matchScore2 = entry.getValue()[1];
                    }
                } else {
                    if (entry.getValue()[0] < matchScore1) {
                        matchID = entry.getKey().substring(0, entry.getKey().indexOf("_"));
                        matchScore1 = entry.getValue()[0];
                        matchScore2 = entry.getValue()[1];
                    }
                }

            }
        }


        s = s.replace("[strategy1]", strategy1);
        s = s.replace("[score1]", Integer.toString(score1));
        s = s.replace("[outcome1]", outcome1);
        s = s.replace("[outcome2]", outcome2);
        s = s.replace("[matchID]", matchID);
        s = s.replace("[matchScore1]", Integer.toString(matchScore1));
        s = s.replace("[matchScore2]", Integer.toString(matchScore2));

        if (high) {
            strategy_high.setText(s);
        } else {
            strategy_low.setText(s);
        }
        return score;
    }

    private void calculateStrategyAverage(int average) {
        strategy_average.setText(strategy_average.getText().replace("[score]", Integer.toString(average)));
    }

    private void calculateStrategyGraph() {

        ArrayList<String> strategyNames = new ArrayList<>();
        ArrayList<Integer> integerArrayList = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : analysis.fetchTournamentScores(true,
                false, false).entrySet()) {
            strategyNames.add(entry.getKey());
            integerArrayList.add(entry.getValue());
        }

        XYChart.Series series = new XYChart.Series();

        strategy_graph.setTitle("Strategy Scores");
        strategy_graph.getXAxis().setLabel("Strategy Names");
        strategy_graph.getYAxis().setLabel("Overall Score");
        strategy_graph.setLegendVisible(false);
        strategy_graph.setAnimated(true);

        for (int i = 0; i < integerArrayList.size(); i++) {
            series.getData().add(new XYChart.Data<>(strategyNames.get(i), integerArrayList.get(i)));
        }
        strategy_graph.getData().clear();
        strategy_graph.getData().add(series);

    }


    private void calculateAnalysisNice() {
    }

    private void calculateAnalysisForgive() {
    }

    private void calculateAnalysisKing() {
    }

    private void calculateAnalysisGraph() {
    }
}
