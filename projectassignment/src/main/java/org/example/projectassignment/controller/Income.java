package org.example.projectassignment.controller ;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.projectassignment.common.TypeTransaction;
import org.example.projectassignment.controller.home.input.ManagerInput;
import org.example.projectassignment.model.User;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.projectassignment.model.CustomButton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Income {
    @FXML
    private DatePicker datePicker;

    public static List<CustomButton> incomeCategories = new ArrayList<>() ;

    @FXML
    private TextField noteField;
    @FXML
    private TextField amountField;

    private String selectedCategory ;

    private FeatureSelection featureSelection;

    private User user;

    private ManagerInput managerInput;

    public void init(FeatureSelection featureSelection, User user) {
        this.user = user;
        managerInput = new ManagerInput();
        this.featureSelection = featureSelection;
    }

    public void switchToEditCategoryIncome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projectassignment/view/EditCategoryRevenue.fxml"));
        Parent root = loader.load();
        EditCategoryIncome controller = loader.getController();
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
        managerInput.addTransaction(datePicker, noteField, amountField, selectedCategory, user, TypeTransaction.INCOME);
    }
    @FXML
    private void submitAction(){
        handleSubmit();
    }
    @FXML
    private void handleCategorySelection(ActionEvent event){
        Button button = (Button) event.getSource() ;
        selectedCategory = button.getText() ;
    }

}
