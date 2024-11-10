package org.example.projectassignment.view.feature.other;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.example.projectassignment.InitApp;
import org.example.projectassignment.Main;
import org.example.projectassignment.model.user.ManagerUser;
import org.example.projectassignment.view.feature.other.informationuser.EditInformation;
import org.example.projectassignment.view.feature.other.informationuser.EditPassword;
import org.example.projectassignment.view.feature.other.annualreport.AnnualReportScene;

import java.io.IOException;
import java.util.Optional;

public class OtherScene {
    private ManagerUser managerUser;
    public void init(ManagerUser managerUser){
        this.managerUser = managerUser;
    }

    @FXML
    private void onActionButtonAnnualReport(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/feature/other/annualreport/AnnualReportScene.fxml"));
        Parent root = loader.load();
        AnnualReportScene annualReportScene = loader.getController();
        annualReportScene.init(managerUser);
        Scene scene = new Scene(root);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
    }
    @FXML
    private void onActionButtonEditInformation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/feature/other/editinformation/EditInformation.fxml"));
        Parent root = loader.load();
        EditInformation editInformation = loader.getController();
        editInformation.init(managerUser) ;
        Scene scene = new Scene(root);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
    }
    @FXML
    private void onActionButtonEditPassword(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/feature/other/editpassword/EditPassword.fxml"));
        Parent root = loader.load();
        EditPassword editPassword = loader.getController();
        editPassword.init(managerUser) ;
        Scene scene = new Scene(root);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
    }
    @FXML
    private void onActionLogOut(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Đăng xuất");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn đăng xuất không ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            InitApp initApp = new InitApp();
            initApp.init(stage);
        }
    }
}
