package org.example.projectassignment.view.utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Notification {
    public void notification(String text){
        Stage notificationStage = new Stage();
        notificationStage.initStyle(StageStyle.UNDECORATED);
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 10px;");
        Label message = new Label(text);
        root.getChildren().add(message);
        Scene scene = new Scene(root);
        notificationStage.setScene(scene);
        notificationStage.show();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> notificationStage.close()));
        timeline.setCycleCount(1);
        timeline.play();
    }
}
