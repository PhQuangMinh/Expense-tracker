package org.example.projectassignment.controller.auth;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.projectassignment.controller.firebase.FirebaseCategory;
import org.example.projectassignment.model.user.informationuser.User;

import java.util.List;

public class ManagerSignUp {
    private FirebaseCategory firebaseCategory;

    public ManagerSignUp(){
        firebaseCategory = new FirebaseCategory();
    }

    private boolean checkText(TextField firstName, TextField lastName, TextField email, PasswordField password, PasswordField confirmPassword){
        return firstName.getText().isEmpty() || lastName.getText().isEmpty() || email.getText().isEmpty()
                || password.getText().isEmpty() || confirmPassword.getText().isEmpty();
    }

    private boolean checkPassword(PasswordField password, PasswordField confirmPassword){
        return password.getText().equals(confirmPassword.getText());
    }

    private boolean checkEmail(List<User> list, TextField email){
        for (User user : list){
            if (user.getEmail().equals(email.getText())) return false;
        }
        return true;
    }

    public int checkValid(List<User> listUsers, TextField firstName, TextField lastName, TextField email, PasswordField password,
                          PasswordField confirmPassword){
        if (checkText(firstName, lastName, email, password, confirmPassword)) return 1;
        if (!checkPassword(password, confirmPassword)) return 2;
        if (!checkEmail(listUsers, email)) return 3;
        return 0;
    }
}
