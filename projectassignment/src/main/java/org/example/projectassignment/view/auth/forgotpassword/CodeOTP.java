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
import org.example.projectassignment.controller.auth.forgotpassword.MailSender;
import org.example.projectassignment.controller.category.ManagerCategory;
import org.example.projectassignment.model.user.informationuser.User;

import java.io.IOException;
import java.util.List;

public class CodeOTP {
    @FXML
    private TextField codeOTP;

    @FXML
    private Button back;

    @FXML
    private Button submit;

    @FXML
    private Label confirm;

    private String codeOTPInput;

    public void init(String emailUser, List<User> listUsers, ManagerCategory managerCategory){
        confirm.setVisible(false);
        MailSender mailSender = new MailSender();
        codeOTPInput = mailSender.sendMail(emailUser);
        submit.setOnAction(event -> {
            try {
                verifyCodeOTP(event, emailUser, listUsers, managerCategory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        back.setOnAction(event -> {
            try {
                backToForgotPassword(event, listUsers, managerCategory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setWrongConfirm(){
        confirm.setTextFill(Color.RED);
        confirm.setText("Mã OTP của bạn không chính xác");
        confirm.setVisible(true);
    }

    private void verifyCodeOTP(ActionEvent actionEvent, String emailUser, List<User> listUsers, ManagerCategory managerCategory) throws IOException {
        if (codeOTP.getText()==null) return;
        if (!codeOTPInput.equals(codeOTP.getText())){
            setWrongConfirm();
            return;
        }
        FXMLLoader loader = new FXMLLoader(NewPassword.class.getResource("NewPassword.fxml"));
        Parent root = loader.load();
        NewPassword newPassword = loader.getController();
        newPassword.init(emailUser, listUsers, managerCategory);
        Scene scene = new Scene(root, 600, 750);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    private void backToForgotPassword(ActionEvent actionEvent, List<User> listUsers, ManagerCategory managerCategory) throws IOException {
        FXMLLoader loader = new FXMLLoader(ForgotPassword.class.getResource("ForgotPassword.fxml"));
        Parent root = loader.load();
        ForgotPassword forgotPassword = loader.getController();
        Scene scene = new Scene(root, 600, 750);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        forgotPassword.init(listUsers, managerCategory);
    }


}
