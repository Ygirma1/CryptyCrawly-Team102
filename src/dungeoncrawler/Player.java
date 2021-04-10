package dungeoncrawler;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {

    private static int health = 20;
    private static int damage;
    private static Weapon[] weaponInventory = {new Weapon("Shortsword", 1),
                                                new Weapon("Bludgeon", 2),
                                                new Weapon("Greatsword", 3)};
    private static Potion[] potionInventory;
    //Indices for three weapons and three potions
    private static int[] inventoryQuantity = {0, 0, 0, 0, 0, 0, 0};
    private static Weapon currentWeapon;

    private boolean goNorth;
    private boolean goSouth;
    private boolean goEast;
    private boolean goWest;
    private static boolean alive = true;
    private boolean isAggressive;

    public Player(double x, double y, int width, int height, Weapon startingWeapon) {
        super(x, y, width, height);
        this.setVisible(true);
        this.setFill(Color.RED);
        this.isAggressive = true;
        this.setId("player");
        currentWeapon = startingWeapon;
        damage = currentWeapon.getDamage();
    }

    public void move() {
        int dx = 0;
        int dy = 0;
        if (goNorth) {
            dy = -7;
        }
        if (goWest) {
            dx = -7;
        }
        if (goSouth) {
            dy = 7;
        }
        if (goEast) {
            dx = 7;
        }

        this.setX(this.getX() + dx);
        this.setY(this.getY() + dy);
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

    public int getDamage() {
        return damage;
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
}
