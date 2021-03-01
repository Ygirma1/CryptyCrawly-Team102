package dungeoncrawler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;

public class ConfigScreen {
    private int width;
    private int height;

    private final TextField nameField;
    private final ComboBox<String> weaponDropdown;
    private final Button proceedButton;
    // NOTE: A button to proceed to initial game screen?
    private Difficulty difficulty;
    private final Text namePromptText;
    private final Text difficultyPromptText;
    private final Text weaponPromptText;
    private final Color backgroundColor = Color.rgb(120, 135, 135);
    private final RadioButton easyRB;
    private final RadioButton mediumRB;
    private final RadioButton hardRB;
    private final ToggleGroup difficultyRBGroup = new ToggleGroup();


    /**
     * No argument constructor that initializes this config screen with given width and height.
     */
    public ConfigScreen() {
        this(500, 500);
    }

    /**
     * ConfigScreen constructor.
     * @param width The width of the scene
     * @param height The height of the scene
     */
    public ConfigScreen(int width, int height) {
        this.width = width;
        this.height = height;

        Font textFont = new Font("High Tower Text", 20);
        Color textColor = Color.rgb(48, 54, 54);
        Font smallFont = new Font("High Tower Text", 14);
        Color nodeColor = Color.rgb(161, 171, 171);

        // Name field and corresponding text
        // Obtain player name with nameField.getText()
        this.namePromptText = new Text("ENTER YOUR NAME:");
        this.namePromptText.setFont(textFont);
        this.namePromptText.setFill(textColor);

        this.nameField = new TextField();
        this.nameField.setId("nameField");
        this.nameField.setFont(smallFont);
        this.nameField.setStyle("-fx-background-color: #a1abab;");
        this.nameField.setAlignment(Pos.CENTER_LEFT);
        this.nameField.setMaxWidth(160);

        // Difficulty RB group init
        // Obtain selected radio button with difficultyRBGroup.getSelectedToggle()
        this.difficultyPromptText = new Text("SELECT DIFFICULTY:");
        this.difficultyPromptText.setFont(textFont);
        this.difficultyPromptText.setFill(textColor);

        this.easyRB = new RadioButton("Easy ");
        this.easyRB.setId("easyRB");
        this.easyRB.setFont(smallFont);
        this.easyRB.setToggleGroup(difficultyRBGroup);
        this.easyRB.setOnAction(e -> this.difficulty = Difficulty.EASY);

        this.mediumRB = new RadioButton("Medium ");
        this.mediumRB.setId("mediumRB");
        this.mediumRB.setFont(smallFont);
        this.mediumRB.setToggleGroup(difficultyRBGroup);
        this.mediumRB.setOnAction(e -> this.difficulty = Difficulty.MEDIUM);

        this.hardRB = new RadioButton("Hard ");
        this.hardRB.setId("hardRB");
        this.hardRB.setFont(smallFont);
        this.hardRB.setToggleGroup(difficultyRBGroup);
        this.hardRB.setOnAction(e -> this.difficulty = Difficulty.HARD);

        // Weapon dropdown init
        this.weaponPromptText = new Text("SELECT YOUR WEAPON:");
        this.weaponPromptText.setFont(textFont);
        this.weaponPromptText.setFill(textColor);

        this.weaponDropdown = new ComboBox<>();
        this.weaponDropdown.setId("weaponDropdown");
        this.weaponDropdown.setBackground(new Background(
                new BackgroundFill(nodeColor, null, null)));
        this.weaponDropdown.setStyle("-fx-font: 14px \"High Tower Text\";");
        this.weaponDropdown.getItems().add("Shortsword");
        this.weaponDropdown.getItems().add("Axe");
        this.weaponDropdown.getItems().add("Hammer");

        // Proceed button init
        this.proceedButton = new Button("PROCEED");
        this.proceedButton.setFont(textFont);
        this.proceedButton.setStyle("-fx-background-color: #a1abab;");
    }


    /**
     * Set up the scene for the configuration screen.
     * @return the Scene object for the config screen
     */
    public Scene getScene() {
        // Name Input
        VBox nameVBox = new VBox();
        nameVBox.getChildren().addAll(namePromptText, nameField);
        nameVBox.setSpacing(10);
        nameVBox.setAlignment(Pos.CENTER);

        // Difficulty Selector
        VBox difficultyVBox = new VBox();
        HBox rbBox = new HBox(easyRB, mediumRB, hardRB);
        rbBox.setSpacing(10);
        rbBox.setAlignment(Pos.CENTER);
        difficultyVBox.getChildren().addAll(difficultyPromptText, rbBox);
        difficultyVBox.setSpacing(10);
        difficultyVBox.setAlignment(Pos.CENTER);

        // Weapon Selector
        VBox weaponVBox = new VBox(weaponPromptText, weaponDropdown);
        weaponVBox.setSpacing(10);
        weaponVBox.setAlignment(Pos.CENTER);

        // All vbox together
        VBox wrapperVBox = new VBox(nameVBox, difficultyVBox, weaponVBox);
        wrapperVBox.getChildren().add(this.proceedButton);
        wrapperVBox.setAlignment(Pos.TOP_CENTER);
        wrapperVBox.setSpacing(30);

        // Create panes for nodes
        BorderPane bPane = new BorderPane();
        bPane.setCenter(wrapperVBox);
        BorderPane.setMargin(wrapperVBox, new Insets(100, 0, 0, 0));
        Rectangle background = new Rectangle(this.width, this.height, this.backgroundColor);
        StackPane sPane = new StackPane(background, bPane);

        return new Scene(sPane, this.width, this.height);
    }

    /**
     * Mutator method for width.
     * @param width the width of the config screen
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Mutator method for height.
     * @param height the height of the config screen
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Accessor method for width.
     * @return width of config screen
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Accessor method for height.
     * @return height of config screen
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Accessor method for nameField object.
     * @return TextField object nameField
     */
    public TextField getNameField() {
        return this.nameField;
    }

    /**
     * Accessor method for weaponDropDown ComboBox.
     * @return the weaponDropDown ComboBox
     */
    public ComboBox<String> getWeaponDropdown() {
        return this.weaponDropdown;
    }

    /**
     * Accessor method for Button proceedButton.
     * @return the Button proceedButton
     */
    public Button getProceedButton() {
        return this.proceedButton;
    }

    /**
     * Accessor method for enum variable difficulty.
     * @return this instance's difficulty
     */
    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    /**
     * Accessor method for the difficulty radio button toggle group.
     * @return the difficultyRBGroup
     */
    public ToggleGroup getDifficultyRBGroup() {
        return this.difficultyRBGroup;
    }
}
