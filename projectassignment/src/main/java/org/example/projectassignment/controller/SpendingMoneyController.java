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
import javafx.stage.Stage;
import org.example.projectassignment.model.Transaction;
import org.example.projectassignment.model.TransactionExporter;


import java.io.IOException;
import java.time.LocalDate;

import java.util.*;
public class SpendingMoneyController {
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

    private String selectedCategory ;
    private List <Transaction> transactions;
    private Stage stage ;
    private Scene scene ;
    private Parent root ;

    @FXML
    public void switchToRevenue( ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/projectassignment/view/Revenue.fxml")) ;
        stage = (Stage)((Node)event.getSource()).getScene().getWindow() ;
        scene = new Scene(root) ;
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
    }


}
