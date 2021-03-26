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
    boolean isMoving;
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

    public void move(double dx, double dy, boolean isMoving) {
        this.isMoving = isMoving;
        move(dx, dy);
    }
    public void move(double dx, double dy) {
        //while (this.isMoving) {
            this.setX(this.x += dx);
            this.setY(this.y += dy);
        //}
    }

    public void stopMoving() {
        System.out.println("STOPPED MOVING METHOD");
        this.isMoving = false;
    }

    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }
}
