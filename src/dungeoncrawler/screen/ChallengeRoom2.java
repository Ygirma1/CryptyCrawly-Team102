package dungeoncrawler.screen;

import dungeoncrawler.Controller;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import dungeoncrawler.entity.Difficulty;

import dungeoncrawler.entity.monster.GreenMonster;
import dungeoncrawler.entity.monster.Monster;
import dungeoncrawler.entity.monster.PinkMonster;
import dungeoncrawler.entity.monster.YellowMonster;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import javafx.scene.shape.Rectangle;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ChallengeRoom2 extends Room {

    private final Button challengeExitButton;
    private static ArrayList<Monster> monsterArrayList;
    private ArrayList<Rectangle> monsterHealthRectList;
    private static boolean itemDropsAvailable = true;
    private static boolean challengeCompleted = false;

    public ChallengeRoom2(int width, int height, String id, Difficulty diff) {
        super(width, height, id, diff);
        this.challengeExitButton = new Button("Exit");
        this.challengeExitButton.setLayoutX(450);
        this.challengeExitButton.setLayoutY(200);
        this.challengeExitButton.setPrefSize(50, 50);
        this.challengeExitButton.setDisable(true);
        this.challengeExitButton.setId("exitButton");
        generateMonsters();
    }

    public Scene getScene() {
        this.getGoldText().setText("Gold: " + Controller.getGold());

        Pane pane = new Pane();

        Group healthGroup = new Group();
        healthGroup.getChildren().addAll(this.getHealthText(), getHealthRect());
        this.getHealthText().relocate(5, 5);
        this.getHealthRect().relocate(35, 6);

        Group monsterHealthGroup = new Group();

        // add monster health
        if (this.monsterHealthRectList.size() != 0) {
            int startX = 395;
            int startY = 46;

            monsterHealthGroup.getChildren().add(this.getMonsterHealthText());

            for (int i = 0; i < this.monsterHealthRectList.size(); i++) {
                monsterHealthGroup.getChildren().add(this.monsterHealthRectList.get(i));
                this.monsterHealthRectList.get(i).relocate(startX, startY + i * 20);
            }
            this.getMonsterHealthText().relocate(startX, 25);
        }

        pane.getChildren().addAll(this.getGoldText(), healthGroup, monsterHealthGroup, challengeExitButton);

        Group playerWeapon = new Group();
        playerWeapon.getChildren().addAll(this.getPlayer(), this.getPlayer().getWeaponSprite());
        pane.getChildren().add(playerWeapon);

        // add monster

        if (this.monsterArrayList.size() != 0) {
            for (Monster monster : this.monsterArrayList) {
                pane.getChildren().add(monster);
            }
        }

        Rectangle background = new Rectangle(this.getWidth(), this.getHeight(), this.getFloorColor());
        StackPane sPane = new StackPane();
        sPane.getChildren().addAll(background, pane);

        return new Scene(sPane, this.getWidth(), this.getHeight());

    }

    private void generateMonsters() {
        monsterArrayList = new ArrayList<>();
        monsterHealthRectList = new ArrayList<>();
        Random rand = new Random();
        int numberOfMonster = Math.abs(rand.nextInt() % 3) + 2;

        for (int i = 0; i < numberOfMonster; i++) {
            int monsterSpawn = Math.abs(rand.nextInt() % 3);

            Monster monster = null;
            switch (monsterSpawn) {
                case 0:
                    try {
                        monster = new GreenMonster(60, 60, 3,
                                new ImagePattern(new Image(new FileInputStream(
                                        System.getProperty("user.dir") + "\\res\\greenMonster.png"))));
                        monsterHealthRectList.add(new Rectangle(100, 15, Color.GREEN));
                    } catch (FileNotFoundException exception) {
                        System.out.println("Green monster image not found " + exception);
                    }
                    break;
                case 1:
                    try {
                        monster = new PinkMonster(70, 70, 4,
                                new ImagePattern(new Image(new FileInputStream(
                                        System.getProperty("user.dir") + "\\res\\pinkMonster.png"))));
                        monsterHealthRectList.add(new Rectangle(100, 15, Color.HOTPINK));
                    } catch (FileNotFoundException exception) {
                        System.out.println("Pink monster image not found " + exception);
                    }
                    break;
                case 2:
                    try {
                        monster = new YellowMonster(80, 80, 5,
                                new ImagePattern(new Image(new FileInputStream(
                                        System.getProperty("user.dir") + "\\res\\yellowMonster.png"))));
                        monsterHealthRectList.add(new Rectangle(100, 15, Color.YELLOW));
                    } catch (FileNotFoundException exception) {
                        System.out.println("Yellow monster image not found " + exception);
                    }
                    break;
                default:
                    break;
            }
            if (monster != null) {
                this.monsterArrayList.add(monster);
            }
        }
    }

    public boolean allMonstersAreDead() {
        for (Monster monster : this.monsterArrayList) {
            if (monster.isAlive()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateMonsterHealthBar() {
        for (int i = 0; i < this.monsterHealthRectList.size();i++) {
            this.monsterHealthRectList.get(i).setWidth(this.monsterArrayList.get(i).getHealth() * 20);
        }
    }


    public static ArrayList<Monster> getMonsterArrayList() {
        return monsterArrayList;
    }

    public void setMonsterArrayList(ArrayList<Monster> monsterArrayList) {
        this.monsterArrayList = monsterArrayList;
    }

    public ArrayList<Rectangle> getMonsterHealthRectList() {
        return monsterHealthRectList;
    }

    public void setMonsterHealthRectList(ArrayList<Rectangle> monsterHealthRectList) {
        this.monsterHealthRectList = monsterHealthRectList;
    }

    public Button getChallengeExitButton() {
        return challengeExitButton;
    }

    public static boolean isItemDropsAvailable() {
        return itemDropsAvailable;
    }

    public static void setItemDropsAvailable(boolean itemDropsAvailable) {
        ChallengeRoom2.itemDropsAvailable = itemDropsAvailable;
    }

    public static boolean isChallengeCompleted() {
        return challengeCompleted;
    }

    public static void setChallengeCompleted(boolean challengeCompleted) {
        ChallengeRoom2.challengeCompleted = challengeCompleted;
    }
}
