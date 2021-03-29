package dungeoncrawler;

//import javafx.animation.KeyFrame;
//import javafx.animation.KeyValue;
//import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
//import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
//import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
//import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class Room {
    private ArrayList<Button> exits;
    private Room[] adjRooms = new Room[4]; // ordered right, left, up, down
    private Button helpButton;
    private Button bLeft;
    private Button bRight;
    private Button bUp;
    private Button bDown;
    private Text id; // just a label for a room, use for debugging
    private Label correctExit; //Displays proper exit in bottom right corner
    private String pathID; //Text displaying correct exit to choose
    private Text goldText;
    private Text healthText;
    private static Rectangle healthRect;
    private Room left;
    private Room right;
    private Room up;
    private Room down;
    private Difficulty diff;
    private Player player;
    private final Color floorColor = Color.rgb(129, 137, 147);
    private final Color goldColor = Color.rgb(255, 215, 0);
    private final Font smallFont = new Font("High Tower Text", 19);
    private int width;
    private int height;
    private Monster monster;

    /**
     * Default constructor for Room class.
     *
     * @param width width of scene
     * @param height height of scene
     */
    public Room(int width, int height) {
        this(width, height, "", Difficulty.EASY);
    }

    /**
     * Constructor for Room class.
     *
     * @param id String to identify a room
     * @param diff the difficulty of the rooms
     */
    public Room(String id, Difficulty diff) {
        this(500, 500, id, diff);
    }



    /**
     * The main constructor of the Room class.
     *
     * @param width width of the scene
     * @param height height of the scene
     * @param id String to identify room
     * @param diff the difficulty of the room
     */
    public Room(int width, int height, String id, Difficulty diff) {
        this.id = new Text(id);
        this.id.setId("id");
        this.exits = new ArrayList<>();
        this.helpButton = new Button("Correct Door");
        this.bLeft = new Button("left");
        this.exits.add(bLeft);
        this.bRight = new Button("right");
        this.exits.add(bRight);
        this.bUp = new Button("up");
        this.exits.add(bUp);
        this.bDown = new Button("down");
        this.exits.add(bDown);
        this.pathID = "";
        for (Button exit : this.exits) {
            exit.setPrefSize(80, 30);
            exit.setFont(smallFont);
            exit.setStyle("-fx-background-color: #62686F;");
        }
        this.width = width;
        this.height = height;
        this.diff = diff;
        this.goldText = new Text("Gold: " + Controller.getGold());
        this.goldText.setFont(smallFont);
        this.goldText.setFill(goldColor);
        this.goldText.setX(420);
        this.goldText.setY(20);
        this.healthText = new Text("HP");
        this.healthText.setFill(Color.RED);
        this.healthText.setFont(smallFont);
        healthRect = new Rectangle(100, 15, Color.RED);
    }

    /**
     * Recursive method to generate the maze of rooms.
     *
     * @param startingRoom The root of the maze, or starting room.
     */
    public void generateMap(Room startingRoom) {
        Room rRoom = new Room("right", this.diff);
        startingRoom.right = rRoom;
        rRoom.left = startingRoom;
        updateRoomArray(rRoom);
        Room lRoom = new Room("left", this.diff);
        startingRoom.left = lRoom;
        lRoom.right = startingRoom;
        updateRoomArray(lRoom);
        Room uRoom = new Room("up", this.diff);
        startingRoom.up = uRoom;
        uRoom.down = startingRoom;
        updateRoomArray(uRoom);
        Room dRoom = new Room("down", this.diff);
        startingRoom.down = dRoom;
        dRoom.up = startingRoom;
        updateRoomArray(dRoom);
        updateRoomArray(startingRoom);
        Random rand = new Random();
        //addMonster(startingRoom);
        for (Room room: startingRoom.adjRooms) {
            addMonster(room);
        }
        int randRoomIndex = rand.nextInt(4);
        Room next = startingRoom.adjRooms[randRoomIndex];
        rGenerateMap(next, 0, randRoomIndex);
        startingRoom.setPathID(pathReveal(randRoomIndex));
    }

    /**
     * Recursive helper method to generate a random sequence of rooms. Rooms lead to
     * boss room
     *
     * @param current      the current room having rooms added to it
     * @param roomDepth    the distance of the rooms from the start
     * @param newRoomIndex the index of the Room[] that will determine the direction
     *                     of the new room
     */
    private void rGenerateMap(Room current, int roomDepth, int newRoomIndex) {
        current.setPathID(pathReveal(newRoomIndex));
        if (roomDepth >= 6) {
            Room nextRoom = new DogeRoom(500, 500, "Boss", current.diff);
            current.adjRooms[newRoomIndex] = nextRoom;
            addMonster(current);
            nextRoom.down = null;
            nextRoom.up = null;
            nextRoom.left = null;
            nextRoom.right = null;
            updateAdjRooms(current);
            updateRoomArray(nextRoom);
        } else {
            Random rand = new Random();
            addMonster(current);
            Room nextRoom = new Room("new" + roomDepth, current.diff);
            current.adjRooms[newRoomIndex] = nextRoom;
            int nextRoomPrevIndex = newRoomIndex;
            if (newRoomIndex % 2 == 0) {
                nextRoomPrevIndex += 1;
            } else {
                nextRoomPrevIndex -= 1;
            }
            nextRoom.adjRooms[nextRoomPrevIndex] = current;

            updateAdjRooms(current);
            updateAdjRooms(nextRoom);
            int nextIndex = nextRoomPrevIndex;
            while (nextIndex == nextRoomPrevIndex) {
                nextIndex = rand.nextInt(4);
            }
            updateClosedExit(newRoomIndex, current);
            rGenerateMap(nextRoom, roomDepth + 1, nextIndex);
        }
    }

    /**
     * Helper method to update the current room's adjRooms[] array.
     *
     * @param current the room currently being updated
     */
    private void updateRoomArray(Room current) {
        current.adjRooms[0] = current.right;
        current.adjRooms[1] = current.left;
        current.adjRooms[2] = current.up;
        current.adjRooms[3] = current.down;
    }

    /**
     * Helper method to update adjacent rooms based on adjRooms[].
     *
     * @param current the current room being updated
     */
    private void updateAdjRooms(Room current) {
        current.right = current.adjRooms[0];
        current.left = current.adjRooms[1];
        current.up = current.adjRooms[2];
        current.down = current.adjRooms[3];
    }

    private void addMonster(Room current) {
        Random rand = new Random();
        int monsterSpawn = Math.abs(rand.nextInt() % 3);
        switch (monsterSpawn) {
            case 0:
                try {
                    current.monster = new GreenMonster(60,60, 3,
                            new ImagePattern(new Image(new FileInputStream(
                                    System.getProperty("user.dir") + "\\res\\greenMonster.png"))));
                } catch (FileNotFoundException exception) {
                    System.out.println("Green monster image not found " + exception);
                }
                break;
            case 1:
                try {
                    current.monster = new PinkMonster(70, 70, 4,
                            new ImagePattern(new Image(new FileInputStream(
                                    System.getProperty("user.dir") + "\\res\\pinkMonster.png"))));
                } catch (FileNotFoundException exception) {
                    System.out.println("Pink monster image not found " + exception);
                }
                break;
            case 2:
                try {
                    current.monster = new YellowMonster(80, 80, 5,
                            new ImagePattern(new Image(new FileInputStream(
                                    System.getProperty("user.dir") + "\\res\\yellowMonster.png"))));
                } catch (FileNotFoundException exception) {
                    System.out.println("Yellow monster image not found " + exception);
                }
                break;
            default:
                break;
        }

    }

    private String pathReveal(int nextIndex) {
        if (nextIndex == 0) {
            this.setPathID("Choose: right");
        } else if (nextIndex == 1) {
            this.setPathID("Choose: left");
        } else if (nextIndex == 2) {
            this.setPathID("Choose: up");
        } else if (nextIndex == 3) {
            this.setPathID("Choose: down");
        }
        return getPathID();
    }
    // When extends this class, override this to set your room's own scene

    /**
     * Returns a new scene based on the Room class.
     *
     * @return The newly created scene
     */
    public Scene getScene() {
        Pane pane = new Pane();
        String path = getPathID();
        this.correctExit = new Label(path);
        this.correctExit.setId("correctExit");
        if (this.right != null) {
            // right button
            exits.get(1).setLayoutX(405);
            exits.get(1).setLayoutY(235);
            pane.getChildren().add(exits.get(1));
        }
        if (this.left != null) {
            // left button
            exits.get(0).setLayoutX(15);
            exits.get(0).setLayoutY(235);
            pane.getChildren().add(exits.get(0));
        }
        if (this.up != null) {
            // up button
            exits.get(2).setLayoutX(210);
            exits.get(2).setLayoutY(15);
            pane.getChildren().add(exits.get(2));
        }
        if (this.down != null) {
            // down button
            exits.get(3).setLayoutX(210);
            exits.get(3).setLayoutY(445);
            pane.getChildren().add(exits.get(3));
        }

        Group helpGroup = new Group();
        helpButton.setPrefHeight(50);
        helpButton.setPrefWidth(125);
        helpButton.setLayoutX(375);
        helpButton.setLayoutY(450);
        helpButton.setFont(new Font("High Tower Text", 15));
        helpButton.setStyle("-fx-background-color: #62686F;");
        helpButton.setVisible(true); // Set to false to get rid of button
        correctExit.setPrefSize(100, helpButton.getPrefHeight());
        correctExit.setLayoutX(400);
        correctExit.setLayoutY(450);
        correctExit.setFont(new Font("High Tower Text", 15));
        helpGroup.getChildren().addAll(helpButton, correctExit);
        helpGroup.getChildren().set(0, helpButton).toFront();
        id.setLayoutY(100);

        Group healthGroup = new Group();
        healthGroup.getChildren().addAll(healthText, healthRect);
        this.healthText.relocate(5, 5);
        healthRect.relocate(35, 6);

        pane.getChildren().addAll(id, this.goldText, healthGroup, helpGroup);

        pane.getChildren().add(player);
        if (this.monster != null) {
            pane.getChildren().add(monster);
        }

        Rectangle background = new Rectangle(this.width, this.height, this.floorColor);
        StackPane sPane = new StackPane();
        sPane.getChildren().addAll(background, pane);

        return new Scene(sPane, this.width, this.height);
    }

    public void updateHealthBar() {
        healthRect.setWidth(Player.getHealth() * 5);
    }

    public void updateClosedExit(int closedExit, Room current) {
        if (current.getMonster() != null && current.getMonster().isAlive()) {
            if (closedExit == 0) {
                closedExit++;
                current.getExits().get(closedExit).setDisable(true);
            } else if (closedExit == 1) {
                closedExit--;
                current.getExits().get(closedExit).setDisable(true);
            } else {
                current.getExits().get(closedExit).setDisable(true);
            }
        }
    }
    public void openClosedExits(Room current) {
        for (Button closedExit : current.getExits()) {
            if (closedExit.isDisable()) {
                closedExit.setDisable(false);
            }
        }
    }

    /**
     * Sets the position of each exit in the window.
     *
     * @param index Index of the exits in the array of exits
     * @param x     The x coordinate
     * @param y     The y coordinate
     */
    public void setExitPos(int index, int x, int y) {
        if (index < 0 || index >= this.exits.size()) {
            return;
        }
        this.exits.get(index).setLayoutX(x);
        this.exits.get(index).setLayoutY(y);
    }



    /**
     * Setter for the text of an exit button
     *
     * @param index Index of the exit in the array of exits
     * @param text  The text to display on the exit button
     */
    public void setExitText(int index, String text) {
        if (index < 0 || index >= this.exits.size()) {
            return;
        }

        this.exits.get(index).setText(text);
    }

    /**
     * Setter for the size of an exit button
     *
     * @param index  Index of the exit in the array of exits
     * @param width  Width of the exit button
     * @param height Height of the exit button
     */
    public void setExitSize(int index, int width, int height) {
        if (index < 0 || index >= this.exits.size()) {
            return;
        }

        this.exits.get(index).setPrefSize(width, height);
    }

    /**
     * Setter for the event handler for an exit button
     *
     * @param index        Index of the exit in the array of exits
     * @param eventHandler The event handler for an exit button
     */
    public void setExitEventHandler(int index, EventHandler<ActionEvent> eventHandler) {
        if (index < 0 || index >= this.exits.size()) {
            return;
        }

        this.exits.get(index).setOnAction(eventHandler);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    /**
     * Getter for the array of exit buttons.
     *
     * @return The array of exit buttons
     */
    public ArrayList<Button> getExits() {
        return exits;
    }

    /**
     * Setter for the exits from a room.
     *
     * @param exits The array of exits
     */
    public void setExits(ArrayList<Button> exits) {
        this.exits = exits;
    }

    /**
     * Getter for the width of the room
     *
     * @return The width of the room
     */
    public int getWidth() {
        return width;
    }

    /**
     * Setter for the width of the room
     *
     * @param width The desired width of the room
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Getter for the height of the room
     *
     * @return The height of the room
     */
    public int getHeight() {
        return height;
    }

    /**
     * Setter for the height of the room
     *
     * @param height The desired width of the room
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Setter for the right exit from a room
     *
     * @param room The room associated with the exit
     */
    public void setRight(Room room) {
        this.right = room;
    }

    /**
     * Getter for the room associated with the right exit
     *
     * @return The room to the right of the current room
     */
    public Room getRight() {
        return this.right;
    }

    /**
     * Getter for the room associated with the left exit
     *
     * @return The room to the left of the current room
     */
    public Room getLeft() {
        return this.left;
    }

    /**
     * Getter for the room associated with the top exit
     *
     * @return The room to the top of the current room
     */
    public Room getUp() {
        return this.up;
    }

    /**
     * Getter for the room associated with the bottom exit
     *
     * @return The room to the bottom of the current room
     */
    public Room getDown() {
        return this.down;
    }

    /**
     * Setter for the left exit from a room
     *
     * @param room The room associated with the exit
     */
    public void setLeft(Room room) {
        this.left = room;
    }

    /**
     * Setter for the top exit from a room
     *
     * @param room The room associated with the exit
     */
    public void setUp(Room room) {
        this.up = room;
    }

    /**
     * Setter for the bottom exit from a room
     *
     * @param room The room associated with the exit
     */
    public void setDown(Room room) {
        this.down = room;
    }

    /**
     * Getter for the button for the right exit
     *
     * @return The exit button
     */
    public Button getBRight() {
        return this.bRight;
    }

    /**
     * Getter for the button for the left exit
     *
     * @return The exit button
     */
    public Button getBLeft() {
        return this.bLeft;
    }

    /**
     * Getter for the button for the top exit
     *
     * @return The exit button
     */
    public Button getBUp() {
        return this.bUp;
    }

    /**
     * Getter for the button for the bottom exit
     *
     * @return The exit button
     */
    public Button getBDown() {
        return this.bDown;
    }

    public void setPathID(String path) {
        pathID = path;
    }

    public String getPathID() {
        return pathID;
    }

    public void setID(String newID) {
        id = new Text(newID);
    }

    public Text getID() {
        return this.id;
    }

    public Button getHelpButton() {
        return this.helpButton;
    }

    public Label getCorrectExit() {
        return this.correctExit;
    }

    public String getCorrectExitRoomName() {
        return getCorrectExit().getText().length() == 0 ? ""
                : getCorrectExit().getText().substring(8);
    }

    public Monster getMonster() {
        return monster;
    }
}
