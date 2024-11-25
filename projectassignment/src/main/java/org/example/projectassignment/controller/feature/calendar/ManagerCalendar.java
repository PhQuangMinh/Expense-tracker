package org.example.projectassignment.controller.feature.calendar;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import org.example.projectassignment.common.TypeCategory;
import org.example.projectassignment.controller.Helper;
import org.example.projectassignment.controller.ManagerUser;
import org.example.projectassignment.controller.feature.input.ManagerInput;
import org.example.projectassignment.controller.feature.input.category.InitCategory;
import org.example.projectassignment.controller.feature.input.category.ManagerCategoryEditor;
import org.example.projectassignment.model.CategoryImage;
import org.example.projectassignment.model.user.informationuser.CalendarDay;
import org.example.projectassignment.model.user.informationuser.Transaction;
import org.example.projectassignment.model.user.informationuser.User;
import org.example.projectassignment.view.utils.Notification;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ManagerCalendar extends InitCategory{
    protected YearMonth currentYearMonth;

    protected ManagerCellCalender managerCellCalender = new ManagerCellCalender();

    protected User user;

    protected List<CalendarDay> listCalendarDays;

    protected List<CalendarDay> listCalendarDaysCurrentMonth;

    protected String idTransactionModifying;

    protected String dateTransactionModifying;

    protected String categoryModifying;

    protected TypeCategory typeTransactionModifying;

    protected ManagerInput managerInput;

    protected Helper helper = new Helper();

    protected long totalExpense, totalIncome, totalSum;

    protected Notification notification = new Notification();

    protected CategoryImage categoryImage;

    protected void loadDataCurrentMonth() {
        listCalendarDaysCurrentMonth = new ArrayList<>();
        for (CalendarDay calendarDay : listCalendarDays) {
            if (calendarDay.getDate().substring(0,7).equals(currentYearMonth.toString())) {
                listCalendarDaysCurrentMonth.add(calendarDay);
            }
        }
    }

    protected void updateNaviTimeLabel(Label naviTimeLabel) {
        int month = currentYearMonth.getMonthValue();
        int year = currentYearMonth.getYear();
        int daysInMonth = currentYearMonth.lengthOfMonth();
        naviTimeLabel.setText(String.format("%d/%d (1/%d - %d/%d)", month, year, month, daysInMonth, month));
    }

    protected void setupTimeSelector(ComboBox<Integer> monthComboBox, ComboBox<Integer> yearComboBox, Pane selectTimeOverlay) {
        monthComboBox.setItems(FXCollections.observableArrayList(IntStream.rangeClosed(1, 12).boxed().toList()));
        yearComboBox.setItems(FXCollections.observableArrayList(IntStream.rangeClosed(2000, 2100).boxed().toList()));
        monthComboBox.setValue(currentYearMonth.getMonthValue());
        yearComboBox.setValue(currentYearMonth.getYear());

        selectTimeOverlay.setVisible(false);
    }

    protected void setHBox(HBox hBox, int width, int height) {
        hBox.setMinSize(width, height);
        hBox.setMaxSize(width, height);
        hBox.setAlignment(Pos.CENTER);
    }

    protected LocalDate getLocalDate(CalendarDay calendarDay){
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(calendarDay.getDate(), inputFormatter);
    }

    protected void addDayHeader(LocalDate date, CalendarDay calendarDay, VBox detailVBox){
        HBox hBoxTitle = new HBox();
        setHBox(hBoxTitle, 600, 20);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Label dateLabel = new Label(String.format("%s (%s)",date.format(outputFormatter), date.getDayOfWeek().toString()));
        dateLabel.setStyle("-fx-font-size: 16px; -fx-padding: 0 0 0 10px");

        Region spacerMid = new Region();
        HBox.setHgrow(spacerMid, Priority.ALWAYS);

        long totalDay = 0;
        for (Transaction transaction : calendarDay.getListTransactions()) {
            if (transaction.getTypeTransaction() == TypeCategory.EXPENSE) totalDay -= transaction.getAmount();
            else totalDay += transaction.getAmount();
        }
        Label totalAmountLabel = new Label(String.format("%,dđ", totalDay));
        totalAmountLabel.setStyle("-fx-font-size: 16px; -fx-padding: 0 10px 0 0");

        hBoxTitle.getChildren().addAll(dateLabel, spacerMid, totalAmountLabel);
        hBoxTitle.setStyle("-fx-border-width: 0 0 1px 0; -fx-border-color: darkgrey; -fx-border-style: solid;");
        detailVBox.getChildren().add(hBoxTitle);
    }

    protected HBox addTransactionDetail(Transaction transaction) {
        HBox hBox = new HBox();
        setHBox(hBox, 600, 50);

        Label cateLabel = new Label(transaction.getCategory());
        cateLabel.setStyle("-fx-font-size: 20px; -fx-padding: 0 0 0 10px");

        Region spacerMid1 = new Region();
        HBox.setHgrow(spacerMid1, Priority.ALWAYS);

        Label amountLabel = new Label(String.format("%,dđ", transaction.getAmount()));
        if (transaction.getTypeTransaction() == TypeCategory.EXPENSE) {
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
        return hBox;
    }

    private void handleCategorySelection(ActionEvent event){
        CategoryImage category = (CategoryImage) event.getSource() ;
        categoryModifying = category.getText();
        if (categoryImageChooser != null) {
            categoryImageChooser.setStyle("-fx-font-weight: bold; -fx-border-color: transparent; -fx-background-color: #ddd;");
        }

        categoryImageChooser = category;
        category.setStyle("-fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 2px; -fx-background-color: #ddd; -fx-text-fill: black;-fx-border-color: black;" +
                "    -fx-border-width: 2px;" +
                "    -fx-border-radius: 2px;");
    }

    protected void setCategoryImage(CategoryImage categoryImage){
        GridPane.setHalignment(categoryImage, HPos.CENTER);
        GridPane.setValignment(categoryImage, VPos.CENTER);
        categoryImage.setStyle("-fx-font-weight: bold; -fx-border-color: transparent; -fx-background-color: #ddd;-fx-font-size: 12px;");
    }

    protected void updateCategory(ManagerUser managerUser, GridPane gridPane, List<CategoryImage> categoryImageList){
        managerUser.updateCategory();
        gridPane.getChildren().clear();
        int row = 0, col = 0;
        int limitColumn = 5;
        System.out.println(categoryImageList.size());
        for (CategoryImage button : categoryImageList) {
            button.setOnAction(this::handleCategorySelection);
            setCategoryImage(button);
            gridPane.add(button, col, row);
            col++;
            if (col == limitColumn) {
                col = 0;
                row++;
            }
        }
    }

}
