package org.example.projectassignment.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import org.example.projectassignment.Main;
import org.example.projectassignment.model.User;
import org.example.projectassignment.view.calendar.Calendar;

import java.io.IOException;

public class FeatureSelection {
    @FXML
    private Pane screenContainer;

    private User user;

    private FXMLLoader loader;

    public void init(User user) throws IOException {
        this.user = user;
        switchExpenseMoneyTab();
    }

    private void loadScreen(String fxmlFile) throws IOException {
        loader = new FXMLLoader(Main.class.getResource(fxmlFile));
        Parent screen = loader.load();
        screenContainer.getChildren().clear();
        screenContainer.getChildren().add(screen);
    }

    public void switchExpenseMoneyTab() throws IOException {
        loadScreen("view/SpendingMoney.fxml");
        Expense expense = loader.getController();
        expense.init(user, this);
    }

    public void switchIncomeTab() throws IOException {
        loadScreen("view/Revenue.fxml");
        Income income = loader.getController();
        income.init(this, user);
    }

    @FXML
    private void onActionButtonInput() throws IOException {
        switchExpenseMoneyTab();
    }

    @FXML
    private void onActionButtonCalendar() throws IOException {
        loadScreen("view/Calendar.fxml");
        Calendar calendar = loader.getController();
        calendar.init(user);
    }

    @FXML
    private void onActionButtonReport() throws IOException {
        loadScreen("view/ReportScreen.fxml");
        ReportScreen reportScreen = loader.getController();
        reportScreen.init(user);
    }

    @FXML
    private void onActionButtonOther() throws IOException {
        loadScreen("view/OtherScreen.fxml");
    }
}