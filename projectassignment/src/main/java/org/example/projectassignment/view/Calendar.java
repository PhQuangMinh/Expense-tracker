package org.example.projectassignment.view;


import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.example.projectassignment.common.Constant;
import org.example.projectassignment.model.CalendarDay;
import javafx.scene.control.Label;


import javafx.geometry.Insets;

import java.io.*;
import java.util.ArrayList;

public class Calendar extends Pane {
    @FXML
    private GridPane gridPaneCalendar;

    @FXML

    private ScrollPane spendHistory;

    @FXML
    private FlowPane flowPane;

    @FXML
    private Label income;

    @FXML
    private Label expense;

    @FXML
    private Label total;

    private final ArrayList<CalendarDay> listCalendarDays;

    public Calendar(){
        listCalendarDays = new ArrayList<>();
    }

    private void loadData() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data_calendar.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            listCalendarDays.add(new CalendarDay(line, reader.readLine(), reader.readLine(), reader.readLine()));
        }
    }

    private StackPane setDay(String day){
        StackPane cell = new StackPane();
        Label dayLabel = new Label();
        if (Integer.parseInt(day)==8){
            dayLabel.setText("CN");
        }
        else dayLabel.setText("T" + day);
        cell.setPadding(new Insets(5));
        cell.getChildren().add(dayLabel);
        StackPane.setAlignment(dayLabel, Pos.CENTER);
        cell.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-style: solid;");
        return cell;
    }

    private StackPane setCalendarDay(CalendarDay calendarDay){
        Label dayLabel = new Label(String.valueOf(calendarDay.getDate().charAt(0))+ calendarDay.getDate().charAt(1));
        dayLabel.setStyle("-fx-font-size: 14;");
        dayLabel.setAlignment(Pos.TOP_LEFT);

        Label incomeLabel = new Label(calendarDay.getIncome());
        incomeLabel.setStyle("-fx-font-size: 12; -fx-text-fill: green;-fx-font-weight: bold");
        incomeLabel.setAlignment(Pos.CENTER_RIGHT);

        Label expenseLabel = new Label(calendarDay.getExpense());
        expenseLabel.setStyle("-fx-font-size: 12; -fx-text-fill: red;-fx-font-weight: bold");
        expenseLabel.setAlignment(Pos.BOTTOM_RIGHT);

        StackPane cell = new StackPane();
        cell.setPadding(new Insets(5));
        cell.getChildren().addAll(dayLabel, incomeLabel, expenseLabel);

        StackPane.setAlignment(dayLabel, Pos.TOP_LEFT);
        StackPane.setAlignment(incomeLabel, Pos.CENTER_RIGHT);
        StackPane.setAlignment(expenseLabel, Pos.BOTTOM_RIGHT);

        cell.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-style: solid;");

        return cell;
    }

    private String setTextDetailSpend(CalendarDay calendarDay){
//        return calendarDay.getCategory() + " ".repeat(Math.max(0, Constant.LABEL_CALENDAR_HISTORY_WIDTH - calendarDay.getIncome().length() - calendarDay.getCategory().length() - 250)) +
//                calendarDay.getIncome();
//        System.out.println(Constant.LABEL_CALENDAR_HISTORY_WIDTH - calendarDay.getIncome().length() - calendarDay.getCategory().length() - 250);
//        return calendarDay.getCategory() + calendarDay.getIncome();
        return calendarDay.getCategory() + " ".repeat(Math.max(0, 80)) +
                calendarDay.getIncome() + "đ";
    }

    private void setCalendarDayHistory(CalendarDay calendarDay){
        Label dateHistory = new Label(calendarDay.getDate());
        dateHistory.setPrefSize(Constant.LABEL_CALENDAR_HISTORY_WIDTH, 20);
        dateHistory.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;-fx-background-color: #EEECEC;");
        dateHistory.setStyle("-fx-border-color: black; " + "-fx-border-width: 2; " + "-fx-padding: 3; ");
        Label spendHistory = new Label(setTextDetailSpend(calendarDay));
        spendHistory.setPrefSize(Constant.LABEL_CALENDAR_HISTORY_WIDTH, 30);
        spendHistory.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        flowPane.getChildren().addAll(dateHistory, spendHistory);
    }

    private void setSpendHistory(){
        spendHistory.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        flowPane.setVgap(0.5);
        flowPane.getChildren().addAll();
        for (CalendarDay calendarDay : listCalendarDays){
            setCalendarDayHistory(calendarDay);
        }
    }

    public void initialize() throws IOException {
        loadData();
        int totalIncome = 0, totalExpense = 0;
        for (int row=0;row < Constant.CALENDAR_ROW;row++) {
            for (int col=0;col<Constant.CALENDAR_COLUMN;col++){
                if (row == 0){
                    gridPaneCalendar.add(setDay(String.valueOf(col+2)), col, 0);
                }
                else{
                    gridPaneCalendar.add(setCalendarDay(listCalendarDays.get((row-1)*Constant.CALENDAR_COLUMN+col)), col, row);
                    totalIncome += Integer.parseInt(listCalendarDays.get((row-1)*Constant.CALENDAR_COLUMN+col).getIncome());
                    totalExpense += Integer.parseInt(listCalendarDays.get((row-1)*Constant.CALENDAR_COLUMN+col).getExpense());
                }
            }
        }
        income.setText(String.valueOf(totalIncome));
        expense.setText(String.valueOf(totalExpense));
        total.setText(String.valueOf(totalIncome - totalExpense));
        if (listCalendarDays.isEmpty()){
            spendHistory.setVisible(false);
            return;
        }
        setSpendHistory();
    }
}
