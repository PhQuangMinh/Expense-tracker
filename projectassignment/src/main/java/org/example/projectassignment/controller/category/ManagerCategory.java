package org.example.projectassignment.controller.category;

import javafx.scene.image.Image;
import org.example.projectassignment.controller.firebase.FirebaseCategory;
import org.example.projectassignment.model.category.CategoryModel;
import org.example.projectassignment.model.category.CategoryUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerCategory {
    private final List<CategoryModel> listCategoryModels;
    private final Map<String, Image> mapCategory;

    public ManagerCategory() throws IOException {
        FirebaseCategory firebaseCategory = new FirebaseCategory();
        mapCategory = new HashMap<>();
        listCategoryModels = firebaseCategory.getListCategory("category");
        for (CategoryModel categoryModel:listCategoryModels){
            mapCategory.put(categoryModel.getIdCategory(), categoryModel.getImage());
            System.out.println(categoryModel);
        }
    }

    public List<CategoryUser> initListUserSignUp(){
        int quantityCategory = 5;
        List<CategoryUser> listCategoryUsers = new ArrayList<>();
        String[] nameCategory = new String[]{"a", "b", "c", "d", "e"};
        for (int i = 0; i < quantityCategory; i++) {
            CategoryUser categoryUser = new CategoryUser(String.valueOf(i), listCategoryModels.get(i).getIdCategory(), nameCategory[i]);
            listCategoryUsers.add(categoryUser);
        }
        return listCategoryUsers;
    }
    public List<CategoryModel> getListCategoryModels (){
        return listCategoryModels;
    }
}
