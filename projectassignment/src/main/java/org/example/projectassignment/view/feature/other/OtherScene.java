package org.example.projectassignment.view.feature.other;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.projectassignment.Main;
import org.example.projectassignment.model.User;
import org.example.projectassignment.view.EditInformation;
import org.example.projectassignment.view.EditPassword;
import org.example.projectassignment.view.feature.other.annualreport.AnnualReportScene;

import java.io.IOException;

public class OtherScene {

    private User user ;
    public void init(User user){
        this.user = user ;
    }

    @FXML
    private void onActionButtonAnnualReport(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/feature/other/annualreport/AnnualReportScene.fxml"));
        Parent root = loader.load();
        AnnualReportScene annualReportScene = loader.getController();
        annualReportScene.init(user);
        Scene scene = new Scene(root);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
    }
    @FXML
    private void onActionButtonEditInformation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/feature/other/editinformation/EditInformation.fxml"));
        Parent root = loader.load();
        EditInformation editInformation = loader.getController();
        editInformation.init(user) ;
        Scene scene = new Scene(root);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
    }
    @FXML
    private void onActionButtonEditPassword(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/feature/other/editpassword/EditPassword.fxml"));
        Parent root = loader.load();
        EditPassword editPassword = loader.getController();
        editPassword.init(user) ;
        Scene scene = new Scene(root);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
    }
}
