package org.example.projectassignment.controller ;

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
public class Income extends Expense {
    private Stage stage ;
    private Scene scene ;
    private Parent root ;
    private FeatureSelectionController featureSelectionController;
    public static List<CustomButton> incomeCategories = new ArrayList<>() ;

    public Income(){
        transactions = new ArrayList<>();
    }

    public void setParentController(FeatureSelectionController controller) {
        this.featureSelectionController = controller;
    }

    @FXML
    public void onActionSwitchExpense (ActionEvent event) throws IOException {
        try {
            featureSelectionController.switchSpendingMoneyTab();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
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

    @Override
    protected List <CustomButton> getCategoryButton(){
        return incomeCategories ;
    }

}
