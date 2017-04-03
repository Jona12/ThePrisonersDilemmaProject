package gui.controllers;

import gui.data_structures.MatchData;
import gui.data_structures.RankData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.*;

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
    //    @FXML
//    private StackedBarChart match_graph;
    @FXML
    private TableView match_graph;
    @FXML
    private TableColumn matchID;
    @FXML
    private TableColumn overallScore;
    @FXML
    private TableColumn score1;
    @FXML
    private TableColumn score2;

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
    private ArrayList<LinkedHashMap<String, int[]>> tournamentResultArray;
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

        Variables.setScoreMatrix(new int[]{5, 0, 3, 1});
        calculateAnalysisData();
//        Variables.setScoreMatrix(null);
    }

    private void setupView() {
        ArrayList<Object> arrayList = CommonFunctions.loadResult(result);

        tournamentLinkedList = (LinkedList<HashMap<String, History>>) arrayList.get(0);
        tournamentResultArray = (ArrayList<LinkedHashMap<String, int[]>>) arrayList.get(1);
        if(arrayList.size() == 3){
            randomLinkedList = (LinkedList<History>) arrayList.get(2);
        }

        analysis = new Analysis(tournamentLinkedList, tournamentResultArray, randomLinkedList);
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

        for (Map.Entry<String, int[]> entry : tournamentResultArray.get(0).entrySet()) {
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

        String[] strings = Analysis.fixMatchStrings(matchID);

        int[] scores = tournamentResultArray.get(0).get(matchID);
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
        for (Map.Entry<String, int[]> entry : tournamentResultArray.get(0).entrySet()) {
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

    private void calculateMatchGraph() {

//        ArrayList<String> stringArrayList = new ArrayList<>();
//        ArrayList<Integer> integerArrayList = new ArrayList<>();
//        ArrayList<Integer> integerArrayList2 = new ArrayList<>();
//
//        for (Map.Entry<String, int[]> entry : tournamentResultArray.get(0).entrySet()) {
//
//            String toAdd = entry.getKey().substring(0, entry.getKey().indexOf("_"));
//            stringArrayList.add(toAdd);
//
//            integerArrayList.add(entry.getValue()[0]);
//            integerArrayList2.add(entry.getValue()[1]);
//
//        }

        ObservableList<MatchData> matchData = FXCollections.observableArrayList();
//        int counter = 1;
//        HashMap<String, Integer> hashMap = analysis.fetchTournamentScores(true, false, false);
//        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
//            matchData.add(new MatchData(counter++, entry.getKey(), entry.getValue()));
//        }
        for (HashMap<String, int[]> integerHashMap : tournamentResultArray) {
            for (Map.Entry<String, int[]> entry : integerHashMap.entrySet()) {
                int[] array = entry.getValue();
                int overall = array[0] + array[1];
                matchData.add(new MatchData(entry.getKey(), overall, array[0], array[1]));
            }
        }

        matchID.setPrefWidth(450);
        overallScore.setPrefWidth(100);
        score1.setPrefWidth(100);
        score2.setPrefWidth(100);

        matchID.setCellValueFactory(new PropertyValueFactory<MatchData, String>("matchID"));
        overallScore.setCellValueFactory(new PropertyValueFactory<MatchData, String>("overallScore"));
        score1.setCellValueFactory(new PropertyValueFactory<Match, Integer>("score1"));
        score2.setCellValueFactory(new PropertyValueFactory<Match, Integer>("score2"));
        match_graph.setItems(matchData);

//        for (int i = 0; i < 10; i++) {
//            series.getData().add(new XYChart.Data<>(stringArrayList.get(i), integerArrayList.get(i)));
//            series2.getData().add(new XYChart.Data<>(stringArrayList.get(i), integerArrayList2.get(i)));
//        }
//        match_graph.getData().clear();
//        match_graph.getData().addAll(series, series2);
    }

    private void calculateStrategyData() {
        calculateStrategy(true);
        calculateStrategyAverage(calculateStrategy(false));
        calculateStrategyGraph();

    }

    private int calculateStrategy(boolean high) {

        String s;
        int matchScore1 = 0;
        if (high) {
            s = strategy_high.getText();
        } else {
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


        LinkedList<HashMap<String, History>> linkedList = tournamentLinkedList;
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

        String strategy2 = "";
        String matchID = "";
        int matchScore2 = 0;
        for (Map.Entry<String, int[]> entry : tournamentResultArray.get(0).entrySet()) {
            if (entry.getKey().contains(strategy1)) {
                if (entry.getValue()[0] > matchScore1) {
                    matchID = entry.getKey().substring(0, entry.getKey().indexOf("_"));
                    matchScore1 = entry.getValue()[0];
                    matchScore2 = entry.getValue()[1];
                }
                if (matchID.contains("_vs._")) {
                    strategy2 = matchID.substring(matchID.indexOf("._") + 2);
                } else if (matchID.contains("_RAND")) {
                    strategy2 = "RANDOM";
                } else {
                    strategy2 = strategy1;
                }
            }
        }


        s = s.replace("[strategy1]", strategy1);
        s = s.replace("[strategy2]", strategy2);
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

    private void calculateAnalysisData() {
        calculateAnalysisNice();
        calculateAnalysisKing();
        calculateAnalysisGraph();
    }

    private void calculateAnalysisNice() {


        int strategyAmount = tournamentLinkedList.getFirst().size() + 1;
        int niceAmount = 0;
        for (HashMap<String, History> hashMap : tournamentLinkedList) {
            for (Map.Entry<String, History> entry : hashMap.entrySet()) {
                HashMap<String, int[][]> stringHashMap = entry.getValue().getSelfMatchResults();
                loop:
                for (Map.Entry<String, int[][]> stringEntry : stringHashMap.entrySet()) {
                    String[][] temp = Variables.calculateMatchScoreString(stringEntry.getValue());
                    for (int i = 0; i < temp.length; i++) {
                        if (temp[i][0].equals(Variables.DEFECT)) {
                            break loop;
                        } else if (temp[i][0].equals(Variables.COOPERATE)
                                && temp[i][1].equals(Variables.DEFECT)) {
                            niceAmount++;
                            break loop;
                        }
                    }
                }
            }
        }

        String s = analysis_nice.getText();
        s = s.replace("[strategyAmount]", Integer.toString(strategyAmount));
        s = s.replace("[niceAmount]", Integer.toString(niceAmount));
        analysis_nice.setText(s);
    }

    private void calculateAnalysisKing() {

        ArrayList<String> highStrategyNames = new ArrayList<>();
        ArrayList<String> lowStrategyNames = new ArrayList<>();
        HashMap<String, Integer> hashMap = analysis.fetchTournamentScores(true, false, false);
        int count = 0;
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            if (count < 3) {
                highStrategyNames.add(entry.getKey());
            }
            if (count++ > hashMap.size() - 5) {
                if (!entry.getKey().equals("RANDOM")) {
                    lowStrategyNames.add(entry.getKey());
                }
            }
        }

        HashSet<String> finalList = new HashSet<>();


        HashMap<String, History> historyHashMap = tournamentLinkedList.getFirst();

        String low;
        String high;

        for (int i = 0; i < lowStrategyNames.size(); i++) {
            for (int j = 0; j < highStrategyNames.size(); j++) {

                low = lowStrategyNames.get(i);
                high = highStrategyNames.get(j);

                int[][] array = historyHashMap.get(low).getSelfMatchResults().get(high);
                int lowScore = 0;
                int highScore = 0;

                for (int x = 0; x < array.length; x++) {
                    lowScore += array[x][0];
                    highScore += array[x][1];

                }
                if (lowScore > 0 && highScore / lowScore > 1.8) {
                    finalList.add(low);
                } else if (lowScore == 0) {
                    finalList.add(low);
                }

            }
        }

        String temp = "";
        for (String t : finalList) {
            temp += t + ", ";
        }
        temp = temp.substring(0, temp.length() - 2);
        String s = analysis_king.getText();
        s = s.replace("[kingmakers]", temp);
        analysis_king.setText(s);
    }

    private void calculateAnalysisGraph() {
        ObservableList<RankData> rankData = FXCollections.observableArrayList();
        int counter = 1;
        HashMap<String, Integer> hashMap = analysis.fetchAverageScores();
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            rankData.add(new RankData(counter++, entry.getKey(), entry.getValue()));
        }

        entry.setPrefWidth(250);
        score.setPrefWidth(150);
        rank.setCellValueFactory(new PropertyValueFactory<RankData, Integer>("rank"));
        entry.setCellValueFactory(new PropertyValueFactory<RankData, String>("entry"));
        score.setCellValueFactory(new PropertyValueFactory<RankData, Integer>("score"));
        analysis_table.setItems(rankData);
    }
}
