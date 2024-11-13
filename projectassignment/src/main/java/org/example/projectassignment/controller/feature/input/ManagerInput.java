package org.example.projectassignment.controller.feature.input;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.example.projectassignment.common.TypeCategory;
import org.example.projectassignment.controller.Helper;
import org.example.projectassignment.controller.ManagerUser;
import org.example.projectassignment.model.CategoryImage;
import org.example.projectassignment.model.user.informationuser.CalendarDay;
import org.example.projectassignment.model.user.informationuser.Transaction;
import org.example.projectassignment.model.user.informationuser.User;
import org.example.projectassignment.view.utils.Notification;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ManagerInput {
    protected String selectedCategory = "";

    protected CategoryImage editButton = new CategoryImage("Chỉnh sửa" ,null);

    private final Notification notification = new Notification();

    protected Helper helper = new Helper();



    protected void setEditButton(CategoryImage editButton){
        editButton.setAlignment(Pos.CENTER);
        editButton.setContentDisplay(ContentDisplay.CENTER);
    }

    public CalendarDay getCalendarInList(List<CalendarDay> listCalendars, LocalDate currentDate) {
        String currentLocalDate = String.format("%04d-%02d-%02d", currentDate.getYear(), currentDate.getMonthValue(), currentDate.getDayOfMonth());
        return listCalendars.stream()
                .filter(cd -> cd.getDate().equals(currentLocalDate))
                .findFirst()
                .orElse(null);
    }

    public void addTransaction(DatePicker datePicker, TextField noteField, TextField amountField, String category, User user, TypeCategory typeTransaction){
        LocalDate date = datePicker.getValue();
        String note = noteField.getText();
        String amount = removeCommas(amountField.getText());
        Transaction transaction = new Transaction(note, Long.parseLong(amount), category, typeTransaction);
        CalendarDay foundDay = getCalendarInList(user.getListCalendarDays(), date);
        if (foundDay == null){
            List<Transaction> listTransaction = new ArrayList<>();
            transaction.setIdTransaction(date + "0");
            listTransaction.add(transaction);
            CalendarDay calendarDay = new CalendarDay(String.valueOf(datePicker.getValue()), listTransaction);
            user.getListCalendarDays().add(calendarDay);
            return;
        }
        transaction.setIdTransaction(date + String.valueOf(Integer.parseInt(foundDay.getListTransactions().getLast().getIdTransaction())+1));
        foundDay.getListTransactions().add(transaction);

    }

    protected String removeCommas(String input){
        return input.replace("," , "") ;
    }

    public void setDatePicker(DatePicker datePicker){
        datePicker.setValue(LocalDate.now());
        datePicker.setEditable(false);
    }


    private void handleCategorySelection(ActionEvent event){
        Button button = (Button) event.getSource() ;
        selectedCategory = button.getText();
        if (selectedCategory == null){
            selectedCategory = "";
        }
    }

    protected void updateCategory(CategoryImage editButton, ManagerUser managerUser, GridPane gridPane, List<CategoryImage> categoryImageList){
        managerUser.updateCategory();
        gridPane.getChildren().clear();
        int row = 0, col = 0;
        int limitColumn = 5;
        for (CategoryImage button : categoryImageList) {
            button.setOnAction(this::handleCategorySelection);
            gridPane.add(button, col, row);
            col++;
            if (col == limitColumn) {
                col = 0;
                row++;
            }
        }
        gridPane.add(editButton, col, row);
    }

    protected void handleSubmit(TextField amountField, DatePicker datePicker, TextField noteField, ManagerUser managerUser, TypeCategory typeCategory){
        if (selectedCategory.isEmpty()){
            notification.notification("Bạn chưa lựa chọn danh mục");
            return;
        }
        double amountValue;
        try {
            amountValue = Double.parseDouble(removeCommas(amountField.getText()));
            if (amountValue <= 0 || amountValue > Long.MAX_VALUE) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            notification.notification("Số tiền không hợp lệ, bạn vui lòng nhập lại số tiền!");
            return;
        }
        addTransaction(datePicker, noteField, amountField, selectedCategory, managerUser.getUser(), typeCategory);
        noteField.clear();
        amountField.clear();
        setDatePicker(datePicker);
        notification.notification("Nhập thành công");
        managerUser.getFirebaseUser().updateUser(managerUser.getUser());
    }
}
