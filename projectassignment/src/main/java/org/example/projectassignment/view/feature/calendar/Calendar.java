package org.example.projectassignment.view.feature.calendar;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.example.projectassignment.common.TypeCategory;
import org.example.projectassignment.controller.feature.calendar.ManagerCalendar;
import org.example.projectassignment.controller.feature.input.ManagerInput;
import org.example.projectassignment.controller.ManagerUser;
import org.example.projectassignment.model.user.informationuser.CalendarDay;
import org.example.projectassignment.model.user.informationuser.Transaction;

import java.time.LocalDate;
import java.time.YearMonth;

public class Calendar extends ManagerCalendar {
    @FXML
    private Label naviTimeLabel;
    @FXML
    private ComboBox<Integer> monthComboBox;
    @FXML
    private ComboBox<Integer> yearComboBox;
    @FXML
    private Pane selectTimeOverlay;
    @FXML
    private GridPane calendarGridPane;
    @FXML
    private VBox detailVBox;
    @FXML
    private Pane modifyOverlay;
    @FXML
    private DatePicker modifyDatePicker;
    @FXML
    private TextField modifyNote;
    @FXML
    private TextField modifyAmount;
    @FXML
    private Label detailHeaderIncome;
    @FXML
    private Label detailHeaderExpense;
    @FXML
    private Label detailHeaderSum;

    @FXML
    private GridPane categoryImageGridPane;

    @FXML
    private ScrollPane scrollPane;


    private ManagerUser managerUser;


    public void init(ManagerUser managerUser){
        this.managerUser = managerUser;
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.user = managerUser.getUser();
        managerInput = new ManagerInput();
        listCalendarDays = user.getListCalendarDays();
        currentYearMonth = YearMonth.now();
        loadDataCurrentMonth();
        updateNaviTimeLabel(naviTimeLabel);
        updateCalendar();
        updateDetail();
        setupTimeSelector(monthComboBox, yearComboBox, selectTimeOverlay);
        helper.setAmountField(modifyAmount);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private void updateWhenHasTimeChanged() {
        loadDataCurrentMonth();
        updateNaviTimeLabel(naviTimeLabel);
        updateCalendar();
        updateDetail();
    }

    private void updateDetail() {
        totalExpense = 0;
        totalIncome = 0;
        detailVBox.getChildren().clear();
        for (CalendarDay calendarDay : listCalendarDaysCurrentMonth) {
            if (!calendarDay.getDate().substring(0,7).equals(currentYearMonth.toString())) continue;
            LocalDate date = getLocalDate(calendarDay);
            addDayHeader(date, calendarDay, detailVBox);

            for (Transaction transaction : calendarDay.getListTransactions()) {
                HBox transactionHBox = addTransactionDetail(transaction);
                transactionHBox.setOnMouseClicked(event -> setEventTransactionHBox(date, transaction, calendarDay));
                detailVBox.getChildren().add(transactionHBox);
            }
        }
        setHeaderTotal();
    }

    private void setEventTransactionHBox(LocalDate date, Transaction transaction, CalendarDay calendarDay){
        modifyOverlay.setVisible(true);
        modifyDatePicker.setValue(date);
        modifyNote.setText(transaction.getNote());
        modifyAmount.setText(String.valueOf(transaction.getAmount()));
        idTransactionModifying = transaction.getIdTransaction();
        categoryModifying = transaction.getCategory();
        typeTransactionModifying = transaction.getTypeTransaction();
        dateTransactionModifying = calendarDay.getDate();
        if (transaction.getTypeTransaction() == TypeCategory.EXPENSE){
            System.out.println("Expense");
            updateCategory(managerUser, categoryImageGridPane, managerUser.getExpenseCategory());
        } else{
            System.out.println("Income");
            updateCategory(managerUser, categoryImageGridPane, managerUser.getIncomeCategory());
        }
    }


    private void setHeaderTotal(){
        totalSum = totalIncome - totalExpense;
        detailHeaderExpense.setText(String.format("%,d", totalExpense));
        detailHeaderIncome.setText(String.format("%,d", totalIncome));
        detailHeaderSum.setText(String.format("%,d", totalSum));
        if (totalSum < 0) {
            detailHeaderSum.setStyle("-fx-text-fill: #fe5d02; -fx-font-size: 20px;");
        } else {
            detailHeaderSum.setStyle("-fx-text-fill: #0592d3; -fx-font-size: 20px;");
        }
    }


    private void updateCalendar() {
        //xoa cell neu hang > 0
        calendarGridPane.getChildren().removeIf(node -> {
            Integer rowIndex = GridPane.getRowIndex(node);
            return rowIndex != null && rowIndex > 0;
        });

        // Lấy ngày đầu tiên của tháng và tính vị trí bắt đầu
        LocalDate firstDayOfMonth = currentYearMonth.atDay(1);
        int daysInMonth = currentYearMonth.lengthOfMonth();
        int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue() - 1;

        // Điền các ngày vào bảng
        int dayCounter = 1;
        for (int row = 1; dayCounter <= daysInMonth; row++) {
            for (int col = (row == 1) ? startDayOfWeek : 0; col < 7 && dayCounter <= daysInMonth; col++) {
                LocalDate currentLocalDateCalendar = currentYearMonth.atDay(dayCounter);
                String currentDateCalendar = currentLocalDateCalendar.toString();
                CalendarDay foundDay = listCalendarDaysCurrentMonth.stream()
                        .filter(cd -> cd.getDate().equals(currentDateCalendar))
                        .findFirst()
                        .orElse(null);
                if (foundDay != null) {
                    long totalExpenseDay = 0;
                    long totalIncomeDay = 0;
                    for (Transaction transaction : foundDay.getListTransactions()){
                        if (transaction.getTypeTransaction() == TypeCategory.EXPENSE){
                            totalExpenseDay += transaction.getAmount();
                        } else if (transaction.getTypeTransaction() == TypeCategory.INCOME) {
                            totalIncomeDay += transaction.getAmount();
                        }
                    }
                    calendarGridPane.add(managerCellCalender.setCalendarCell(currentYearMonth, dayCounter, totalIncomeDay, totalExpenseDay), col, row);
                } else {
                    calendarGridPane.add(managerCellCalender.setCalendarCell(currentYearMonth, dayCounter, 0, 0), col, row);
                }
                dayCounter++;
            }
        }
    }

    @FXML
    private void onActionButtonDecreaseMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        updateWhenHasTimeChanged();
    }

