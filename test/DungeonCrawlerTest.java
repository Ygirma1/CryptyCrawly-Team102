import dungeoncrawler.Controller;
import dungeoncrawler.Difficulty;
import dungeoncrawler.PuzzleRoom;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;

public class DungeonCrawlerTest extends ApplicationTest {
    private Controller controller = new Controller();
    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new Controller();
        controller.start(primaryStage);
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
    public void testPuzzleRoom() {
        PuzzleRoom testPuzzle = new PuzzleRoom(500, 500, 0);

        clickOn("#Correct1");
        verifyThat("#Question2", NodeMatchers.isNotNull());
        clickOn("#Correct2");
        verifyThat("#Question3", NodeMatchers.isNotNull());
        clickOn("Yes!");
        verifyThat("#exit", NodeMatchers.isEnabled());
    }
}

