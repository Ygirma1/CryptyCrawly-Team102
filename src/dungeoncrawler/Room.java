package dungeoncrawler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Random;

public class Room {
    private ArrayList<Button> exits;
    private Room[] adjRooms = new Room[4]; // ordered right, left, up, down
    private Button bLeft;
    private Button bRight;
    private Button bUp;
    private Button bDown;
    private Text id; // just a label for a room, use for debugging
    private Text goldText;
    private Room left;
    private Room right;
    private Room up;
    private Room down;
    private Difficulty diff;
    private final Color floorColor = Color.rgb(129, 137, 147);
    private final Color goldColor = Color.rgb(255, 215, 0);
    private final Font smallFont = new Font("High Tower Text", 19);
    private int width;
    private int height;

    public Room(int width, int height) {
        this(width, height, "doge", Difficulty.EASY);
    }

    public Room(String id, Difficulty diff) {
        this(500, 500, id, diff);
    }

    public Room(int width, int height, String id, Difficulty diff) {
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
    }

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
        int randRoomIndex = rand.nextInt(4);
        Room next = startingRoom.adjRooms[randRoomIndex];
        rGenerateMap(next, 0, randRoomIndex);
    }

    /**
     * Recursive helper method to generate a random sequence of rooms.
     * Rooms lead to boss room
     *
     * @param current the current room having rooms added to it
     * @param roomDepth the distance of the rooms from the start
     * @param newRoomIndex the index of the Room[] that will determine the direction of the new room
     */
    private void rGenerateMap(Room current, int roomDepth, int newRoomIndex) {
        if (roomDepth >= 6) {
            Room nextRoom = new DogeRoom(500, 500, "Boss", current.diff);
            current.adjRooms[newRoomIndex] = nextRoom;
            nextRoom.down = null;
            nextRoom.up = null;
            nextRoom.left = null;
            nextRoom.right = null;
            updateAdjRooms(current);
            updateRoomArray(nextRoom);
        } else {
            Random rand = new Random();
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
            rGenerateMap(nextRoom, roomDepth + 1, nextIndex);
        }
    }

    // When extends this class, override this to set your room's own scene
    public Scene getScene() {
        Pane pane = new Pane();
        if (this.right != null) {
            //right button
            exits.get(1).setLayoutX(405);
            exits.get(1).setLayoutY(235);
            pane.getChildren().add(exits.get(1));
        }
        if (this.left != null) {
            //left button
            exits.get(0).setLayoutX(15);
            exits.get(0).setLayoutY(235);
            pane.getChildren().add(exits.get(0));
        }
        if (this.up != null) {
            //up button
            exits.get(2).setLayoutX(210);
            exits.get(2).setLayoutY(15);
            pane.getChildren().add(exits.get(2));
        }
        if (this.down != null) {
            //down button
            exits.get(3).setLayoutX(210);
            exits.get(3).setLayoutY(445);
            pane.getChildren().add(exits.get(3));
        }
        id.setLayoutY(100);
        pane.getChildren().add(id);
        pane.getChildren().add(this.goldText);

        Rectangle background = new Rectangle(this.width, this.height, this.floorColor);
        StackPane sPane = new StackPane();
        sPane.getChildren().addAll(background, pane);

        return new Scene(sPane, this.width, this.height);
    }

    private void updateRoomArray(Room current) {
        current.adjRooms[0] = current.right;
        current.adjRooms[1] = current.left;
        current.adjRooms[2] = current.up;
        current.adjRooms[3] = current.down;
    }

    private void updateAdjRooms(Room current) {
        current.right = current.adjRooms[0];
        current.left = current.adjRooms[1];
        current.up = current.adjRooms[2];
        current.down = current.adjRooms[3];
    }

    public void setExitPos(int index, int x, int y) {
        if (index < 0 || index >= this.exits.size()) {
            return;
        }
        this.exits.get(index).setLayoutX(x);
        this.exits.get(index).setLayoutY(y);
    }

    public void setExitText(int index, String text) {
        if (index < 0 || index >= this.exits.size()) {
            return;
        }

        this.exits.get(index).setText(text);
    }

    public void setExitSize(int index, int width, int height) {
        if (index < 0 || index >= this.exits.size()) {
            return;
        }

        this.exits.get(index).setPrefSize(width, height);
    }

    public void setExitEventHandler(int index, EventHandler<ActionEvent> eventHandler) {
        if (index < 0 || index >= this.exits.size()) {
            return;
        }

        this.exits.get(index).setOnAction(eventHandler);
    }

    public ArrayList<Button> getExits() {
        return exits;
    }

    public void setExits(ArrayList<Button> exits) {
        this.exits = exits;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setRight(Room room) {
        this.right = room;
    }

    public Room getRight() {
        return this.right;
    }

    public Room getLeft() {
        return this.left;
    }

    public Room getUp() {
        return this.up;
    }

    public Room getDown() {
        return this.down;
    }

    public void setLeft(Room room) {
        this.left = room;
    }

    public void setUp(Room room) {
        this.up = room;
    }

    public void setDown(Room room) {
        this.down = room;
    }

    public Button getBRight() {
        return this.bRight;
    }

    public Button getBLeft() {
        return this.bLeft;
    }

    public Button getBUp() {
        return this.bUp;
    }

    public Button getBDown() {
        return this.bDown;
    }
}
