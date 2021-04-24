package dungeoncrawler.entity.monster;
import dungeoncrawler.entity.Player;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
//import javafx.animation.Animation;
//import javafx.geometry.Bounds;
//import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
//import java.security.Key;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Timer;

// In case where you wanna create your own monster,
// just extends this class and implement startMoving
public class Monster extends Rectangle {
    private int health;
    private int damage = 1;
    private boolean alive = true;
    private boolean itemDropAvailable;

    public Monster(int width, int height, int health, Color color) {
        super(width, height, color);
        this.health = health;
        this.itemDropAvailable = true;
    }

    /**
     * Continuous function for animating the movement of the monster class.
     *
     * @param pane the pane the monster is in
     */
    public void move(Pane pane) {
        if (this.alive && Player.isAlive()) {
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
        if (this.health <= 0) {
            Player.killMonster();
            this.setVisible(false);
            this.alive = false;
        } else {
            if (this instanceof GreenMonster) {
                try {
                    this.setFill(new ImagePattern(new Image(new FileInputStream(
                            System.getProperty("user.dir") + "\\res\\greenMonster2.png"))));
                    this.damageAnimation(this, new ImagePattern(new Image(new FileInputStream(
                            System.getProperty("user.dir") + "\\res\\greenMonster.png"))));
                } catch (FileNotFoundException exception) {
                    System.out.println("Green monster image not found " + exception);
                }
            } else if (this instanceof PinkMonster) {
                try {
                    this.setFill(new ImagePattern(new Image(new FileInputStream(
                            System.getProperty("user.dir") + "\\res\\pinkMonster2.png"))));
                    this.damageAnimation(this, new ImagePattern(new Image(new FileInputStream(
                            System.getProperty("user.dir") + "\\res\\pinkMonster.png"))));
                } catch (FileNotFoundException exception) {
                    System.out.println("Pink monster image not found " + exception);
                }
            } else if (this instanceof YellowMonster) {
                try {
                    this.setFill(new ImagePattern(new Image(new FileInputStream(
                            System.getProperty("user.dir") + "\\res\\yellowMonster2.png"))));
                    this.damageAnimation(this, new ImagePattern(new Image(new FileInputStream(
                            System.getProperty("user.dir") + "\\res\\yellowMonster.png"))));
                } catch (FileNotFoundException exception) {
                    System.out.println("Yellow monster image not found " + exception);
                }
            }
        }
    }

    public void damageAnimation(Monster monster, ImagePattern img) {
        Timer t = new java.util.Timer();
        t.schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        monster.setFill(img);
                        t.cancel();
                    }
                }, 50);
    }

    public void attackPlayer(Player player) {
        if (this.getBoundsInParent().intersects(player.getBoundsInParent())) {
            if (player.getIsAggressive()) {
                this.takeDamage(player.getDamage());
            } else {
                player.takeDamage(this.damage);
            }
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

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public boolean isItemDropAvailable() {
        return itemDropAvailable;
    }


    public void setItemDropAvailable(boolean itemDropAvailable) {
        this.itemDropAvailable = itemDropAvailable;
    }
}
