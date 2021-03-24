package dungeoncrawler;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

public class Player extends Sprite {
    static int health = 5;
    static int damage = 1;
    //static Image image = new Image("https://crhscountyline.com/wp-content/uploads/2020/03/Capture.png");

    public Player(double x, double y, int width, int height) {
        super(x, y, width, height);
    }

    public void addPlayer(Pane pane, Player player) {
        pane.getChildren().add(player);
    }

    public void move(double dx, double dy) {
        super.move(dx, dy);
    }
}
