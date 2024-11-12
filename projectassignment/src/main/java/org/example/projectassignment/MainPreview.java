package org.example.projectassignment;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
public class MainPreview extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainPreview.class.getResource("view/auth/signin/signin.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root , 600 , 750);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Edit Information");
        primaryStage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}