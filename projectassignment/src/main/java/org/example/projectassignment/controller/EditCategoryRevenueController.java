package org.example.projectassignment.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader ;
import javafx.scene.Scene ;
import javafx.scene.Node ;
import javafx.scene.Parent ;
import javafx.scene.control.Button ;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage ;
import org.example.projectassignment.view.CustomButton;

import java.io.IOException ;

import java.util.* ;

import static org.example.projectassignment.controller.SpendingMoneyController.spendingMoneyCategories;

public class EditCategoryRevenueController {
    @FXML
    private Button backButton ;
    @FXML
    private Button addButton ;

    @FXML
    private GridPane category ;
    private Stage stage ;
    public static boolean addRevenue = false ;
    public static boolean editRevenue = false ;
    @FXML
    public void onActionButtonSwitchRevenue(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/projectassignment/view/Revenue.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow() ;
        Scene scene = new Scene(root) ;
        stage.setScene(scene) ;
        stage.show() ;
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
    @FXML
    public void switchToEditButtonCategory(ActionEvent event) {
        try {
            CustomButton button = (CustomButton) event.getSource();
            FXMLLoader loader  = new FXMLLoader(getClass().getResource("/org/example/projectassignment/view/EditButtonCategory.fxml"));
            Parent root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root) ;
            stage.setScene(scene) ;
            editRevenue = true ;
            stage.show() ;
            EditButtonController controller = loader.getController() ;
            controller.getButton(button);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    public void switchToAddButtonCategory(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/projectassignment/view/AddButtonCategory.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow() ;
        Scene scene = new Scene(root) ;
        addRevenue = true ;
        stage.setScene(scene) ;
        stage.show() ;
    }
    public void updateCategory(List <CustomButton> categoryButtons) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projectassignment/view/EditCategoryRevenue.fxml"));
            GridPane root = loader.getRoot();
            category.getChildren().clear();
            int row = 0, col = 0;
            for (CustomButton button : categoryButtons) {
                button.setOnAction(event -> switchToEditButtonCategory(event));
                category.add(button, col, row);
                col++;
                if (col == 4) {
                    col = 0;
                    row++;
                }
            }
        }
}
