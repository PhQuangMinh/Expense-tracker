package org.example.projectassignment.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.projectassignment.Main;
import org.example.projectassignment.model.CalendarDay;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class ReportScreenController {
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
    private ArrayList<CalendarDay> listCalendarDays;
    private int flagFeature;

    public ReportScreenController() {
        listCalendarDays = new ArrayList<>();
        currentYearMonth = YearMonth.now();
        flagFeature = 0;
    }

    private void loadData() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data_calendar.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            listCalendarDays.add(new CalendarDay(line, reader.readLine(), reader.readLine(), reader.readLine()));
        }
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
        int month = currentYearMonth.getMonthValue();
        int year = currentYearMonth.getYear();
        String currentMonth = String.format("%02d/%d", month, year);
        Map<String, Long> expenseMap = new HashMap<>();
        for (CalendarDay calendarDay : listCalendarDays) {
            if (calendarDay.getDate().substring(3).equals(currentMonth)) {
                expenseMap.put(calendarDay.getCategory(), expenseMap.getOrDefault(calendarDay.getCategory(), 0L) + Long.parseLong(calendarDay.getExpense()));
            }
        }
        for (Map.Entry<String, Long> entry : expenseMap.entrySet()) {
            pieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }

    private void updatePieChartIncome() {
        pieChart.getData().clear();
        int month = currentYearMonth.getMonthValue();
        int year = currentYearMonth.getYear();
        String currentMonth = String.format("%02d/%d", month, year);
        Map<String, Long> incomeMap = new HashMap<>();
        for (CalendarDay calendarDay : listCalendarDays) {
            if (calendarDay.getDate().substring(3).equals(currentMonth)) {
                incomeMap.put(calendarDay.getCategory(), incomeMap.getOrDefault(calendarDay.getCategory(), 0L) + Long.parseLong(calendarDay.getIncome()));
            }
        }
        for (Map.Entry<String, Long> entry : incomeMap.entrySet()) {
            pieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }

    private void updateLabelExpenseIncome() {
        int month = currentYearMonth.getMonthValue();
        int year = currentYearMonth.getYear();
        String currentMonth = String.format("%02d/%d", month, year);

        long totalExpense = 0;
        long totalIncome = 0;
        long totalNetIncome = 0;
        for (CalendarDay calendarDay : listCalendarDays) {
            if (calendarDay.getDate().substring(3).equals(currentMonth)) {
                totalExpense += Long.parseLong(calendarDay.getExpense());
                totalIncome += Long.parseLong(calendarDay.getIncome());
            }
        }
        totalNetIncome = totalIncome - totalExpense;

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

    public void initialize() throws IOException {
        loadData();

        updateLabelShowMonthYear();

        updateLabelExpenseIncome();

        updatePieChartExpense();

        setupMonthYearSelector();

    }
}
