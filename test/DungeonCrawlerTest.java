import dungeoncrawler.Controller;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

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
        verifyThat("ENTER YOUR NAME:", NodeMatchers.isFocused());
    }

    @Test
    public void testEmptyName() {
        clickOn("PROCEED");
        
    }
}

