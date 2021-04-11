package dungeoncrawler.entity;

import dungeoncrawler.entity.potion.Potion;
import dungeoncrawler.entity.potion.HealthPotion;
import dungeoncrawler.entity.potion.AttackPotion;
import dungeoncrawler.entity.potion.ZoomPotion;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Player extends Rectangle {

    private static int health = 20;
    private static int damage;
    private static final Weapon[] weaponInventory = {new Weapon("Shortsword", 1),
                                                new Weapon("Bludgeon", 2),
                                                new Weapon("Greatsword", 3)};
    private final static Potion[] potionInventory = {new HealthPotion(), new AttackPotion(), new ZoomPotion()};
    private static final int[] inventoryQuantity = {0, 0, 0, 1, 1, 1}; //alter this to change potion quantity
    private static Weapon currentWeapon;
    private Rectangle weapon;
    private boolean goNorth;
    private boolean goSouth;
    private boolean goEast;
    private boolean goWest;
    private static boolean alive = true;
    private boolean isAggressive;
    public static int speed = 7;

    public Player(double x, double y, int width, int height, Weapon startingWeapon) {
        super(x, y, width, height);
        this.setVisible(true);
        this.setFill(Color.RED);
        this.isAggressive = true;
        this.setId("player");
        currentWeapon = startingWeapon;
        damage = currentWeapon.getDamage();
    }

    /**
     * Add a potion into potionInventory at the first available slot
     * @param potion Potion to add
     */
    public static void addPotion(Potion potion) {
        for (int i = 0 ; i < 3; i++) {
            if (Player.potionInventory[i] == null) {
                Player.potionInventory[i] = potion;
                return;
            }
        }
        System.out.println("Potion Inventory is full... Can't add potion");
    }

    public Rectangle getWeaponSprite() {
        weapon = new Rectangle(this.getWidth(), this.getHeight());
        if (this.getCurrentWeapon().getName().equals("Bludgeon")) {
            try {
                weapon.setFill(new ImagePattern(new Image(new FileInputStream(
                        System.getProperty("user.dir") + "\\res\\bludgeon.png"))));
            } catch (FileNotFoundException exception) {
                System.out.println("Bludgeon image not found " + exception);
            }
        } else if (this.getCurrentWeapon().getName().equals("Greatsword")) {
            try {
                weapon.setFill(new ImagePattern(new Image(new FileInputStream(
                        System.getProperty("user.dir") + "\\res\\greatsword.png"))));
            } catch (FileNotFoundException exception) {
                System.out.println("Greatsword image not found " + exception);
            }
        } else {
            try {
                weapon.setFill(new ImagePattern(new Image(new FileInputStream(
                        System.getProperty("user.dir") + "\\res\\shortsword.png"))));
            } catch (FileNotFoundException exception) {
                System.out.println("Shortsword image not found " + exception);
            }
        }
        weapon.setX(this.getX() + 25);
        weapon.setY(this.getY());
        return (weapon);
    }

    public void move(int height, int width) {

        int dx = 0;
        int dy = 0;
        if (goNorth) {
            dy = -this.speed;
        }
        if (goWest) {
            dx = -this.speed;
        }
        if (goSouth) {
            dy = this.speed;
        }
        if (goEast) {
            dx = this.speed;
        }

        double newX = this.getX() + dx;
        double newY = this.getY() + dy;
        if (newY < 0) {
            newY = 0;
        }
        if (newX < 0) {
            newX = 0;
        }
        if (newY + this.getHeight() > height) {
            newY = height - this.getHeight();
        }
        if (newX + this.getWidth() > width) {
            newX = width - this.getWidth();
        }

        this.setX(newX);
        weapon.setX(newX + 25);
        this.setY(newY);
        weapon.setY(newY);
    }

    public void takeDamage(int damageCount) {
        health -= damageCount;
        if (health <= 0) {
            alive = false;
        }
    }

    public static void updateWeapon(Weapon newWeapon) {
        currentWeapon = newWeapon;
    }

    public static Weapon[] getWeaponInventory() {
        return weaponInventory;
    }

    public static Potion[] getPotionInventory() {
        return potionInventory;
    }

    public static int[] getInventoryQuantity() {
        return inventoryQuantity;
    }

    public static Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public static boolean isAlive() {
        return alive;
    }

    public static void setHealth(int newHealth) {
        health = newHealth;
    }

    public static void setIsAlive(boolean isAlive) {
        alive = isAlive;
    }

    public void setGoNorth(boolean goNorth) {
        this.goNorth = goNorth;
    }

    public void setGoSouth(boolean goSouth) {
        this.goSouth = goSouth;
    }

    public void setGoEast(boolean goEast) {
        this.goEast = goEast;
    }

    public void setGoWest(boolean goWest) {
        this.goWest = goWest;
    }

    public static int getDamage() {
        return damage;
    }

    public static void setDamage(int newDamage) {
        damage = newDamage;
    }

    public void setIsAggressive(boolean isAggressive) {
        this.isAggressive = isAggressive;
    }

    public boolean getIsAggressive() {
        return this.isAggressive;
    }

    public static int getHealth() {
        return health;
    }
    public static void setSpeed(int speed) {
        Player.speed = speed;
    }


}