    @FXML
    private void onActionButtonIncreaseMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        updateWhenHasTimeChanged();
    }

    @FXML
    private void onMouseClickedNaviMonthYearLabel() {
        selectTimeOverlay.setVisible(true);
    }

    @FXML
    private void onActionConfirmButton() {
        currentYearMonth = YearMonth.of(yearComboBox.getValue(), monthComboBox.getValue());
        updateWhenHasTimeChanged();

        selectTimeOverlay.setVisible(false);
    }

    @FXML
    private void onActionCancelButton() {
        selectTimeOverlay.setVisible(false);
    }

    @FXML
    private void onActionButtonBackToCalendar() {
        modifyOverlay.setVisible(false);
    }

    @FXML
    private void onActionButtonModifyConfirm() {
        if (modifyNote.getText().isEmpty() || modifyAmount.getText().isEmpty()) {
            notification.notification("Không được để trống");
            return;
        }
        String date = modifyDatePicker.getValue().toString();
        String note = modifyNote.getText();
        long amount = Long.parseLong(helper.removeCommas(modifyAmount.getText()));
        Transaction newTransaction = new Transaction(note, amount, categoryModifying, typeTransactionModifying);
        newTransaction.setIdTransaction(idTransactionModifying);

        if (date.equals(dateTransactionModifying)) {
            for (CalendarDay calendarDay : listCalendarDays) {
                if (!calendarDay.getDate().equals(dateTransactionModifying)) continue;
                for (int i = 0; i < calendarDay.getListTransactions().size(); i++) {
                    Transaction transaction = calendarDay.getListTransactions().get(i);
                    if (transaction.getIdTransaction().equals(idTransactionModifying)) {
                        calendarDay.getListTransactions().set(i, newTransaction);
                        break;
                    }
                }
            }
        } else {
            for (CalendarDay calendarDay : listCalendarDays) {
                if (!calendarDay.getDate().equals(dateTransactionModifying)) continue;
                calendarDay.getListTransactions().removeIf(transaction -> transaction.getIdTransaction().equals(idTransactionModifying));
            }
            managerInput.addTransaction(modifyDatePicker, modifyNote, modifyAmount, categoryModifying, user, typeTransactionModifying);
            for (int i = 0; i < listCalendarDays.size(); i++) {
                if (listCalendarDays.get(i).getDate().equals(dateTransactionModifying)) {
                    if (listCalendarDays.get(i).getListTransactions().isEmpty()) listCalendarDays.remove(i);
                }
            }
        }

        modifyOverlay.setVisible(false);
        loadDataCurrentMonth();
        updateCalendar();
        updateDetail();
    }

    @FXML
    private void onActionButtonModifyDelete() {
        for (CalendarDay calendarDay : listCalendarDays) {
            if (!calendarDay.getDate().equals(dateTransactionModifying)) continue;
            calendarDay.getListTransactions().removeIf(transaction -> transaction.getIdTransaction().equals(idTransactionModifying));
        }
        for (int i = 0; i < listCalendarDays.size(); i++) {
            if (listCalendarDays.get(i).getDate().equals(dateTransactionModifying)) {
                if (listCalendarDays.get(i).getListTransactions().isEmpty()) listCalendarDays.remove(i);
            }
        }
        modifyOverlay.setVisible(false);
        loadDataCurrentMonth();
        updateCalendar();
        updateDetail();
    }

}