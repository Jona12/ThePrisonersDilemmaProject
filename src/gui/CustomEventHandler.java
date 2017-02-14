package gui;

import gui.data_structures.ModeData;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import main.Tournament;
import main.Variables;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dbrisingr on 09/02/2017.
 */
public class CustomEventHandler implements EventHandler {

    HashMap<Node, Object[]> nodeHashMap;
    String text;

    public CustomEventHandler(HashMap<Node, Object[]> nodeHashMap) {
        this.nodeHashMap = nodeHashMap;
        text ="";
    }

    // for non-launcher buttons
    public CustomEventHandler(String text){
        this.text = text;
    }

    @Override
    public void handle(Event event) {
        Parent root;

        if (text.equals("")) {
            try {
                String window = (String) nodeHashMap.get(event.getSource())[0];
                System.out.println("window: "+window);
                String title = (String) nodeHashMap.get(event.getSource())[1];
                System.out.println("title: "+title);
                root = FXMLLoader.load(getClass().getResource(window));
                Stage stage = new Stage();
                stage.setTitle("Iterated Prisoner's Dilemma - " + title);
                stage.setScene(new Scene(root));
                stage.setMaximized(true);
                stage.show();
                // Hide this current window (if this is what you want)
//                    ((Node)(event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if(text.equals("run_simulation")){
                ArrayList<String> strategyArrayList = new ArrayList<>();
                strategyArrayList.add("ALWAYS_COOPERATE");
                strategyArrayList.add("ALWAYS_DEFECT");
                strategyArrayList.add("TIT_FOR_TAT");
                Tournament tournament = new Tournament(strategyArrayList, Tournament.TournamentMode.MODE_ORIGINAL);
                tournament.executeMatches();
                tournament.printTournamentScores(true, false, false);
            }else if(text.equals("stop_simulation")){

            }else if(text.equals("listView")){
                ListView<String> listView = (ListView<String>) event.getSource();
                String s = listView.getSelectionModel().getSelectedItem();

                HashMap<String, HashMap<String, Object>> modeHashMap = Tournament.TournamentMode.getModesHashMap();

                int numberOfRounds = (int) modeHashMap.get(s).get(Variables.ROUNDS);
                boolean repeat = (boolean) modeHashMap.get(s).get(Variables.REPEAT);
                boolean twin = (boolean) modeHashMap.get(s).get(Variables.TWIN);
                boolean random = (boolean) modeHashMap.get(s).get(Variables.RANDOM);

                ObservableList<ModeData> modeData = ControllerTournament.Observables.getModeData();
                modeData.add(new ModeData(Variables.ROUNDS, ""+numberOfRounds));
                modeData.add(new ModeData(Variables.REPEAT, ""+repeat));
                modeData.add(new ModeData(Variables.TWIN, ""+twin));
                modeData.add(new ModeData(Variables.RANDOM, ""+random));
            }
        }
    }
}
