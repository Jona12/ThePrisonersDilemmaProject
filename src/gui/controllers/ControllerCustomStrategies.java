package gui.controllers;

import gui.data_structures.Observables;
import gui.event_handlers.CustomStrategyHandler;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import main.CommonFunctions;
import main.Variables;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

/**
 * Created by dbrisingr on 06/03/2017.
 */
public class ControllerCustomStrategies implements Initializable {

    @FXML
    private ListView strategy_list;
    @FXML
    private ListView list_view_edit;
    @FXML
    private Button delete;
    @FXML
    private Button add;
    @FXML
    private TextArea text_area;
    @FXML
    private Button save;
    @FXML
    private TitledPane titled_pane;
    @FXML
    private TitledPane titled_pane2;
    @FXML
    private Accordion accordion;

    private Observables observables;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accordion.setExpandedPane(titled_pane);
        setListView();
        setTextArea();
    }

    public void setObservables(Observables observables){
        this.observables = observables;

        Object[] components = {
                strategy_list, list_view_edit, text_area, save, add, delete
        };
        CustomStrategyHandler customStrategyHandler = new CustomStrategyHandler(components, observables);
        save.setOnAction(customStrategyHandler);
        save.setDisable(true);
        add.setOnAction(customStrategyHandler);
        delete.setOnAction(customStrategyHandler);
    }

    private void setListView() {
        ArrayList<String> strategies = CommonFunctions.getStrategies(false, false);
        ObservableList<String> strategyData = FXCollections.observableArrayList(strategies);
        strategy_list.setItems(strategyData);

        ArrayList<String> strategies2 = CommonFunctions.getStrategies(false, true);
        ObservableList<String> strategyData2 = FXCollections.observableArrayList(strategies2);
        list_view_edit.setItems(strategyData2);
    }

    private void setTextArea() {
        strategy_list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                text_area.clear();
                Scanner s;
                String file;
                String toPut;
                text_area.setEditable(false);
                Platform.runLater(() -> {
                    list_view_edit.getSelectionModel().clearSelection();
                });

                if (newValue != null) {

                    String val = newValue.toString();
                    if (val.contains(" (original)")) {
                        file = "src/strategies/original/";
                        toPut = ((String) newValue).substring(0, ((String) newValue).indexOf(" (original)"));
                    } else {
                        file = "src/strategies/built_in/";
                        toPut = ((String) newValue).substring(0, ((String) newValue).indexOf(" (built-in)"));
                    }
                    try {
                        s = new Scanner(new File(file + toPut + ".java")).useDelimiter("'");
                        while (s.hasNext()) {
                            if (s.hasNextInt()) {
                                text_area.appendText(s.nextInt() + " ");
                            } else {
                                text_area.appendText(s.next() + " ");
                            }
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        });


        list_view_edit.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                text_area.clear();
                Scanner s;
                text_area.setEditable(true);
                Platform.runLater(() -> {
                    strategy_list.getSelectionModel().clearSelection();
                });

                if (newValue != null) {
                    try {
                        s = new Scanner(new File(osPath+"/src/strategies/custom/" + newValue + ".java")).useDelimiter("'");
                        while (s.hasNext()) {
                            if (s.hasNextInt()) {
                                text_area.appendText(s.nextInt() + " ");
                            } else {
                                text_area.appendText(s.next() + " ");
                            }
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
    private final static String osPath = System.getProperty("user.home");
}
