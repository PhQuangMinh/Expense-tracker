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

    opens org.example.projectassignment to javafx.fxml;
    opens org.example.projectassignment.view to javafx.fxml;
    opens org.example.projectassignment.controller to javafx.fxml;
    opens org.example.projectassignment.view.auth.signin to javafx.fxml;
    opens org.example.projectassignment.view.auth.signup to javafx.fxml;
    opens org.example.projectassignment.model to firebase.admin;

    exports org.example.projectassignment;
    exports org.example.projectassignment.view;
    exports org.example.projectassignment.controller;
    exports org.example.projectassignment.model;
    exports org.example.projectassignment.view.auth.signin;
    exports org.example.projectassignment.view.auth.signup;
    exports org.example.projectassignment.controller.auth;
    exports org.example.projectassignment.common;
    opens org.example.projectassignment.controller.auth to javafx.fxml;
    exports org.example.projectassignment.view.calendar;
    opens org.example.projectassignment.view.calendar to javafx.fxml;
}