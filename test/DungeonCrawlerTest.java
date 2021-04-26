import dungeoncrawler.*;

//import javafx.scene.Node;
import dungeoncrawler.entity.Difficulty;
import dungeoncrawler.entity.Player;
import dungeoncrawler.entity.monster.DogeMonster;
import dungeoncrawler.entity.monster.GreenMonster;
import dungeoncrawler.entity.monster.Monster;
import dungeoncrawler.entity.monster.PinkMonster;
import dungeoncrawler.entity.monster.YellowMonster;
import dungeoncrawler.entity.potion.Potion;
import dungeoncrawler.entity.potion.ZoomPotion;
//import dungeoncrawler.screen.InventoryScreen;
//import javafx.scene.control.Control;
import dungeoncrawler.screen.ChallengeRoom1;
import dungeoncrawler.screen.ChallengeRoom2;
import dungeoncrawler.screen.DogeRoom;
import dungeoncrawler.screen.GoldRoom;
import dungeoncrawler.screen.Room;
import dungeoncrawler.screen.WinScreen;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
//import javafx.scene.control.Button;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
//import javafx.scene.Scene;
import org.junit.Test;
import javafx.scene.shape.Rectangle;
//import org.testfx.assertions.api.ButtonAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
//import org.testfx.matcher.control.ButtonMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;
import javafx.scene.control.Label;

import javafx.scene.text.Text;
//import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
//import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;
import javafx.scene.input.KeyCode;

