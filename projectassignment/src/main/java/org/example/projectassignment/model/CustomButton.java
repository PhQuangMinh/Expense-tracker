package org.example.projectassignment.model;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CustomButton extends Button {
    private String name ;
    private Image image ;

    public CustomButton(String name , Image image) {
        this.name = name ;
        this.image = image ;
    }

    public void setButton(){
        ImageView graphic = new ImageView(this.image) ;
        graphic.setFitHeight(70);
        graphic.setFitWidth(70);
        setText(this.name);
        setGraphic(graphic);
        setContentDisplay(ContentDisplay.TOP);
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
