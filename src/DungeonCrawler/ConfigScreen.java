package DungeonCrawler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;


public class ConfigScreen {
    private int width;
    private int height;
    private final TextField nameField;
    private final ComboBox<String> difficultyDropdown;
    private final ComboBox<String> weaponDropdown;
    private final Button proceedButton;
    // NOTE: A button to proceed to initial game screen?

    public ConfigScreen() {
        this(500, 500);
    }

    /**
     * ConfigScreen constructor
     * @param width The width of the scene
     * @param height The height of the scene
     */
    public ConfigScreen(int width, int height) {
        this.width = width;
        this.height = height;
        this.nameField = new TextField(); // hb.getChildren().addAll(label1, textField); make sure to add label
        this.nameField.setPromptText("Enter your name");
        this.nameField.setPrefWidth(110);

        // Difficulty dropdown init
        // To read the selected value, do this.difficultyDropdown.getValue()
        this.difficultyDropdown = new ComboBox<>();
        this.difficultyDropdown.getItems().add("Easy");
        this.difficultyDropdown.getItems().add("Medium");
        this.difficultyDropdown.getItems().add("Hard");

        // Weapon dropdown init
        this.weaponDropdown = new ComboBox<>();
        this.weaponDropdown.getItems().add("Shortsword"); // TODO: Change this
        this.weaponDropdown.getItems().add("Bludgeon");
        this.weaponDropdown.getItems().add("Greatsword");

        // Proceed button init
        this.proceedButton = new Button("Proceed to next screen"); // TODO: add lambda to proceed here
    }


    /**
     * Set up the scene for the config screen
     * @return the Scene object for the config screen
     */
    public Scene getScene() {
        // Name Hbox
        HBox nameHBox = new HBox(new Label("Enter Name: "), this.nameField);
        nameHBox.setAlignment(Pos.CENTER);
        nameHBox.setSpacing(10);

        // Dropdown HBox
        HBox diffDropdownHBox = new HBox(new Label("Select difficulty:"), this.difficultyDropdown);
        diffDropdownHBox.setSpacing(10);
        diffDropdownHBox.setAlignment(Pos.CENTER);
        HBox weaponDropdownHBox = new HBox(new Label("Select weapon:"), this.weaponDropdown);
        weaponDropdownHBox.setSpacing(10);
        weaponDropdownHBox.setAlignment(Pos.CENTER);
        HBox dropdowns = new HBox(diffDropdownHBox, weaponDropdownHBox);
        dropdowns.setAlignment(Pos.CENTER);
        dropdowns.setSpacing(20);
        // proceedButton
        VBox wrapperVBox = new VBox(nameHBox, dropdowns, this.proceedButton);
        wrapperVBox.setSpacing(70);
        wrapperVBox.setAlignment(Pos.TOP_CENTER);


        BorderPane pane = new BorderPane();
        BorderPane.setAlignment(wrapperVBox, Pos.CENTER);
        BorderPane.setMargin(wrapperVBox, new Insets(100, 0, 0 ,0));

        pane.setCenter(wrapperVBox);
        return new Scene(pane, this.width, this.height);
    }

    /**
     * Width setter
     * @param width Width of the game window.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Height setter
     * @param height Height of the game window.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Width getter
     * @return The width of the game window.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Height getter
     * @return The height of the game window.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * nameField TextField object getter
     * @return Text Field for player name.
     */
    public TextField getNameField() {
        return this.nameField;
    }

    /**
     * difficultyDropdown TextField object getter
     * @return Difficulty dropdown list
     */
    public ComboBox<String> getDifficultyDropdown() {
        return this.difficultyDropdown;
    }

    /**
     * weaponDropdown ComboBox object getter
     * @return Weapon dropdown list
     */
    public ComboBox<String> getWeaponDropdown() {
        return this.weaponDropdown;
    }

    /**
     * proceedButton Button object getter
     * @return Proceed button
     */
    public Button getProceedButton() {
        return this.proceedButton;
    }
}
