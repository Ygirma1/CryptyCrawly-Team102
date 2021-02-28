import dungeoncrawler.ConfigScreen;
import dungeoncrawler.Controller;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;

public class DungeonCrawlerTest extends ApplicationTest {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        controller.start(primaryStage);
    }

    @Test
    public void testStart() {
        clickOn("Start");
        verifyThat("ENTER YOUR NAME:", NodeMatchers.isNotNull());
        System.out.println("start works");
    }

    @Test
    public void testEmptyName() {
        ConfigScreen config = new ConfigScreen();
        clickOn("PROCEED");
        if (config.getNameField().getText().isEmpty()) {
            assertEquals("Please enter a character name.", config.getNameField().getText());
        } else {
            verifyThat("Starting Room", NodeMatchers.isNotNull());
        }

    }
}

