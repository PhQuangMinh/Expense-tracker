package org.example.projectassignment.model;

import org.example.projectassignment.common.TypeTransaction;

import java.io.Serializable;
import java.time.LocalDate;

public class Transaction implements Serializable {
    private String note;
    private long amount;
    private String category;
    private TypeTransaction typeTransaction;

    public Transaction(){
    }

    public Transaction(String note , long amount , String category, TypeTransaction typeTransaction) {
        this.note = note;
        this.amount = amount;
        this.category = category;
        this.typeTransaction = typeTransaction;
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

    public TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    @Override
    public String toString(){
        return typeTransaction + " " + note + " " + amount + " " + category ;
    }
}
