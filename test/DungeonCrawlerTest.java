import dungeoncrawler.Controller;
import dungeoncrawler.Difficulty;
<<<<<<< HEAD
import dungeoncrawler.InitialGameScreen;
=======
>>>>>>> master
import javafx.stage.Stage;
import org.junit.Test;
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
        //WelcomeScreen ws = new WelcomeScreen(500,500);
        clickOn("Start");
        verifyThat("ENTER YOUR NAME:", NodeMatchers.isNotNull());
        System.out.println("start works");
    }

    @Test
    public void testEmptyName() {
        clickOn("Start");
        clickOn("PROCEED");
        verifyThat("#nameField", TextInputControlMatchers.hasText("Please enter a character name."));
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
        clickOn("Shortsword");
        clickOn("PROCEED");
        assertEquals("Peter", controller.getCharacterName());
        assertEquals("Shortsword", controller.getWeapon());
        assertEquals(Difficulty.HARD, controller.getDifficulty());
    }

    @Test
    public void testInitGameScreen() {
        clickOn("Start");
        clickOn("#nameField").write("GAMESCREEN");
        clickOn("#easyRB");
        clickOn("#weaponDropdown");
        clickOn("Axe");
        clickOn("PROCEED");
        verifyThat("Starting Room", NodeMatchers.isNotNull());
    }

    @Test
    public void testGoldCount() {
        InitialGameScreen goldVerification = new InitialGameScreen();
        clickOn("Start");
        clickOn("#nameField").write("MONEYUP");
        clickOn("#easyRB");
        clickOn("#weaponDropdown");
        clickOn("Axe");
        clickOn("PROCEED");
        assertEquals(100, goldVerification.getGold());
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
}

