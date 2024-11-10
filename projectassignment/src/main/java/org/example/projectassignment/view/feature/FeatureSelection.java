package org.example.projectassignment.view.feature;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import org.example.projectassignment.Main;
import org.example.projectassignment.view.feature.input.expense.Expense;
import org.example.projectassignment.view.feature.input.income.Income;
import org.example.projectassignment.model.user.ManagerUser;
import org.example.projectassignment.model.user.informationuser.User;
import org.example.projectassignment.view.feature.calendar.Calendar;
import org.example.projectassignment.view.feature.other.OtherScene;
import org.example.projectassignment.view.feature.report.ReportScene;

import java.io.IOException;

public class FeatureSelection {
    @FXML
    private Pane screenContainer;

    private User user;

    private FXMLLoader loader;

    private ManagerUser managerUser;



    public void init(ManagerUser managerUser) throws IOException {
        this.managerUser = managerUser;
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
        expense.init(managerUser, this);
    }

    public void switchIncomeTab() throws IOException {
        loadScreen("view/Revenue.fxml");
        Income income = loader.getController();
        income.init(managerUser, this);
    }

    public void switchOtherTab() throws IOException {
        loadScreen("view/feature/other/OtherScene.fxml");
        OtherScene otherScene = loader.getController();
        otherScene.init(managerUser);
    }
    @FXML
    private void onActionButtonCalendar() throws IOException {
        loadScreen("view/Calendar.fxml");
        Calendar calendar = loader.getController();
        calendar.init(managerUser);
    }

    @FXML
    private void onActionButtonInput() throws IOException {
        switchExpenseMoneyTab();
    }


    @FXML
    private void onActionButtonReport() throws IOException {
        loadScreen("view/feature/report/ReportScene.fxml");
        ReportScene reportScene = loader.getController();
        reportScene.init(managerUser);
    }

    @FXML
    private void onActionButtonOther() throws IOException {
        loadScreen("view/feature/other/OtherScene.fxml");
        OtherScene otherScene = loader.getController();
        otherScene.init(managerUser);
    }
}
