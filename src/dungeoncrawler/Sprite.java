package dungeoncrawler;

import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public abstract class Sprite extends Rectangle {
    double x;
    double y;
    int width;
    int height;
    Image image;
    ImageView imageView;

    public Sprite(double x, double y, int width, int height) {
        super(x, y, width, height);
        this.setVisible(true);
        this.setFill(Color.RED);
    }

    public Sprite(double x, double y, Image image, int width, int height) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.width = width;
        this.height = height;

        this.imageView = new ImageView(image);
        this.imageView.relocate(x, y);
        this.imageView.setFitWidth(width);
        this.imageView.setFitHeight(height);
    }

    public void move(double dx, double dy) {
        this.setX(this.x += dx);
        this.setY(this.y += dy);
        this.setVisible(true);
    }
}
