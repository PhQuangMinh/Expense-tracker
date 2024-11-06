package org.example.projectassignment.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import org.example.projectassignment.Main;
import org.example.projectassignment.model.User;

import java.io.IOException;

public class FeatureSelectionController {
    @FXML
    private Pane screenContainer;

    private User user;

    public void init(User user) throws IOException {
        this.user = user;
        switchSpendingMoneyTab();
    }

    private void loadScreen(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
        Parent screen = loader.load();
        screenContainer.getChildren().clear();
        screenContainer.getChildren().add(screen);
    }

    public void switchSpendingMoneyTab() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/SpendingMoney.fxml"));
        Parent screen = loader.load();
        SpendingMoneyController screenController = loader.getController();
        screenController.setParentController(this);
        screenContainer.getChildren().clear();
        screenContainer.getChildren().add(screen);
    }

    public void switchRevenueTab() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/Revenue.fxml"));
        Parent screen = loader.load();
        RevenueController screenController = loader.getController();
        screenController.setParentController(this);
        screenContainer.getChildren().clear();
        screenContainer.getChildren().add(screen);
    }

    @FXML
    private void onActionButtonInput() throws IOException {
        switchSpendingMoneyTab();
    }

    @FXML
    private void onActionButtonCalendar() throws IOException {
        loadScreen("view/calendar.fxml");
    }

    @FXML
    private void onActionButtonReport() throws IOException {
        loadScreen("view/ReportScreen.fxml");
    }

    @FXML
    private void onActionButtonOther() throws IOException {
        loadScreen("view/OtherScreen.fxml");
    }
}
