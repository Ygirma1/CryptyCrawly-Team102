import dungeoncrawler.*;

//import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import org.junit.Test;

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
//import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;
import javafx.scene.input.KeyCode;

public class DungeonCrawlerTest extends ApplicationTest {
    private Controller controller = new Controller();
    private Player player = new Player(250, 250, 100, 100);
    private Monster monster = new Monster(50, 50, 25, Color.RED);
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
            if (roomID.equals("start")) {
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
        assertEquals(100, controller.getGold());
    }

    @Test
    public void testMediumGoldAmount() {
        clickOn("Start");
        clickOn("#nameField").write("Nishant");
        clickOn("#mediumRB");
        clickOn("#weaponDropdown");
        clickOn("Greatsword");
        clickOn("PROCEED");
        assertEquals(75, controller.getGold());
    }

    @Test
    public void testHardGoldAmount() {
        clickOn("Start");
        clickOn("#nameField").write("Nishant");
        clickOn("#hardRB");
        clickOn("#weaponDropdown");
        clickOn("Greatsword");
        clickOn("PROCEED");
        assertEquals(50, controller.getGold());
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
        clickOn("Start");
        clickOn("#nameField").write("Yafet");
        clickOn("#easyRB");
        clickOn("#weaponDropdown");
        clickOn("Shortsword");
        clickOn("PROCEED");
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
        assertFalse(player.isAlive());
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
        Player myPlayer = (Player) lookup("#player").queryAs(Player.class);
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
        Player myPlayer = (Player) lookup("#player").queryAs(Player.class);

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
}

