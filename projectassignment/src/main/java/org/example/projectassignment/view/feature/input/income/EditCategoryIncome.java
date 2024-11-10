package org.example.projectassignment.view.feature.input.income;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader ;
import javafx.scene.Scene ;
import javafx.scene.Node ;
import javafx.scene.Parent ;
import javafx.scene.control.Button ;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage ;
import org.example.projectassignment.view.feature.input.CategoryEditor;
import org.example.projectassignment.model.user.ManagerUser;
import org.example.projectassignment.view.feature.FeatureSelection;
import org.example.projectassignment.model.CustomButton;
import org.example.projectassignment.view.feature.input.expense.EditCategoryExpense;

import java.io.IOException ;

import java.util.* ;

import static org.example.projectassignment.view.feature.input.expense.Expense.expenseCategories;

public class EditCategoryIncome {
    @FXML
    private Button backButton ;
    @FXML
    private Button addButton ;

    @FXML
    private GridPane category ;
    private Stage stage ;
    public static boolean addRevenue = false ;
    public static boolean editRevenue = false ;

    private ManagerUser managerUser;

    public void init(ManagerUser managerUser) {
        this.managerUser = managerUser;
    }

    @FXML
    public void onActionButtonSwitchIncome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader (getClass().getResource("/org/example/projectassignment/view/feature/FeatureSelection.fxml"));
        Parent root = loader.load() ;
        FeatureSelection featureSelection = loader.getController() ;
        featureSelection.init(managerUser);
        Scene scene = new Scene(root , 600 , 750) ;
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
        currentStage.setScene(scene);
        featureSelection.switchIncomeTab();
    }
    @FXML
    public void switchToEditCategoryExpense(ActionEvent event) throws IOException{
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/org/example/projectassignment/view/EditCategorySpendingMoney.fxml"));
        Parent root = loader.load();
        EditCategoryExpense controller = loader.getController();
        controller.init(managerUser);
        controller.updateCategorySpendingMoney(expenseCategories);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow() ;
        Scene scene = new Scene(root) ;
        stage.setScene(scene) ;
        stage.show() ;
    }
    @FXML
    public void switchToEditButtonCategory(ActionEvent event) throws IOException {
        CustomButton button = (CustomButton) event.getSource();
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/org/example/projectassignment/view/EditButtonCategory.fxml"));
        Parent root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root) ;
        stage.setScene(scene) ;
        editRevenue = true ;
        stage.show() ;
        CategoryEditor controller = loader.getController() ;
        controller.init(managerUser);
        controller.getButton(button);
    }
    @FXML
    public void switchToAddButtonCategory(ActionEvent event) throws IOException{
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/org/example/projectassignment/view/AddButtonCategory.fxml"));
        Parent root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow() ;
        Scene scene = new Scene(root) ;
        CategoryEditor categoryEditor = loader.getController();
        categoryEditor.init(managerUser);
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
                button.setOnAction(event -> {
                    try {
                        switchToEditButtonCategory(event);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                int limitColumn = 4 ;
                category.add(button, col, row);
                col++;
                if (col == limitColumn) {
                    col = 0;
                    row++;
                }
            }
        }
}
