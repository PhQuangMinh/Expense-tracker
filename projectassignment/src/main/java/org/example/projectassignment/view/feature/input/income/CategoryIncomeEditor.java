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
import org.example.projectassignment.controller.feature.input.category.ManagerCategoryEditor;
import org.example.projectassignment.view.feature.input.CategoryEditor;
import org.example.projectassignment.controller.ManagerUser;
import org.example.projectassignment.view.feature.FeatureSelection;
import org.example.projectassignment.model.CategoryImage;
import org.example.projectassignment.view.feature.input.expense.CategoryExpenseEditor;

import java.io.IOException ;

import java.util.* ;

public class CategoryIncomeEditor extends ManagerCategoryEditor {
    @FXML
    private Button backButton ;

    @FXML
    private Button addButton ;

    @FXML
    private GridPane category ;

    private Stage stage ;

    public static boolean addIncome = false ;

    public static boolean editIncome = false ;

    private ManagerUser managerUser;

    public void init(ManagerUser managerUser) throws IOException {
        this.managerUser = managerUser;
        updateCategory(managerUser.getIncomeCategory());
        addButton.setOnAction(event -> {
            try {
                switchToAddButtonCategory(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        backButton.setOnAction(event -> {
            try {
                onActionButtonSwitchIncome(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
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
    public void switchToCategoryExpenseEditor(ActionEvent event) throws IOException{
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/org/example/projectassignment/view/CategoryExpenseEditor.fxml"));
        Parent root = loader.load();
        CategoryExpenseEditor controller = loader.getController();
        controller.init(managerUser);
        managerUser.updateCategory();
        controller.updateCategoryExpense(managerUser.getExpenseCategory());
        stage = (Stage)((Node)event.getSource()).getScene().getWindow() ;
        Scene scene = new Scene(root) ;
        stage.setScene(scene) ;
        stage.show() ;
    }
    @FXML
    public void switchToEditButtonCategory(ActionEvent event) throws IOException {
        CategoryImage button = (CategoryImage) event.getSource();
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/org/example/projectassignment/view/EditButtonCategory.fxml"));
        Parent root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root) ;
        stage.setScene(scene) ;
        editIncome = true ;
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
        addIncome = true ;
        stage.setScene(scene) ;
        stage.show() ;
    }
    public void updateCategory(List<CategoryImage> categoryButtons){
        managerUser.updateCategory();
        category.getChildren().clear();
        int row = 0, col = 0;
        int limitColumn = 5;
        for (CategoryImage button : categoryButtons) {
            button.setOnAction(event -> {
                try {
                    switchToEditButtonCategory(event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            category.add(button, col, row);
            col++;
            if (col == limitColumn) {
                col = 0;
                row++;
            }
        }
    }
}
