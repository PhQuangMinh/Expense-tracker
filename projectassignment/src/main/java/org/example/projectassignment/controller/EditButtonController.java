package org.example.projectassignment.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.projectassignment.view.CustomButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.projectassignment.controller.EditCategorySpendingMoneyController.addSpendingMoney;
import static org.example.projectassignment.controller.EditCategorySpendingMoneyController.editSpendingMoney;
import static org.example.projectassignment.controller.RevenueController.categoryButtons;
import static org.example.projectassignment.controller.SpendingMoneyController.spendingMoneyCategories ;
import static org.example.projectassignment.controller.EditCategoryRevenueController.addRevenue;
import static org.example.projectassignment.controller.EditCategoryRevenueController.editRevenue;


public class EditButtonController {
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
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to delete this button?");
        if(alert.showAndWait().get() == ButtonType.OK){
            if(categoryButtons.contains(customButton)) {
                categoryButtons.remove(customButton);
                switchToEditCategoryRevenue(event);
            }
            else{
                spendingMoneyCategories.remove(customButton);
                switchToEditCategorySpendingMoney(event);
            }
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
        customButton = button ;
        nameButton.setText(customButton.getName());
        editNameButton.setText(customButton.getName());
    }

    @FXML
    private void saveEdit(ActionEvent event) throws IOException {
        customButton.setName(editNameButton.getText());
        customButton.setMaxHeight(100);
        customButton.setMinHeight(100);
        customButton.setMaxWidth(100);
        customButton.setMinWidth(100);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        customButton.setGraphic(imageView);
        customButton.setContentDisplay(ContentDisplay.TOP);
        if(editSpendingMoney){
            switchToEditCategorySpendingMoney(event);
            editSpendingMoney = false;
        }
        else{
            switchToEditCategoryRevenue(event);
            editRevenue = false;
        }
    }

    @FXML
    private void addButton(ActionEvent event) throws IOException {
        String name = editNameButton.getText();
        CustomButton cb = new CustomButton(name,image ) ;
        cb.setMaxHeight(100);
        cb.setMinHeight(100);
        cb.setMaxWidth(100);
        cb.setMinWidth(100);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        cb.setGraphic(imageView);
        cb.setContentDisplay(ContentDisplay.TOP);
        if(addSpendingMoney){
            spendingMoneyCategories.add(cb) ;
            switchToEditCategorySpendingMoney(event);
            addSpendingMoney = false;
        }
        else {
            categoryButtons.add(cb);
            switchToEditCategoryRevenue(event);
            addRevenue = false;
        }
    }
    @FXML
    public void switchToEditCategoryRevenue(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projectassignment/view/EditCategoryRevenue.fxml"));
        Parent root = loader.load();
        EditCategoryRevenueController controller = loader.getController();
        controller.updateCategory(categoryButtons);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToEditCategorySpendingMoney(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projectassignment/view/EditCategorySpendingMoney.fxml"));
        Parent root = loader.load();
        EditCategorySpendingMoneyController controller = loader.getController();
        controller.updateCategorySpendingMoney(spendingMoneyCategories);
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
}
