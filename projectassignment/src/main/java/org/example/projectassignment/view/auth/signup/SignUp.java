package org.example.projectassignment.view.auth.signup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.example.projectassignment.controller.auth.ManagerSignUp;
import org.example.projectassignment.controller.feature.input.category.ManagerCategory;
import org.example.projectassignment.controller.firebase.FirebaseUser;
import org.example.projectassignment.model.user.informationuser.User;
import org.example.projectassignment.view.auth.signin.SignIn;

import java.io.IOException;
import java.util.List;

public class SignUp {
    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private Label signIn;

    @FXML
    private Label inform;

    @FXML
    private Button signUp;

    @FXML
    private AnchorPane pane;

    private ManagerSignUp managerSignUp;

    private List<User> listUsers;

    private FirebaseUser firebaseUser;

    public void init(List<User> listUsers, ManagerCategory managerCategory){
        inform.setVisible(false);
        this.listUsers = listUsers;
        managerSignUp = new ManagerSignUp();
        firebaseUser = new FirebaseUser();
        signUp.setOnAction(event -> signUpAction(managerCategory));
        signIn.setOnMouseClicked(event -> {
            try {
                signInAction(managerCategory, firebaseUser);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void signInAction(ManagerCategory managerCategory, FirebaseUser firebaseUser) throws IOException {
        FXMLLoader loader = new FXMLLoader(SignIn.class.getResource("SignIn.fxml"));
        Parent root = loader.load();
        SignIn signIn = loader.getController();
        pane.getChildren().removeAll();
        pane.getChildren().setAll(root);
        signIn.init(listUsers, managerCategory);
    }


    private void signUpAction(ManagerCategory managerCategory){
        int checkValid = managerSignUp.checkValid(listUsers, firstName, lastName, email, password, confirmPassword);
        if (checkValid == 0){
            User user = new User(String.valueOf(listUsers.size()), firstName.getText(), lastName.getText(), email.getText(),
                    password.getText(), managerCategory.initListUserSignUp());
            listUsers.add(user);
            firebaseUser.saveUser(listUsers);
            inform.setTextFill(Color.GREEN);
            inform.setText("Đăng kí thành công!");
            inform.setVisible(true);
            return;
        }
        if (checkValid==1) inform.setText("Hãy cung cấp thêm thông tin.");
        else
        if (checkValid==2) inform.setText("Mật khẩu không hợp lệ!");
        else inform.setText("Email đã tồn tại!");
        inform.setVisible(true);
        inform.setTextFill(Color.RED);
    }
}
