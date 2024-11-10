package org.example.projectassignment.view.feature.input.expense;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader ;
import javafx.scene.Scene ;
import javafx.scene.Node ;
import javafx.scene.Parent ;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage ;
import org.example.projectassignment.view.feature.input.CategoryEditor;
import org.example.projectassignment.view.feature.input.income.EditCategoryIncome;
import org.example.projectassignment.model.user.ManagerUser;
import org.example.projectassignment.view.feature.FeatureSelection;
import org.example.projectassignment.model.CustomButton;

import java.io.IOException ;
import java.util.List;
import java.util.Objects;

import static org.example.projectassignment.view.feature.input.income.Income.incomeCategories;

public class EditCategoryExpense {
    @FXML
    private GridPane spendingMoneyGridPane;
    private Stage stage ;
    public static boolean addSpendingMoney = false ;
    public static boolean editSpendingMoney = false ;

    private ManagerUser managerUser;

    public void init(ManagerUser managerUser){
        this.managerUser = managerUser;
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
    public void switchToEditCategoryRevenue(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projectassignment/view/EditCategoryRevenue.fxml"));
        Parent root = loader.load();
        EditCategoryIncome controller = loader.getController();
        controller.init(managerUser);
        controller.updateCategory(incomeCategories);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToEditButtonCategory(ActionEvent event) throws IOException {
        CustomButton button = (CustomButton) event.getSource();
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/org/example/projectassignment/view/EditButtonCategory.fxml"));
        Parent root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root) ;
        stage.setScene(scene) ;
        editSpendingMoney = true ;
        stage.show() ;
        CategoryEditor controller = loader.getController() ;
        controller.init(managerUser);
        controller.getButton(button);
    }
    @FXML
    public void switchToAddButtonCategory(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/projectassignment/view/AddButtonCategory.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow() ;
        Scene scene = new Scene(root) ;
        addSpendingMoney = true;
        stage.setScene(scene) ;
        stage.show() ;
    }
    public void updateCategorySpendingMoney(List<CustomButton> categoryButtons) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projectassignment/view/EditCategorySpendingMoney.fxml"));
        GridPane root = loader.getRoot();
        spendingMoneyGridPane.getChildren().clear();
        int row = 0, col = 0;
        int limitColumn = 4 ;
        for (CustomButton button : categoryButtons) {
            button.setOnAction(event -> {
                try {
                    switchToEditButtonCategory(event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            spendingMoneyGridPane.add(button, col, row);
            col++;
            if (col == limitColumn) {
                col = 0;
                row++;
            }
        }
    }


}
