package gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;

/**
 * Created by dbrisingr on 08/02/2017.
 */
public class GUI extends Application {

    public static void main(String[] args) {
        Application.launch(GUI.class, (java.lang.String[]) null);
    }

    private boolean controlPressed = false;
    private final static String osPath = System.getProperty("user.home");
    @Override
    public void start(Stage primaryStage) {

        File directory = new File(osPath+"/src/strategies/custom/");
        File folder = new File(osPath+"/src/main/results_data");
        File mode = new File(osPath+"/src/main/mode_data/");
        if (! directory.exists()){
            directory.mkdirs();
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }
        if(!folder.exists()){
            folder.mkdirs();
        }
        if(!mode.exists()){
            mode.mkdirs();
        }
        try {
            Parent root = FXMLLoader.load(getClass().getResource("event_handlers/fxml/welcome.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("gui/css/stylesheet.css");
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                public void handle(KeyEvent ke) {
                    if (ke.getCode() == KeyCode.W && !ke.isControlDown()) {
                        primaryStage.close();
                    }

                }
            });


            primaryStage.getIcons().add(new Image("/images/logo.png"));
            primaryStage.setTitle("Robin - Welcome");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(500);
            primaryStage.setMaxWidth(800);
            primaryStage.setMaxHeight(500);
            primaryStage.setWidth(800);
            primaryStage.setHeight(500);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
