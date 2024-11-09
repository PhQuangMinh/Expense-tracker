package org.example.projectassignment;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.projectassignment.controller.firebase.FirebaseUser;
import org.example.projectassignment.model.User;
import org.example.projectassignment.view.auth.signin.SignIn;

import java.io.IOException;

public class InitApp {
    private  FirebaseUser firebaseUser;

    public InitApp(){

    }

    public void init(Stage stage) throws IOException {
        firebaseUser = new FirebaseUser();
        firebaseUser.getUsers().thenAccept(users -> {
            Platform.runLater(() -> {
                FXMLLoader loader = new FXMLLoader(SignIn.class.getResource("signin.fxml"));
                try {
                    Parent root = loader.load();
                    SignIn signIn = loader.getController();
                    signIn.init(users);
                    Scene scene = new Scene(root ,  600, 750);
                    stage.setTitle("Sổ thu chi");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.setOnCloseRequest(event -> {
                    firebaseUser.saveUser(users);
                    for (User user : users) {
                        System.out.println(user);
                    }
                });
            });
        });
    }
}
