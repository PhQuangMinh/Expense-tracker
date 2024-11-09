package org.example.projectassignment.view.feature.calendar;


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
import org.example.projectassignment.common.TypeTransaction;
import org.example.projectassignment.controller.SelectorMonthYear;
import org.example.projectassignment.model.CalendarDay;
import javafx.scene.control.Label;


import javafx.geometry.Insets;
import org.example.projectassignment.model.Transaction;
import org.example.projectassignment.model.User;

import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;

public class Calendar extends Pane {
    @FXML
    private GridPane gridPaneCalendar;

    @FXML
    private ScrollPane expenseHistory;

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

    private YearMonth currentYearMonth = YearMonth.now();

    private User user;

    public Calendar(){
    }

    public void init(User user){
        this.user = user;
        expenseHistory.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        flowPane.setVgap(0.5);
        updateLabelShowMonthYear();
        createWeekdaysHeader();
        updateCalendar();
        for (CalendarDay day:user.getListCalendarDays()){
            for(Transaction transaction:day.getListTransactions()){
                System.out.println(transaction);
            }
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

    private StackPane setCalendarDay(LocalDate localDate, long incomeDay, long expenseDay){
        StackPane cell = new StackPane();
        cell.setPadding(new Insets(5));

        Label dayLabel = new Label(String.valueOf(localDate.getDayOfMonth()));
        dayLabel.setStyle("-fx-font-size: 14;");
        dayLabel.setAlignment(Pos.TOP_LEFT);
        cell.getChildren().add(dayLabel);
        StackPane.setAlignment(dayLabel, Pos.TOP_LEFT);

        if (incomeDay>0){
            Label incomeLabel = new Label(String.valueOf(incomeDay));
            incomeLabel.setStyle("-fx-font-size: 12; -fx-text-fill: green;-fx-font-weight: bold");
            incomeLabel.setAlignment(Pos.CENTER_RIGHT);
            cell.getChildren().add(incomeLabel);
            StackPane.setAlignment(incomeLabel, Pos.CENTER_RIGHT);
        }

        if (expenseDay>0){
            Label expenseLabel = new Label(String.valueOf(expenseDay));
            expenseLabel.setStyle("-fx-font-size: 12; -fx-text-fill: red;-fx-font-weight: bold");
            expenseLabel.setAlignment(Pos.BOTTOM_RIGHT);
            cell.getChildren().add(expenseLabel);
            StackPane.setAlignment(expenseLabel, Pos.BOTTOM_RIGHT);
        }
        cell.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-style: solid;");

        return cell;
    }

    private String setTextDetailTransaction(Transaction transactionHistory){
        return transactionHistory.getCategory() + " ".repeat(Math.max(0, 80)) +
                transactionHistory.getAmount() + "đ";
    }

    private void setDateInHistory(LocalDate dateHistory){
        Label dateHistoryLabel = new Label(String.valueOf(dateHistory));
        dateHistoryLabel.setPrefSize(Constant.LABEL_CALENDAR_HISTORY_WIDTH, 20);
        dateHistoryLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;-fx-background-color: #EEECEC;");
        dateHistoryLabel.setStyle("-fx-border-color: black; " + "-fx-border-width: 2; " + "-fx-padding: 3; ");
        flowPane.getChildren().add(dateHistoryLabel);
    }

    private void setTransactionInHistory(Transaction transactionHistory){
        Label transactionHistoryLabel = new Label(setTextDetailTransaction(transactionHistory));
//        expenseHistory.setPrefSize(Constant.LABEL_CALENDAR_HISTORY_WIDTH, 30);
        expenseHistory.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        flowPane.getChildren().addAll(transactionHistoryLabel);
    }

    private void createWeekdaysHeader() {
        // Thêm các tên ngày vào hàng 0
        for (int col = 0; col < 7; col++) {
            gridPaneCalendar.add(setDay(String.valueOf(col+2)), col, 0); // Thêm vào hàng 0
        }
    }

    private void updateCalendar() {
        long totalIncomeMonth = 0, totalExpenseMonth = 0;

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
                String currentDate = String.format("%04d-%02d-%02d", currentLocalDate.getYear(), currentLocalDate.getMonthValue(), currentLocalDate.getDayOfMonth());
                CalendarDay foundDay = user.getListCalendarDays().stream()
                        .filter(cd -> cd.getDate().equals(currentDate))
                        .findFirst()
                        .orElse(null);
                if (foundDay != null) {
                    setDateInHistory(currentLocalDate);
                    long totalIncomeDay = 0, totalExpenseDay = 0;
                    for (Transaction transaction:foundDay.getListTransactions()){
                        setTransactionInHistory(transaction);
                        if (transaction.getTypeTransaction()== TypeTransaction.EXPENSE){
                            totalExpenseDay += transaction.getAmount();
                        }
                        else totalIncomeDay += transaction.getAmount();
                    }
                    gridPaneCalendar.add(setCalendarDay(currentLocalDate, totalIncomeDay, totalExpenseDay), col, row);
                    totalIncomeMonth += totalIncomeDay;
                    totalExpenseMonth += totalExpenseDay;
                } else {
                    gridPaneCalendar.add(setCalendarDay(currentLocalDate, 0, 0), col, row);
                }
                dayCounter++;
            }
        }

        income.setText(String.valueOf(totalIncomeMonth));
        expense.setText(String.valueOf(totalExpenseMonth));
        total.setText(String.valueOf(totalIncomeMonth - totalExpenseMonth));
        if (user.getListCalendarDays().isEmpty()){
            expenseHistory.setVisible(false);
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
    private void onMouseClickedLabelShowYearMonth() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/SelectorYearMonth.fxml"));
        Scene scene = new Scene(loader.load());

        SelectorMonthYear popUpController = loader.getController();

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
    }


}