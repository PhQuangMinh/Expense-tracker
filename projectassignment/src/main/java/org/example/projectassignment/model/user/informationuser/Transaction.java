package org.example.projectassignment.model.user.informationuser;

import org.example.projectassignment.common.TypeCategory;

import java.io.Serializable;

public class Transaction implements Serializable {
    private String idTransaction;
    private String note;
    private long amount;
    private String category;
    private TypeCategory typeTransaction;
    private String idCategory;

    public Transaction(){
    }

    public Transaction(String note , long amount , String category, TypeCategory typeTransaction, String idCategory) {
        this.note = note;
        this.amount = amount;
        this.category = category;
        this.typeTransaction = typeTransaction;
        this.idCategory = idCategory;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public TypeCategory getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(TypeCategory typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    @Override
    public String toString(){
        return typeTransaction + " " + note + " " + amount + " " + category ;
    }
}
