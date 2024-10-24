package org.example.projectassignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Stage_1.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Sổ thu chi");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public static void main(String[] args){
        launch(args);
    }

}