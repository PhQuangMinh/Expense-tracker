package org.example.projectassignment;

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
    private Button EatButton;
    @FXML
    private Button ClothesButton;
    @FXML
    private Button ElectricButton;
    @FXML
    private Button BuyButton;


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
        String category = getButtonText();
        String note = noteField.getText();
        String amount = amountField.getText();
        Transaction transaction = new Transaction( date , note , Double.parseDouble(amount) , category);
        transactions.add(transaction);
        TransactionExporter exporter = new TransactionExporter();
        exporter.exportTransaction(transactions , "D:\\BTL-OOP\\projectassignment\\Output\\file.txt");
        noteField.clear();
        amountField.clear();
        datePicker.setValue(null);
    }

    @FXML
    private void submmitAction(){
        handleSubmit();
    }
    private String getButtonText(){
        return EatButton.getText();
    }

}
