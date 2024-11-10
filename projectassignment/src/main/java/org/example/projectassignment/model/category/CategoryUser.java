package org.example.projectassignment.model.category;

public class CategoryUser{
    private String idCategoryUser;
    private String idCategoryModel;
    private String nameCategory;


    public CategoryUser(){
    }

    public CategoryUser(String idCategoryUser, String idCategoryModel, String nameCategory) {
        this.idCategoryUser = idCategoryUser;
        this.idCategoryModel = idCategoryModel;
        this.nameCategory = nameCategory;
    }

    public String getIdCategoryUser() {
        return idCategoryUser;
    }

    public void setIdCategoryUser(String idCategoryUser) {
        this.idCategoryUser = idCategoryUser;
    }

    public String getIdCategoryModel() {
        return idCategoryModel;
    }

    public void setIdCategoryModel(String idCategoryModel) {
        this.idCategoryModel = idCategoryModel;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }
}
