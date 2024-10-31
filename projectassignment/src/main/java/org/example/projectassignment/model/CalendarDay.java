package org.example.projectassignment.model;

public class CalendarDay {
    private String date;
    private String income;
    private String expense;
    private String category;
    public CalendarDay(String date, String income, String expense, String category) {
        this.date = date;
        this.income = income;
        this.expense = expense;
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
