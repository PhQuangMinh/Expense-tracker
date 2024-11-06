package org.example.projectassignment.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.projectassignment.model.InitCategory;
import org.example.projectassignment.model.Transaction;
import org.example.projectassignment.model.TransactionExporter;
import org.example.projectassignment.view.CustomButton;


import java.io.IOException;
import java.time.LocalDate;

import java.util.*;

import static org.example.projectassignment.controller.RevenueController.categoryButtons;

public class SpendingMoneyController {
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField noteField;
    @FXML
    private TextField amountField;

    @FXML
    private GridPane spendingMoneyGridPane;
    private String selectedCategory ;
    private List <Transaction> transactions;
    private Stage stage ;
    private Scene scene ;
    private Parent root ;
    private FeatureSelectionController featureSelectionController;
    public static List<CustomButton> spendingMoneyCategories = new ArrayList<>();
    public void setParentController(FeatureSelectionController controller) {
        this.featureSelectionController = controller;
    }

    @FXML
    public void onActionButtonSwitchRevenue(ActionEvent event) {
        try {
            featureSelectionController.switchRevenueTab();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void switchToEditCategorySpendingMoney(ActionEvent event) throws IOException{
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/org/example/projectassignment/view/EditCategorySpendingMoney.fxml"));
        Parent root = loader.load();
        EditCategorySpendingMoneyController controller = loader.getController();
        controller.updateCategorySpendingMoney(spendingMoneyCategories);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow() ;
        Scene scene = new Scene(root) ;
        stage.setScene(scene) ;
        stage.show() ;
    }
    public SpendingMoneyController(){
        transactions = new ArrayList<>();
    }
    @FXML
    private void handleSubmit(){
        LocalDate date = datePicker.getValue();
        if(date == null){
            System.out.println("date is null");
        }
        String note = noteField.getText();
        String amount = amountField.getText();
        String category = selectedCategory ;
        Transaction transaction = new Transaction( date , note , Double.parseDouble(amount) , category);
        TransactionExporter exporter = new TransactionExporter();
        transactions.add(transaction);
        exporter.exportTransaction(transactions , "FileBinary.in");
        noteField.clear();
        amountField.clear();
        datePicker.setValue(null);

    }
    @FXML
    private void submitAction(){
        handleSubmit();
    }
    @FXML
    private void handleCategorySelection(ActionEvent event){
        Button button = (Button) event.getSource() ;
        selectedCategory = button.getText() ;
        InitCategory category = new InitCategory();
        category.addButton(spendingMoneyGridPane, spendingMoneyCategories);
    }


}
