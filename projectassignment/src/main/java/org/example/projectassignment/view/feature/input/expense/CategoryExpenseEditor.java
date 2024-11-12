package org.example.projectassignment.view.feature.input.expense;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader ;
import javafx.scene.Scene ;
import javafx.scene.Node ;
import javafx.scene.Parent ;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage ;
import org.example.projectassignment.controller.feature.input.ManagerCategoryEditor;
import org.example.projectassignment.view.feature.input.CategoryEditor;
import org.example.projectassignment.view.feature.input.income.CategoryIncomeEditor;
import org.example.projectassignment.controller.ManagerUser;
import org.example.projectassignment.view.feature.FeatureSelection;
import org.example.projectassignment.model.CategoryImage;

import java.io.IOException ;
import java.util.List;
import java.util.Objects;

import static org.example.projectassignment.view.feature.input.income.Income.incomeCategories;


public class CategoryExpenseEditor extends ManagerCategoryEditor {
    @FXML
    private GridPane expenseGridPane;

    private Stage stage ;

    public static boolean addExpense = false ;

    public static boolean editExpense = false ;

    private ManagerUser managerUser;

    public void init(ManagerUser managerUser){
        this.managerUser = managerUser;
        updateCategoryExpense(managerUser.getExpenseCategory());
    }
    @FXML
    public void onActionSwitchExpense (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader (getClass().getResource("/org/example/projectassignment/view/feature/FeatureSelection.fxml"));
        Parent root = loader.load() ;
        FeatureSelection featureSelection = loader.getController();
        featureSelection.init(managerUser);
        Scene scene = new Scene(root , 600 , 750) ;
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
        currentStage.setScene(scene);
        featureSelection.switchExpenseMoneyTab();
    }

    @FXML
    public void switchToEditCategoryIncome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projectassignment/view/CategoryIncomeEditor.fxml"));
        Parent root = loader.load();
        CategoryIncomeEditor controller = loader.getController();
        controller.init(managerUser);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToEditButtonCategory(ActionEvent event) throws IOException {
        CategoryImage button = (CategoryImage) event.getSource();
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/org/example/projectassignment/view/EditButtonCategory.fxml"));
        Parent root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root) ;
        stage.setScene(scene) ;
        editExpense = true ;
        stage.show() ;
        CategoryEditor controller = loader.getController() ;
        controller.init(managerUser);
        controller.getButton(button);
    }
    @FXML
    public void switchToAddButtonCategory(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/org/example/projectassignment/view/AddButtonCategory.fxml")));
        Parent root = loader.load();
        CategoryEditor categoryEditor = loader.getController();
        categoryEditor.init(managerUser);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow() ;
        Scene scene = new Scene(root) ;
        addExpense = true;
        stage.setScene(scene) ;
        stage.show() ;
    }

    public void updateCategoryExpense(List<CategoryImage> categoryButtons){
        managerUser.updateCategory();
        expenseGridPane.getChildren().clear();
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
            expenseGridPane.add(button, col, row);
            col++;
            if (col == limitColumn) {
                col = 0;
                row++;
            }
        }
    }


}
