package org.example.projectassignment.controller;

import org.example.projectassignment.common.TypeCategory;
import org.example.projectassignment.controller.feature.input.category.ManagerCategory;
import org.example.projectassignment.controller.firebase.FirebaseUser;
import org.example.projectassignment.model.CategoryImage;
import org.example.projectassignment.model.category.CategoryModel;
import org.example.projectassignment.model.category.CategoryUser;
import org.example.projectassignment.model.user.informationuser.User;

import java.util.ArrayList;
import java.util.List;

public class ManagerUser  {
    private User user;
    private ManagerCategory managerCategory;
    private List<CategoryImage> expenseCategory = new ArrayList<>();
    private List<CategoryImage> incomeCategory = new ArrayList<>();
    private FirebaseUser firebaseUser;

    public ManagerUser(User user, FirebaseUser firebaseUser, ManagerCategory managerCategory) {
        this.user = user;
        this.firebaseUser = firebaseUser;
        this.managerCategory = managerCategory;
        loadCategory();
    }

    public void updateCategory(){
        expenseCategory.clear();
        incomeCategory.clear();
        loadCategory();
    }

    private void  loadCategory() {
        for(CategoryUser categoryUser : user.getListCategoryUsers()){
            for(CategoryModel categoryModel : this.getManagerCategory().getListCategoryModels() ){
                if(categoryUser.getIdCategoryModel().equals(categoryModel.getIdCategory())) {
                    CategoryImage categoryImage = new CategoryImage(categoryUser.getNameCategory(), categoryModel.getImage());
                    categoryImage.setPrefSize(70, 70);
                    if (categoryUser.getTypeCategory() == TypeCategory.EXPENSE){
                        expenseCategory.add(categoryImage);
                    }
                    else{
                        incomeCategory.add(categoryImage);
                    }
                }
            }
        }
    }

    public void setExpenseCategory(List<CategoryImage> expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ManagerCategory getManagerCategory() {
        return managerCategory;
    }

    public void setManagerCategory(ManagerCategory managerCategory) {
        this.managerCategory = managerCategory;
    }

    public List<CategoryImage> getExpenseCategory() {
        return expenseCategory;
    }

    public List<CategoryImage> getIncomeCategory() {
        return incomeCategory;
    }

    public void setIncomeCategory(List<CategoryImage> incomeCategory) {
        this.incomeCategory = incomeCategory;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }
}
