package org.example.projectassignment.model;

import org.example.projectassignment.common.TypeTransaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<CalendarDay> listCalendarDays;
    private List<CategoryUser> listCategoryUsers;

    public User(){
    }

    public User(String id, String firstName, String lastName, String email, String password, List<CategoryUser> listCategoryUsers) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.listCalendarDays = new ArrayList<>();
        this.listCategoryUsers = listCategoryUsers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CalendarDay> getListCalendarDays() {
        return listCalendarDays;
    }

    public void setListCalendarDays(List<CalendarDay> listCalendarDays) {
        this.listCalendarDays = listCalendarDays;
    }

    public List<CategoryUser> getListCategoryUsers() {
        return listCategoryUsers;
    }

    public void setListCategoryUsers(List<CategoryUser> listCategoryUsers) {
        this.listCategoryUsers = listCategoryUsers;
    }

    @Override
    public String toString(){
        if (listCalendarDays==null){
            return firstName + " " + lastName;
        }
        for (CalendarDay calendarDay : listCalendarDays){
            System.out.println(calendarDay.getDate());
            for (Transaction transaction:calendarDay.getListTransactions()){
                System.out.println(transaction);
            }
        }
        return firstName + " " + lastName;
    }
}
