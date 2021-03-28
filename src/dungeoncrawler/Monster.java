package dungeoncrawler;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;

import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

// In case where you wanna create your own monster, just extends this class and implement startMoving
public class Monster extends Rectangle {
    int health;
    int damage = 1;
    boolean alive = true;
    public Monster(int width, int height, int health, Color color) {
        super(width, height, color);
        this.health = health;
    }

    /**
     * Start moving the monster's animation
     * Change the KeyValue parameters to change the behavior of the monster
     * See this for example: https://mkyong.com/javafx/javafx-animated-ball-example/
     * @param x x start position
     * @param y y start position
     * @param pane The current pane monster is in. Should be this.primaryStage.getScene().getRoot()
     */
    public void startMoving(int x, int y, Pane pane) {
        this.relocate(x, y);
        Bounds bounds = pane.getBoundsInLocal();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), new KeyValue(this.layoutXProperty(), bounds.getMaxX()-this.getWidth())));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void takeDamage(int damageCount) {
        this.health -= damageCount;
        System.out.println(this.health);
        if (this.health <= 0) {
            this.setVisible(false);
            this.alive = false;
        }
    }

    public void attackPlayer(Player player) {
        if (this.getBoundsInParent().intersects(player.getBoundsInParent())) {
            player.takeDamage(this.damage);
            System.out.println(player.getHealth());
        }
    }

    public int getHealth() {
        return this.health;
    }
}
