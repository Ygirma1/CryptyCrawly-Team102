package dungeoncrawler;

import javafx.scene.control.ButtonType;
import dungeoncrawler.entity.Difficulty;
import dungeoncrawler.entity.Player;
import dungeoncrawler.entity.Weapon;
import dungeoncrawler.entity.monster.Monster;
import dungeoncrawler.entity.monster.DogeMonster;
import dungeoncrawler.screen.*;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import java.util.*;
import javafx.scene.control.Alert;
import org.assertj.core.internal.Diff;


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
    private Player currPlayer;
    private static boolean challengeRoom1Reached = false;
    private static boolean challengeRoom2Reached = false;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Dungeon Crawler");
        //initRoom(new DogeRoom(500, 500, "boss", Difficulty.EASY));
        welcomeScreen();
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
            int selectedIndex = configScreen.getWeaponDropdown().getItems().indexOf(
                    configScreen.getWeaponDropdown().getValue());
            this.startingWeapon = Player.getWeaponInventory()[selectedIndex];
            Player.getInventoryQuantity()[selectedIndex] = 1;
            //not sure if needed anymore
            this.weapon = configScreen.getWeaponDropdown().getValue();
            Room start = new Room("start", configScreen.getDifficulty());
            start.generateMap(start);
            initRoom(start);
        });
        this.primaryStage.setScene(configScreen.getScene());
        this.primaryStage.show();
    }

    private void initRoom(Room room) {
        if (room instanceof ChallengeRoom1) {
            Button exitButton = ((ChallengeRoom1) room).getChallengeExitButton();
            exitButton.setOnAction(e -> {
                Room challengeRoom2 = new ChallengeRoom2(500, 500, "ChallengeRoom2", Controller.diff);
                challengeRoom2.setPlayer(currPlayer);
                initRoom(challengeRoom2);
            });
        }

        if (room instanceof ChallengeRoom2) {
            Button exitButton = ((ChallengeRoom2) room).getChallengeExitButton();
            exitButton.setOnAction(e -> {
                int prevRoomIndex = ((ChallengeRoom2) room).getPrevRoomIndex();
                Room dogeRoom = new DogeRoom(500, 500, "Doge", Controller.diff);
                dogeRoom.setPlayer(currPlayer);
                initRoom(dogeRoom);
            });
        }

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
                winScreen();
            });
        }

        Player player = new Player(100, 100, 50, 50, this.startingWeapon);
        currPlayer = player;

        Monster monster = room.getMonster();
        if (room instanceof ChallengeRoom1) {
            challengeRoom1Reached = true;

            if (ChallengeRoom1.isChallengeCompleted()) {
                // this is so that when we go back from the inventory, the challenge does not start again
                ((ChallengeRoom1) room).setMonsterArrayList(new ArrayList<>());
                ((ChallengeRoom1) room).setMonsterHealthRectList(new ArrayList<>());
                ((ChallengeRoom1) room).updateMonsterHealthBar();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Challenge");
                alert.setHeaderText("A challenge is presented");
                alert.setContentText("Do you want to take the challenge?\nIf you win, you get hella prizes!");

                ButtonType buttonTypeOK = new ButtonType("OK");
                ButtonType buttonTypeNope = new ButtonType("Nope");
                alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeNope);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == buttonTypeNope) {
                    ((ChallengeRoom1) room).setMonsterArrayList(new ArrayList<>());
                    ((ChallengeRoom1) room).setMonsterHealthRectList(new ArrayList<>());
                    ChallengeRoom1.setChallengeCompleted(true);
                    ChallengeRoom1.setItemDropsAvailable(false);
                    ((ChallengeRoom1) room).getChallengeExitButton().setDisable(false);
                } else {
                    ((ChallengeRoom1) room).updateMonsterHealthBar();
                }
            }
        } else if (room instanceof ChallengeRoom2) {
            challengeRoom2Reached = true;

            if (ChallengeRoom2.isChallengeCompleted()) {
                // this is so that when we go back from the inventory, the challenge does not start again
                ((ChallengeRoom2) room).setMonsterArrayList(new ArrayList<>());
                ((ChallengeRoom2) room).setMonsterHealthRectList(new ArrayList<>());
                ((ChallengeRoom2) room).updateMonsterHealthBar();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Challenge");
                alert.setHeaderText("A challenge is presented");
                alert.setContentText("Do you want to take the challenge?\nIf you win, you get hella prizes!");

                ButtonType buttonTypeOK = new ButtonType("OK");
                ButtonType buttonTypeNope = new ButtonType("Nope");
                alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeNope);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == buttonTypeNope) {
                    ((ChallengeRoom2) room).setMonsterArrayList(new ArrayList<>());
                    ((ChallengeRoom2) room).setMonsterHealthRectList(new ArrayList<>());
                    ChallengeRoom2.setChallengeCompleted(true);
                    ChallengeRoom2.setItemDropsAvailable(false);
                    ((ChallengeRoom2) room).getChallengeExitButton().setDisable(false);
                } else {
                    ((ChallengeRoom2) room).updateMonsterHealthBar();
                }
            }
        } else {
            roomMonster = monster;
            room.updateMonsterHealthBar();
        }
        room.setPlayer(player);

        if (Player.getHealth() > 20) {
            Player.setHealth(Player.ORIGINAL_HEALTH);
        }

        room.updateHealthBar();
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
                if (room instanceof ChallengeRoom1) {
                    for (Monster monster : ((ChallengeRoom1) room).getMonsterArrayList()) {
                        monster.attackPlayer(player);
                        ((ChallengeRoom1) room).updateMonsterHealthBar();

                        if (((ChallengeRoom1) room).allMonstersAreDead()) {
                            cancel();
                        }
                    }
                } else if (room instanceof ChallengeRoom2) {
                    for (Monster monster : ((ChallengeRoom2) room).getMonsterArrayList()) {
                        monster.attackPlayer(player);
                        ((ChallengeRoom2) room).updateMonsterHealthBar();

                        if (((ChallengeRoom2) room).allMonstersAreDead()) {
                            cancel();
                        }
                    }
                } else if (room instanceof DogeRoom) {
                    DogeMonster doge = ((DogeRoom) room).getDogeMonster();
                    doge.attackPlayer(player);
                    ((DogeRoom) room).updateDogeHealthBar();
                    if (!doge.isAlive() || !Player.isAlive()) {
                        cancel();
                    }
                    if (!doge.isAlive()) {
                        ((DogeRoom) room).getExitButton().setDisable(false);
                    }
                } else {
                    monster.attackPlayer(player);
                    room.updateMonsterHealthBar();
                    if (!monster.isAlive() || !Player.isAlive()) {
                        cancel();
                    }
                }
                room.updateHealthBar();
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
        if (room instanceof ChallengeRoom1) {
            for (Monster tempMonster : ((ChallengeRoom1) room).getMonsterArrayList()) {
                tempMonster.move((Pane) this.primaryStage.getScene().getRoot());
                TimerTask task = new Helper();
                timer.schedule(task, 0, 500);
            }
        } else if (room instanceof ChallengeRoom2) {
            for (Monster tempMonster : ((ChallengeRoom2) room).getMonsterArrayList()) {
                tempMonster.move((Pane) this.primaryStage.getScene().getRoot());
                TimerTask task = new Helper();
                timer.schedule(task, 0, 500);
            }
        } else {
            if (room instanceof DogeRoom) {
                ((DogeRoom) room).getDogeMonster().move((Pane) this.primaryStage.getScene().getRoot());
                TimerTask task = new Helper();
                timer.schedule(task, 0, 500);
            } else if (monster != null) {
                monster.move((Pane) this.primaryStage.getScene().getRoot());
                TimerTask task = new Helper();
                timer.schedule(task, 0, 500);
            }
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
                if ((challengeRoom1Reached && !ChallengeRoom1.isChallengeCompleted()
                        || (challengeRoom2Reached && !ChallengeRoom2.isChallengeCompleted()))) {
                    // disable inventory button while in the challenge
                    break;
                }
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
            default:
                break;
            }

            if (room instanceof ChallengeRoom1) {
                if (((ChallengeRoom1) room).allMonstersAreDead() && ChallengeRoom1.isItemDropsAvailable()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Challenge Complete");
                    alert.setContentText("You received 2 potions of each type");
                    ((ChallengeRoom1) room).getChallengeExitButton().setDisable(false);
                    player.getInventoryQuantity()[3] += 2;
                    player.getInventoryQuantity()[4] += 2;
                    player.getInventoryQuantity()[5] += 2;
                    alert.show();
                    ((ChallengeRoom1) room).setItemDropsAvailable(false);
                    ((ChallengeRoom1) room).getChallengeExitButton().setDisable(false);
                    ((ChallengeRoom1) room).setChallengeCompleted(true);
                }
            }
            if (room instanceof ChallengeRoom2) {
                if (((ChallengeRoom2) room).allMonstersAreDead() && ChallengeRoom2.isItemDropsAvailable()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Challenge Complete");
                    alert.setContentText("You received 75 gold for completing the room!");
                    ((ChallengeRoom2) room).getChallengeExitButton().setDisable(false);
                    Controller.setGold(Controller.getGold() + 75);
                    alert.show();
                    ((ChallengeRoom2) room).setItemDropsAvailable(false);
                    ((ChallengeRoom2) room).getChallengeExitButton().setDisable(false);
                    ((ChallengeRoom2) room).setChallengeCompleted(true);
                }
            }


            if ((!(room instanceof ChallengeRoom1) || !(room instanceof ChallengeRoom2))
                    && monster != null && !monster.isAlive()) {
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
                        } else if (randomNumber2 == 2) {
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

        if (room instanceof ChallengeRoom1) {
            for (Monster tempMonster : ((ChallengeRoom1) room).getMonsterArrayList()) {
                tempMonster.setOnMouseClicked(e -> {
                    tempMonster.takeDamage(player.getDamage());
                    room.updateMonsterHealthBar();
                });
            }
        } else if (room instanceof ChallengeRoom2) {
            for (Monster tempMonster : ((ChallengeRoom2) room).getMonsterArrayList()) {
                tempMonster.setOnMouseClicked(e -> {
                    tempMonster.takeDamage(player.getDamage());
                    room.updateMonsterHealthBar();
                });
            }
        } else if (room instanceof DogeRoom) {
            DogeRoom dogeRoom = (DogeRoom) room;
            DogeMonster dogeMonster = dogeRoom.getDogeMonster();
            if (dogeMonster != null) {
                dogeMonster.setOnMouseClicked(e -> {
                    dogeMonster.takeDamage(player.getDamage());
                    dogeRoom.updateDogeHealthBar();
                    if (dogeMonster != null && !dogeMonster.isAlive()) {
                        dogeRoom.openClosedExits(dogeRoom);
                    }
                });
            }
        } else {
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


    }

    private void gameOverScreen() {
        GameOverScreen gameOverScreen = new GameOverScreen();
        Button playButton = gameOverScreen.getPlayButton();
        playButton.setOnAction(e -> {
            Room start = new Room("start", diff);
            Player.resetStats();
            start.generateMap(start);
            initRoom(start);
        });
        this.primaryStage.setScene(gameOverScreen.getScene());
        this.primaryStage.show();
    }

    public void winScreen() {
        WinScreen winScreen = new WinScreen();
        Button playAgainButton = winScreen.getPlayButton();
        playAgainButton.setOnAction(e -> {
            Room start = new Room("start", diff);
            start.generateMap(start);
            Player.resetStats();
            initRoom(start);
        });
        this.primaryStage.setScene(winScreen.getScene());
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

    public static Difficulty getDifficulty() {
        return diff;
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

    public Player getPlayer() {
        return (currPlayer);
    }


}
