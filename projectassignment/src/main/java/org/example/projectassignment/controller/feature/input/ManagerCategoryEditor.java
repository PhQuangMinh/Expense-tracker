package org.example.projectassignment.controller.feature.input;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.example.projectassignment.controller.ManagerUser;
import org.example.projectassignment.model.CategoryImage;
import org.example.projectassignment.model.category.CategoryUser;
import org.example.projectassignment.view.utils.Notification;

import java.util.List;

public class ManagerCategoryEditor {
    protected Notification notification = new Notification();

    protected CategoryUser categoryUserEditor;

    protected CategoryImage categoryImageChooser;

    protected void setCategoryImage(CategoryImage categoryImage) {
        categoryImage.setPrefSize(80, 80);
        categoryImage.setStyle("-fx-text-fill: transparent;");
        categoryImage.setContentDisplay(ContentDisplay.CENTER);
        GridPane.setHalignment(categoryImage, HPos.CENTER);
        GridPane.setValignment(categoryImage, VPos.CENTER);
    }

    protected void addCategoryImage(GridPane gridPane, List<CategoryImage> listCategoryImages) {
        gridPane.getChildren().clear();
        int row = 0, col = 0;
        int limitColumn = 5;
        for (CategoryImage button : listCategoryImages) {
            gridPane.add(button, col, row);
            col++;
            if (col == limitColumn) {
                col = 0;
                row++;
            }
        }
    }

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
}
