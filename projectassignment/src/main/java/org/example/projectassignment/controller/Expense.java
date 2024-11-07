package org.example.projectassignment.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.projectassignment.model.CustomButton;
import java.io.IOException;
import java.util.*;

public class  Expense extends TransactionBase {

    private Stage stage ;
    private Scene scene ;
    private Parent root ;
    public static List<CustomButton> expenseCategories ;

    public Expense(){
        transactions = new ArrayList<>();
        expenseCategories = new ArrayList<>();
    }

    @FXML
    private void onActionButtonSwitchRevenue(ActionEvent event) {
        try {
            featureSelectionController.switchRevenueTab();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToEditCategorySpendingMoney(ActionEvent event) throws IOException{
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/org/example/projectassignment/view/EditCategorySpendingMoney.fxml"));
        Parent root = loader.load();
        EditCategoryExpense controller = loader.getController();
        controller.updateCategorySpendingMoney(expenseCategories);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow() ;
        Scene scene = new Scene(root) ;
        stage.setScene(scene) ;
        stage.show() ;
    }

    @Override
    protected List <CustomButton> getCategoryButton(){
        return  expenseCategories ;
    }
}
