package dungeoncrawler;

//import javafx.animation.KeyFrame;
//import javafx.animation.KeyValue;
//import javafx.animation.Timeline;

import dungeoncrawler.entity.Difficulty;
import dungeoncrawler.entity.Player;
import dungeoncrawler.entity.Weapon;
import dungeoncrawler.entity.monster.Monster;
import dungeoncrawler.screen.*;
import javafx.scene.paint.Color;
import javafx.application.Application;
//import javafx.event.EventHandler;
//import javafx.geometry.Bounds;
//import javafx.scene.Scene;
import javafx.scene.control.Button;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyCodeCombination;
//import javafx.scene.input.KeyCombination;
//import javafx.scene.input.KeyEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
//import javafx.util.Duration;
//import javafx.scene.paint.Color;
//import java.sql.Time;
import javafx.scene.layout.Pane;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.Random;

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
    private String prevExitText = "";
    private Monster roomMonster;
    private Weapon startingWeapon;
    private static Random rand = new Random();

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Dungeon Crawler");

        welcomeScreen();
        //inventoryScreen();
    }

    private void welcomeScreen() {
        WelcomeScreen welcomeScreen = new WelcomeScreen();
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
            diff = configScreen.getDifficulty();
            gold = 100 - 25 * configScreen.getDifficulty().ordinal();
            //Checking if selected weapon is valid (not null)
            if (configScreen.getWeaponDropdown().getValue() == null) {
                return;
            }
            int selectedIndex = configScreen.getWeaponDropdown().getItems().indexOf(configScreen.getWeaponDropdown().getValue());
            this.startingWeapon = Player.getWeaponInventory()[selectedIndex];
            Player.getInventoryQuantity()[selectedIndex] = 1;
            this.weapon = configScreen.getWeaponDropdown().getValue(); //not sure if needed anymore
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

        if (room.getIdText().equals("start")) {
            Player.updateWeapon(null);
            Player.resetStats();
            int index = -1;
            if (this.startingWeapon.getName().equals("Shortsword")) {
                index = 0;
            } else if (this.startingWeapon.getName().equals("Bludgeon")) {
                index = 1;
            } else {
                index = 2;
            }
            Player.getInventoryQuantity()[index] = 1;
        }

        Player player = new Player(100, 100, 50, 50, this.startingWeapon);
        Monster monster = room.getMonster();
        roomMonster = monster;
        room.updateMonsterHealthBar();
        room.setPlayer(player);
        int prevRoomIndex = room.getPrevRoomIndex();
        prevExitText = "" + prevRoomIndex;
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
        this.primaryStage.show();

        class Helper extends TimerTask {
            public void run() {
                monster.attackPlayer(player);
                room.updateMonsterHealthBar();
                room.updateHealthBar();
                if (!monster.isAlive() || !Player.isAlive()) {
                    cancel();
                }
            }
        }

        class PlayerSwitchState extends TimerTask {
            public void run() {
                if (player.getIsAggressive()) {
                    player.setFill(Color.BLUE); // red for aggressive, blue for neutral
                    player.setIsAggressive(false);
                } else {
                    player.setFill(Color.RED);
                    player.setIsAggressive(true);
                }
            }
        }

        Timer timer = new Timer();
        if (monster != null) {
            monster.move((Pane) this.primaryStage.getScene().getRoot());
            TimerTask task = new Helper();
            timer.schedule(task, 0, 500);
        }

        TimerTask playerSwitchState = new PlayerSwitchState();
        if (player.getCurrentWeapon().getName().equals("Bludgeon")) {
            timer.schedule(playerSwitchState, 0, 2000);
        } else if (player.getCurrentWeapon().getName().equals("Greatsword")) {
            timer.schedule(playerSwitchState, 0, 2500);
        } else {
            timer.schedule(playerSwitchState, 0, 1500);
        }

        primaryStage.getScene().setOnKeyPressed(e -> {
            switch (e.getText()) {
            case "w":
                player.setGoNorth(true);
                break;
            case "a":
                player.setGoWest(true);
                break;
            case "s":
                player.setGoSouth(true);
                break;
            case "d":
                player.setGoEast(true);
                break;
            case "b":
                InventoryScreen inventoryScreen = new InventoryScreen();
                inventoryScreen.getBackButton().setOnAction(event -> {
                    initRoom(room);
                });
                primaryStage.setScene(inventoryScreen.getScene());
                break;
            default:
                break;
            }
            player.move(room.getHeight(), room.getWidth());
        });

        primaryStage.getScene().setOnKeyReleased(e -> {
            switch (e.getText()) {
            case "w":
                player.setGoNorth(false);
                break;
            case "a":
                player.setGoWest(false);
                break;
            case "s":
                player.setGoSouth(false);
                break;
            case "d":
                player.setGoEast(false);
                break;
            case "b":
                InventoryScreen inventoryScreen = new InventoryScreen();
                inventoryScreen.getBackButton().setOnAction(event -> {
                    initRoom(room);
                });
                primaryStage.setScene(inventoryScreen.getScene());
                break;
            default:
                break;
            }
            if (monster != null && !monster.isAlive()) {
                if (monster.isItemDropAvailable()) {
                    int randomNumber = rand.nextInt(10) + 1; // 1 to 10
                    if (randomNumber <= 5) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Item Drop Notification");
                        alert.setHeaderText(null);
                        int itemIndex = -1;
                        int randomNumber2 = rand.nextInt(6);
                        System.out.println(randomNumber2);
                        if (randomNumber2 == 0) {
                            alert.setContentText("The monster drops a Health potion");
                            itemIndex = 3;
                        } else if (randomNumber2 == 1) {
                            alert.setContentText("The monster drops an Attack potion");
                            itemIndex = 4;
                        } else if (randomNumber2 == 2){
                            alert.setContentText("The monster drops a Zoom potion");
                            itemIndex = 5;
                        } else if (randomNumber2 == 3) {
                            alert.setContentText("The monster drops a Shortsword");
                            itemIndex = 0;
                        } else if (randomNumber2 == 4) {
                            alert.setContentText("The monster drops a Bludgeon");
                            itemIndex = 1;
                        } else if (randomNumber2 == 5) {
                            alert.setContentText("The monster drops a Greatsword");
                            itemIndex = 2;
                        }
                        if (itemIndex != -1) {
                            if (itemIndex >= 0 && itemIndex <= 2) {
                                if (player.getInventoryQuantity()[itemIndex] != 1) {
                                    player.getInventoryQuantity()[itemIndex]++;
                                    alert.show();
                                }
                            } else {
                                player.getInventoryQuantity()[itemIndex]++;
                                alert.show();
                            }
                        }
                        monster.setItemDropAvailable(false);
                    }
                }
                room.openClosedExits(room);
            }
        });
        primaryStage.getScene().setOnMouseMoved(e -> {
            if (!Player.isAlive()) {
                Player.setIsAlive(true);
                Player.setHealth(20);
                gameOverScreen();
            }
        });
        if (monster != null) {
            monster.setOnMouseClicked(e -> {
                monster.takeDamage(player.getDamage());
                room.updateMonsterHealthBar();
                if (monster != null && !monster.isAlive()) {
                    room.openClosedExits(room);
                }
            });
        }
    }

    private void gameOverScreen() {
        GameOverScreen gameOverScreen = new GameOverScreen();
        Button playButton = gameOverScreen.getPlayButton();
        playButton.setOnAction(e -> {
            Room start = new Room("start", diff);
            start.generateMap(start);
            initRoom(start);
        });
        this.primaryStage.setScene(gameOverScreen.getScene());
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

    public String getPrevExitText() {
        return prevExitText;
    }

    public Monster getRoomMonster() {
        return roomMonster;
    }
}
