import dungeoncrawler.*;

import javafx.stage.Stage;
import org.junit.Test;

import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;
import javafx.scene.control.Label;

import javafx.scene.text.Text;
import org.testfx.matcher.control.TextMatchers;

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;

public class DungeonCrawlerTest extends ApplicationTest {
    private Controller controller = new Controller();
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
            Label correctExitLabel = (Label) lookup("#correctExit").queryLabeled();
            String correctPath = correctExitLabel.getText().substring(8);
            if (roomID.getText().equals("new5")) {
                clickOn(correctPath);
                break;
            }
            clickOn(correctPath);
        }
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
        clickOn("#Correct1");
        clickOn("#Correct2");
        verifyThat("#Question3",NodeMatchers.isNotNull());
        clickOn("#WrongAnswer");
        verifyThat("#Question2", NodeMatchers.isNotNull());
        clickOn("69");
        verifyThat("#Question1", NodeMatchers.isNotNull());


    }

    @Test
    public void testPuzzleRoom() {
        traversal();

        for (int i = 0; i < 5; i++) {
            clickOn("#dogeButton");
        }

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
        clickOn("Start");
        clickOn("#nameField").write("Chuong");
        clickOn("#easyRB");
        clickOn("#weaponDropdown");
        clickOn("Bludgeon");
        clickOn("PROCEED");

        while (true) {
            Text roomID = lookup("#id").queryText();
            Label correctExitLabel = (Label) lookup("#correctExit").queryLabeled();
            String correctPath = correctExitLabel.getText().substring(8);
            if (roomID.getText().equals("new5")) {
                clickOn(correctPath);
                break;
            }
            clickOn(correctPath);
        }

        // Got to doge room

        verifyThat("#dogeButton", NodeMatchers.isEnabled());
        verifyThat("#exitButton", NodeMatchers.isDisabled());
    }

    @Test
    public void testDogeRoomClick() {
        clickOn("Start");
        clickOn("#nameField").write("Chuong");
        clickOn("#easyRB");
        clickOn("#weaponDropdown");
        clickOn("Bludgeon");
        clickOn("PROCEED");

        while (true) {
            Text roomID = lookup("#id").queryText();
            Label correctExitLabel = (Label) lookup("#correctExit").queryLabeled();
            String correctPath = correctExitLabel.getText().substring(8);
            if (roomID.getText().equals("new5")) {
                clickOn(correctPath);
                break;
            }
            clickOn(correctPath);
        }

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

}

