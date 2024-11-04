package org.example.projectassignment.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.YearMonth;
import java.util.stream.IntStream;

public class SelectorMonthYearController {
    @FXML
    private ComboBox<Integer> monthComboBox;
    @FXML
    private ComboBox<Integer> yearComboBox;

    private YearMonth selectedYearMonth;

    public void initialize() {
        monthComboBox.setItems(FXCollections.observableArrayList(IntStream.rangeClosed(1, 12).boxed().toList()));
        yearComboBox.setItems(FXCollections.observableArrayList(IntStream.rangeClosed(2000, 2100).boxed().toList()));

        // Đặt giá trị mặc định cho tháng và năm
        monthComboBox.setValue(1);
        yearComboBox.setValue(2024);
    }

    @FXML
    private void onActionConfirmButton() {
        selectedYearMonth = YearMonth.of(yearComboBox.getValue(), monthComboBox.getValue());
        // Đóng cửa sổ
        Stage stage = (Stage) monthComboBox.getScene().getWindow();
        stage.close();
    }

    public YearMonth getSelectedYearMonth() {
        return selectedYearMonth; // Trả về giá trị YearMonth đã chọn
    }
}