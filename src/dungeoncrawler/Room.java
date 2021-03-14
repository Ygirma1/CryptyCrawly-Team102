package dungeoncrawler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Random;

public class Room {
    private ArrayList<Button> exits;
    private final Room[] adjRooms = new Room[4]; // ordered right, left, up, down
    private final Button bLeft;
    private final Button bRight;
    private final Button bUp;
    private final Button bDown;
    private final Text id; // just a label for a room, use for debugging
    private String roomID; // maybe we need this to keep track of the room position?
    private static int roomCount;
    private final int exitNum;
    private Room left;
    private Room right;
    private Room up;
    private Room down;
    private int width;
    private int height;
    private final Label goldLabel;

    /**
     * Chained constructor for Room object
     *
     * @param width The width of the window
     * @param height The height of the window
     * @param numberOfRooms The number of exits from the room
     */
    public Room(int width, int height, int numberOfRooms) {
        this(width, height, numberOfRooms, "doge");
    }

    /**
     * Chained constructor with hard-coded values for width, height and
     * number of exits
     *
     * @param id String id for keeping track of each room in maze
     */
    public Room(String id) {
        this(500, 500, 4, id);
    }

    /**
     * Constructor for Room object.
     *
     * @param width The width of the window
     * @param height The height of the window
     * @param numberOfRooms Number of attached rooms
     * @param id ID for keeping track of each room
     */
    public Room(int width, int height, int numberOfRooms, String id) {
        this.id = new Text(id);
        this.exits = new ArrayList<>();
        this.bLeft = new Button("left");
        this.exits.add(bLeft);
        this.bRight = new Button("right");
        this.exits.add(bRight);
        this.bUp = new Button("up");
        this.exits.add(bUp);
        this.bDown = new Button("down");
        this.exits.add(bDown);
        for (Button exit : this.exits) {
            exit.setPrefSize(50, 50);
        }
        this.width = width;
        this.height = height;
        this.exitNum = numberOfRooms;

        this.goldLabel = new Label("Gold: " + Controller.getGold());

        this.goldLabel.setLayoutX(400);
        this.goldLabel.setLayoutY(100);

    }

    /**
     * Recursive method to generate the maze of rooms.
     *
     * @param startingRoom The root of the maze, or starting room.
     */
    public void generateMap(Room startingRoom) {
        Room rRoom = new Room("right");
        startingRoom.right = rRoom;
        rRoom.left = startingRoom;
        updateAdjRooms(rRoom);
        Room lRoom = new Room("left");
        startingRoom.left = lRoom;
        lRoom.right = startingRoom;
        updateAdjRooms(lRoom);
        Room uRoom = new Room("up");
        startingRoom.up = uRoom;
        uRoom.down = startingRoom;
        updateAdjRooms(uRoom);
        Room dRoom = new Room("down");
        startingRoom.down = dRoom;
        dRoom.up = startingRoom;
        updateAdjRooms(dRoom);
        updateAdjRooms(startingRoom);
        Random rand = new Random();
        int randRoomIndex = rand.nextInt(4);
        Room next = startingRoom.adjRooms[randRoomIndex];
        rGenerateMap(next, 0, randRoomIndex);
    }

    /**
     * Recursive helper method for generateRoom method
     * @param current The current room
     * @param roomDepth Number of rooms from the starting room
     * @param newRoomIndex The index of the newly created room
     */
    private void rGenerateMap(Room current, int roomDepth, int newRoomIndex) {
        if (roomDepth >= 6) {
            Room nextRoom = new DogeRoom(500, 500, 4);
            current.adjRooms[newRoomIndex] = nextRoom;
            nextRoom.down = null;
            nextRoom.up = null;
            nextRoom.left = null;
            nextRoom.right = null;
            updateAdjRooms(current, true);
            updateAdjRooms(nextRoom);
        } else {
            Random rand = new Random();
            Room nextRoom = new Room("new" + roomDepth);
            current.adjRooms[newRoomIndex] = nextRoom;
            int nextRoomPrevIndex = newRoomIndex;
            if (newRoomIndex % 2 == 0) {
                nextRoomPrevIndex += 1;
            } else {
                nextRoomPrevIndex -= 1;
            }
            nextRoom.adjRooms[nextRoomPrevIndex] = current;
            updateAdjRooms(current, true);
            updateAdjRooms(nextRoom, true);

            int nextIndex = nextRoomPrevIndex;
            while (nextIndex == nextRoomPrevIndex) {
                nextIndex = rand.nextInt(4);
            }
            rGenerateMap(nextRoom, roomDepth + 1, nextIndex);
        }
    }

    private void generateBossRoom(Room current, int newRoomIndex) {
        //TODO generate boss room (doge room?????)



    }

    /**
     * Updates the adjacency array of rooms for current room.
     *
     * @param current The room currently displayed
     */
    private void updateAdjRooms(Room current) {
        current.adjRooms[0] = current.right;
        current.adjRooms[1] = current.left;
        current.adjRooms[2] = current.up;
        current.adjRooms[3] = current.down;
    }

    /**
     * Updates the right, left, top and bottom rooms using the adjacency array
     *
     * @param current Current room
     * @param foo
     */
    private void updateAdjRooms(Room current, boolean foo) {
        current.right = current.adjRooms[0];
        current.left = current.adjRooms[1];
        current.up = current.adjRooms[2];
        current.down = current.adjRooms[3];
    }

    // When extends this class, override this to set your room's own scene

    /**
     * Returns a new scene
     *
     * @return The newly created scene
     */
    public Scene getScene() {
        Pane pane = new Pane();
        if (this.right != null) {
            //right button
            exits.get(1).setLayoutX(300);
            exits.get(1).setLayoutY(200);
            pane.getChildren().add(exits.get(1));
        }
        if (this.left != null) {
            //left button
            exits.get(0).setLayoutX(100);
            exits.get(0).setLayoutY(200);
            pane.getChildren().add(exits.get(0));
        }
        if (this.up != null) {
            //up button
            exits.get(2).setLayoutX(200);
            exits.get(2).setLayoutY(100);
            pane.getChildren().add(exits.get(2));
        }
        if (this.down != null) {
            //down button
            exits.get(3).setLayoutX(200);
            exits.get(3).setLayoutY(400);
            pane.getChildren().add(exits.get(3));
        }
        id.setLayoutY(100);
        pane.getChildren().add(id);
        pane.getChildren().add(this.goldLabel);
        return new Scene(pane, this.width, this.height);
    }

    /**
     * Sets the position of each exit in the window.
     *
     * @param index Index of the exits in the array of exits
     * @param x The x coordinate
     * @param y The y coordinate
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
     * @param text The text to display on the exit button
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
     * @param index Index of the exit in the array of exits
     * @param width Width of the exit button
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
     * @param index Index of the exit in the array of exits
     * @param eventHandler The event handler for an exit button
     */
    public void setExitEventHandler(int index, EventHandler<ActionEvent> eventHandler) {
        if (index < 0 || index >= this.exits.size()) {
            return;
        }

        this.exits.get(index).setOnAction(eventHandler);
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
     * Getter for the number of exits from a room
     *
     * @return The number of exits
     */
    public int getExitNum() {
        return this.exitNum;
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
}
