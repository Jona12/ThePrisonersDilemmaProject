package gui.event_handlers;

import gui.controllers.ControllerAnalysis;
import gui.controllers.ControllerCustomMode;
import gui.controllers.ControllerCustomStrategies;
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
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import main.*;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by dbrisingr on 09/02/2017.
 */
public class CustomEventHandler implements EventHandler {

    HashMap<Node, Object[]> nodeHashMap;
    private Observables observables;

    private boolean isLauncher;

    private final static String select_original = "Select Original";
    private final static String deselect_original = "Deselect Original";
    private final static String select_all = "Select All";
    private final static String deselect_all = "Deselect All";
    private final static String run_simulation = "Run Simulation";
    private final static String stop_simulation = "Stop Simulation";
    private final static String analysisText = "Go To Analysis";
    private final static String save = "Save Result";

    private Task task;

    public CustomEventHandler(HashMap<Node, Object[]> nodeHashMap, Observables observables) {
        isLauncher = true;
        this.nodeHashMap = nodeHashMap;
        this.observables = observables;
    }

    public CustomEventHandler(HashMap<Node, Object[]> nodeHashMap) {
        isLauncher = true;
        this.nodeHashMap = nodeHashMap;
    }

    // for non-launcher buttons
    public CustomEventHandler(Observables observables) {
        isLauncher = false;
        this.observables = observables;
    }

    private Thread thread;
    private Tournament tournament;
    private Button simulateButton;
    private Analysis analysis;

    private String result;

    @Override
    public void handle(Event event) {

        String text = ((Button) event.getSource()).getText();
        if (isLauncher) {

            String temp = (String) nodeHashMap.get(event.getSource())[0];
            if (temp.equals("fxml/analysis.fxml")) {
                result = handleLoad();
                if (result != null) {
                    handleWindowLaunches(event);
                }
            } else {
                handleWindowLaunches(event);
            }
        } else {
            Button button = (Button) event.getSource();
            if (text.equals(select_original) || text.equals(deselect_original)) {
                handleOriginalSelection(button);
            } else if (text.equals(select_all) || text.equals(deselect_all)) {
                handleSelection(button);
            } else if (text.equals(run_simulation)) {
                simulateButton = button;
                thread = handleSimulation(button);
            } else if (text.equals(stop_simulation)) {
                if (thread != null && simulateButton != null) {
                    tournament.setCancel(true);
                    task.cancel();
                    simulateButton.setDisable(false);
                }
            } else if (text.equals(save)) {
                handleSave();
            }
        }
    }

    private String handleLoad() {

        LinkedHashSet<String> results = CommonFunctions.loadResultsList();
        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(null, results);

        choiceDialog.setTitle("Result Name Selection");
        choiceDialog.setHeaderText("Please select one of the results below");
        Optional<String> optional = choiceDialog.showAndWait();

        if (optional.isPresent()) {
            String selected = choiceDialog.getSelectedItem();
            return selected;
        }

//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("No Result Selected");
//        alert.setHeaderText(null);
//        alert.setContentText("Please select a result");
//        alert.showAndWait();
        return null;
    }

    private void handleSave() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("File Name Required");
        dialog.setHeaderText("Please enter a name for the tournament results");
        Optional<String> result = dialog.showAndWait();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String title;
        String contentText;

        String path = "src/main/results_data/" + result + ".txt";
        File f = new File(path);
        if ((f.exists() && !f.isDirectory()) || analysis == null) {
            if (f.exists() && !f.isDirectory()) {
                title = "File Name Already In Use";
                contentText = "Please select a different file name";
            } else {
                title = "No Tournament Results Found";
                contentText = "Please run a tournament first";
            }

            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(contentText);
            alert.showAndWait();
        } else {
            try {

                path = "src/main/results_data/" + result + ".txt";
                FileOutputStream fileOutputStream = new FileOutputStream(path);
                ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);

                out.writeObject(analysis.getTournamentLinkedList());
                out.flush();
                fileOutputStream.close();

                path = "src/main/results_data/" + result + "_MATCH_" + ".txt";
                fileOutputStream = new FileOutputStream(path);
                out = new ObjectOutputStream(fileOutputStream);
                out.writeObject(analysis.getTournamentResultArray());
                out.flush();
                fileOutputStream.close();


                if (analysis.getRandomLinkedList() != null) {
                    path = "src/main/results_data/" + result + "_RANDOM_.txt";
                    fileOutputStream = new FileOutputStream(path);
                    out = new ObjectOutputStream(fileOutputStream);
                    out.writeObject(analysis.getRandomLinkedList());
                    out.flush();
                    fileOutputStream.close();
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Result saved");
            alert.setHeaderText(null);
            alert.setContentText("The tournament results have been successfully saved");
            alert.showAndWait();
        }


    }

