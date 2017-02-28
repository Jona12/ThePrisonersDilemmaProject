package gui.data_structures;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import main.Tournament;

/**
 * Created by dbrisingr on 28/02/2017.
 */
public class Observables {


    String mode = "";
    ObservableList<ModeData> modeData = FXCollections.observableArrayList();
    ObservableList<RankData> rankData = FXCollections.observableArrayList();
    ObservableList<StrategyData> strategyData = FXCollections.observableArrayList();
    ObservableList<String> originalModesData = FXCollections.observableArrayList(Tournament.TournamentMode.getOriginalModes());
    ObservableList<XYChart.Series> graphData = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

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

    public ObservableList<PieChart.Data> getPieChartData() {
        return pieChartData;
    }

    public void setPieChartData(ObservableList<PieChart.Data> pieChartData) {
        this.pieChartData = pieChartData;
    }

}
