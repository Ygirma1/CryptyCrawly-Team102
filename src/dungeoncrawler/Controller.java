package dungeoncrawler;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;

import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.awt.event.KeyListener;

public class Controller extends Application {
    private Stage primaryStage;
    private final int width = 500;
    private final int height = 500;
    private String characterName = "";
    private String difficulty = "";
    private String weapon = "";
    private String correctExitText = "";
    private static Difficulty diff;
    private static int gold = 0;
    private Button helpButton;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Dungeon Crawler");

        Room start = new Room("start", Difficulty.EASY);
        start.generateMap(start);
        initRoom(start);

        welcomeScreen();
    }

    private void welcomeScreen() {
        WelcomeScreen welcomeScreen = new WelcomeScreen();
        Button play = welcomeScreen.getPlayButton();
        this.primaryStage.setScene(welcomeScreen.getScene());
        this.primaryStage.show();
        Button startButton = welcomeScreen.getPlayButton();
        startButton.setOnAction(e -> {
            initConfigScreen();
        });
    }

    private void initConfigScreen() {
        ConfigScreen configScreen = new ConfigScreen(width, height);
        Button proceedButton = configScreen.getProceedButton();
        proceedButton.setOnAction(e -> {
            // Checking if character name is valid
            this.characterName = configScreen.getNameField().getText();
            if (this.characterName == null || this.characterName.length() == 0
                    || this.characterName.strip().length() == 0) {
                configScreen.getNameField().setText("Please enter a character name.");
                return;
            }
            //Checking if selected difficulty is valid (not null)
            if (configScreen.getDifficultyRBGroup().getSelectedToggle() == null) {
                return;
            }
            this.difficulty = configScreen.getDifficulty().toString();
            this.diff = configScreen.getDifficulty();
            this.gold = 100 - 25 * configScreen.getDifficulty().ordinal();
            //Checking if selected weapon is valid (not null)
            if (configScreen.getWeaponDropdown().getValue() == null) {
                return;
            }
            this.weapon = configScreen.getWeaponDropdown().getValue();
            Room start = new Room("start", configScreen.getDifficulty());
            start.generateMap(start);
            initRoom(start);
        });
        this.primaryStage.setScene(configScreen.getScene());
        this.primaryStage.show();
    }

    private void initRoom(Room room) {
        if (room instanceof DogeRoom) {
            Button exitButton = ((DogeRoom) room).getExitButton();
            exitButton.setOnAction(e -> {
                Room goldRoom = new GoldRoom(500, 500, "gold", Controller.diff);
                initRoom(goldRoom);
            });
        }

        if (room instanceof GoldRoom) {
            Button exitButton = ((GoldRoom) room).getExitButton();
            exitButton.setOnAction(e -> {
                Room puzzleRoom = new PuzzleRoom(500, 500, Controller.diff);
                initRoom(puzzleRoom);
            });
        }

        if (room instanceof PuzzleRoom) {
            Button exitButton = ((PuzzleRoom) room).getExitButton();
            exitButton.setOnAction(e -> {
                Room winningRoom = new WinningRoom(500, 500, Controller.diff);
                initRoom(winningRoom);
            });
        }

        Player player = new Player(100, 100, 50, 50);
        room.setPlayer(player);

        Button right = room.getBRight();
        right.setOnAction(e -> {
            if (room.getRight() != null) {
                initRoom(room.getRight());
            }
        });
        Button left = room.getBLeft();
        left.setOnAction(e -> {
            if (room.getLeft() != null) {
                initRoom(room.getLeft());
            }
        });
        Button up = room.getBUp();
        up.setOnAction(e -> {
            if (room.getUp() != null) {
                initRoom(room.getUp());
            }
        });
        Button down = room.getBDown();
        down.setOnAction(e -> {
            if (room.getDown() != null) {
                initRoom(room.getDown());
            }
        });
        Button help = room.getHelpButton();
        helpButton = help;
        help.setOnAction(e -> {
            help.setVisible(false);
            String exit = room.getCorrectExitRoomName();
            correctExitText = exit;
        });
        this.primaryStage.setScene(room.getScene());
        primaryStage.getScene().setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.W)) {
                player.move(0, -4);
            }
            if (e.getCode().equals(KeyCode.A)) {
                player.move(-4, 0);
            }
            if (e.getCode().equals(KeyCode.S)) {
                player.move(0, 4);
            }
            if (e.getCode().equals(KeyCode.D)) {
                player.move(4, 0);
            }
        });
        this.primaryStage.show();
    }

    private void proceedToGameScreen() {
        InitialGameScreen screen = new InitialGameScreen(difficulty, width, height);
        this.primaryStage.setScene(screen.getScene());
        this.primaryStage.show();
        gold = screen.getGold();
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getWeapon() {
        return weapon;
    }

    public static int getGold() {
        return gold;
    }

    public static void setGold(int newGold) {
        gold = newGold;
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Button getHelpButton() {
        return helpButton;
    }

    public String getCorrectRoomText() {
        return correctExitText;
    }
}
