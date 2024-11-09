package org.example.projectassignment.view.feature.report;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import org.example.projectassignment.model.User;

import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public class ReportScene {
    @FXML
    private Label naviTimeLabel;
    @FXML
    private Label reportLabelIncome;
    @FXML
    private Label reportLabelTotal;
    @FXML
    private Label reportLabelExpense;
    @FXML
    private PieChart pieChart;
    @FXML
    private ComboBox<Integer> monthComboBox;
    @FXML
    private ComboBox<Integer> yearComboBox;
    @FXML
    private Pane selectTimeOverlay;
    @FXML
    private Button buttonExpense;
    @FXML
    private Button buttonIncome;
    @FXML
    private VBox detailVBox;

    private User user;
    private YearMonth currentYearMonth;
    private LinkedHashMap<String, Long> expenseMap;
    private LinkedHashMap<String, Long> incomeMap;
    private long totalExpense;
    private long totalIncome;
    private long totalSum;
    private int flagFeature;

    public void init(User user){
        this.user = user;
        System.out.println(user);
        currentYearMonth = YearMonth.now();
        flagFeature = 1;
        setActiveButton(buttonExpense, buttonIncome);
        loadDataCurrentYearMonth();
        updateNaviTimeLabel();
        updateReportLabel();
        updatePieChart(expenseMap);
        updateDetailScrollPane(expenseMap);
        setupTimeSelector();
    }

    private void loadDataCurrentYearMonth() {
        Random random = new Random();
        expenseMap = new LinkedHashMap<>();
        incomeMap = new LinkedHashMap<>();
        totalExpense = 0;
        totalIncome = 0;
        totalSum = 0;

        String[] cateExpense = {"Ăn uống", "Quần áo", "Đóng học", "Đi net", "Đá phò"};
        for (String cate : cateExpense) {
            long tmp = random.nextLong(10000001);
            totalExpense += tmp;
            expenseMap.put(cate, tmp);
        }

        String[] cateIncome = {"Lương", "Chứng khoán", "BTC", "Ăn xin"};
        for (String cate : cateIncome) {
            long tmp = random.nextLong(10000001);
            totalIncome += tmp;
            incomeMap.put(cate, tmp);
        }

        totalSum = totalIncome - totalExpense;
    }

    private void updateNaviTimeLabel() {
        int month = currentYearMonth.getMonthValue();
        int year = currentYearMonth.getYear();
        int daysInMonth = currentYearMonth.lengthOfMonth();
        naviTimeLabel.setText(String.format("%d/%d (1/%d - %d/%d)", month, year, month, daysInMonth, month));
    }

    private void updateWhenHasTimeChanged()  {
        loadDataCurrentYearMonth();
        updateNaviTimeLabel();
        updateReportLabel();
        if (flagFeature == 1) {
            updatePieChart(expenseMap);
            updateDetailScrollPane(expenseMap);
        } else {
            updatePieChart(incomeMap);
            updateDetailScrollPane(incomeMap);
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

    private void updatePieChart(LinkedHashMap<String, Long> dataMap) {
        pieChart.getData().clear();
        for (Map.Entry<String, Long> entry : dataMap.entrySet()) {
            pieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }

    private void updateReportLabel() {
        reportLabelExpense.setText(String.format("-%,dđ", totalExpense));
        reportLabelIncome.setText(String.format("+%,dđ", totalIncome));
        reportLabelTotal.setText(String.format("%,dđ", totalSum));
    }

    private void updateDetailScrollPane(LinkedHashMap<String, Long> dataMap) {
        detailVBox.getChildren().clear();
        long total = 0;
        if (flagFeature == 1) {
            total = totalExpense;
        } else {
            total = totalIncome;
        }
        for (Map.Entry<String, Long> entry : dataMap.entrySet()) {
            HBox hBox = new HBox();
            hBox.setMinSize(600, 50);
            hBox.setMaxSize(600, 50);
            hBox.setAlignment(Pos.CENTER);

            Label titleLabel = new Label(entry.getKey());
            titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-padding: 0 0 0 10px");

            Region spacerMid = new Region();
            HBox.setHgrow(spacerMid, Priority.ALWAYS);

            Label amountLabel = new Label(String.format("%,dđ", entry.getValue()));
            amountLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            Label percentLabel = new Label(String.format("%.1f%%", ((double) entry.getValue() / total) * 100));
            percentLabel.setMaxSize(70, 50);
            percentLabel.setMinSize(70, 50);
            percentLabel.setStyle("-fx-font-size: 16px; -fx-padding: 0 0 0 10px");

            hBox.getChildren().addAll(titleLabel, spacerMid, amountLabel, percentLabel);
            hBox.setStyle("-fx-border-width: 1px 0 0 0; -fx-border-color: darkgrey; -fx-border-style: solid; -fx-background-color: white;");
            detailVBox.getChildren().add(hBox);
        }
    }

    private void setActiveButton(Button activeButton, Button other1) {
        activeButton.setStyle("-fx-border-color: #fe5d02; -fx-text-fill: #fe5d02;");

        other1.setStyle("-fx-border-color: darkgrey; -fx-text-fill: darkgrey;");
    }

    @FXML
    private void onActionButtonExpense() {
        flagFeature = 1;
        setActiveButton(buttonExpense, buttonIncome);
        updatePieChart(expenseMap);
        updateDetailScrollPane(expenseMap);
    }

    @FXML
    private void onActionButtonIncome() {
        flagFeature = 2;
        setActiveButton(buttonIncome, buttonExpense);
        updatePieChart(incomeMap);
        updateDetailScrollPane(incomeMap);
    }

    private void setupTimeSelector() {
        monthComboBox.setItems(FXCollections.observableArrayList(IntStream.rangeClosed(1, 12).boxed().toList()));
        yearComboBox.setItems(FXCollections.observableArrayList(IntStream.rangeClosed(2000, 2100).boxed().toList()));
        monthComboBox.setValue(currentYearMonth.getMonthValue());
        yearComboBox.setValue(currentYearMonth.getYear());

        selectTimeOverlay.setVisible(false);
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

//    public void initialize() throws IOException {
//        currentYearMonth = YearMonth.now();
//
//        flagFeature = 1;
//
//        setActiveButton(buttonExpense, buttonIncome);
//
//        loadDataCurrentYearMonth();
//
//        updateNaviTimeLabel();
//
//        updateReportLabel();
//
//        updatePieChart(expenseMap);
//
//        updateDetailScrollPane(expenseMap);
//
//        setupTimeSelector();
//    }
}
