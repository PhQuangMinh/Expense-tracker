package org.example.projectassignment;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

import java.util.*;
public class Controller {
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

    public Controller(){
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
