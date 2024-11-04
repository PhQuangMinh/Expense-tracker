package org.example.projectassignment.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import org.example.projectassignment.model.Transaction;
import org.example.projectassignment.model.TransactionExporter;
import org.example.projectassignment.model.CustomButton;


import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;

import java.util.*;
public abstract class  TransactionBase implements Initializable {
    @FXML
    protected DatePicker datePicker;
    @FXML
    protected TextField noteField;
    @FXML
    protected TextField amountField;

    @FXML
    protected GridPane GridPane;
    protected String selectedCategory ;
    protected List <Transaction> transactions;
    protected InitCategory initCategory ;
    private Stage stage ;
    private Scene scene ;
    private Parent root ;
    protected FeatureSelectionController featureSelectionController;
    protected void setParentController(FeatureSelectionController controller) {
        this.featureSelectionController = controller;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datePicker.setValue(LocalDate.now());
        datePicker.setEditable(false);

        datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #EEEEEE;");
                        }
                    }
                };
            }
        });
        amountField.textProperty().addListener(new ChangeListener<String>() {
            private boolean changing = false;

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (changing) {
                    return;
                }
                changing = true;
                String cleanString = newValue.replaceAll(",", "");
                if (!cleanString.isEmpty()) {
                    try {
                        double parsed = Double.parseDouble(cleanString);
                        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
                        formatter.setGroupingUsed(true);
                        String formattedString = formatter.format(parsed);
                        amountField.setText(formattedString);
                    } catch (NumberFormatException e) {
                    }
                }

                changing = false;
            }
        });
    }
    public TransactionBase() {
        transactions = new ArrayList<>();
        initCategory = new InitCategory();
    }
    @FXML
    protected void handleSubmit(){
        LocalDate date = datePicker.getValue();
        String note = noteField.getText();
        String amount = amountField.getText();
        double amountValue;
        try {
            amountValue = Double.parseDouble(removeCommas(amount));
            if (amountValue <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Số tiền không hợp lệ, bạn vui lòng nhập lại số tiền!");
            alert.showAndWait();
            return;
        }
        String category = selectedCategory ;
        Transaction transaction = new Transaction( date , note , Double.parseDouble(removeCommas(amount)) , category);
        TransactionExporter exporter = new TransactionExporter();
        transactions.add(transaction);
        exporter.exportTransaction(transactions , "FileBinary.in");
        noteField.clear();
        amountField.clear();
        datePicker.setValue(null);
        Stage notificationStage = new Stage();
        notificationStage.initStyle(StageStyle.UNDECORATED);
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 10px;");
        Label message = new Label("Đã nhập dữ liệu !");
        root.getChildren().add(message);
        Scene scene = new Scene(root);
        notificationStage.setScene(scene);
        notificationStage.show();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> notificationStage.close()));
        timeline.setCycleCount(1);
        timeline.play();
    }
    @FXML
    protected void handleCategorySelection(ActionEvent event){
        Button button = (Button) event.getSource() ;
        selectedCategory = button.getText() ;
        initCategory.addButton(GridPane, getCategoryButton() );
    }

    protected abstract List <CustomButton> getCategoryButton() ;

    private String removeCommas(String input) {
        return input.replaceAll(",", "");
    }
}
