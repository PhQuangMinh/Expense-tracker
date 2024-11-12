package org.example.projectassignment.view.feature.input.expense;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import org.example.projectassignment.common.TypeTransaction;
import org.example.projectassignment.controller.category.ManagerCategory;
import org.example.projectassignment.model.category.CategoryModel;
import org.example.projectassignment.model.category.CategoryUser;
import org.example.projectassignment.model.user.ManagerUser;
import org.example.projectassignment.model.user.informationuser.User;
import org.example.projectassignment.view.feature.FeatureSelection;
import org.example.projectassignment.controller.home.input.ManagerInput;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.projectassignment.model.CustomButton;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;

import static javafx.scene.control.ContentDisplay.CENTER;
import static javafx.scene.control.ContentDisplay.TOP;


public class Expense implements Initializable {
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField noteField;
    @FXML
    private TextField amountField;

    private String selectedCategory ;

    private FeatureSelection featureSelection;

    private ManagerUser managerUser;

    private ManagerInput managerInput;

    private User user ;

    @FXML
    private GridPane expenseGridPane ;

    private List <CustomButton> expenseCategory ;
    public static List<CustomButton> expenseCategories = new ArrayList<>();
    public void init(ManagerUser managerUser, FeatureSelection featureSelection) {
        this.featureSelection = featureSelection;
        this.managerUser = managerUser;
        managerInput = new ManagerInput();
    }

    @FXML
    private void switchToEditCategorySpendingMoney(ActionEvent event) throws IOException{
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/org/example/projectassignment/view/EditCategorySpendingMoney.fxml"));
        Parent root = loader.load();
        EditCategoryExpense controller = loader.getController();
        controller.init(managerUser);
        controller.updateCategorySpendingMoney(expenseCategories);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow() ;
        Scene scene = new Scene(root) ;
        stage.setScene(scene) ;
        stage.show() ;
    }

    @FXML
    public void onActionButtonSwitchRevenue() throws IOException {
        featureSelection.switchIncomeTab();
    }

    @FXML
    private void handleSubmit(){
        double amountValue;
        try {
            amountValue = Double.parseDouble(removeCommas(amountField.getText()));
            if (amountValue <= 0 || amountValue > Long.MAX_VALUE) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Số tiền không hợp lệ, bạn vui lòng nhập lại số tiền!");
            alert.showAndWait();
            return;
        }
        managerInput.addTransaction(datePicker, noteField, amountField, selectedCategory, managerUser.getUser(), TypeTransaction.EXPENSE);
        noteField.clear();
        amountField.clear();
        datePicker.setValue(null);
        notification();

    }

    @FXML
    private void handleCategorySelection(ActionEvent event){
        Button button = (Button) event.getSource() ;
        selectedCategory = button.getText() ;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datePicker.setValue(LocalDate.now());
        datePicker.setEditable(false);

        amountField.textProperty().addListener(new ChangeListener<String>() {
            private boolean changing = false;

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (changing) {
                    return;
                }
                changing = true;
                try{
                    if(newValue == null || newValue.isEmpty()){
                        amountField.setText("") ;
                    }
                    else{
                        String cleanString = removeCommas(newValue);
                        double parsed = Double.parseDouble(cleanString) ;
                        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
                        formatter.setGroupingUsed(true);
                        String formattedString = formatter.format(parsed);
                        amountField.setText(formattedString);
                    }
                }
                catch(NumberFormatException e){
                    amountField.setText(oldValue);
                }
                changing = false;
            }
        });
    }
    private String removeCommas(String input){
        return input.replace("," , "") ;
    }
    private void notification(){
        Stage notificationStage = new Stage();
        notificationStage.initStyle(StageStyle.UNDECORATED);
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 10px;");
        Label message = new Label("Đã nhập dữ liệu !");
        root.getChildren().add(message);
        Scene scene = new Scene(root);
        notificationStage.setScene(scene);
        notificationStage.show();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> notificationStage.close()));
        timeline.setCycleCount(1);
        timeline.play();
    }

    public void updateCategoryExpense() throws IOException {
        CustomButton editButton = new CustomButton("Chỉnh sửa" ,null)  ;
        editButton.setButton() ;
        editButton.setAlignment(Pos.CENTER);
        editButton.setContentDisplay(ContentDisplay.CENTER);
        editButton.setOnAction(event ->{
            try{
            switchToEditCategorySpendingMoney(event);
            }catch(IOException e){
            e.printStackTrace();}
                }
            );
        FXMLLoader loader = new FXMLLoader(Expense.class.getResource("SpendingMoney.fxml"));
        GridPane root = loader.getRoot();
        expenseGridPane.getChildren().clear();
        int row = 0, col = 0;
        int limitColumn = 4 ;
        for (CustomButton button : managerUser.getExpenseCategory()) {
            button.setButton();
            button.setOnAction(event -> {
                    handleCategorySelection(event);
            });
            expenseGridPane.add(button, col, row);
            col++;
            if (col == limitColumn) {
                col = 0;
                row++;
            }
        }
        expenseGridPane.add(editButton, col, row);
    }

}