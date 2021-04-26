package dungeoncrawler.screen;

import dungeoncrawler.entity.Difficulty;
import dungeoncrawler.entity.Player;
import dungeoncrawler.entity.monster.Monster;
import dungeoncrawler.entity.monster.DogeMonster;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DogeRoom extends Room {
    private Label instructionLabel;
    private Label instructionLabel2;
    private int dogeCounter;
    private final Button exitButton;
    private final Font smallFont = new Font("High Tower Text", 19);
    private final Text healthText;
    private static Rectangle healthRect;
    private final Text bossHealthText;
    private static Rectangle bossHealthRect;

    private static DogeMonster dogeMonster;
    private static int dogeHealth = 100;
    private Player player;


    public DogeRoom(int width, int height, String id, Difficulty diff) {
        super(width, height, id, diff);

        this.exitButton = new Button("Exit");
        this.exitButton.setLayoutX(420);
        this.exitButton.setLayoutY(200);
        this.exitButton.setDisable(true);
        this.exitButton.setId("exitButton");
        this.exitButton.setPrefSize(80, 30);
        this.exitButton.setFont(smallFont);
        this.exitButton.setStyle("-fx-background-color: #62686F;");

        this.healthText = new Text("HP");
        this.healthText.setFill(Color.RED);
        this.healthText.setFont(smallFont);
        healthRect = new Rectangle(100, 15, Color.RED);
        healthRect.setWidth(Player.getHealth() * 5);

        this.bossHealthText = new Text("Doge HP");
        this.bossHealthText.setFont(smallFont);
        bossHealthRect = new Rectangle(200, 15, Color.DARKRED);

        addDoge(this);
    }

    @Override
    public Scene getScene() {
        Pane pane = new Pane();
        pane.getChildren().add(this.exitButton);
        pane.getChildren().add(dogeMonster);
        if (player == null || player.getWeaponSprite() == null) {
            System.out.println("fail");
        }
        pane.getChildren().addAll(player, player.getWeaponSprite());

        Group healthGroup = new Group();
        healthGroup.getChildren().addAll(healthText, healthRect);
        this.healthText.relocate(5, 5);
        healthRect.relocate(35, 6);

        Group bossHealthGroup = new Group();
        bossHealthGroup.getChildren().addAll(bossHealthText, bossHealthRect);
        this.bossHealthText.relocate(100, 450);
        bossHealthRect.relocate(175, 450);

        pane.getChildren().addAll(healthGroup, bossHealthGroup);

        return new Scene(pane, this.getWidth(), this.getHeight());
    }

    public static void addDoge(DogeRoom bossRoom) {
        try {
            bossRoom.dogeMonster = new DogeMonster(150, 150, dogeHealth,
                    new ImagePattern(new Image(new FileInputStream(
                    System.getProperty("user.dir") + "\\res\\doge.png"))));
            bossRoom.dogeMonster.setVisible(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Button getExitButton() {
        return this.exitButton;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public static DogeMonster getDogeMonster() {
        return dogeMonster;
    }

    public void updateHealthBar() {
        healthRect.setWidth(Player.getHealth() * 5);
    }

    public void updateDogeHealthBar() {
        bossHealthRect.setWidth(dogeMonster.getHealth() * 2);
    }
}