package org.example.projectassignment.view.feature.input;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.projectassignment.common.TypeCategory;
import org.example.projectassignment.controller.feature.input.category.ManagerCategoryEditor;
import org.example.projectassignment.model.CategoryImage;
import org.example.projectassignment.controller.ManagerUser;
import org.example.projectassignment.model.category.CategoryModel;
import org.example.projectassignment.model.category.CategoryUser;
import org.example.projectassignment.view.feature.input.expense.CategoryExpenseEditor;
import org.example.projectassignment.view.feature.input.income.CategoryIncomeEditor;
import org.example.projectassignment.view.utils.Notification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.example.projectassignment.view.feature.input.expense.CategoryExpenseEditor.addExpense;
import static org.example.projectassignment.view.feature.input.expense.CategoryExpenseEditor.editExpense;
import static org.example.projectassignment.view.feature.input.income.CategoryIncomeEditor.addIncome;
import static org.example.projectassignment.view.feature.input.income.CategoryIncomeEditor.editIncome;


public class CategoryEditor extends ManagerCategoryEditor {
    @FXML
    private TextField editNameButton;

    @FXML
    private GridPane categoryImagesGrid;

    @FXML
    private ScrollPane scrollPane;

    private Stage stage ;

    private ManagerUser managerUser;

    public void init(ManagerUser managerUser) {
        this.managerUser = managerUser;
        notification = new Notification();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setActionImageCategory(managerUser, categoryImagesGrid);
    }

    @FXML
    private void deleteButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận ");
        alert.setHeaderText("Bạn có chắc chắn xóa nút không? ");
        if(alert.showAndWait().get() == ButtonType.OK){
            managerUser.getUser().getListCategoryUsers().remove(categoryUserEditor);
            if (categoryUserEditor.getTypeCategory() == TypeCategory.EXPENSE){
                switchToCategoryExpenseEditor(event);
            }
            else {
                switchToEditCategoryIncome(event);
            }
            notification.notification("Xóa nút thành công!");
        }
    }

    public void getButton(CategoryImage button){
        Optional<CategoryUser> findCategoryUser = managerUser.getUser().getListCategoryUsers().stream()
                        .filter(categoryUser -> categoryUser.getNameCategory().equals(button.getName()))
                                .findFirst();
        if(findCategoryUser.isPresent()){
            categoryUserEditor = findCategoryUser.get();
            editNameButton.setText(button.getName());
            categoryImageChooser = button;
        }
        else{
            System.out.println("Không tìm thấy");
        }
    }

    @FXML
    private void saveEdit(ActionEvent event) throws IOException {
        if (isValidCategory(editNameButton, managerUser)){
            return;
        }
        if (!Objects.equals(editNameButton.getText(), categoryUserEditor.getNameCategory())){
            Optional<CategoryUser> findCategoryUser = managerUser.getUser().getListCategoryUsers().stream()
                    .filter(categoryUser -> categoryUser.getNameCategory().equals(editNameButton.getText()))
                    .findFirst();
            if (findCategoryUser.isPresent()){
                notification.notification("Tên danh mục đã tồn tại");
                return;
            }
        }
        categoryUserEditor.setNameCategory(editNameButton.getText());
        categoryUserEditor.setIdCategoryModel(categoryImageChooser.getName());
        if(editExpense){
            switchToCategoryExpenseEditor(event);
            editExpense = false;
        }
        else{
            switchToEditCategoryIncome(event);
            editIncome = false;
        }
        notification.notification("Lưu thành công!");
    }

    @FXML
    private void addButton(ActionEvent event) throws IOException {
        if (isValidCategory(editNameButton, managerUser)){
            return;
        }
        Optional<CategoryUser> findCategoryUser = managerUser.getUser().getListCategoryUsers().stream()
                .filter(categoryUser -> categoryUser.getNameCategory().equals(editNameButton.getText()))
                .findFirst();
        if (findCategoryUser.isPresent()){
            notification.notification("Tên danh mục đã tồn tại");
            return;
        }
        if(addExpense){
            CategoryUser newCategoryUser = new CategoryUser(categoryImageChooser.getName(), editNameButton.getText(), TypeCategory.EXPENSE);
            managerUser.getUser().getListCategoryUsers().add(newCategoryUser);
            switchToCategoryExpenseEditor(event);
            addExpense = false;
        }
        else {
            CategoryUser newCategoryUser = new CategoryUser(categoryImageChooser.getName(), editNameButton.getText(), TypeCategory.INCOME);
            managerUser.getUser().getListCategoryUsers().add(newCategoryUser);
            switchToEditCategoryIncome(event);
            addIncome = false;
        }
        notification.notification("Thêm nút thành công!");
    }

    @FXML
    public void switchToEditCategoryIncome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projectassignment/view/CategoryIncomeEditor.fxml"));
        Parent root = loader.load();
        CategoryIncomeEditor controller = loader.getController();
        controller.init(managerUser);
        managerUser.updateCategory();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToCategoryExpenseEditor(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projectassignment/view/CategoryExpenseEditor.fxml"));
        Parent root = loader.load();
        CategoryExpenseEditor controller = loader.getController();
        controller.init(managerUser);
        managerUser.updateCategory();
        controller.updateCategoryExpense(managerUser.getExpenseCategory());
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void goBackEdit(ActionEvent event) throws IOException {
        if(editIncome){
            switchToEditCategoryIncome(event);
            editIncome = false;
        }
        else{
            switchToCategoryExpenseEditor(event);
            editExpense = false;
        }

    }

    @FXML
    public void goBackAdd(ActionEvent event) throws IOException {
        if(addIncome){
            switchToEditCategoryIncome(event);
            addIncome = false;
        }
        else{
            switchToCategoryExpenseEditor(event);
            addExpense = false;
        }
    }
}
