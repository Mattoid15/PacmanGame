package com.example;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

@SuppressWarnings("exports")
public class game {
    // Data used to display the game
    public static StackPane root = null;
    public static Canvas canvas = null;
    public static Scene scene = null;
    public static Label scoreText = null;

    // Data used for frame rate
    private static int FPS = 60;
    private static long NANOINTER = 1_000_000_000 / FPS;
    private static long lastUpdateTime = 0;
    private static long lastGhostUpdate = 0;

    // Size of each grid cell
    private static final int CELL_SIZE = 20;

    // Layout for the maze/world
    // 1 = Wall, 0 = Open area
    // 2 = food, 3 = power pellet
    public static final int[][] maze = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
        {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
        {1, 3, 1, 0, 0, 1, 2, 1, 0, 0, 0, 1, 2, 1, 1, 2, 1, 0, 0, 0, 1, 2, 1, 0, 0, 1, 3, 1},
        {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
        {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
        {1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1},
        {1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1},
        {1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1},
        {1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1},
        {0, 0, 0, 0, 0, 1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 2, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0},
        {1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1},
        {0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0},
        {1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1},
        {0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0},
        {1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1},
        {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
        {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
        {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
        {1, 3, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 3, 1},
        {1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1},
        {1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1},
        {1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1},
        {1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1},
        {1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1},
        {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    // All of the game's agents initial locations
    public static int[][] agentLoc = {
        {20, 13}, // Pacman
        {17, 14}, // Blinky
        {17, 13}, // Pinky
        {17, 12}, // Inky
        {17, 15}  // Clyde
    };
    public static int score = 0;
    public static boolean waiting = true;

    // Displays the original screen
    public static void start(Stage primaryStage) {
        root = new StackPane();
        canvas = new Canvas(CELL_SIZE * maze[0].length, CELL_SIZE * maze.length);
        root.getChildren().add(canvas);
        scene = new Scene(root);

        scoreText = new Label("Score: 0000");
        StackPane.setAlignment(scoreText, Pos.TOP_RIGHT);
        root.getChildren().add(scoreText);

        primaryStage.setTitle("Pacman");
        primaryStage.setScene(scene);
        primaryStage.show();

        setKeyListeners(scene);

        // Updates the screen according to the frame rate that is set
        // pacman moves at 10 tiles per second
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdateTime >= NANOINTER) {
                    updateScreen();
                    lastUpdateTime = now;
                }
            }
        };
        timer.start();

        AnimationTimer ghostsTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastGhostUpdate >= NANOINTER*6) {
                    // Moves ghosts
                    for (int i = 1; i < agentLoc.length; i++) {
                        int[] currentGhost = agentLoc[i];
                        int[] ghostMove = ghostAgents.getAction(i, currentGhost);
                        currentGhost = Movement.updateLoc(currentGhost, ghostMove[0], ghostMove[1]);
                        agentLoc[i] = currentGhost;
                    }
                    lastGhostUpdate = now;
                }
            }
        };

        ghostsTimer.start();
    }

    // Updates the location on the scree of all the ghosts
    public static void drawGhosts(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        // For each ghost, set the color according to which ghost is currently being updated
        for(int i = 1; i < agentLoc.length; i++) {
            switch (i) {
                case 1:
                    gc.setFill(javafx.scene.paint.Color.RED);
                    break;
                case 2:
                    gc.setFill(javafx.scene.paint.Color.PINK);
                    break;
                case 3:
                    gc.setFill(javafx.scene.paint.Color.CYAN);
                    break;
                case 4:
                    gc.setFill(javafx.scene.paint.Color.ORANGE);
                    break;
                default:
                    break;
            }

            gc.fillOval(agentLoc[i][1] * CELL_SIZE, agentLoc[i][0] * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    // Gets input from the user, and moves pacman if possible
    public static void setKeyListeners(Scene scene) {
        scene.setOnKeyPressed(e -> {
            KeyCode code = e.getCode();
            System.out.println("Key Pressed: "+code);
            switch (code) {
                case UP:
                    agentLoc[0] = Movement.updateLoc(agentLoc[0], -1, 0);
                    break;
                case DOWN:
                    agentLoc[0] = Movement.updateLoc(agentLoc[0], 1, 0);
                    break;
                case LEFT:
                    agentLoc[0] = Movement.updateLoc(agentLoc[0], 0, -1);
                    break;
                case RIGHT:
                    agentLoc[0] = Movement.updateLoc(agentLoc[0], 0, 1);
                    break;
                default:
                    break;
            }
        });
    }

    // Updates the screen according to the framerate setting
    public static void updateScreen() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Redraw the map, removing the old locations of all agents
        for(int i = 0; i < maze.length; i++) {
            for(int j = 0; j < maze[i].length; j++) {
                if(maze[i][j] == 1) { //If wall
                    gc.setFill(javafx.scene.paint.Color.BLUE);
                    gc.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                } else if (maze[i][j] == 2) { // If there is food
                    gc.setFill(javafx.scene.paint.Color.BLACK);
                    gc.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    gc.setFill(javafx.scene.paint.Color.WHITE);
                    gc.fillOval(j * CELL_SIZE+7, i * CELL_SIZE+7, CELL_SIZE/3, CELL_SIZE/3);
                } else if (maze[i][j] == 3) { // If there is a power pellet
                    gc.setFill(javafx.scene.paint.Color.BLACK);
                    gc.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    gc.setFill(javafx.scene.paint.Color.WHITE);
                    gc.fillOval(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                } else { // If there is nothing
                    gc.setFill(javafx.scene.paint.Color.BLACK);
                    gc.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
                gc.setStroke(javafx.scene.paint.Color.BLACK);
                gc.strokeRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        // Checks if the loction pacman is in has food
        // If there is food, remove the food and update the score
        if (maze[agentLoc[0][0]][agentLoc[0][1]] == 2) {
            maze[agentLoc[0][0]][agentLoc[0][1]] = 0;
            score = score + 10;
            scoreText.setText("Score: " + score);
        }

        // Update pacman's location on the screen
        gc.setFill(javafx.scene.paint.Color.YELLOW);
        gc.fillOval(agentLoc[0][1] * CELL_SIZE, agentLoc[0][0] * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        // Updates the ghosts locations on the screen
        drawGhosts(canvas);
    }
}
