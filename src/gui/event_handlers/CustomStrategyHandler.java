package gui.event_handlers;

import gui.data_structures.Observables;
import gui.data_structures.StrategyData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import main.CommonFunctions;
import main.TournamentMode;
import main.Variables;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Created by dbrisingr on 07/03/2017.
 */
public class CustomStrategyHandler implements EventHandler {

    private ListView listView_original;
    private ListView listView_custom;
    private TextArea textArea;
    private Button save;
    private Button add;
    private Button delete;

    private Observables observables;

    public CustomStrategyHandler(Object[] components, Observables observables) {
        listView_original = (ListView) components[0];
        listView_custom = (ListView) components[1];
        textArea = (TextArea) components[2];
        save = (Button) components[3];
        add = (Button) components[4];
        delete = (Button) components[5];
        this.observables = observables;
    }
    private final static String osPath = System.getProperty("user.home");
    @Override
    public void handle(Event event) {
        if (event.getSource() == delete) {
            if (listView_custom.getSelectionModel().getSelectedItem() != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Confirmation");
                alert.setHeaderText("Are you sure you want to delete the selected file?");
                Optional<ButtonType> confirm = alert.showAndWait();

                String result = listView_custom.getSelectionModel().getSelectedItem().toString();
                String path = osPath+"/src/strategies/custom/" + result + ".java";
                if (confirm.get() == ButtonType.OK) {
                    try {
                        Files.delete(Paths.get(path));
                    } catch (NoSuchFileException x) {
                        x.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    textArea.clear();
                }
            }
            updateTournamentList();
            listView_custom.setItems(FXCollections.observableArrayList(new CommonFunctions().getStrategies(false, true)));
        } else if (event.getSource() == add) {
            textArea.clear();
            save.setDisable(false);
        } else if (event.getSource() == save) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Strategy Name Required");
            dialog.setHeaderText("Please enter a name for your strategy");
            Optional<String> result = dialog.showAndWait();
            List check = listView_custom.getItems();

            if (result.isPresent() && !check.contains(result.get())) {
                ObservableList<CharSequence> paragraph = textArea.getParagraphs();
                Iterator<CharSequence> iterator = paragraph.iterator();

                File directory = new File(osPath+"/src/strategies/custom/");
                try {
                    File file = new File(directory.toPath() + "/" + result.get() + ".java");
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bf = new BufferedWriter(fw);
                    while (iterator.hasNext()) {
                        CharSequence seq = iterator.next();
                        bf.append(seq);
                        bf.newLine();
                    }
                    bf.flush();
                    bf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            listView_custom.setItems(FXCollections.observableArrayList(new CommonFunctions().getStrategies(false, true)));
            updateTournamentList();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Strategy saved");
            alert.setHeaderText(null);
            alert.setContentText("The new strategy have been successfully saved");
            alert.showAndWait();

            save.setDisable(true);
        }
    }

    private void updateTournamentList(){
        if(observables != null){
            ObservableList<StrategyData> strategyData = observables.getStrategyData();
            strategyData.clear();
            ArrayList<String> strategies = new CommonFunctions().getStrategies(true, false);
            strategies.remove("RANDOM (built-in)");
            for (String s : strategies) {
                strategyData.add(new StrategyData(s, false));
            }
        }
    }
}
