package dungeoncrawler.entity;

//import javafx.scene.layout.Pane;
//import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public abstract class Sprite extends Rectangle {
    private double x;
    private double y;
    private int width;
    private int height;
    private Image image;
    private ImageView imageView;

    public Sprite(double x, double y, int width, int height) {
        super(x, y, width, height);
        this.setVisible(true);
        this.setFill(Color.RED);
    }

    public Sprite(double x, double y, Image image, int width, int height) {
        this.x = x;
        //setSpriteX(x);
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
        //double newX = this.x + dx;
        //double newY = this.y + dy;
        this.setX(this.x + dx);
        this.setY(this.y + dy);
    }

    public double getSpriteX() {
        return x;
    }

    public void setSpriteX(double x) {
        this.x = x;
    }
    public double getSpriteY() {
        return y;
    }

    public void setSpriteY(double y) {
        this.y = y;
    }

    public int getSpriteWidth() {
        return width;
    }

    public void setSpriteWidth(int width) {
        this.width = width;
    }
    public int getSpriteHeight() {
        return height;
    }

    public void setSpriteHeight(int height) {
        this.height = height;
    }


}
