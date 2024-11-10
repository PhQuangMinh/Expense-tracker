package org.example.projectassignment.view.auth.forgotpassword;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.projectassignment.controller.category.ManagerCategory;
import org.example.projectassignment.model.user.informationuser.User;
import org.example.projectassignment.view.auth.signin.SignIn;

import java.io.IOException;
import java.util.List;

public class ForgotPassword {
    @FXML
    private TextField emailUser;

    @FXML
    private Button sendCode;

    @FXML
    private Button back;

    @FXML
    private Label confirm;

    public void init(List<User> listUsers, ManagerCategory managerCategory){
        confirm.setVisible(false);
        sendCode.setOnAction(event -> {
            try {
                sendCodeEvent(listUsers, event, managerCategory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        back.setOnAction(event -> {
            try {
                backToSignIn(listUsers, event, managerCategory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private User checkEmail(List<User> list){
        User findUser = null;
        for (User user : list) {
            if (user.getEmail().equals(emailUser.getText())) {
                findUser = user;
            }
        }
        return findUser;
    }

    public void sendCodeEvent(List<User> listUsers, ActionEvent event, ManagerCategory managerCategory) throws IOException {
        User user = checkEmail(listUsers);
        if (user!=null){
            FXMLLoader loader = new FXMLLoader(ForgotPassword.class.getResource("CodeOTP.fxml"));
            Parent root = loader.load();
            CodeOTP codeOTP = loader.getController();
            Scene scene = new Scene(root, 600, 750);
            Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            currentStage.setScene(scene);
            codeOTP.init(emailUser.getText(), listUsers, managerCategory);
        }
        else{
            confirm.setTextFill(Color.RED);
            confirm.setVisible(true);
        }
    }

    private void backToSignIn(List<User> listUsers, ActionEvent event, ManagerCategory managerCategory) throws IOException {
        FXMLLoader loader = new FXMLLoader(SignIn.class.getResource("signin.fxml"));
        Parent root = loader.load();
        SignIn signIn = loader.getController();
        Scene scene = new Scene(root, 600, 750);
        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
        signIn.init(listUsers, managerCategory);
    }

}
