package org.example.projectassignment.controller.feature.input.category;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.example.projectassignment.controller.ManagerUser;
import org.example.projectassignment.model.CategoryImage;
import org.example.projectassignment.model.category.CategoryUser;
import org.example.projectassignment.view.utils.Notification;


public class ManagerCategoryEditor extends InitCategory{
    protected Notification notification = new Notification();

    protected CategoryUser categoryUserEditor;

    protected boolean isValidCategory(TextField editNameButton, ManagerUser managerUser){
        if (editNameButton.getText().isEmpty()){
            notification.notification("Tên danh mục không được để trống");
            return true;
        }
        if(categoryImageChooser == null){
            notification.notification("Bạn chưa chọn hình ảnh danh mục");
            return true;
        }
        return false;
    }

    protected void setCategoryImage(CategoryImage categoryImage){
        GridPane.setHalignment(categoryImage, HPos.CENTER);
        GridPane.setValignment(categoryImage, VPos.CENTER);
        categoryImage.setStyle("-fx-font-weight: bold; -fx-border-color: transparent; -fx-background-color: #ddd;");
    }
}
