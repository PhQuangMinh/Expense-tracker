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
import org.example.projectassignment.controller.category.ManagerCategory;
import org.example.projectassignment.model.user.ManagerUser;
import org.example.projectassignment.view.auth.forgotpassword.ForgotPassword;
import org.example.projectassignment.view.feature.FeatureSelection;
import org.example.projectassignment.model.user.informationuser.User;
import org.example.projectassignment.view.auth.signup.SignUp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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

    @FXML
    private Label forgotPassword;

    List<User> listUsers;


    public void init(List<User> listUsers, ManagerCategory managerCategory) throws IOException {
        this.listUsers = listUsers;
        inform.setVisible(false);
        signIn.setOnAction(event -> {
            try {
                signInAction(managerCategory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        inform.setVisible(false) ;
        signUp.setOnMouseClicked(event -> {
            try {
                signUpAction(managerCategory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        forgotPassword.setOnMouseClicked(event -> {
            try {
                forgotPasswordAction(managerCategory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void signUpAction(ManagerCategory managerCategory) throws IOException {
        FXMLLoader loader = new FXMLLoader(SignUp.class.getResource("signup.fxml"));
        Parent root = loader.load();
        pane.getChildren().clear();
        pane.getChildren().add(root);
        SignUp signUpController = loader.getController();
        signUpController.init(listUsers, managerCategory);
    }

    private void signInAction(ManagerCategory managerCategory) throws IOException {
        for (User user : listUsers){
            if(user.getEmail().equals(email.getText()) && user.getPassword().equals(password.getText())){
                if (user.getListCalendarDays() == null){
                    user.setListCalendarDays(new ArrayList<>());
                }
                homePage(user, managerCategory);
                return;
            }
        }
        inform.setVisible(true);
        inform.setTextFill(Color.RED);
        inform.setText("Email or password is incorrect");
    }

    private void forgotPasswordAction(ManagerCategory managerCategory) throws IOException {
        FXMLLoader loader = new FXMLLoader(ForgotPassword.class.getResource("ForgotPassword.fxml"));
        Parent root = loader.load();
        pane.getChildren().removeAll();
        pane.getChildren().setAll(root);
        ForgotPassword forgotPassword = loader.getController();
        forgotPassword.init(listUsers, managerCategory);
    }

    private void homePage(User user, ManagerCategory managerCategory) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projectassignment/view/feature/FeatureSelection.fxml"));
        Parent root = loader.load();
        pane.getChildren().clear();
        pane.getChildren().add(root);
        FeatureSelection featureSelect = loader.getController();
        ManagerUser managerUser = new ManagerUser(user, managerCategory);
        featureSelect.init(managerUser);
    }
}
