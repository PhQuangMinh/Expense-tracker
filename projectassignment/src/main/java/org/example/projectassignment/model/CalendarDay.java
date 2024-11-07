package org.example.projectassignment.model;

import java.time.LocalDate;
import java.util.List;

public class CalendarDay {
    private String date;
    private List<Transaction> listTransactions;

    public CalendarDay(){

    }

    public CalendarDay(String date, List<Transaction> listTransaction) {
        this.date = date;
        this.listTransactions = listTransaction;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Transaction> getListTransactions() {
        return listTransactions;
    }

    public void setListTransactions(List<Transaction> listTransactions) {
        this.listTransactions = listTransactions;
    }
}
