package org.example.projectassignment.controller.feature.input.category;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.GridPane;
import org.example.projectassignment.controller.ManagerUser;
import org.example.projectassignment.model.CategoryImage;
import org.example.projectassignment.model.category.CategoryModel;
import org.example.projectassignment.model.category.CategoryUser;
import org.example.projectassignment.model.user.informationuser.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        categoryImageChooser = (CategoryImage) event.getSource();
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
            gridPane.add(button, col, row);
            col++;
            if (col == limitColumn) {
                col = 0;
                row++;
            }
        }
    }

    protected void setCategoryImageChooser(Transaction transaction, ManagerUser managerUser){
        Optional<CategoryUser> categoryUserOptional = managerUser.getUser().getListCategoryUsers().stream()
                .filter(cattegory -> cattegory.getNameCategory().equals(transaction.getCategory()))
                .findFirst();
        if (categoryUserOptional.isPresent()) {
            Optional<CategoryModel> categoryModelOptional = managerUser.getManagerCategory().getListCategoryModels().stream()
                    .filter(category -> category.getIdCategory().equals(categoryUserOptional.get().getIdCategoryModel()))
                    .findFirst();
            categoryModelOptional.ifPresent(categoryModel -> categoryImageChooser = new CategoryImage(categoryUserOptional.get().getNameCategory(), categoryModel.getImage()));
        }
    }
}
