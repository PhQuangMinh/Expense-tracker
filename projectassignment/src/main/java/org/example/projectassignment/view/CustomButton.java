package org.example.projectassignment.view;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CustomButton extends Button {
    private String name ;
    private Image image ;

    public CustomButton(String name , Image image) {
        this.name = name ;
        this.image = image ;
        setGraphic(new ImageView(this.image));
        setText(name) ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setText(name);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
        setGraphic(new ImageView(image));
    }

}
