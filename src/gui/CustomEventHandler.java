package gui;

import com.sun.javafx.tk.Toolkit;
import gui.data_structures.Observables;
import gui.data_structures.RankData;
import gui.data_structures.StrategyData;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.Analysis;
import main.History;
import main.Tournament;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dbrisingr on 09/02/2017.
 */
public class CustomEventHandler implements EventHandler {

    HashMap<Node, Object[]> nodeHashMap;
    private Observables observables;
    boolean isLauncher;
    private static String select_all = "Select All";
    private static String deselect_all = "Deselect All";
    private static String run_simulation = "Run Simulation";
    private static String stop_simulation = "Stop Simulation";

    public CustomEventHandler(HashMap<Node, Object[]> nodeHashMap) {
        isLauncher = true;
        this.nodeHashMap = nodeHashMap;
    }

    // for non-launcher buttons
    public CustomEventHandler(String text, Observables observables) {
        isLauncher = false;
        this.observables = observables;
    }

    @Override
    public void handle(Event event) {

        String text = ((Button) event.getSource()).getText();
        if (isLauncher) {
            handleWindowLaunches(event);
        } else {
            if (text.equals(select_all) || text.equals(deselect_all)) {
                Button button = (Button) event.getSource();
                handleSelection(button);
            } else if (text.equals(run_simulation)) {
                handleSimulation();
            } else if (text.equals(stop_simulation)) {

            }
        }
    }

    private void handleWindowLaunches(Event event) {
        Parent root;
        try {
            String window = (String) nodeHashMap.get(event.getSource())[0];
            System.out.println("window: " + window);
            String title = (String) nodeHashMap.get(event.getSource())[1];
            System.out.println("title: " + title);
            root = FXMLLoader.load(getClass().getResource(window));
            Stage stage = new Stage();
            stage.setTitle("Iterated Prisoner's Dilemma - " + title);
            Scene scene = new Scene(root);
            File f = new File("src/gui/css/alignment.css");
            scene.getStylesheets().add(f.toURI().toURL().toExternalForm());
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
            // Hide this current window (if this is what you want)
//                    ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSelection(Button button) {
        ObservableList<StrategyData> strategyData = observables.getStrategyData();

        String text = button.getText();

        if (text.equals(select_all)) {
            for (StrategyData s : strategyData) {
                s.setSelect(true);
            }
            button.setText(deselect_all);
        } else {
            for (StrategyData s : strategyData) {
                s.setSelect(false);
            }
            button.setText(select_all);
        }
    }

    private void handleSimulation() {
        // GET ALL THE STRATEGIES
        ArrayList<String> strategyArrayList = new ArrayList<>();
        ObservableList<StrategyData> strategyData = observables.getStrategyData();
        for (StrategyData s : strategyData) {
            if (s.selectProperty().getValue()) {
                strategyArrayList.add(s.strategyProperty().getValue());
            }
        }

        // CHECK IF A MODE IS SELECTED
        if (observables.getMode().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mode not selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a mode first");
            alert.showAndWait();
        }
        if (strategyArrayList.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Not enough strategies selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select enough strategies and try again");
            alert.showAndWait();
        } else {
            // RUN AND EXECUTE TOURNAMENT
            Tournament tournament = new Tournament(strategyArrayList, observables.getMode());
            Task task = new Task() {
                @Override
                protected Object call() throws Exception {

                    tournament.executeMatches();

                    HashMap<String, History> tempHashMap = tournament.getHistoryHashMap();
                    Analysis analysis = new Analysis(tempHashMap);
                    HashMap<String, Integer> hashMap = analysis.fetchTournamentScores(true, false, false);


                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            ObservableList<RankData> rankData = observables.getRankData();
                            rankData.clear();

                            int counter = 1;
                            for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
                                rankData.add(new RankData(counter++, entry.getKey(), entry.getValue()));
                            }

                            ObservableList<PieChart.Data> pieData = observables.getPieChartData();
                            pieData.clear();
                            for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
                                pieData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
                            }
                        }
                    });


                    // GRAPH DATA
//                            ObservableList<XYChart.Series> graphData = observables.getGraphData();
//                            graphData.clear();
//
//
//                            for (Map.Entry<String, History> entry : tempHashMap.entrySet()) {
//                                int[] averageScore = entry.getValue().calculateAverageRoundScores();
//                                XYChart.Series series = new XYChart.Series();
//                                series.setName(entry.getKey());
//                                for (int i = 0; i < averageScore.length; i++) {
//                                    series.getData().add(new XYChart.Data<>(i + 1, averageScore[i]));
//                                }
//                                graphData.add(series);
//                            }


                    return null;
                }
            };
            new Thread(task).start();
        }
    }
}
