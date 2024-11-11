package org.example.projectassignment.view.utils;

import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.projectassignment.common.Constant;

public class WaitStage extends Stage {
    public WaitStage(){
        setTitle("Waiting...");
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        BorderPane pane = new BorderPane();
        pane.setCenter(progressIndicator);
        Scene scene = new Scene(pane, Constant.WIDTH_WAIT_STAGE, Constant.HEIGHT_WAIT_STAGE);
        centerOnScreen();
        setResizable(false);
        initStyle(StageStyle.UNDECORATED);
        setScene(scene);
    }
}
