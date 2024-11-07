package org.example.projectassignment.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.example.projectassignment.model.CustomButton;
import java.io.IOException;
import static org.example.projectassignment.common.Constant.sizeButton;
import static org.example.projectassignment.controller.EditCategoryExpense.addSpendingMoney;
import static org.example.projectassignment.controller.EditCategoryExpense.editSpendingMoney;
import static org.example.projectassignment.controller.Income.incomeCategories;
import static org.example.projectassignment.controller.Expense.expenseCategories ;
import static org.example.projectassignment.controller.EditCategoryIncome.addRevenue;
import static org.example.projectassignment.controller.EditCategoryIncome.editRevenue;


public class EditButton {
    @FXML
    private TextField nameButton ;
    @FXML
    private TextField editNameButton ;

    private CustomButton customButton;
    private Image image ;
    private Stage stage ;

    @FXML
    private void deleteButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận ");
        alert.setHeaderText("Bạn có chắc chắn xóa nút không? ");
        if(alert.showAndWait().get() == ButtonType.OK){
            if(incomeCategories.contains(customButton)) {
                incomeCategories.remove(customButton);
                switchToEditCategoryRevenue(event);
            }
            else{
                expenseCategories.remove(customButton);
                switchToEditCategorySpendingMoney(event);
            }
            notification("Xóa nút thành công!");
        }
    }

    @FXML
    private void getImage(ActionEvent event) {
        Button button = (Button) event.getSource();
        if (button.getGraphic() instanceof ImageView) {
            ImageView imageView = (ImageView) button.getGraphic();
            imageView.setFitHeight(70);
            imageView.setFitWidth(70);
            image = imageView.getImage() ;
        }
    }
    public void getButton(CustomButton button){
        customButton = button;
        nameButton.setText(button.getName());
        editNameButton.setText(button.getName());
        image = button.getImage() ;
    }

    @FXML
    private void saveEdit(ActionEvent event) throws IOException {
        customButton.setName(editNameButton.getText());
        customButton.setImage(image);
        customButton.setButton() ;
        customButton.setPrefHeight(sizeButton);
        customButton.setPrefWidth(sizeButton);
        if(editSpendingMoney){
            switchToEditCategorySpendingMoney(event);
            editSpendingMoney = false;
        }
        else{
            switchToEditCategoryRevenue(event);
            editRevenue = false;
        }
        notification("Lưu thành công!");
    }

    @FXML
    private void addButton(ActionEvent event) throws IOException {
        String name = editNameButton.getText();
        CustomButton newButton = new CustomButton(name,image ) ;
        newButton.setButton();
        newButton.setPrefHeight(sizeButton);
        newButton.setPrefWidth(sizeButton);
        if(image == null  && name.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Bạn cần thêm tên nút hoặc ảnh của nút!");
            alert.showAndWait();
            return;
        }
        if(addSpendingMoney){
            expenseCategories.add(newButton) ;
            switchToEditCategorySpendingMoney(event);
            addSpendingMoney = false;
        }
        else {
            incomeCategories.add(newButton);
            switchToEditCategoryRevenue(event);
            addRevenue = false;
        }
        notification("Thêm nút thành công!");
    }

    @FXML
    public void switchToEditCategoryRevenue(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projectassignment/view/EditCategoryRevenue.fxml"));
        Parent root = loader.load();
        EditCategoryIncome controller = loader.getController();
        controller.updateCategory(incomeCategories);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToEditCategorySpendingMoney(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projectassignment/view/EditCategorySpendingMoney.fxml"));
        Parent root = loader.load();
        EditCategoryExpense controller = loader.getController();
        controller.updateCategorySpendingMoney(expenseCategories);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void goBackEdit(ActionEvent event) throws IOException {
        if(editRevenue){
            switchToEditCategoryRevenue(event);
            editRevenue = false;
        }
        else{
            switchToEditCategorySpendingMoney(event);
            editSpendingMoney = false;
        }

    }

    @FXML
    public void goBackAdd(ActionEvent event) throws IOException {
        if(addRevenue){
            switchToEditCategoryRevenue(event);
            addRevenue = false;
        }
        else{
            switchToEditCategorySpendingMoney(event);
            addSpendingMoney = false;
        }
    }

    private void notification(String text){
        Stage notificationStage = new Stage();
        notificationStage.initStyle(StageStyle.UNDECORATED);
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 10px;");
        Label message = new Label(text);
        root.getChildren().add(message);
        Scene scene = new Scene(root);
        notificationStage.setScene(scene);
        notificationStage.show();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> notificationStage.close()));
        timeline.setCycleCount(1);
        timeline.play();
    }
}
