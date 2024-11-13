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
import org.example.projectassignment.controller.feature.input.category.ManagerCategory;
import org.example.projectassignment.controller.firebase.FirebaseUser;
import org.example.projectassignment.model.user.informationuser.User;
import org.example.projectassignment.view.auth.signin.SignIn;

import java.io.IOException;
import java.util.List;

public class NewPassword {
    @FXML
    private TextField newPassword;

    @FXML
    private TextField confirmPassword;

    @FXML
    private Label confirmPasswordLabel;

    @FXML
    private Label back ;
    @FXML
    private Button submitButton;

    private String emailUser;

    public void init(String emailUser, List<User> listUsers, ManagerCategory managerCategory){
        this.emailUser = emailUser;
        submitButton.setOnAction(event -> {
            try {
                submitEvent(event, listUsers, managerCategory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        back.setOnMouseClicked(event -> {
            try {
                backToSignIn(listUsers, event, managerCategory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        confirmPasswordLabel.setVisible(false);
    }

    private boolean checkPassword(){
        if (newPassword.getText().isEmpty() || confirmPassword.getText().isEmpty()) return false;
        return newPassword.getText().equals(confirmPassword.getText());
    }

    private User getUser(List<User> listUsers){
        User user = null;
        for (User user1 : listUsers) {
            if (user1.getEmail().equals(emailUser)) {
                user = user1;
            }
        }
        return user;
    }
    private void updateUser(List<User> listUsers){
        User user = getUser(listUsers);
        user.setPassword(newPassword.getText());
        FirebaseUser firebaseUser = new FirebaseUser();
        firebaseUser.updateUser(user);
    }

    private void submitEvent(ActionEvent event, List<User> listUsers, ManagerCategory managerCategory) throws IOException {
        if (!checkPassword()){
            confirmPasswordLabel.setTextFill(Color.RED);
            confirmPasswordLabel.setVisible(true);
            confirmPasswordLabel.setText("Mật khẩu không hợp lệ");
            return;
        }
        updateUser(listUsers);
        FXMLLoader loader = new FXMLLoader(SignIn.class.getResource("signin.fxml"));
        Parent root = loader.load();
        SignIn signIn = loader.getController();
        Scene scene = new Scene(root, 600, 750);
        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
        signIn.init(listUsers, managerCategory);
    }

    private void backToSignIn(List<User> listUsers, javafx.scene.input.MouseEvent event , ManagerCategory managerCategory) throws IOException {
        FXMLLoader loader = new FXMLLoader(SignIn.class.getResource("signin.fxml"));
        Parent root = loader.load();
        SignIn signIn = loader.getController();
        Scene scene = new Scene(root, 600, 750);
        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
        signIn.init(listUsers, managerCategory);
    }
}
