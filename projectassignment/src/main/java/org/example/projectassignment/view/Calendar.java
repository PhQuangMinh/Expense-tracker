package org.example.projectassignment.view;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.projectassignment.Main;
import org.example.projectassignment.common.Constant;
import org.example.projectassignment.controller.SelectorMonthYearController;
import org.example.projectassignment.model.CalendarDay;
import javafx.scene.control.Label;


import javafx.geometry.Insets;

import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
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

    @FXML
    private Label labelShowYearMonth;

    private final ArrayList<CalendarDay> listCalendarDays;

    private YearMonth currentYearMonth = YearMonth.now();

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

    private void createWeekdaysHeader() {
        // Thêm các tên ngày vào hàng 0
        for (int col = 0; col < 7; col++) {
            gridPaneCalendar.add(setDay(String.valueOf(col+2)), col, 0); // Thêm vào hàng 0
        }
    }

    private void updateCalendar() {
        int totalIncome = 0, totalExpense = 0;

        // Xóa các ô cũ trong lịch
        gridPaneCalendar.getChildren().removeIf(node -> GridPane.getRowIndex(node) > 0);

        // Lấy ngày đầu tiên của tháng và tính vị trí bắt đầu
        LocalDate firstDayOfMonth = currentYearMonth.atDay(1);
        int daysInMonth = currentYearMonth.lengthOfMonth();
        int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue() - 1; // Chuyển thành 0 cho thứ Hai

        // Điền các ngày vào bảng
        int dayCounter = 1;
        for (int row = 1; dayCounter <= daysInMonth; row++) {
            for (int col = (row == 1) ? startDayOfWeek : 0; col < 7 && dayCounter <= daysInMonth; col++) {
                LocalDate currentLocalDate = currentYearMonth.atDay(dayCounter);
                String currentDate = String.format("%02d/%02d/%d", currentLocalDate.getDayOfMonth(), currentLocalDate.getMonthValue(), currentLocalDate.getYear());
                // Kiểm tra xem ngày có trong danh sách không
                CalendarDay foundDay = listCalendarDays.stream()
                        .filter(cd -> cd.getDate().equals(currentDate))
                        .findFirst()
                        .orElse(null);

                if (foundDay != null) {
                    gridPaneCalendar.add(setCalendarDay(foundDay), col, row);
                    totalIncome += Integer.parseInt(foundDay.getIncome());
                    totalExpense += Integer.parseInt(foundDay.getExpense());
                } else {
                    gridPaneCalendar.add(setCalendarDay(new CalendarDay(currentDate, "", "", "")), col, row);
                }
                dayCounter++;
            }
        }

        income.setText(String.valueOf(totalIncome));
        expense.setText(String.valueOf(totalExpense));
        total.setText(String.valueOf(totalIncome - totalExpense));
        if (listCalendarDays.isEmpty()){
            spendHistory.setVisible(false);
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
        updateCalendar();
    }

    @FXML
    private void onActionButtonIncreaseMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        updateLabelShowMonthYear();
        updateCalendar();
    }

    @FXML
    private void onMouseClickedLabelShowYearMonth() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/SelectorYearMonth.fxml"));
            Scene scene = new Scene(loader.load());

            SelectorMonthYearController popUpController = loader.getController();

            Stage selectorYearMonthStage = new Stage();
            selectorYearMonthStage.initModality(Modality.APPLICATION_MODAL);
            selectorYearMonthStage.setTitle("Chọn Tháng/Năm");
            selectorYearMonthStage.setScene(scene);
            selectorYearMonthStage.showAndWait();

            // Lấy YearMonth đã chọn từ controller
            YearMonth selectedYearMonth = popUpController.getSelectedYearMonth();
            if (selectedYearMonth != null) {
                currentYearMonth = selectedYearMonth;
                updateLabelShowMonthYear();
                updateCalendar();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() throws IOException {
        loadData();

        updateLabelShowMonthYear();

        createWeekdaysHeader();

        updateCalendar();

        setSpendHistory();
    }

}