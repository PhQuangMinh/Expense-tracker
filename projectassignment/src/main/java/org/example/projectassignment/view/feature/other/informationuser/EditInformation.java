package org.example.projectassignment.view.feature.other.informationuser;
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
import org.example.projectassignment.model.user.ManagerUser;
import org.example.projectassignment.view.feature.FeatureSelection;

import java.io.IOException;

public class EditInformation {
    private ManagerUser managerUser;

    @FXML
    private TextField firstName ;
    @FXML
    private TextField lastName ;
    @FXML
    private TextField email ;
    @FXML
    private TextField id ;
    public void init(ManagerUser managerUser){
        this.managerUser = managerUser;
        firstName.setText(managerUser.getUser().getFirstName());
        lastName.setText(managerUser.getUser().getLastName());
        email.setText(managerUser.getUser().getEmail());
        id.setText(managerUser.getUser().getId());
        id.setEditable(false);
        email.setEditable(false);
    }
    @FXML
    private  void saveUserInformation(ActionEvent event) throws IOException {
        if(firstName.getText().isEmpty() || lastName.getText().isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Tên không hợp lệ!");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Chỉnh sửa thành công!");
        alert.showAndWait();
        managerUser.getUser().setFirstName(firstName.getText());
        managerUser.getUser().setLastName(lastName.getText());
        goBack(event);
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
}
