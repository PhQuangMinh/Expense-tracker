package org.example.projectassignment.view.feature.calendar;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import org.example.projectassignment.common.TypeTransaction;
import org.example.projectassignment.controller.home.input.ManagerInput;
import org.example.projectassignment.model.user.ManagerUser;
import org.example.projectassignment.model.user.informationuser.CalendarDay;
import org.example.projectassignment.model.user.informationuser.Transaction;
import org.example.projectassignment.model.user.informationuser.User;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Calendar extends Pane {
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


    private ManagerUser managerUser;
    private User user;
    private List<CalendarDay> listCalendarDays;
    private List<CalendarDay> listCalendarDaysCurrentMonth;
    private YearMonth currentYearMonth;
    private long totalExpense;
    private long totalIncome;
    private long totalSum;
    private String idTransactionModifying;
    private String dateTransactionModifying;
    private String categoryModifying;
    private TypeTransaction typeTransactionModifying;
    private String idCategoryModifying;
    ManagerInput managerInput;


    public void init(ManagerUser managerUser){
        this.managerUser = managerUser;
        this.user = managerUser.getUser();
        managerInput = new ManagerInput();
        listCalendarDays = user.getListCalendarDays();
        currentYearMonth = YearMonth.now();
        loadDataCurrentMonth();
        updateNaviTimeLabel();
        updateCalendar();
        updateDetail();
        setupTimeSelector();
    }

    private void loadDataCurrentMonth() {
        listCalendarDaysCurrentMonth = new ArrayList<>();
        for (CalendarDay calendarDay : listCalendarDays) {
            if (calendarDay.getDate().substring(0,7).equals(currentYearMonth.toString())) {
                listCalendarDaysCurrentMonth.add(calendarDay);
            }
        }
    }

    private void updateWhenHasTimeChanged() {
        loadDataCurrentMonth();
        updateNaviTimeLabel();
        updateCalendar();
        updateDetail();
    }

    private void updateDetail() {
        detailVBox.getChildren().clear();
        totalExpense = 0;
        totalIncome = 0;
        totalSum = 0;
        for (CalendarDay calendarDay : listCalendarDaysCurrentMonth) {
            if (!calendarDay.getDate().substring(0,7).equals(currentYearMonth.toString())) continue;
            HBox hBoxTitle = new HBox();
            hBoxTitle.setMinSize(600, 20);
            hBoxTitle.setMaxSize(600, 20);
            hBoxTitle.setAlignment(Pos.CENTER);

            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(calendarDay.getDate(), inputFormatter);
            Label dateLabel = new Label(String.format("%s (%s)",date.format(outputFormatter), date.getDayOfWeek().toString()));
            dateLabel.setStyle("-fx-font-size: 16px; -fx-padding: 0 0 0 10px");

            Region spacerMid = new Region();
            HBox.setHgrow(spacerMid, Priority.ALWAYS);

            long totalDay = 0;
            for (Transaction transaction : calendarDay.getListTransactions()) {
                if (transaction.getTypeTransaction() == TypeTransaction.EXPENSE) totalDay -= transaction.getAmount();
                else totalDay += transaction.getAmount();
            }
            Label totalAmountLabel = new Label(String.format("%,dđ", totalDay));
            totalAmountLabel.setStyle("-fx-font-size: 16px; -fx-padding: 0 10px 0 0");

            hBoxTitle.getChildren().addAll(dateLabel, spacerMid, totalAmountLabel);
            hBoxTitle.setStyle("-fx-border-width: 0 0 1px 0; -fx-border-color: darkgrey; -fx-border-style: solid;");
            detailVBox.getChildren().add(hBoxTitle);

            for (Transaction transaction : calendarDay.getListTransactions()) {
                HBox hBox = new HBox();
                hBox.setMinSize(600, 50);
                hBox.setMaxSize(600, 50);
                hBox.setAlignment(Pos.CENTER);

                Label cateLabel = new Label(transaction.getCategory());
                cateLabel.setStyle("-fx-font-size: 20px; -fx-padding: 0 0 0 10px");

                Region spacerMid1 = new Region();
                HBox.setHgrow(spacerMid1, Priority.ALWAYS);

                Label amountLabel = new Label(String.format("%,dđ", transaction.getAmount()));
                if (transaction.getTypeTransaction() == TypeTransaction.EXPENSE) {
                    amountLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #fe5d02");
                    totalExpense += transaction.getAmount();
                }
                else {
                    amountLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #0592d3");
                    totalIncome += transaction.getAmount();
                }

                Label greatThanLabel = new Label(">");
                greatThanLabel.setStyle("-fx-font-size: 20px; -fx-padding: 0 10px 0 10px");

                hBox.getChildren().addAll(cateLabel, spacerMid1, amountLabel, greatThanLabel);
                hBox.setStyle("-fx-border-width: 0 0 1px 0; -fx-border-color: darkgrey; -fx-border-style: solid; -fx-background-color: white");
                detailVBox.getChildren().add(hBox);

                hBox.setOnMouseClicked(event -> {
                    modifyOverlay.setVisible(true);
                    modifyDatePicker.setValue(date);
                    modifyNote.setText(transaction.getNote());
                    modifyAmount.setText(String.valueOf(transaction.getAmount()));
                    idTransactionModifying = transaction.getIdTransaction();
                    categoryModifying = transaction.getCategory();
                    idCategoryModifying = transaction.getIdCategory();
                    typeTransactionModifying = transaction.getTypeTransaction();
                    dateTransactionModifying = calendarDay.getDate();
                });
            }
        }
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

    private StackPane setCalendarCell(int day, long incomeDay, long expenseDay){
        StackPane cell = new StackPane();
        cell.setMaxSize(80, 50);
        cell.setMinSize(80, 50);

        Label dayLabel = new Label(String.valueOf(day));
        dayLabel.setStyle("-fx-font-size: 14;");
        dayLabel.setAlignment(Pos.TOP_LEFT);
        cell.getChildren().add(dayLabel);
        StackPane.setAlignment(dayLabel, Pos.TOP_LEFT);

        if (incomeDay>0){
            Label incomeLabel = new Label(String.format("%,d", incomeDay));
            incomeLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #0592d3;-fx-font-weight: bold");
            incomeLabel.setAlignment(Pos.CENTER_RIGHT);
            cell.getChildren().add(incomeLabel);
            StackPane.setAlignment(incomeLabel, Pos.CENTER_RIGHT);
        }

        if (expenseDay>0){
            Label expenseLabel = new Label(String.format("%,d", expenseDay));
            expenseLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #fe5d02;-fx-font-weight: bold");
            expenseLabel.setAlignment(Pos.BOTTOM_RIGHT);
            cell.getChildren().add(expenseLabel);
            StackPane.setAlignment(expenseLabel, Pos.BOTTOM_RIGHT);
        }
        return cell;
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
                        if (transaction.getTypeTransaction() == TypeTransaction.EXPENSE){
                            totalExpenseDay += transaction.getAmount();
                        } else if (transaction.getTypeTransaction() == TypeTransaction.INCOME) {
                            totalIncomeDay += transaction.getAmount();
                        }
                    }
                    calendarGridPane.add(setCalendarCell(dayCounter, totalIncomeDay, totalExpenseDay), col, row);
                } else {
                    calendarGridPane.add(setCalendarCell(dayCounter, 0, 0), col, row);
                }
                dayCounter++;
            }
        }
    }

    private void updateNaviTimeLabel() {
        int month = currentYearMonth.getMonthValue();
        int year = currentYearMonth.getYear();
        int daysInMonth = currentYearMonth.lengthOfMonth();
        naviTimeLabel.setText(String.format("%d/%d (1/%d - %d/%d)", month, year, month, daysInMonth, month));
    }

    private void setupTimeSelector() {
        monthComboBox.setItems(FXCollections.observableArrayList(IntStream.rangeClosed(1, 12).boxed().toList()));
        yearComboBox.setItems(FXCollections.observableArrayList(IntStream.rangeClosed(2000, 2100).boxed().toList()));
        monthComboBox.setValue(currentYearMonth.getMonthValue());
        yearComboBox.setValue(currentYearMonth.getYear());

        selectTimeOverlay.setVisible(false);
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
        String date = modifyDatePicker.getValue().toString();
        String note = modifyNote.getText().toString();
        long amount = Long.parseLong(modifyAmount.getText().toString());
        Transaction newTransaction = new Transaction(note, amount, categoryModifying, typeTransactionModifying, idCategoryModifying);
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
                    if (listCalendarDays.get(i).getListTransactions().size() == 0) listCalendarDays.remove(i);
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
                if (listCalendarDays.get(i).getListTransactions().size() == 0) listCalendarDays.remove(i);
            }
        }
        modifyOverlay.setVisible(false);
        loadDataCurrentMonth();
        updateCalendar();
        updateDetail();
    }
}