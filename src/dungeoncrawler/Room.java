package dungeoncrawler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

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
    private Label correctExit; //Displays proper exit in bottom right
    private String pathID; //Text displaying correct exit to choose
    private String roomID; //maybe we need this to \keep track of the room position?
    private static int roomCount;
    private int exitNum;
    private Room left;
    private Room right;
    private Room up;
    private Room down;
    private int width;
    private int height;
    private Label goldLabel;

    public Room(int width, int height, int numberOfRooms) {
        this(width, height, numberOfRooms, "doge");
    }

    public Room(String id) {
        this(500, 500, 4, id);
    }

    public Room(int width, int height, int numberOfRooms, String id) {
        this.correctExit = new Label("");
        this.id = new Text(id);
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
            exit.setPrefSize(50, 50);
        }
        this.width = width;
        this.height = height;
        this.exitNum = numberOfRooms;

        this.goldLabel = new Label("Gold: " + Controller.getGold());

        this.goldLabel.setLayoutX(400);
        this.goldLabel.setLayoutY(100);
    }

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
        startingRoom.setPathID(pathReveal(randRoomIndex));
    }

    private void rGenerateMap(Room current, int roomDepth, int newRoomIndex) {
        current.setPathID(pathReveal(newRoomIndex));
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
            current.adjRooms[newRoomIndex] = nextRoom; //First new room has the exit across from it everytime, r -> r
            int nextRoomPrevIndex = newRoomIndex; // r (0) = 0
            if (newRoomIndex % 2 == 0) { //true, nextRoomPrevIndex = 1
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

    private void updateAdjRooms(Room current) {
        current.adjRooms[0] = current.right;
        current.adjRooms[1] = current.left;
        current.adjRooms[2] = current.up;
        current.adjRooms[3] = current.down;
    }

    private void updateAdjRooms(Room current, boolean foo) {
        current.right = current.adjRooms[0];
        current.left = current.adjRooms[1];
        current.up = current.adjRooms[2];
        current.down = current.adjRooms[3];
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
    public Scene getScene() {
        Pane pane = new Pane();
        String path = getPathID();
        this.correctExit = new Label(path);
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
        Group helpGroup = new Group();
        helpButton.setPrefHeight(50);
        helpButton.setPrefWidth(100);
        helpButton.setLayoutX(350);
        helpButton.setLayoutY(400);
        correctExit.setPrefSize(100, 50);
        correctExit.setLayoutX(350);
        correctExit.setLayoutY(400);
        correctExit.setAlignment(Pos.CENTER);
        correctExit.setStyle("-fx-border-color: black;");
        helpGroup.getChildren().addAll(helpButton, correctExit);
        helpGroup.getChildren().set(0, helpButton).toFront();
        id.setLayoutY(100);
        pane.getChildren().addAll(id, this.goldLabel, helpGroup);
        return new Scene(pane, this.width, this.height);
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

    public int getExitNum() {
        return this.exitNum;
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
}