    private void handleWindowLaunches(Event event) {
        Parent root;
        try {
            String window = (String) nodeHashMap.get(event.getSource())[0];
            String title = (String) nodeHashMap.get(event.getSource())[1];

            FXMLLoader loader = new FXMLLoader(getClass().getResource(window));
            root = loader.load();

            Stage stage = new Stage();
            stage.getIcons().add(new Image("file:src/images/logo.png"));
            stage.setTitle("Robin - " + title);

            Scene scene = new Scene(root);
            scene.getStylesheets().add("gui/css/stylesheet.css");

            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                public void handle(KeyEvent ke) {
                    if (ke.getCode() == KeyCode.W && !ke.isControlDown()) {
                        stage.close();
                    }

                }
            });

            stage.setScene(scene);
            if (!window.equals("fxml/edit_custom_mode.fxml") && !window.equals("fxml/strategies.fxml")
                    && !window.contains("concept")) {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                double width = screenSize.getWidth();
                double height = screenSize.getHeight();
                stage.setWidth(width);
                stage.setHeight(height);

                if (window.equals("fxml/analysis.fxml")) {
                    ControllerAnalysis controllerAnalysis = loader.getController();
                    controllerAnalysis.setResult(result);
                    result = null;
                }

            } else {
                if (window.equals("fxml/edit_custom_mode.fxml")) {
                    ControllerCustomMode controller = loader.getController();
                    controller.setObservables(observables);
                } else if (window.equals("fxml/strategies.fxml")) {
                    ControllerCustomStrategies controllerCustomStrategies = loader.getController();
                    controllerCustomStrategies.setObservables(observables);
                }

                stage.setMinWidth(800);
                stage.setMinHeight(500);
                stage.setMaxWidth(800);
                stage.setMaxHeight(500);
            }

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleOriginalSelection(Button button) {
        ObservableList<StrategyData> strategyData = observables.getStrategyData();
        String text = button.getText();
        if (text.equals(select_original)) {
            for (StrategyData s : strategyData) {
                if (s.strategyProperty().getValue().contains("(original)")) {
                    s.setSelect(true);
                }
            }
            button.setText(deselect_original);
        } else {
            for (StrategyData s : strategyData) {
                if (s.strategyProperty().getValue().contains("(original)")) {
                    s.setSelect(false);
                }
            }
            button.setText(select_original);
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

    private Thread handleSimulation(Button button) {

        Thread thread = null;
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
        } else if (!observables.getMode().equals("") && strategyArrayList.size() != 0) {
            button.setDisable(true);
            // RUN AND EXECUTE TOURNAMENT
            tournament = new Tournament(strategyArrayList, observables.getMode());
            task = new Task() {
                @Override
                protected Object call() throws Exception {

                    tournament.executeMatches();

                    if (isCancelled()) {
                        return null;
                    }
                    HashMap<String, HashMap<Object, Object>> modeHashMap = TournamentMode.getModesHashMap();
                    if ((boolean) modeHashMap.get(observables.getMode()).get(Variables.RANDOM)) {
                        analysis = new Analysis(tournament.getTournamentLinkedList(), tournament.getTournamentResultArray(), tournament.getRandomLinkedList());
                    } else {
                        analysis = new Analysis(tournament.getTournamentResultArray(), tournament.getTournamentLinkedList());
                    }
                    HashMap<String, Integer> hashMap = analysis.fetchAverageScores();

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            ObservableList<RankData> rankData = observables.getRankData();
                            rankData.clear();

                            int counter = 1;
                            for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
                                rankData.add(new RankData(counter++, entry.getKey(), entry.getValue()));
                            }

//                            ObservableList<PieChart.Data> pieData = observables.getPieChartData();
//                            pieData.clear();
                            ArrayList<String> strategyNames = new ArrayList<>();
                            ArrayList<Integer> integerArrayList = new ArrayList<>();

                            for (Map.Entry<String, Integer> entry : analysis.fetchTournamentScores(true,
                                    false, false).entrySet()) {
                                strategyNames.add(entry.getKey());
                                integerArrayList.add(entry.getValue());
                            }

                            XYChart.Series series = new XYChart.Series();
                            for (int i = 0; i < integerArrayList.size(); i++) {
                                series.getData().add(new XYChart.Data<>(strategyNames.get(i), integerArrayList.get(i)));
                            }
                            ObservableList<XYChart.Series> graphData = observables.getGraphData();
                            graphData.clear();
                            graphData.add(series);
                        }
                    });
                    button.setDisable(false);
                    return null;
                }
            };
            thread = new Thread(task);
            thread.start();
        }
        return thread;
    }
}
