package org.example.projectassignment.model;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static org.example.projectassignment.common.Constant.SIZE_BUTTON;

public class CategoryImage extends Button {
    private String name ;
    private Image image ;

    public CategoryImage(){}

    public CategoryImage(String name , Image image) {
        this.name = name ;
        this.image = image ;
        setButton();
    }

    public void setButton(){
        ImageView graphic = new ImageView(this.image) ;
        graphic.setFitHeight(70);
        graphic.setFitWidth(70);
        setText(this.name);
        setGraphic(graphic);
        setContentDisplay(ContentDisplay.TOP);
        this.setPrefHeight(SIZE_BUTTON);
        this.setPrefWidth(SIZE_BUTTON);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}
