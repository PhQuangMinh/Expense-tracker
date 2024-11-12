package org.example.projectassignment.controller.category;

import javafx.scene.image.Image;
import org.example.projectassignment.common.TypeCategory;
import org.example.projectassignment.model.category.CategoryModel;
import org.example.projectassignment.model.category.CategoryUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ManagerCategory {
    private final List<CategoryModel> listCategoryModels;

    public ManagerCategory() throws IOException, ExecutionException, InterruptedException {
        CategoryLoader categoryLoader = new CategoryLoader();
        listCategoryModels = categoryLoader.getListCategoryModels();
        System.out.println(listCategoryModels.size());
        for (CategoryModel categoryModel:listCategoryModels){
            System.out.println(categoryModel);
        }
    }

    public List<CategoryUser> initListUserSignUp(){
        List<CategoryUser> listCategoryUsers = new ArrayList<>();
        String[] idCategoryIncome = new String[]{"01", "11", "12", "14", "21", "27", "35"};
        String[] nameCategoryIncome = new String[]{"Heo đất", "Bán hàng", "Tiền nhận", "Đầu tư", "Quà tặng", "Gửi tiết kiệm", "Tiền lương"};
        addListCategory(listCategoryUsers, nameCategoryIncome, idCategoryIncome, TypeCategory.INCOME);
        String[] idCategoryExpense = new String[]{"05", "06", "16", "17", "18", "25", "26", "28", "31", "33"};
        String[] nameCategoryExpense = new String[]{"Bia", "Ăn sáng", "Gym", "Học tập", "Tiền điện", "Đi lại", "Giao lưu", "Mua sắm", "Sức khỏe", "Tiền nước"};
        addListCategory(listCategoryUsers, nameCategoryExpense, idCategoryExpense, TypeCategory.EXPENSE);
        return listCategoryUsers;
    }

    private void addListCategory(List<CategoryUser> listCategories, String[] nameCategory, String[] idCategory, TypeCategory typeCategory){
        for (int i = 0; i < nameCategory.length; i++) {
            CategoryUser categoryUser = new CategoryUser(idCategory[i], nameCategory[i], typeCategory);
            listCategories.add(categoryUser);
        }
    }

    public List<CategoryModel> getListCategoryModels (){
        return listCategoryModels;
    }
}