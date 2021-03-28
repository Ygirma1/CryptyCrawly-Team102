package dungeoncrawler;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
//import javafx.animation.Animation;
//import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
//import java.security.Key;

import java.util.Random;

// In case where you wanna create your own monster,
// just extends this class and implement startMoving
public class Monster extends Rectangle {
    private int health;
    private int damage = 1;
    private boolean alive = true;

    public Monster(int width, int height, int health, Color color) {
        super(width, height, color);
        this.health = health;
    }

    /**
     * Continuous function for animating the movement of the monster class.
     *
     * @param pane the pane the monster is in
     */
    public void move(Pane pane) {
        if (this.alive) {
            Random rand = new Random();
            int maxValue = (int) Math.abs(pane.getWidth() - this.getWidth());
            int endX = rand.nextInt(maxValue);
            int endY = rand.nextInt(maxValue);
            double duration = 2.5;
            int maxDistance = (int) Math.max(Math.abs(endX - this.getX()),
                    Math.abs(endY - this.getY()));
            if (maxDistance <= 150) {
                duration = .8;
            } else if (maxDistance <= 275) {
                duration = 1.5;
            }
            KeyValue x = new KeyValue(this.layoutXProperty(), endX);
            KeyValue y = new KeyValue(this.layoutYProperty(), endY);
            KeyFrame frame = new KeyFrame(Duration.seconds(rand.nextDouble() + duration), x, y);
            Timeline timeline = new Timeline(frame);
            timeline.setCycleCount(1);
            timeline.play();
            timeline.setOnFinished(e -> {
                move(pane);
            });
        }
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
            if (player.getIsAggressive()) {
                System.out.println("Attacking monster");
                this.takeDamage(player.getDamage());
            } else {
                System.out.println("Monster attacking");
                player.takeDamage(this.damage);
            }
            player.takeDamage(this.damage);
            System.out.println(player.getHealth());
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }



}
