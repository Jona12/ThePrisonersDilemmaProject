package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by dbrisingr on 08/02/2017.
 */
public class GUI extends Application {

    public static void main(String[] args) {
        Application.launch(GUI.class, (java.lang.String[]) null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/welcome.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Iterated Prisoner's Dilemma - Welcome");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
