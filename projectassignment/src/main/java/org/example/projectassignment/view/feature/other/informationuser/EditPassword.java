package org.example.projectassignment.view.feature.other.informationuser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.projectassignment.Main;
import org.example.projectassignment.controller.ManagerUser;
import org.example.projectassignment.view.feature.FeatureSelection;

import java.io.IOException;

public class EditPassword {
    @FXML
    private PasswordField currentPassword ;

    @FXML
    private TextField newPassword ;

    @FXML
    private TextField repeatPassword ;
    @FXML
    private Label labelCurrentPassword ;
    @FXML
    private Label labelNewPassword ;
    @FXML
    private Label labelRepeatPassword ;

    private ManagerUser managerUser;
    public void init(ManagerUser managerUser){
        this.managerUser = managerUser;
    }

    @FXML
    private void goBack(ActionEvent event ) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/feature/FeatureSelection.fxml")) ;
        Parent root = loader.load() ;
        FeatureSelection featureSelection = loader.getController() ;
        featureSelection.init(managerUser);
        Scene scene = new Scene(root , 600 , 750 ) ;
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
        currentStage.setScene(scene);
        featureSelection.switchOtherTab();
    }

    @FXML
    private void savePassword(ActionEvent event) throws IOException {
        clearAllErrors();
        if(currentPassword.getText().isEmpty() || !managerUser.getUser().getPassword().equals(currentPassword.getText())){
            setInform(labelCurrentPassword , "Mật khẩu hiện tại không chính xác")  ;
            return ;

        }
        if(!newPassword.getText().equals(repeatPassword.getText())){
            setInform(labelRepeatPassword , "Mật khẩu không trùng khớp") ;

            return ;
        }
        if(!checkPassword(newPassword.getText())){
            setInform(labelNewPassword , "Mật khẩu của bạn phải dài từ 8 đến 16 ký tự, \nphải chứa ít nhất 1 ký tự viết hoa, 1 ký tự viết \nthường, 1 ký tự số và 1 ký tự đặc biệt") ;
            return ;
        }
        this.managerUser.getUser().setPassword(newPassword.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Thay đổi mật khẩu thành công!");
        managerUser.getFirebaseUser().updateUser(managerUser.getUser());
        if(alert.showAndWait().get()  == ButtonType.OK){
            goBack(event) ;
        }

    }
    private void clearAllErrors() {
        labelNewPassword.setVisible(false);
        labelCurrentPassword.setVisible(false);
        labelRepeatPassword.setVisible(false);
    }
    private void setInform(Label label , String inform){
        label.setVisible(true);
        label.setTextFill(Color.RED);
        label.setText(inform);
    }
    private boolean checkPassword(String password){
        if (password.length() < 8 || password.length() > 16) {
            return false;
        }

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowerCase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(ch)) {
                hasSpecialChar = true;
            }
        }
        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }

}
