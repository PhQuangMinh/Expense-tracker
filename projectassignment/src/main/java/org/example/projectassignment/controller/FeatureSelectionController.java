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
            Parent screen = FXMLLoader.load(Main.class.getResource(fxmlFile));
            screenContainer.getChildren().clear(); // Xóa màn hình cũ
            screenContainer.getChildren().add(screen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onActionButtonInput() {
        loadScreen("view/Revenue.fxml");
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
