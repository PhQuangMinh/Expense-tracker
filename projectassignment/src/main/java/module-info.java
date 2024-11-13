module org.example.projectassignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires annotations;
    requires javafx.graphics;
    requires com.google.auth.oauth2;
    requires firebase.admin;
    requires com.google.auth;
    requires google.cloud.storage;
    requires gax;
    requires google.cloud.core;
    requires google.cloud.firestore;
    requires java.mail;

    exports org.example.projectassignment.model.category;
    opens org.example.projectassignment to javafx.fxml;
    opens org.example.projectassignment.view to javafx.fxml;
    opens org.example.projectassignment.view.auth.signin to javafx.fxml;
    opens org.example.projectassignment.view.auth.signup to javafx.fxml;
    opens org.example.projectassignment.model to firebase.admin;
    opens org.example.projectassignment.controller.auth to javafx.fxml;
    opens org.example.projectassignment.view.feature.calendar to javafx.fxml;
    opens org.example.projectassignment.view.feature to javafx.fxml;
    opens org.example.projectassignment.view.feature.other to javafx.fxml;
    opens org.example.projectassignment.view.feature.other.annualreport to javafx.fxml;
    opens org.example.projectassignment.view.feature.report to javafx.fxml;

    exports org.example.projectassignment.view.auth.forgotpassword to javafx.fxml;
    opens org.example.projectassignment.view.auth.forgotpassword to javafx.fxml;
    exports org.example.projectassignment;
    exports org.example.projectassignment.controller;
    exports org.example.projectassignment.model;
    exports org.example.projectassignment.view.auth.signin;
    exports org.example.projectassignment.view.auth.signup;
    exports org.example.projectassignment.controller.auth;
    exports org.example.projectassignment.common;
    exports org.example.projectassignment.view.feature.calendar;
    exports org.example.projectassignment.view.feature;
    exports org.example.projectassignment.view.feature.other to javafx.fxml;
    exports org.example.projectassignment.view.feature.other.annualreport to javafx.fxml;
    exports org.example.projectassignment.view.feature.report to javafx.fxml;
    exports org.example.projectassignment.view.feature.input.income;
    opens org.example.projectassignment.view.feature.input.income to javafx.fxml;
    exports org.example.projectassignment.view.feature.input.expense;
    opens org.example.projectassignment.view.feature.input.expense to javafx.fxml;
    exports org.example.projectassignment.view.feature.input;
    opens org.example.projectassignment.view.feature.input to javafx.fxml;
    exports org.example.projectassignment.view.feature.other.informationuser;
    opens org.example.projectassignment.view.feature.other.informationuser to javafx.fxml;
    opens org.example.projectassignment.model.category to firebase.admin;
    exports org.example.projectassignment.model.user.informationuser;
    opens org.example.projectassignment.model.user.informationuser to firebase.admin;
    opens org.example.projectassignment.controller to firebase.admin, javafx.fxml;
}