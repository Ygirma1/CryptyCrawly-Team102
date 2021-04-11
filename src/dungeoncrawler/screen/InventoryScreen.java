package dungeoncrawler.screen;

import dungeoncrawler.entity.Weapon;
import dungeoncrawler.entity.Player;
import dungeoncrawler.entity.potion.AttackPotion;
import dungeoncrawler.entity.potion.HealthPotion;
import dungeoncrawler.entity.potion.Potion;
import dungeoncrawler.entity.potion.ZoomPotion;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

//import java.io.FileInputStream;

public class InventoryScreen {
    private final Weapon[] weapons;
    private final Potion[] potions;
    private final int[] quantities;
    private Button backButton;
    private Player player;
    private final Button[] items;
    private final int width;
    private final int height;

    public InventoryScreen() {
        this(500, 500);
    }


    public InventoryScreen(int width, int height) {
        this.width = width;
        this.height = height;
        this.weapons = Player.getWeaponInventory();
        this.potions = Player.getPotionInventory();
        this.quantities = Player.getInventoryQuantity();
        this.items = new Button[6];

        //init empty buttons
        for (int i = 0; i < 6; i++) {
            int finalI = i;
            if (i < 3) {
                items[i] = new Button(weapons[i].getName());
                if (weapons[i].getName() == "Shortsword") {
                    try {
                        Image shortSword = new Image(new FileInputStream(
                                System.getProperty("user.dir") + "\\res\\shortsword.png"));
                        ImageView shortSwordView = new ImageView(shortSword);
                        shortSwordView.setFitWidth(32);
                        shortSwordView.setFitHeight(32);
                        items[i].setGraphic(shortSwordView);
                    } catch (FileNotFoundException exception) {
                        System.out.println("Shortsword image not found " + exception);
                    }
                } else if (weapons[i].getName() == "Bludgeon") {
                    try {
                        Image bludgeon = new Image(new FileInputStream(
                                System.getProperty("user.dir") + "\\res\\bludgeon.png"));
                        ImageView bludgeonView = new ImageView(bludgeon);
                        bludgeonView.setFitWidth(32);
                        bludgeonView.setFitHeight(32);
                        items[i].setGraphic(bludgeonView);
                    } catch (FileNotFoundException exception) {
                        System.out.println("Bludgeon image not found " + exception);
                    }
                } else if (weapons[i].getName() == "Greatsword") {
                    try {
                        Image greatSword = new Image(new FileInputStream(
                                System.getProperty("user.dir") + "\\res\\greatsword.png"));
                        ImageView greatSwordView = new ImageView(greatSword);
                        greatSwordView.setFitWidth(40);
                        greatSwordView.setFitHeight(40);
                        items[i].setGraphic(greatSwordView);
                    } catch (FileNotFoundException exception) {
                        System.out.println("Greatsword image not found " + exception);
                    }
                }
                items[i].setOnAction(e -> {
                    Player.updateWeapon(Player.getWeaponInventory()[finalI]);
                });
            } else {
                items[i] = new Button(potions[i - 3].toString());
                if (potions[i - 3] instanceof HealthPotion) {
                    try {
                        items[i].setGraphic(new ImageView(new Image(new FileInputStream(
                                System.getProperty("user.dir") + "\\res\\healthPotion.png"))));
                    } catch (FileNotFoundException exception) {
                        System.out.println("Health potion image not found " + exception);
                    }
                } else if (potions[i - 3] instanceof ZoomPotion) {
                    try {
                        items[i].setGraphic(new ImageView(new Image(new FileInputStream(
                                System.getProperty("user.dir") + "\\res\\zoomPotion.png"))));
                    } catch (FileNotFoundException exception) {
                        System.out.println("Zoom potion image not found " + exception);
                    }
                } else if (potions[i - 3] instanceof AttackPotion) {
                    try {
                        items[i].setGraphic(new ImageView(new Image(new FileInputStream(
                                System.getProperty("user.dir") + "\\res\\attackPotion.png"))));
                    } catch (FileNotFoundException exception) {
                        System.out.println("Attack potion image not found " + exception);
                    }
                }
                items[i].setOnAction(e -> {
                    potions[finalI - 3].applyEffect();
                    quantities[finalI]--;
                    if (quantities[finalI] <= 0) {
                        items[finalI].setDisable(true);
                    }
                });
            }
            items[i].setPrefSize(125, 70);
            if (quantities[i] <= 0) {
                items[i].setDisable(true);
            }

        }

        this.backButton = new Button("Back");
        this.backButton.setLayoutX(450);
        this.backButton.setLayoutY(450);
        this.backButton.setPrefSize(50, 50);
        this.backButton.setId("backButton");
    }

    //alter player inventory in player constructor
    public Scene getScene() {
        VBox weaponBox = new VBox();
        for (Button button: items) {
            if (button != null) {
                weaponBox.getChildren().add(button);
            }
        }
        Pane pane = new Pane();
        pane.getChildren().addAll(weaponBox, this.backButton);

        return new Scene(pane, width, height);
    }

    public Button[] getItems() {
        return this.items;
    }


    public Button getBackButton() {
        return backButton;
    }

    public void setBackButton(Button backButton) {
        this.backButton = backButton;
    }
}
