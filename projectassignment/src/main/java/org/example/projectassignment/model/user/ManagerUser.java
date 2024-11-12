package org.example.projectassignment.model.user;

import javafx.fxml.Initializable;
import org.example.projectassignment.controller.category.ManagerCategory;
import org.example.projectassignment.model.CustomButton;
import org.example.projectassignment.model.category.CategoryModel;
import org.example.projectassignment.model.category.CategoryUser;
import org.example.projectassignment.model.user.informationuser.User;

import java.util.ArrayList;
import java.util.List;

public class ManagerUser  {
    private User user;
    private ManagerCategory managerCategory;
    private List<CustomButton> expenseCategory ;
    private List<CustomButton> incomeCategory ;
    public ManagerUser(User user, ManagerCategory managerCategory) {
        this.user = user;
        this.managerCategory = managerCategory;
        expenseCategory = initExpenseCategory();
        incomeCategory = initIncomeCategory() ;
    }

    public ManagerCategory getManagerCategory() {
        return managerCategory;
    }

    public void setManagerCategory(ManagerCategory managerCategory) {
        this.managerCategory = managerCategory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CustomButton> getExpenseCategory() {
        return expenseCategory;
    }

    public List<CustomButton> getIncomeCategory() {
        return incomeCategory;
    }

    public void setExpenseCategory(List<CustomButton> expenseCategory) {
        this.expenseCategory = expenseCategory;
    }
    private List<CustomButton>  initIncomeCategory() {
        List <CategoryUser>  listCategoryUser = user.getListCategoryUsers() ;
        List <CustomButton> initCategory = new ArrayList<>();
        for(CategoryUser categoryUser : listCategoryUser){
            for(CategoryModel categoryModel : this.getManagerCategory().getListCategoryModels() ){
                if(categoryUser.getIdCategoryModel().equals(categoryModel.getIdCategory())) {
                    CustomButton customButton = new CustomButton(categoryUser.getNameCategory(), categoryModel.getImage());
                    initCategory.add(customButton);
                    break;
                }
            }
        }
        return initCategory;
    }

    private List<CustomButton>  initExpenseCategory() {
        List <CategoryUser>  listCategoryUser = user.getListCategoryUsers() ;
        List <CustomButton> initCategory = new ArrayList<>();
        for(CategoryUser categoryUser : listCategoryUser){
            for(CategoryModel categoryModel : this.getManagerCategory().getListCategoryModels() ){
                if(categoryUser.getIdCategoryModel().equals(categoryModel.getIdCategory())) {
                    CustomButton customButton = new CustomButton(categoryUser.getNameCategory(), categoryModel.getImage());
                    initCategory.add(customButton);
                    break;
                }
            }
        }
        return initCategory;
    }

}
