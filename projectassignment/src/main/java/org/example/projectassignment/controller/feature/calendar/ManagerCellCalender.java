package org.example.projectassignment.controller.feature.calendar;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

public class ManagerCellCalender {
    public StackPane setCalendarCell(YearMonth currentYearMonth, int day, long incomeDay, long expenseDay){
        StackPane cell = new StackPane();
        cell.setMaxSize(80, 50);
        cell.setMinSize(80, 50);

        Label dayLabel = new Label(String.valueOf(day));
        LocalDate currentDate = currentYearMonth.atDay(day);
        if (currentDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            dayLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #0592d3;");
        } else if (currentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            dayLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #fe5d02;");
        } else dayLabel.setStyle("-fx-font-size: 14;");
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
}
