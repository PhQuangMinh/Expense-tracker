package org.example.projectassignment.view.auth.signin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.example.projectassignment.controller.RevenueController;
import org.example.projectassignment.model.User;
import org.example.projectassignment.view.auth.signup.SignUp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class SignIn {
    @FXML
    public AnchorPane pane;
    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Label inform;

    @FXML
    private Button signIn;

    @FXML
    private Label signUp;

    List<User> listUsers;

    public void init(List<User> listUsers){
        this.listUsers = listUsers;
        inform.setVisible(false);
        signIn.setOnAction(event -> signInAction());
        signUp.setOnMouseClicked(event -> signUpAction());
    }

    private void signUpAction(){
        FXMLLoader loader = new FXMLLoader(SignUp.class.getResource("signup.fxml"));
        try {
            Parent root = loader.load();
            pane.getChildren().clear();
            pane.getChildren().add(root);
            SignUp signUpController = loader.getController();
            signUpController.init(listUsers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void signInAction(){
        for (User user : listUsers){
            if(user.getEmail().equals(email.getText()) && user.getPassword().equals(password.getText())){
                homePage(user);
                return;
            }
        }
        inform.setVisible(true);
        inform.setTextFill(Color.RED);
        inform.setText("Email or password is incorrect");
    }

    private void homePage(User user){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projectassignment/view/Revenue.fxml"));
        try {
            Parent root = loader.load();
            pane.getChildren().clear();
            pane.getChildren().add(root);
            RevenueController revenueController = loader.getController();
            revenueController.init(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
