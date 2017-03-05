package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
            scene.getStylesheets().add("gui/css/stylesheet.css");
            primaryStage.getIcons().add(new Image("file:src/images/logo.png"));
            primaryStage.setTitle("Robin - Welcome");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(600);
            primaryStage.setMinHeight(500);
            primaryStage.setMaxWidth(600);
            primaryStage.setMaxHeight(500);
            primaryStage.setWidth(600);
            primaryStage.setHeight(500);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
