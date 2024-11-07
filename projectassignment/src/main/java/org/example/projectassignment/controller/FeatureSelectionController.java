package org.example.projectassignment.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import org.example.projectassignment.Main;

import java.io.IOException;

public class FeatureSelectionController {
    @FXML
    private Pane screenContainer;

    private void loadScreen(String fxmlFile) {
        try {
            // Tải FXML và thêm vào container
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
            Parent screen = loader.load();
            screenContainer.getChildren().clear();
            screenContainer.getChildren().add(screen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchSpendingMoneyTab() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/SpendingMoney.fxml"));
            Parent screen = loader.load();
            Expense screenController = loader.getController();
            screenController.setParentController(this);
            screenContainer.getChildren().clear();
            screenContainer.getChildren().add(screen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchRevenueTab() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/Revenue.fxml"));
            Parent screen = loader.load();
            Income screenController = loader.getController();
            screenController.setParentController(this);
            screenContainer.getChildren().clear();
            screenContainer.getChildren().add(screen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onActionButtonInput() {
        switchSpendingMoneyTab();
    }

    @FXML
    private void onActionButtonCalendar() {
        loadScreen("view/calendar.fxml");
    }

    @FXML
    private void onActionButtonReport() {
        loadScreen("view/ReportScreen.fxml");
    }

    @FXML
    private void onActionButtonOther() {
        loadScreen("view/OtherScreen.fxml");
    }

}
