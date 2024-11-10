package org.example.projectassignment.view.feature.input.income;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.example.projectassignment.common.TypeTransaction;
import org.example.projectassignment.model.user.ManagerUser;
import org.example.projectassignment.view.feature.FeatureSelection;
import org.example.projectassignment.controller.home.input.ManagerInput;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.projectassignment.model.CustomButton;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Income implements Initializable {
    @FXML
    private DatePicker datePicker;

    public static List<CustomButton> incomeCategories = new ArrayList<>() ;

    @FXML
    private TextField noteField;
    @FXML
    private TextField amountField;

    private String selectedCategory ;

    private FeatureSelection featureSelection;

    private ManagerUser managerUser;

    private ManagerInput managerInput;

    public void init(ManagerUser managerUser, FeatureSelection featureSelection) {
        this.managerUser = managerUser;
        managerInput = new ManagerInput();
        this.featureSelection = featureSelection;
    }

    public void switchToEditCategoryIncome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projectassignment/view/EditCategoryRevenue.fxml"));
        Parent root = loader.load();
        EditCategoryIncome controller = loader.getController();
        controller.init(managerUser);
        controller.updateCategory(incomeCategories);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onActionSwitchExpense () throws IOException {
        featureSelection.switchExpenseMoneyTab();
    }

    @FXML
    private void handleSubmit(){
        double amountValue;
        try {
            amountValue = Double.parseDouble(removeCommas(amountField.getText()));
            if (amountValue <= 0 || amountValue > Long.MAX_VALUE) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Số tiền không hợp lệ, bạn vui lòng nhập lại số tiền!");
            alert.showAndWait();
            return;
        }
        managerInput.addTransaction(datePicker, noteField, amountField, selectedCategory, managerUser.getUser(), TypeTransaction.INCOME);
        noteField.clear();
        amountField.clear();
        datePicker.setValue(null);
        notification();
    }

    @FXML
    private void handleCategorySelection(ActionEvent event){
        Button button = (Button) event.getSource() ;
        selectedCategory = button.getText() ;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datePicker.setValue(LocalDate.now());
        datePicker.setEditable(false);

        amountField.textProperty().addListener(new ChangeListener<String>() {
            private boolean changing = false;

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (changing) {
                    return;
                }
                changing = true;
                try{
                    if(newValue == null || newValue.isEmpty()){
                        amountField.setText("") ;
                    }
                    else{
                        String cleanString = removeCommas(newValue);
                        double parsed = Double.parseDouble(cleanString) ;
                        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
                        formatter.setGroupingUsed(true);
                        String formattedString = formatter.format(parsed);
                        amountField.setText(formattedString);
                    }
                }
                catch(NumberFormatException e){
                    amountField.setText(oldValue);
                }
                changing = false;
            }
        });
    }
    private String removeCommas(String input){
        return input.replace("," , "") ;
    }
    private void notification(){
        Stage notificationStage = new Stage();
        notificationStage.initStyle(StageStyle.UNDECORATED);
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 10px;");
        Label message = new Label("Đã nhập dữ liệu !");
        root.getChildren().add(message);
        Scene scene = new Scene(root);
        notificationStage.setScene(scene);
        notificationStage.show();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> notificationStage.close()));
        timeline.setCycleCount(1);
        timeline.play();
    }
}
