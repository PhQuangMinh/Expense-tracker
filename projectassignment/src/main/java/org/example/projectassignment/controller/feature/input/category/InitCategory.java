package org.example.projectassignment.controller.feature.input.category;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.GridPane;
import org.example.projectassignment.controller.ManagerUser;
import org.example.projectassignment.model.CategoryImage;
import org.example.projectassignment.model.category.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class InitCategory {

    protected CategoryImage categoryImageChooser = new CategoryImage();
    private final List<CategoryImage> categoryImageList = new ArrayList<>();

    protected void setCategoryImage(CategoryImage categoryImage) {
        categoryImage.setPrefSize(80, 80);
        categoryImage.setStyle("-fx-text-fill: transparent;");
        categoryImage.setContentDisplay(ContentDisplay.CENTER);
        GridPane.setHalignment(categoryImage, HPos.CENTER);
        GridPane.setValignment(categoryImage, VPos.CENTER);
    }

    private void getImage(ActionEvent event) {
        CategoryImage categoryImage = (CategoryImage) event.getSource();
        if (categoryImageChooser != null) {
            categoryImageChooser.setStyle("-fx-font-weight: bold; -fx-border-color: transparent; -fx-background-color: #ddd;-fx-text-fill: transparent;");
        }

        categoryImageChooser = categoryImage;
        categoryImage.setStyle("-fx-font-weight: bold; -fx-border-width: 2px; -fx-background-color: #ddd;-fx-border-color: black;-fx-text-fill: transparent;" +
                "    -fx-border-width: 2px;" +
                "    -fx-border-radius: 2px;");

    }

    protected void setActionImageCategory(ManagerUser managerUser, GridPane categoryImagesGrid){
        for (CategoryModel categoryModel : managerUser.getManagerCategory().getListCategoryModels()){
            CategoryImage categoryImage = new CategoryImage(categoryModel.getIdCategory(), categoryModel.getImage());
            setCategoryImage(categoryImage);
            categoryImage.setOnAction(this::getImage);
            categoryImageList.add(categoryImage);
        }
        addCategoryImage(categoryImagesGrid, categoryImageList);
    }

    protected void addCategoryImage(GridPane gridPane, List<CategoryImage> listCategoryImages) {
        gridPane.getChildren().clear();
        int row = 0, col = 0;
        int limitColumn = 5;
        for (CategoryImage button : listCategoryImages) {
            button.setStyle("-fx-font-weight: bold; -fx-border-color: transparent; -fx-background-color: #ddd;-fx-text-fill: transparent;");
            gridPane.add(button, col, row);
            col++;
            if (col == limitColumn) {
                col = 0;
                row++;
            }
        }
    }
}
