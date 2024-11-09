package org.example.projectassignment.view.feature.other.annualreport;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.projectassignment.Main;
import org.example.projectassignment.model.User;
import org.example.projectassignment.view.feature.FeatureSelection;

import java.io.IOException;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public class AnnualReportScene {
    @FXML
    private Label naviYearLabel;
    @FXML
    private Pane selectYearOverlayPane;
    @FXML
    private ComboBox yearComboBox;
    @FXML
    private Button button1, button2, button3;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private VBox detailInforVBox;
    @FXML
    private Label detailTotalAmount;
    @FXML
    private Label detailAvgAmount;

    private User user ;
    private YearMonth currentYearMonth;
    private LinkedHashMap<String, Long> expenseMap;
    private LinkedHashMap<String, Long> incomeMap;
    private LinkedHashMap<String, Long> sumMap;
    private int flagFeature;

    public void init(User user){
        this.user = user ;
    }
    private void loadDataCurrentYear() {
        Random random = new Random();
        expenseMap = new LinkedHashMap<>();
        incomeMap = new LinkedHashMap<>();
        sumMap = new LinkedHashMap<>();

        for (int i = 1; i <= 12; i++) {
            expenseMap.put("T" + i, (long) (random.nextInt(10000001)));
        }
        for (int i = 1; i <= 12; i++) {
            incomeMap.put("T" + i, (long) (random.nextInt(10000001)));
        }
        for (int i = 1; i <= 12; i++) {
            sumMap.put("T" + i, (long) (random.nextInt(20000001) - 10000000));
        }
    }

    private void updateNaviYearLabel() {
        int year = currentYearMonth.getYear();
        naviYearLabel.setText(String.format("%d (01/01 - 31/12)", year));
    }

    @FXML
    private void onActionButtonDecreaseYear() {
        currentYearMonth = currentYearMonth.minusYears(1L);
        updateWhenHasTimeChanged();
    }

    @FXML
    private void onActionButtonIncreaseYear() {
        currentYearMonth = currentYearMonth.plusYears(1L);
        updateWhenHasTimeChanged();
    }

    @FXML
    private void onMouseClickedNaviYearLabel() {
        selectYearOverlayPane.setVisible(true);
    }

    @FXML
    private void onActionButtonConfirmYear() {
        currentYearMonth = YearMonth.of((Integer) yearComboBox.getValue(), 1);
        updateWhenHasTimeChanged();
        selectYearOverlayPane.setVisible(false);
    }

    @FXML
    private void onActionCancelButton() {
        selectYearOverlayPane.setVisible(false);
    }

    @FXML
    private void onActionButtonBackToOtherScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/feature/FeatureSelection.fxml"));
        Parent root = loader.load();
        FeatureSelection featureSelectionController = loader.getController();
        featureSelectionController.init(user);
        Scene scene = new Scene(root, 600.0, 750.0);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
        featureSelectionController.switchOtherTab();
    }

    private void setupMonthYearSelector() {
        yearComboBox.setItems(FXCollections.observableArrayList(IntStream.rangeClosed(2000, 2100).boxed().toList()));
        yearComboBox.setValue(currentYearMonth.getYear());

        selectYearOverlayPane.setVisible(false);
    }

    private void setActiveButton(Button activeButton, Button other1, Button other2) {
        activeButton.setStyle("-fx-background-color: #fe5d02; -fx-text-fill: white;");

        other1.setStyle("-fx-background-color: transparent; -fx-text-fill: #fe5d02;");
        other2.setStyle("-fx-background-color: transparent; -fx-text-fill: #fe5d02;");
    }

    @FXML
    private void onActionButtonExpense() {
        flagFeature = 1;
        setActiveButton(button1, button2, button3);
        updateBarChart(expenseMap);
        updateDetailInforScrollpane(expenseMap);
    }

    @FXML
    private void onActionButtonIncome() {
        flagFeature = 2;
        setActiveButton(button2, button1, button3);
        updateBarChart(incomeMap);
        updateDetailInforScrollpane(incomeMap);
    }

    @FXML
    private void onActionButtonTotal() {
        flagFeature = 3;
        setActiveButton(button3, button1, button2);
        updateBarChart(sumMap);
        updateDetailInforScrollpane(sumMap);
    }

    private void updateBarChart(LinkedHashMap<String, Long> dataMap) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String, Long> entry : dataMap.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        barChart.getData().clear();
        barChart.getData().add(series);
        barChart.setLegendVisible(false);
    }

    private void updateDetailInforScrollpane(LinkedHashMap<String, Long> dataMap) {
        detailInforVBox.getChildren().clear();
        long total = 0;
        for (Map.Entry<String, Long> entry : dataMap.entrySet()) {
            total += entry.getValue();
        }
        long average = total / 12;

        detailTotalAmount.setText(String.format("%,dđ", total));
        if (total < 0) {
            detailTotalAmount.setStyle("-fx-text-fill: red;");
        } else {
            detailTotalAmount.setStyle("-fx-text-fill: #0592d3;");
        }

        detailAvgAmount.setText(String.format("%,dđ", average));
        if (average < 0) {
            detailAvgAmount.setStyle("-fx-text-fill: red;");
        } else {
            detailAvgAmount.setStyle("-fx-text-fill: #0592d3;");
        }

        for (Map.Entry<String, Long> entry : dataMap.entrySet()) {
            HBox hBox = new HBox();
            hBox.setMinSize(600, 50);
            hBox.setMaxSize(600, 50);
            hBox.setAlignment(Pos.CENTER);

            Label monthLabel = new Label("Tháng " + entry.getKey().substring(1));
            monthLabel.setPrefSize(100, 35);
            monthLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            Pane spacerHead = new Pane();
            spacerHead.setPrefSize(10, 50);
            Region spacerMid = new Region();
            HBox.setHgrow(spacerMid, Priority.ALWAYS); // Cho phép Region mở rộng
            Pane spacerTail = new Pane();
            spacerTail.setPrefSize(10, 50);

            Label moneyLabel = new Label(String.format("%,dđ", entry.getValue()));
            moneyLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            hBox.getChildren().addAll(spacerHead, monthLabel, spacerMid, moneyLabel, spacerTail);
            hBox.setStyle("-fx-border-width: 1px 0 0 0; -fx-border-color: darkgrey; -fx-border-style: solid; -fx-background-color: white;");
            detailInforVBox.getChildren().add(hBox);
        }
    }

    private void updateWhenHasTimeChanged() {
        updateNaviYearLabel();
        switch (flagFeature) {
            case 1:
                loadDataCurrentYear();
                updateBarChart(expenseMap);
                updateDetailInforScrollpane(expenseMap);
                break;
            case 2:
                loadDataCurrentYear();
                updateBarChart(incomeMap);
                updateDetailInforScrollpane(incomeMap);
                break;
            case 3:
                loadDataCurrentYear();
                updateBarChart(sumMap);
                updateDetailInforScrollpane(sumMap);
                break;
        }
    }

    public void initialize() {
        currentYearMonth = YearMonth.now();

        flagFeature = 1;

        setActiveButton(button1, button2, button3);

        updateNaviYearLabel();

        setupMonthYearSelector();

        loadDataCurrentYear();

        updateBarChart(expenseMap);

        updateDetailInforScrollpane(expenseMap);
    }
}
