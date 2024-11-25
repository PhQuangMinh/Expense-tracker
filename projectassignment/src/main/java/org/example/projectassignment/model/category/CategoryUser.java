package org.example.projectassignment.model.category;

import org.example.projectassignment.common.TypeCategory;

public class CategoryUser{
//    private String idCategoryUser;
    private String idCategoryModel;
    private String nameCategory;
    private TypeCategory typeCategory;
    

    public CategoryUser(){
    }

    public CategoryUser(String idCategoryModel, String nameCategory, TypeCategory typeCategory) {
//        this.idCategoryUser = idCategoryUser;
        this.idCategoryModel = idCategoryModel;
        this.nameCategory = nameCategory;
        this.typeCategory = typeCategory;
    }

//    public String getIdCategoryUser() {
//        return idCategoryUser;
//    }
//
//    public void setIdCategoryUser(String idCategoryUser) {
//        this.idCategoryUser = idCategoryUser;
//    }

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

    public TypeCategory getTypeCategory() {
        return typeCategory;
    }

    public void setTypeCategory(TypeCategory typeCategory) {
        this.typeCategory = typeCategory;
    }

    @Override
    public String toString() {
        return "CategoryUser{" +
                "idCategoryModel='" + idCategoryModel + '\'' +
                ", nameCategory='" + nameCategory + '\'' +
                ", typeCategory=" + typeCategory +
                '}';
    }
}