public class DungeonCrawlerTest extends ApplicationTest {
    private Controller controller = new Controller();
    private final Player player = new Player(250, 250, 100, 100, Player.getWeaponInventory()[1]);
    private final Monster monster = new Monster(50, 50, 25, Color.RED);
    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new Controller();
        controller.start(primaryStage);
    }

    //Peter's code to traverse past the maze room.
    public void traversal() {
        clickOn("Start");
        clickOn("#nameField").write("Chuong");
        clickOn("#easyRB");
        clickOn("#weaponDropdown");
        clickOn("Bludgeon");
        clickOn("PROCEED");

        while (true) {
            Text roomID = lookup("#id").queryText();
            String room = roomID.toString();
            if (room.equals("start")) {
                Label correctExitLabel = (Label) lookup("#correctExit").queryLabeled();
                String correctPath = correctExitLabel.getText().substring(8);
                clickOn(correctPath);
            }
            Label correctExitLabel = (Label) lookup("#correctExit").queryLabeled();
            String correctPath = correctExitLabel.getText().substring(8);
            if (controller.getRoomMonster() != null) {
                controller.getRoomMonster().takeDamage(20);
                type(KeyCode.S, 1);
            }
            if (roomID.getText().equals("new5")) {
                controller.getRoomMonster().takeDamage(20);
                type(KeyCode.S, 1);
                clickOn(correctPath);
                break;
            }
            clickOn(correctPath);
        }
    }

    //pushes through to start room
    public void getToStartRoom() {
        clickOn("Start");
        clickOn("#nameField").write("Test");
        clickOn("#hardRB");
        clickOn("#weaponDropdown");
        clickOn("Bludgeon");
        clickOn("PROCEED");
    }

    @Test
    public void testStart() {
        clickOn("Start");
        verifyThat("ENTER YOUR NAME:", NodeMatchers.isNotNull());
    }

    @Test
    public void testEmptyName() {
        clickOn("Start");
        clickOn("PROCEED");
        verifyThat("#nameField", TextInputControlMatchers.hasText(
                "Please enter a character name."));
    }

    @Test
    public void testConfigScreenNameField() { // test if name field works
        clickOn("Start");
        clickOn("#nameField").write("YEET");
        verifyThat("#nameField", TextInputControlMatchers.hasText("YEET"));
    }

    @Test
    public void testConfigSavedUponProceed() { // test if configs are saved upon proceed to the game
        clickOn("Start");
        clickOn("#nameField").write("Peter");
        clickOn("#hardRB");
        clickOn("#weaponDropdown");
        clickOn("Bludgeon");
        clickOn("PROCEED");
        assertEquals("Peter", controller.getCharacterName());
        assertEquals("Bludgeon", controller.getWeapon());
        assertEquals("HARD", controller.getDifficulty());
    }

    @Test
    public void testEasyDifficulty() {
        clickOn("Start");
        clickOn("#nameField").write("Tristan");
        clickOn("#easyRB");
        clickOn("#weaponDropdown");
        clickOn("Greatsword");
        clickOn("PROCEED");
        assertEquals(controller.getDifficulty(), Difficulty.EASY.toString());
    }

    @Test
    public void testMediumDifficulty() {
        clickOn("Start");
        clickOn("#nameField").write("Tristan");
        clickOn("#mediumRB");
        clickOn("#weaponDropdown");
        clickOn("Greatsword");
        clickOn("PROCEED");
        assertEquals(controller.getDifficulty(), Difficulty.MEDIUM.toString());
    }

    @Test
    public void testHardDifficulty() {
        clickOn("Start");
        clickOn("#nameField").write("Tristan");
        clickOn("#hardRB");
        clickOn("#weaponDropdown");
        clickOn("Greatsword");
        clickOn("PROCEED");
        assertEquals(controller.getDifficulty(), Difficulty.HARD.toString());
    }

    @Test
    public void testEasyGoldAmount() {
        clickOn("Start");
        clickOn("#nameField").write("Nishant");
        clickOn("#easyRB");
        clickOn("#weaponDropdown");
        clickOn("Greatsword");
        clickOn("PROCEED");
        assertEquals(100, Controller.getGold());
    }

    @Test
    public void testMediumGoldAmount() {
        clickOn("Start");
        clickOn("#nameField").write("Nishant");
        clickOn("#mediumRB");
        clickOn("#weaponDropdown");
        clickOn("Greatsword");
        clickOn("PROCEED");
        assertEquals(75, Controller.getGold());
    }

    @Test
    public void testHardGoldAmount() {
        clickOn("Start");
        clickOn("#nameField").write("Nishant");
        clickOn("#hardRB");
        clickOn("#weaponDropdown");
        clickOn("Greatsword");
        clickOn("PROCEED");
        assertEquals(50, Controller.getGold());
    }

    @Test
    public void testGreatSwordWeaponChoice() {
        clickOn("Start");
        clickOn("#nameField").write("Yafet");
        clickOn("#easyRB");
        clickOn("#weaponDropdown");
        clickOn("Greatsword");
        clickOn("PROCEED");
        assertEquals(controller.getWeapon(), "Greatsword");
    }

    @Test
    public void testBludgeonWeaponChoice() {
        clickOn("Start");
        clickOn("#nameField").write("Yafet");
        clickOn("#easyRB");
        clickOn("#weaponDropdown");
        clickOn("Bludgeon");
        clickOn("PROCEED");
        assertEquals(controller.getWeapon(), "Bludgeon");
    }

    @Test
    public void testShortswordWeaponChoice() {
        clickOn("Start");
        clickOn("#nameField").write("Yafet");
        clickOn("#easyRB");
        clickOn("#weaponDropdown");
        clickOn("Shortsword");
        clickOn("PROCEED");
        assertEquals(controller.getWeapon(), "Shortsword");
    }

    @Test
    public void wrongAnswerTest() {
        traversal();

        for (int i = 0; i < 5; i++) {
            clickOn("#dogeButton");
        }

        clickOn("#exitButton");

        clickOn("#goldButton");
        clickOn("#goldButton2");
        clickOn("#goldButton3");
        clickOn("#exitButton");

        clickOn("#Correct1");
        clickOn("#Correct2");
        verifyThat("#Question3", NodeMatchers.isNotNull());
        clickOn("#WrongAnswer");
        verifyThat("#Question2", NodeMatchers.isNotNull());
        clickOn("69");
        verifyThat("#Question1", NodeMatchers.isNotNull());


    }

    @Test
    public void testHelpButtonVisibility() {
        getToStartRoom();
        clickOn("Correct Door");
        assertFalse(controller.getHelpButton().isVisible());
    }

    @Test
    public void testCorrectDoorShowing() {
        clickOn("Start");
        clickOn("#nameField").write("Yafet");
        clickOn("#easyRB");
        clickOn("#weaponDropdown");
        clickOn("Shortsword");
        clickOn("PROCEED");
        clickOn("Correct Door");
        assertFalse(controller.getHelpButton().isVisible());
        if (!controller.getHelpButton().isVisible()) {
            String correctPathParse = controller.getCorrectRoomText();
            clickOn(correctPathParse);
        }
    }

    @Test
    public void testPuzzleRoom() {
        traversal();

        for (int i = 0; i < 5; i++) {
            clickOn("#dogeButton");
        }

        clickOn("#exitButton");

        clickOn("#goldButton");
        clickOn("#goldButton2");
        clickOn("#goldButton3");
        clickOn("#exitButton");

        clickOn("#Correct1");
        verifyThat("#Question2", NodeMatchers.isNotNull());
        clickOn("#Correct2");
        verifyThat("#Question3", NodeMatchers.isNotNull());
        clickOn("Yes!!");
        verifyThat("#dungeonExit", NodeMatchers.isEnabled());
        clickOn("#dungeonExit");
        verifyThat("YOU WIN!!!", NodeMatchers.isVisible());
    }

    @Test
    public void testDogeRoom() {
        traversal();

        // Got to doge room

        verifyThat("#dogeButton", NodeMatchers.isEnabled());
        verifyThat("#exitButton", NodeMatchers.isDisabled());
    }

    @Test
    public void testDogeRoomClick() {
        traversal();

        // Got to doge room

        verifyThat("#dogeButton", NodeMatchers.isEnabled());
        verifyThat("#exitButton", NodeMatchers.isDisabled());

        for (int i = 0; i < 4; i++) {
            clickOn("#dogeButton");
            verifyThat("#exitButton", NodeMatchers.isDisabled());
        }
        clickOn("#dogeButton");
        verifyThat("#exitButton", NodeMatchers.isEnabled());
    }

    @Test
    public void testStartingLeftRoom() {
        clickOn("Start");
        clickOn("#nameField").write("Tristan");
        clickOn("#easyRB");
        clickOn("#weaponDropdown");
        clickOn("Shortsword");
        clickOn("PROCEED");
        clickOn("left");
        Text roomID = lookup("#id").queryText();
        assertEquals(roomID.getText(), "left");
    }

    @Test
    public void testStartingRightRoom() {
        clickOn("Start");
        clickOn("#nameField").write("Tristan");
        clickOn("#easyRB");
        clickOn("#weaponDropdown");
        clickOn("Shortsword");
        clickOn("PROCEED");
        clickOn("right");
        Text roomID = lookup("#id").queryText();
        assertEquals(roomID.getText(), "right");
    }

    @Test
    public void testStartingUpRoom() {
        clickOn("Start");
        clickOn("#nameField").write("Tristan");
        clickOn("#easyRB");
        clickOn("#weaponDropdown");
        clickOn("Shortsword");
        clickOn("PROCEED");
        clickOn("up");
        Text roomID = lookup("#id").queryText();
        assertEquals(roomID.getText(), "up");
    }

    @Test
    public void testStartingDownRoom() {
        clickOn("Start");
        clickOn("#nameField").write("Tristan");
        clickOn("#easyRB");
        clickOn("#weaponDropdown");
        clickOn("Shortsword");
        clickOn("PROCEED");
        clickOn("down");
        Text roomID = lookup("#id").queryText();
        assertEquals(roomID.getText(), "down");
    }

    @Test
    public void testGoldRoom() {
        traversal();
        // Got to doge room

        for (int i = 0; i < 5; i++) {
            clickOn("#dogeButton");
        }

        clickOn("#exitButton");
        //Now at gold room

        verifyThat("#goldButton", NodeMatchers.isEnabled());
        verifyThat("#goldButton2", NodeMatchers.isEnabled());
        verifyThat("#goldButton3", NodeMatchers.isEnabled());
        verifyThat("#exitButton", NodeMatchers.isDisabled());
    }

    @Test
    public void testGoldRoomClick() {
        traversal();
        // Got to doge room

        for (int i = 0; i < 5; i++) {
            clickOn("#dogeButton");
        }

        clickOn("#exitButton");
        //Now at gold room

        verifyThat("#exitButton", NodeMatchers.isDisabled());

        clickOn("#goldButton");
        verifyThat("#exitButton", NodeMatchers.isDisabled());

        clickOn("#goldButton2");
        verifyThat("#exitButton", NodeMatchers.isDisabled());

        clickOn("#goldButton3");
        verifyThat("#exitButton", NodeMatchers.isEnabled());
    }

    @Test
    public void testPlayerHP() {
        ImagePattern img;
        try {
            img = new ImagePattern(new Image(new FileInputStream(
                System.getProperty("user.dir") + "\\res\\greenMonster.png")));
            Monster instanceMonster = new GreenMonster(30, 30, 5, img);
            assertEquals(20, Player.getHealth());
            player.takeDamage(instanceMonster.getDamage());
            assertEquals(19, Player.getHealth());
        } catch (FileNotFoundException exception) {
            System.out.println("The monster image file wasn't found" + exception);
        }
    }

    @Test
    public void testRespawn() {
        getToStartRoom();
        player.takeDamage(20);
        moveBy(1, 1);
        clickOn("Play again");
        assertTrue(Player.isAlive());
        assertEquals(20, Player.getHealth());
    }

    @Test
    public void testDeath() {
        getToStartRoom();
        Label correctExitLabel = (Label) lookup("#correctExit").queryLabeled();
        String correctPath = correctExitLabel.getText().substring(8);
        clickOn(correctPath);
        player.takeDamage(20);
        assertFalse(Player.isAlive());
    }

    @Test
    public void testDamageMonster() {
        getToStartRoom();
        Label correctExitLabel = (Label) lookup("#correctExit").queryLabeled();
        String correctPath = correctExitLabel.getText().substring(8);
        clickOn(correctPath);

        int initHealth = monster.getHealth();
        monster.takeDamage(10);
        assertTrue(monster.getHealth() == (initHealth - 10));
    }

    @Test
    public void testMonsterImage() {
        getToStartRoom();
        Label correctExitLabel = (Label) lookup("#correctExit").queryLabeled();
        String correctPath = correctExitLabel.getText().substring(8);
        clickOn(correctPath);

        try {
            if (monster instanceof GreenMonster) {
                assertEquals(monster.getFill(), new ImagePattern(new Image((new FileInputStream(
                        System.getProperty("user.dir") + "\\res\\greenMonster.png")))));
            } else if (monster instanceof PinkMonster) {
                assertEquals(monster.getFill(), new ImagePattern(new Image((new FileInputStream(
                        System.getProperty("user.dir") + "\\res\\yellowMonster.png")))));
            } else if (monster instanceof YellowMonster) {
                assertEquals(monster.getFill(), new ImagePattern(new Image((new FileInputStream(
                        System.getProperty("user.dir") + "\\res\\yellowMonster.png")))));
            }
        } catch (FileNotFoundException exception) {
            System.out.println("The monster image file wasn't found" + exception);
        }
    }

    @Test
    public void testMonsterDamageImage() {
        getToStartRoom();
        Label correctExitLabel = (Label) lookup("#correctExit").queryLabeled();
        String correctPath = correctExitLabel.getText().substring(8);
        clickOn(correctPath);

        monster.takeDamage(1);
        try {
            if (monster instanceof GreenMonster) {
                assertEquals(monster.getFill(), new ImagePattern(new Image((new FileInputStream(
                        System.getProperty("user.dir") + "\\res\\greenMonster2.png")))));
                sleep(50);
                assertEquals(monster.getFill(), new ImagePattern(new Image((new FileInputStream(
                        System.getProperty("user.dir") + "\\res\\greenMonster.png")))));
            } else if (monster instanceof PinkMonster) {
                assertEquals(monster.getFill(), new ImagePattern(new Image((new FileInputStream(
                        System.getProperty("user.dir") + "\\res\\pinkMonster2.png")))));
                sleep(50);
                assertEquals(monster.getFill(), new ImagePattern(new Image((new FileInputStream(
                        System.getProperty("user.dir") + "\\res\\pinkMonster.png")))));
            } else if (monster instanceof YellowMonster) {
                assertEquals(monster.getFill(), new ImagePattern(new Image((new FileInputStream(
                        System.getProperty("user.dir") + "\\res\\yellowMonster2.png")))));
                sleep(50);
                assertEquals(monster.getFill(), new ImagePattern(new Image((new FileInputStream(
                        System.getProperty("user.dir") + "\\*\\res\\yellowMonster.png")))));
            }
        } catch (FileNotFoundException exception) {
            System.out.println("The monster image file wasn't found" + exception);
        }
    }

    @Test
    public void testBasicMovement() {
        getToStartRoom();
        Player myPlayer = lookup("#player").queryAs(Player.class);
        assertEquals(100.0, myPlayer.getX(), 0.1);
        assertEquals(100.0, myPlayer.getY(), 0.1);
        type(KeyCode.S, 20);
        type(KeyCode.D, 20);
        assertEquals(240.0, myPlayer.getX(), 0.1);
        assertEquals(240.0, myPlayer.getY(), 0.1);
        type(KeyCode.W, 10);
        assertEquals(240.0, myPlayer.getX(), 0.1);
        assertEquals(170.0, myPlayer.getY(), 0.1);
        type(KeyCode.A, 10);
        assertEquals(170.0, myPlayer.getX(), 0.1);
        assertEquals(170.0, myPlayer.getY(), 0.1);
    }

    @Test
    public void testSwitchColor() {
        getToStartRoom();
        Player myPlayer = lookup("#player").queryAs(Player.class);

        assertEquals(Color.BLUE, myPlayer.getFill());
        sleep(500);
        assertEquals(Color.BLUE, myPlayer.getFill());
        sleep(1500);
        assertEquals(Color.RED, myPlayer.getFill());
        sleep(500);
        assertEquals(Color.RED, myPlayer.getFill());
        sleep(1500);
        assertEquals(Color.BLUE, myPlayer.getFill());
    }

    @Test
    public void testClosedExits() {
        getToStartRoom();
        clickOn("Correct Door");
        assertFalse(controller.getHelpButton().isVisible());
        if (!controller.getHelpButton().isVisible()) {
            String correctPathParse = controller.getCorrectRoomText();
            clickOn(correctPathParse);
        }
        clickOn("Correct Door");
        assertFalse(controller.getHelpButton().isVisible());
        if (!controller.getHelpButton().isVisible()) {
            String correctPathParse = controller.getCorrectRoomText();
            clickOn(correctPathParse);
            verifyThat("#" + correctPathParse, NodeMatchers.isDisabled());
            sleep(3000);
        }
    }

    @Test
    public void testExitOpenOnMonsterKill() {
        getToStartRoom();
        if (controller.getRoomMonster() == null) {
            clickOn("Correct Door");
        }
        assertFalse(controller.getHelpButton().isVisible());
        if (!controller.getHelpButton().isVisible()) {
            String correctPathParse = controller.getCorrectRoomText();
            clickOn(correctPathParse);
        }
        for (int i = 0; i < 7; i++) {
            clickOn("Correct Door");
            assertFalse(controller.getHelpButton().isVisible());
            if (!controller.getHelpButton().isVisible()) {
                String correctPathParse = controller.getCorrectRoomText();
                verifyThat("#" + correctPathParse, NodeMatchers.isDisabled());
            }
            controller.getRoomMonster().takeDamage(20);
            type(KeyCode.S, 1);
            if (!controller.getHelpButton().isVisible()) {
                String correctPathParse = controller.getCorrectRoomText();
                verifyThat("#" + correctPathParse, NodeMatchers.isEnabled());
                clickOn(correctPathParse);
            }
        }
    }

    @Test
    public void testZoomPotion() {
        getToStartRoom();
        assertEquals(Player.getSpeed(), Player.ORIGINAL_SPEED);
        Potion potion = new ZoomPotion();
        potion.applyEffect();
        assertEquals(Player.getSpeed(), Player.ORIGINAL_SPEED + 3);
        potion.applyEffect();
        assertEquals(Player.getSpeed(), Player.ORIGINAL_SPEED + 6);
        player.takeDamage(999); // kill player
        moveBy(1, 1);
        clickOn("Play again");
        assertEquals(Player.getSpeed(), Player.ORIGINAL_SPEED);
    }

    @Test
    public void testEnteringInventoryScreen() {
        getToStartRoom();
        type(KeyCode.B, 1);
        clickOn("Attack Potion");
        clickOn("Back");

        Label correctExitLabel = (Label) lookup("#correctExit").queryLabeled();
        assertNotNull(correctExitLabel);
        String correctPath = correctExitLabel.getText().substring(8);
        assertNotNull(correctPath);
    }

    @Test
    public void testInventorySizeDecr() {
        int initialInv = 0;
        int decrInv = 0;

        getToStartRoom();

        for (int i = 0; i < player.getInventoryQuantity().length; i++) {
            if (player.getInventoryQuantity()[i] != 0) {
                initialInv += player.getInventoryQuantity()[i];
            }
        }

        type(KeyCode.B, 1);
        clickOn("Attack Potion");
        clickOn("Zoom Potion");
        clickOn("Health Potion");

        for (int i = 0; i < player.getInventoryQuantity().length; i++) {
            if (player.getInventoryQuantity()[i] != 0) {
                decrInv += player.getInventoryQuantity()[i];
            }
        }

        assertEquals(decrInv, initialInv - 3);
    }

    @Test
    public void testStartWeaponInvBludgeon() {
        //default selects bludgeon
        getToStartRoom();
        int[] startingInv = player.getInventoryQuantity();
        assertEquals(startingInv[1], 1);

    }

    @Test
    public void testStartWeaponInvShortsword() {
        int[] startingInv = player.getInventoryQuantity();
        clickOn("Start");
        clickOn("#nameField").write("Test");
        clickOn("#hardRB");
        clickOn("#weaponDropdown");
        clickOn("Shortsword");
        clickOn("PROCEED");

        assertEquals(startingInv[0], 1);
    }

    @Test
    public void testStartWeaponInvGreatsword() {
        int[] startingInv = player.getInventoryQuantity();
        clickOn("Start");
        clickOn("#nameField").write("Test");
        clickOn("#hardRB");
        clickOn("#weaponDropdown");
        clickOn("Greatsword");
        clickOn("PROCEED");

        assertEquals(startingInv[2], 1);
    }

    @Test
    public void testShopPurchase() {
        clickOn("Start");
        clickOn("#nameField").write("Tristan");
        clickOn("#hardRB");
        clickOn("#weaponDropdown");
        clickOn("Greatsword");
        clickOn("PROCEED");
        for (int i = 0; i < 6; i++) {
            player.getInventoryQuantity()[i] = 0;
        }
        type(KeyCode.B, 1);
        clickOn("Purchase");
        assertTrue(Controller.getGold() != 50);
    }

    @Test
    public void testInvalidPurchase() {
        clickOn("Start");
        clickOn("#nameField").write("Tristan");
        clickOn("#hardRB");
        clickOn("#weaponDropdown");
        clickOn("Greatsword");
        clickOn("PROCEED");
        Controller.setGold(10);
        type(KeyCode.B, 1);
        clickOn("Purchase");
        assertEquals(Controller.getGold(), 10);
    }

    @Test
    public void testBludgeonDamage() {
        clickOn("Start");
        clickOn("#nameField").write("Nishant");
        clickOn("#hardRB");
        clickOn("#weaponDropdown");
        clickOn("Bludgeon");
        clickOn("PROCEED");

        assertEquals(controller.getPlayer().getCurrentWeapon().getName(), "Bludgeon");
        clickOn("up");
        clickOn(controller.getRoomMonster());
        if (controller.getRoomMonster() instanceof GreenMonster) {
            assertEquals(1, controller.getRoomMonster().getHealth());
        } else if (controller.getRoomMonster() instanceof PinkMonster) {
            assertEquals(2, controller.getRoomMonster().getHealth());
        } else {
            assertEquals(3, controller.getRoomMonster().getHealth());
        }
    }

    @Test
    public void testGreatswordDamage() {
        clickOn("Start");
        clickOn("#nameField").write("Nishant");
        clickOn("#hardRB");
        clickOn("#weaponDropdown");
        clickOn("Greatsword");
        clickOn("PROCEED");

        assertEquals("Greatsword", controller.getPlayer().getCurrentWeapon().getName());
        clickOn("up");
        clickOn(controller.getRoomMonster());
        if (controller.getRoomMonster() instanceof GreenMonster) {
            assertEquals(0, controller.getRoomMonster().getHealth());
        } else if (controller.getRoomMonster() instanceof PinkMonster) {
            assertEquals(1, controller.getRoomMonster().getHealth());
        } else {
            assertEquals(2, controller.getRoomMonster().getHealth());
        }
    }

    @Test
    public void testShortswordDamage() {
        clickOn("Start");
        clickOn("#nameField").write("Nishant");
        clickOn("#hardRB");
        clickOn("#weaponDropdown");
        clickOn("Shortsword");
        clickOn("PROCEED");

        assertEquals(controller.getPlayer().getCurrentWeapon().getName(), "Shortsword");
        clickOn("up");
        clickOn(controller.getRoomMonster());
        if (controller.getRoomMonster() instanceof GreenMonster) {
            assertEquals(2, controller.getRoomMonster().getHealth());
        } else if (controller.getRoomMonster() instanceof PinkMonster) {
            assertEquals(3, controller.getRoomMonster().getHealth());
        } else {
            assertEquals(4, controller.getRoomMonster().getHealth());
        }
    }

    @Test
    public void testBludgeonTimer() {
        clickOn("Start");
        clickOn("#nameField").write("Nishant");
        clickOn("#hardRB");
        clickOn("#weaponDropdown");
        clickOn("Bludgeon");
        clickOn("PROCEED");

        assertEquals(controller.getPlayer().getCurrentWeapon().getName(), "Bludgeon");
        assertEquals(controller.getPlayer().getIsAggressive(), false);
        sleep(2000);
        assertEquals(controller.getPlayer().getIsAggressive(), true);
        sleep(2000);
        assertEquals(controller.getPlayer().getIsAggressive(), false);
    }

    @Test
    public void testGreatswordTimer() {
        clickOn("Start");
        clickOn("#nameField").write("Nishant");
        clickOn("#hardRB");
        clickOn("#weaponDropdown");
        clickOn("Greatsword");
        clickOn("PROCEED");

        assertEquals(controller.getPlayer().getCurrentWeapon().getName(), "Greatsword");
        assertEquals(controller.getPlayer().getIsAggressive(), false);
        sleep(2500);
        assertEquals(controller.getPlayer().getIsAggressive(), true);
        sleep(2500);
        assertEquals(controller.getPlayer().getIsAggressive(), false);
    }

    @Test
    public void testShortswordTimer() {
        clickOn("Start");
        clickOn("#nameField").write("Nishant");
        clickOn("#hardRB");
        clickOn("#weaponDropdown");
        clickOn("Shortsword");
        clickOn("PROCEED");

        assertEquals(controller.getPlayer().getCurrentWeapon().getName(), "Shortsword");
        assertEquals(controller.getPlayer().getIsAggressive(), false);
        sleep(1500);
        assertEquals(controller.getPlayer().getIsAggressive(), true);
        sleep(1500);
        assertEquals(controller.getPlayer().getIsAggressive(), false);
    }

    @Test
    public void testHealthPotion() {
        getToStartRoom();
        Player.setHealth(1); //Sets player health to 1
        int healthbefore = Player.getHealth();
        assertEquals(healthbefore, 1);
        type(KeyCode.B);
        clickOn("Health Potion");
        assertEquals(healthbefore + 10, Player.getHealth());
        clickOn("Back");
        assertEquals(healthbefore + 10, Player.getHealth());
        sleep(1000); //Used to show that the health bar reflects proper health
    }

    @Test
    public void testAttackPotion() {
        getToStartRoom();
        assertEquals(2, Player.getDamage());
        type(KeyCode.B);
        clickOn("Attack Potion");
        clickOn("Back");
        assertEquals(3, Player.getDamage());
    }

    @Test
    public void testMonstersKilled() {
        for (int i = 0; i < 30; i++) {
            Player.killMonster();
        }
        assertTrue(Player.getMonstersKilled() == 30);
    }

    @Test
    public void testPotionsDrank() {
        for (int i = 0; i < 50; i++) {
            Player.drinkPotion();
        }
        assertTrue(Player.getPotionsDrank() == 50);
    }

    @Test
    public void testItemsPurchased() {
        for (int i = 0; i < 40; i++) {
            Player.purchaseItem();
        }
        assertTrue(Player.getItemsPurchased() == 40);
    }

    @Test
    public void testSingleItemPurchase() {
        clickOn("Start");
        clickOn("#nameField").write("Tristan");
        clickOn("#hardRB");
        clickOn("#weaponDropdown");
        clickOn("Greatsword");
        clickOn("PROCEED");
        press(KeyCode.B);
        clickOn("Purchase");
        assertTrue(Player.getItemsPurchased() == 1);
    }

    @Test
    public void testChallengeRoomNotificationOk() {
        getToStartRoom();
        // to skip to challenge room 1, press "p"
        type(KeyCode.P, 1);
        verifyThat("OK", NodeMatchers.isVisible());
        verifyThat("Nope", NodeMatchers.isVisible());
        clickOn("OK");
        assertTrue(ChallengeRoom1.getMonsterArrayList().size() >= 2);
        assertTrue(!ChallengeRoom1.isChallengeCompleted());
        assertTrue(!ChallengeRoom1.allMonstersAreDead());
        assertTrue(ChallengeRoom1.getChallengeExitButton().isDisabled());
    }

    @Test
    public void testChallengeRoomNotificationNope() {
        getToStartRoom();
        // to skip to challenge room 1, press "p"
        type(KeyCode.P, 1);
        verifyThat("OK", NodeMatchers.isVisible());
        verifyThat("Nope", NodeMatchers.isVisible());
        clickOn("Nope");
        assertTrue(ChallengeRoom1.getMonsterArrayList().size() == 0);
        assertTrue(ChallengeRoom1.isChallengeCompleted());
        assertTrue(ChallengeRoom1.allMonstersAreDead());
        assertTrue(!ChallengeRoom1.getChallengeExitButton().isDisabled());
    }

    @Test
    public void testChallengeRoomFinished() {
        int potion1Original = player.getInventoryQuantity()[3];
        int potion2Original = player.getInventoryQuantity()[4];
        int potion3Original = player.getInventoryQuantity()[5];
        getToStartRoom();
        // to skip to challenge room 1, press "p"
        type(KeyCode.P, 1);
        clickOn("OK");

        assertTrue(ChallengeRoom1.isItemDropsAvailable());
        for (Monster monster : ChallengeRoom1.getMonsterArrayList()) {
            if (monster.isAlive()) {
                monster.setAlive(false);
            }
        }

        type(KeyCode.S, 2); // trigger notification to receive potions!

        assertTrue(ChallengeRoom1.isChallengeCompleted());
        
        clickOn("OK");
        assertEquals(potion1Original + 2, player.getInventoryQuantity()[3]);
        assertEquals(potion2Original + 2, player.getInventoryQuantity()[4]);
        assertEquals(potion3Original + 2, player.getInventoryQuantity()[5]);
    }

    @Test
    public void testPlayAgainWin() {

        getToStartRoom();
        // to skip to challenge room 2, press ";"
        type(KeyCode.SEMICOLON, 1);
        clickOn("OK");

        for (Monster monster : ChallengeRoom2.getMonsterArrayList()) {
            if (monster.isAlive()) {
                monster.setAlive(false);
            }
        }

        type(KeyCode.S, 2); // trigger notification to receive potions!
        clickOn("OK");


        ChallengeRoom2.setChallengeCompleted(true);
        clickOn("Exit");
        Player.setDamage(9999);
        sleep(1000);
        clickOn("#Doge");

        clickOn("Exit");


        clickOn("#goldButton");
        clickOn("#goldButton2");
        clickOn("#goldButton3");
        clickOn("#exitButton");

        clickOn("#Correct1");

        clickOn("#Correct2");

        clickOn("Yes!!");

        clickOn("#dungeonExit");

        clickOn("Play again");
        assertEquals(Player.getHealth(), 20);
        assertEquals(Player.getDamageModifier(), 0);
        assertEquals(Player.getSpeed(), 7);

    }

    @Test
    public void testDogeKill() {
        getToStartRoom();
        Label correctExitLabel = (Label) lookup("#correctExit").queryLabeled();
        String correctPath = correctExitLabel.getText().substring(8);
        clickOn(correctPath);
        boolean condition = true;

        while (condition) {
            Player.setHealth(20);
            Player.setDamage(20);
            while (controller.getRoomMonster() != null && controller.getRoomMonster().isAlive()) {
                clickOn(controller.getRoomMonster());
            }
            correctExitLabel = (Label) lookup("#correctExit").queryLabeled();
            correctPath = correctExitLabel.getText().substring(8);
            clickOn(correctPath);

            if (!lookup("OK").tryQuery().isEmpty()) {
                condition = false;
            }
        }
        clickOn("OK");

        for (Monster monster : ChallengeRoom1.getMonsterArrayList()) {
            Player.setHealth(20);
            Player.setDamage(20);
            while (monster.isAlive()) {
                clickOn(monster);
            }
        }
        sleep (2000);
        type(KeyCode.S, 2); // trigger notification to receive potions!
        if (!lookup("OK").tryQuery().isEmpty()) {
            clickOn("OK");
        }
        clickOn("Exit");
        clickOn("OK");

        for (Monster monster : ChallengeRoom2.getMonsterArrayList()) {
            Player.setHealth(20);
            Player.setDamage(20);
            while (monster.isAlive()) {
                clickOn(monster);
            }
        }
        sleep (2000);
        type(KeyCode.S, 2); // trigger notification to receive potions!
        if (!lookup("OK").tryQuery().isEmpty()) {
            clickOn("OK");
        }
        clickOn("Exit");
        clickOn("Exit");

        assertEquals(true, DogeRoom.getDogeMonster().isAlive());
        while (DogeRoom.getDogeMonster().getHealth() > 0) {
            Player.setHealth(20);
            Player.setDamage(20);
            clickOn("#Doge");
        }
        assertEquals(false, DogeRoom.getDogeMonster().isAlive());
        assertTrue(lookup("Exit").tryQuery().get().isVisible());
    }

    @Test
    public void testDogeHealth() {
        getToStartRoom();
        type(KeyCode.SEMICOLON, 1);
        clickOn("OK");

        for (Monster monster : ChallengeRoom2.getMonsterArrayList()) {
            if (monster.isAlive()) {
                monster.takeDamage(5);
            }
        }
        type(KeyCode.S, 2); // trigger notification to receive potions!
        clickOn("OK");

        ChallengeRoom2.setChallengeCompleted(true);
        clickOn("Exit");
        if (player.getIsAggressive()) {
            sleep(2000);
        }
        assertEquals(98, DogeRoom.getDogeMonster().getHealth());
        // the initial health is 100, but since the player and the monster spawn on top of each other,
        // the monster takes damage immediately
        clickOn(DogeRoom.getDogeMonster());
        assertEquals(96, DogeRoom.getDogeMonster().getHealth());
    }

    @Test
    public void testDogeDamageAnimation() {
        DogeMonster dogeMonster;
        try {
            dogeMonster = new DogeMonster(150, 150, 100,
                    new ImagePattern(new Image(new FileInputStream(System.getProperty("user.dir")
                            + "\\res\\doge.png"))));
            int x = (int) (Math.random() * 512);
            int y = (int) (Math.random() * 512);
            // x and y are random pixels chosed from the image
            ImagePattern img = (ImagePattern) dogeMonster.getFill();
            assertEquals(img.getImage().getPixelReader().getColor(x, y), new Image
                    (new FileInputStream(System.getProperty("user.dir")
                    + "\\res\\doge.png")).getPixelReader().getColor(x, y));
            dogeMonster.takeDamage(1);
            img = (ImagePattern) dogeMonster.getFill();
            x = (int) (Math.random() * 512);
            y = (int) (Math.random() * 512);
            assertEquals(img.getImage().getPixelReader().getColor(x, y), new Image
                    (new FileInputStream(System.getProperty("user.dir")
                            + "\\res\\doge2.png")).getPixelReader().getColor(x, y));
            sleep(50);
            img = (ImagePattern) dogeMonster.getFill();
            x = (int) (Math.random() * 512);
            y = (int) (Math.random() * 512);
            assertEquals(img.getImage().getPixelReader().getColor(x, y), new Image
                    (new FileInputStream(System.getProperty("user.dir")
                            + "\\res\\doge.png")).getPixelReader().getColor(x, y));
        } catch (FileNotFoundException e) {
            System.out.println("The Doge monster image file wasn't found" + e);
        }
    }
}