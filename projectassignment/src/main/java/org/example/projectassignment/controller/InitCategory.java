package org.example.projectassignment.controller;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.GridPane;
import org.example.projectassignment.model.CustomButton;

import java.util.List;

import static org.example.projectassignment.common.Constant.sizeButton;


public class InitCategory {
    public void addButton(GridPane gridPane , List<CustomButton> categoryButtons) {
        for(Node node : gridPane.getChildren()){
            if(node instanceof Button){
                Button button = (Button) node;
                System.out.println(button.getText());
                if(!button.getText().equals("Chỉnh sửa")) {
                    ImageView graphic = (ImageView) button.getGraphic();
                    Image image = graphic.getImage();
                    CustomButton newButton = new CustomButton(button.getText() ,image ) ;
                    newButton.setButton();
                    newButton.setPrefHeight(sizeButton);
                    newButton.setPrefWidth(sizeButton);
                    categoryButtons.add(newButton);
                }
            }
        }
    }
}
