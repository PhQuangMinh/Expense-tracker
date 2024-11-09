package org.example.projectassignment.view;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.projectassignment.Main;
import org.example.projectassignment.controller.feature.FeatureSelection;
import org.example.projectassignment.model.User ;

import java.io.IOException;

public class EditInformation {
    private User user ;
    @FXML TextField firstName ;
    @FXML TextField lastName ;
    @FXML TextField email ;
    @FXML TextField id ;
    @FXML TextField fullName ;
    public EditInformation() {
        user = new User("1234" , "Nguyen Van" , "Hoc" , "hoc@gmail.com" , "1234");
    }
    @FXML
    public void getUserInformation(){
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmail());
        id.setText(user.getId());
        email.setEditable(false);
        id.setEditable(false);
        fullName.setEditable(false);
    }
    @FXML
    public void saveUserInformation(){
        if(firstName.getText().isEmpty() || lastName.getText().isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Tên không hợp lệ!");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Chỉnh sửa thành công!");
        alert.showAndWait();
        user.setFirstName(firstName.getText());
        user.setLastName(lastName.getText());
    }
    @FXML
    private void goBack(ActionEvent event ) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/feature/FeatureSelection.fxml")) ;
        Parent root = loader.load() ;
        FeatureSelection featureSelection = loader.getController() ;
        Scene scene = new Scene(root , 600 , 750 ) ;
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
        currentStage.setScene(scene);
        featureSelection.switchOtherTab();
    }
}
