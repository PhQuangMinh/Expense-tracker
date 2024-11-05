package org.example.projectassignment.controller ;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.projectassignment.model.Transaction;
import org.example.projectassignment.model.TransactionExporter;

import java.io.IOException;
import java.time.LocalDate;

import java.util.*;
public class RevenueController {
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField noteField;
    @FXML
    private TextField amountField;
    @FXML
    private Button submitButton;

    @FXML
    private Button entertainmentButton;

    @FXML
    private Button shoppingButton;

    @FXML
    private Button communicationFeeButton;

    @FXML
    private Button educationButton;

    @FXML
    private Button rentButton;

    @FXML
    private Button cosmeticsButton;

    @FXML
    private Button healthcareButton;

    @FXML
    private Button transportationFeeButton;

    @FXML
    private Button foodAndDrinkButton;

    @FXML
    private Button clothingButton;

    @FXML
    private Button electricityBillButton;

    @FXML
    private Button editButton;

    @FXML
    private Button buttonSwitchIncome;

    private String selectedCategory ;
    private List <Transaction> transactions;
    private Stage stage ;
    private Scene scene ;
    private Parent root ;
    private FeatureSelectionController featureSelectionController;

    public void setParentController(FeatureSelectionController controller) {
        this.featureSelectionController = controller;
    }

    @FXML
    public void onActionSwitchSpendingMoney (ActionEvent event) throws IOException {
        try {
            featureSelectionController.switchSpendingMoneyTab();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RevenueController(){
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
    }


}
