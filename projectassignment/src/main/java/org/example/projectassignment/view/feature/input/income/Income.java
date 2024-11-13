package org.example.projectassignment.view.feature.input.income;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.example.projectassignment.common.TypeCategory;
import org.example.projectassignment.controller.ManagerUser;
import org.example.projectassignment.view.feature.FeatureSelection;
import org.example.projectassignment.controller.feature.input.ManagerInput;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.projectassignment.model.CategoryImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Income extends ManagerInput{
    @FXML
    private DatePicker datePicker;

    public static List<CategoryImage> incomeCategories = new ArrayList<>() ;

    @FXML
    private GridPane incomeGridPane ;

    @FXML
    private TextField noteField;

    @FXML
    private TextField amountField;

    @FXML
    private Button submitButton;

    @FXML
    private ScrollPane scrollPane;

    private FeatureSelection featureSelection;

    private ManagerUser managerUser;

    public void init(ManagerUser managerUser, FeatureSelection featureSelection) {
        this.managerUser = managerUser;
        this.featureSelection = featureSelection;
        setDatePicker(datePicker);
        helper.setAmountField(amountField);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        submitButton.setOnAction(event -> handleSubmit(amountField, datePicker, noteField, managerUser, TypeCategory.INCOME));
    }

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
    public void onActionSwitchExpense () throws IOException {
        featureSelection.switchExpenseMoneyTab();
    }

    public void updateCategoryIncome(){
        setEditButton(editButton);
        editButton.setOnAction(event ->{
            try{
                switchToEditCategoryIncome(event);
            }catch(IOException e){
                e.printStackTrace();
            }
        });
        updateCategory(editButton, managerUser, incomeGridPane, managerUser.getIncomeCategory());
    }
}
