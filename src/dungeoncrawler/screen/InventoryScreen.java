package dungeoncrawler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;

public class InventoryScreen {
    private Weapon[] weapons;
    private Potion[] potions;
    private int[] quantities;
    private Button[] items;
    private int width;
    private int height;

    public InventoryScreen() {
        this(500,500);
    }

    public InventoryScreen(int width, int height) {
        this.width = width;
        this.height = height;
        this.weapons = Player.getWeaponInventory();
        this.potions = Player.getPotionInventory();
        this.quantities = Player.getInventoryQuantity();
        this.items = new Button[6];

        for (int i = 0; i < 3; i++) {
            items[i] = new Button(weapons[i].getName());
            items[i].setPrefSize(40, 80);

            if (potions != null) { //TODO delete line after implementing potions
                items[i + 3] = new Button(potions[i].toString()); //TODO update name of potions
                items[i + 3].setPrefSize(40, 80);
            }
        }
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
        pane.getChildren().add(weaponBox);
        return new Scene(pane, width, height);
    }

    public Button[] getItems() {
        return this.items;
    }
}
