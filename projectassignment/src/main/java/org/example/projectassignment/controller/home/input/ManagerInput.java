package org.example.projectassignment.controller.home.input;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.projectassignment.common.TypeTransaction;
import org.example.projectassignment.model.user.informationuser.CalendarDay;
import org.example.projectassignment.model.user.informationuser.Transaction;
import org.example.projectassignment.model.user.informationuser.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ManagerInput {
    public CalendarDay getCalendarInList(List<CalendarDay> listCalendars, LocalDate currentDate) {
        String currentLocalDate = String.format("%04d-%02d-%02d", currentDate.getYear(), currentDate.getMonthValue(), currentDate.getDayOfMonth());
        return listCalendars.stream()
                .filter(cd -> cd.getDate().equals(currentLocalDate))
                .findFirst()
                .orElse(null);
    }

    public void addTransaction(DatePicker datePicker, TextField noteField, TextField amountField, String category, User user, TypeTransaction typeTransaction){
        LocalDate date = datePicker.getValue();
        String note = noteField.getText();
        String amount = removeCommas(amountField.getText());
        Transaction transaction = new Transaction(note, Long.parseLong(amount), category, typeTransaction, "0");
        CalendarDay foundDay = getCalendarInList(user.getListCalendarDays(), date);
        if (foundDay == null){
            List<Transaction> listTransaction = new ArrayList<>();
            listTransaction.add(transaction);
            CalendarDay calendarDay = new CalendarDay(String.valueOf(datePicker.getValue()), listTransaction);
            user.getListCalendarDays().add(calendarDay);
            System.out.println(user);
            return;
        }
        System.out.println("nhap thanh cong");
        foundDay.getListTransactions().add(transaction);

    }
    private String removeCommas(String input){
        return input.replace("," , "") ;
    }
}
