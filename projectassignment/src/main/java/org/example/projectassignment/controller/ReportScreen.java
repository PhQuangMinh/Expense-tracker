package org.example.projectassignment.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.example.projectassignment.common.TypeTransaction;
import org.example.projectassignment.model.CalendarDay;
import org.example.projectassignment.model.Transaction;
import org.example.projectassignment.model.User;

import java.io.IOException;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class ReportScreen {
    @FXML
    private Label labelShowYearMonth;
    @FXML
    private Label labelTotalIncome;
    @FXML
    private Label labelNetIncome;
    @FXML
    private Label labelTotalExpense;
    @FXML
    private PieChart pieChart;
    @FXML
    private Pane paneMonthYearSelector;
    @FXML
    private ComboBox<Integer> monthComboBox;
    @FXML
    private ComboBox<Integer> yearComboBox;
    @FXML
    private StackPane stackPane;
    @FXML
    private Pane paneOverlay;

    private YearMonth currentYearMonth;
    private int flagFeature;
    private User user;

    public ReportScreen() {
    }

    public void init(User user){
        currentYearMonth = YearMonth.now();
        flagFeature = 0;
        this.user = user;
        System.out.println(user);
        updateLabelShowMonthYear();
        updateLabelExpenseIncome();
        updatePieChartExpense();
        setupMonthYearSelector();
    }


    private void updateLabelShowMonthYear() {
        int month = currentYearMonth.getMonthValue();
        int year = currentYearMonth.getYear();
        int daysInMonth = currentYearMonth.lengthOfMonth();
        labelShowYearMonth.setText(String.format("%d/%d (1/%d - %d/%d)", month, year, month, daysInMonth, month));
    }

    @FXML
    private void onActionButtonDecreaseMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        updateLabelShowMonthYear();
        if (flagFeature == 0) {
            updatePieChartExpense();
        } else {
            updatePieChartIncome();
        }
        updateLabelExpenseIncome();
    }

    @FXML
    private void onActionButtonIncreaseMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        updateLabelShowMonthYear();
        if (flagFeature == 0) {
            updatePieChartExpense();
        } else {
            updatePieChartIncome();
        }
        updateLabelExpenseIncome();
    }

    @FXML
    private void onMouseClickedLabelShowYearMonth() {
        paneOverlay.setVisible(true);
    }

    private void updatePieChartExpense() {
        pieChart.getData().clear();
        Map<String, Long> expenseMap = totalDataCategory(TypeTransaction.EXPENSE);
        for (Map.Entry<String, Long> entry : expenseMap.entrySet()) {
            pieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }

    private void updatePieChartIncome() {
        pieChart.getData().clear();
        Map<String, Long> incomeMap = totalDataCategory(TypeTransaction.INCOME);
        for (Map.Entry<String, Long> entry : incomeMap.entrySet()) {
            pieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }

    private Map<String, Long> totalDataCategory(TypeTransaction typeTransaction){
        int month = currentYearMonth.getMonthValue();
        int year = currentYearMonth.getYear();
        String currentMonth = String.format("%04d-%02d", year, month);
        System.out.println(currentMonth);
        Map<String, Long> totalMap = new HashMap<>();
        for (CalendarDay calendarDay : user.getListCalendarDays()) {
            if (calendarDay.getDate().substring(0, 7).equals(currentMonth)) {
                for (Transaction transaction:calendarDay.getListTransactions()){
                    if (transaction.getTypeTransaction() == typeTransaction)
                        totalMap.put(transaction.getCategory(), totalMap.getOrDefault(transaction.getCategory(), 0L) + transaction.getAmount());
                }
            }
        }
        return totalMap;
    }

    private void updateLabelExpenseIncome() {
        int month = currentYearMonth.getMonthValue();
        int year = currentYearMonth.getYear();
        String currentMonth = String.format("%04d-%02d", year, month);

        long totalExpense = 0;
        long totalIncome = 0;
        for (CalendarDay calendarDay : user.getListCalendarDays()) {
            if (calendarDay.getDate().substring(0, 7).equals(currentMonth)) {
                for (Transaction transaction:calendarDay.getListTransactions()) {
                    if (transaction.getTypeTransaction()== TypeTransaction.EXPENSE){
                        totalExpense += transaction.getAmount();
                    }
                    else{
                        totalIncome += transaction.getAmount();
                    }
                }
            }
        }
        long totalNetIncome = totalIncome - totalExpense;

        labelTotalExpense.setText(String.format("-%,dđ", totalExpense));
        labelTotalIncome.setText(String.format("+%,dđ", totalIncome));
        labelNetIncome.setText(String.format("%,dđ", totalNetIncome));
    }

    @FXML
    private void onActionButtonExpense() {
        flagFeature = 0;
        updatePieChartExpense();
    }

    @FXML
    private void onActionButtonIncome() {
        flagFeature = 1;
        updatePieChartIncome();
    }

    private void setupMonthYearSelector() {
        monthComboBox.setItems(FXCollections.observableArrayList(IntStream.rangeClosed(1, 12).boxed().toList()));
        yearComboBox.setItems(FXCollections.observableArrayList(IntStream.rangeClosed(2000, 2100).boxed().toList()));
        monthComboBox.setValue(currentYearMonth.getMonthValue());
        yearComboBox.setValue(currentYearMonth.getYear());

        paneOverlay.setVisible(false);

    }

    @FXML
    private void onActionConfirmButton() {
        currentYearMonth = YearMonth.of(yearComboBox.getValue(), monthComboBox.getValue());
        updateLabelShowMonthYear();
        if (flagFeature == 0) {
            updatePieChartExpense();
        } else {
            updatePieChartIncome();
        }
        updateLabelExpenseIncome();
        // Đóng cửa sổ
        paneOverlay.setVisible(false);
    }

    @FXML
    private void onActionCancelButton() {
        paneOverlay.setVisible(false);
    }

}
