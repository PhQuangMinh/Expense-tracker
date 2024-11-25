package org.example.projectassignment.view.feature.input.expense;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.example.projectassignment.common.TypeCategory;
import org.example.projectassignment.controller.ManagerUser;
import org.example.projectassignment.view.feature.FeatureSelection;
import org.example.projectassignment.controller.feature.input.ManagerInput;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Expense extends ManagerInput {
    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField noteField;

    @FXML
    private TextField amountField;

    @FXML
    private Button submitButton;

    @FXML
    private GridPane expenseGridPane ;

    @FXML
    private ScrollPane scrollPane;

    private FeatureSelection featureSelection;

    private ManagerUser managerUser;


    public void init(ManagerUser managerUser, FeatureSelection featureSelection) {
        this.featureSelection = featureSelection;
        this.managerUser = managerUser;
        setDatePicker(datePicker);
        helper.setAmountField(amountField);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        submitButton.setOnAction(event -> handleSubmit(amountField, datePicker, noteField, managerUser, TypeCategory.EXPENSE));
    }

    @FXML
    private void switchToCategoryExpenseEditor(ActionEvent event) throws IOException{
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/org/example/projectassignment/view/CategoryExpenseEditor.fxml"));
        Parent root = loader.load();
        CategoryExpenseEditor controller = loader.getController();
        controller.init(managerUser);
        managerUser.updateCategory();
        controller.updateCategoryExpense(managerUser.getExpenseCategory());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow() ;
        Scene scene = new Scene(root) ;
        stage.setScene(scene) ;
        stage.show() ;
    }

    @FXML
    public void onActionButtonSwitchIncome() throws IOException {
        featureSelection.switchIncomeTab();
    }

    public void updateCategoryExpense(){
        setEditButton(editButton);
        editButton.setOnAction(event ->{
            try{
                switchToCategoryExpenseEditor(event);
            }
            catch(IOException e){
                e.printStackTrace();}
            });
        updateCategory(editButton, managerUser, expenseGridPane, managerUser.getExpenseCategory());
    }

}
