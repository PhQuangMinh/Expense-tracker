package org.example.projectassignment.model;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.GridPane;
import org.example.projectassignment.view.CustomButton;

import java.util.List;


public class InitCategory {
    public void addButton(GridPane gridPane , List<CustomButton> categoryButtons) {
        for(Node node : gridPane.getChildren()){
            if(node instanceof Button){
                Button button = (Button) node;
                System.out.println(button.getText());
                if(!button.getText().equals("Chỉnh sửa")) {
                    ImageView graphic = (ImageView) button.getGraphic();
                    graphic.setFitHeight(70);
                    graphic.setFitWidth(70);
                    Image image = graphic.getImage();
                    CustomButton cb = new CustomButton(button.getText() ,image ) ;
                    cb.setMaxHeight(100);
                    cb.setMinHeight(100);
                    cb.setMaxWidth(100);
                    cb.setMinWidth(100);
                    cb.setGraphic(graphic);
                    cb.setContentDisplay(ContentDisplay.TOP);
                    categoryButtons.add(cb);
                }
            }
        }
    }
}
