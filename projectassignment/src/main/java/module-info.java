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

    opens org.example.projectassignment to javafx.fxml;
    opens org.example.projectassignment.view to javafx.fxml;

    exports org.example.projectassignment;
    exports org.example.projectassignment.view;
//    exports org.example.projectassignment.model;
//    exports org.example.projectassignment.common;
//    exports org.example.projectassignment.controller;
}