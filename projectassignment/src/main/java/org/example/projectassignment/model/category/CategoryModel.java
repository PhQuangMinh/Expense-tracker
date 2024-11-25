package org.example.projectassignment.model.category;

import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

public class CategoryModel implements Comparable<CategoryModel> {
    private String idCategory;
    private Image image;

    public CategoryModel(){

    }

    public CategoryModel(String idCategory, Image image) {
        this.idCategory = idCategory;
        this.image = image;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public int compareTo(@NotNull CategoryModel o) {
        return this.idCategory.compareTo(o.idCategory);
    }

    @Override
    public String toString(){
        return idCategory;
    }
}
