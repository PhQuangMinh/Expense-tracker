package org.example.projectassignment.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Transaction implements Serializable {
    private LocalDate date ;
    private String note;
    private double amount;
    private String category;

    public Transaction(LocalDate date , String note , double amount , String category) {
        this.date = date;
        this.note = note;
        this.amount = amount;
        this.category = category;
    }

    public LocalDate getDate(){
        return date;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    public String getNote(){
        return note;
    }

    public void setNote(String note){
        this.note = note;
    }

    public double getAmount(){
        return amount;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    @Override
    public String toString(){
        return date + " " + note + " " + amount + " " + category ;
    }
}
