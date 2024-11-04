package org.example.projectassignment.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ChooseDateController {
    @FXML
    private DatePicker datePicker;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void initialize() {
        // Set định dạng cho DatePicker thành dd/MM/yyyy
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date); // Hiển thị theo định dạng dd/MM/yyyy
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String stringInput) {
                if (stringInput != null && !stringInput.isEmpty()) {
                    return LocalDate.parse(stringInput, dateFormatter); // Chuyển chuỗi thành LocalDate
                } else {
                    return null;
                }
            }
        });

        // Khởi tạo DatePicker với ngày hiện tại
        datePicker.setValue(LocalDate.now());
    }

    public void onActionDecreaseDay(ActionEvent event) {
        LocalDate localDate = datePicker.getValue();
        if (localDate != null) {
            datePicker.setValue(localDate.minusDays(1));
        }
    }

    public void onActionIncreaseDay(ActionEvent event) {
        LocalDate currentDate = datePicker.getValue();
        if (currentDate != null) {
            datePicker.setValue(currentDate.plusDays(1));
        }
    }
}
