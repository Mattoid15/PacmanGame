// Written by: Matthew Lingenfelter
package com.mygame;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;

@SuppressWarnings("exports")
public class PacmanGame {
    private static final int TILE_SIZE = 30;
    public static LocalDateTime nextTime = LocalDateTime.now();
    private static Pacman pacman;
    private static GameBoard gameboard;
    private static Canvas canvas;
    private static GraphicsContext gc;
    private static Text text;
    private static StackPane root;
    private static Scene scene;

    public static void start(Stage primaryStage) {
        primaryStage.setTitle("Pac-Man");

        // Creates Pacman
        pacman = new Pacman();

        // Creates the gameBoard object, used to update the canvas/screen
        gameboard = new GameBoard();

        // Creates the food pacman must eat
        Food foods = new Food();
        int[][] foodsLeft = foods.getFood();

        // Creates each ghost, then add them all to an array of all ghosts
        Ghosts inky = new Ghosts("Inky", 14f, 14f);
        Ghosts blinky = new Ghosts("Blinky", 13f, 14f);
        Ghosts pinky = new Ghosts("Pinky", 14f, 15f);
        Ghosts clyde = new Ghosts("Clyde", 13f, 15f);
        Ghosts[] allGhosts = {inky, blinky, pinky, clyde};

        // Creates the GUI canvas for the game
        canvas = new Canvas(gameboard.getWidth()*TILE_SIZE, gameboard.getHeight()*TILE_SIZE);
        gc = canvas.getGraphicsContext2D();
        text = new Text();
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        text.setStrokeWidth(2);
        text.setStroke(Color.WHITE);
        text.setTranslateX(-11*TILE_SIZE-15);
        text.setTranslateY(-4*TILE_SIZE);
        text.setText("Score: "+pacman.getScore());

        root = new StackPane();
        root.getChildren().add(canvas);
        root.getChildren().add(text);

        // Creates a new scene for the game
        scene = new Scene(root, gameboard.getWidth()*TILE_SIZE, gameboard.getHeight()*TILE_SIZE);

        // When a key is pressed, change pacman's direction
        scene.setOnKeyPressed(e -> pacman.changeDirection(e.getCode()));

        primaryStage.setScene(scene);
        primaryStage.show();

        // Game loop
        gameLoop(primaryStage, allGhosts, foodsLeft);
    }

    // Restarts the game until there are no lives remaining
    public static void restartGame(Stage primaryStage, int[][]foodLeft) {
        System.out.println("Restarting");
        
        // Recreates each ghost, then add them all to an array of all ghosts
        Ghosts inky = new Ghosts("Inky", 14f, 14f);
        Ghosts blinky = new Ghosts("Blinky", 13f, 14f);
        Ghosts pinky = new Ghosts("Pinky", 14f, 15f);
        Ghosts clyde = new Ghosts("Clyde", 13f, 15f);
        Ghosts[] allGhosts = {inky, blinky, pinky, clyde};

        pacman.resetPos();
        pacman.wasHit = false;
        nextTime = nextTime.minusSeconds(20);

        gameLoop(primaryStage, allGhosts, foodLeft);
    }

    // Main loop that drives the game
    private static void gameLoop(Stage primaryStage, Ghosts[] allGhosts, int[][] foodLeft) {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Counts the remaining food on the board
                int foodRemaining = 0;
                for(int r = 0; r < foodLeft[0].length; r++){
                    for(int c = 0; c < foodLeft[r].length; c++) {
                        if(foodLeft[r][c]==1) {
                            foodRemaining++;
                        }
                    }
                }
                
                // Checks Game Over Conditions (No more food, out of lives)
                if(foodRemaining <=0 || pacman.livesLeft <= 0) {
                    stop();
                    // Writes score to output file
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("scores"));
                        writer.append(' ');
                        writer.append(""+pacman.getScore());
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Returns to main menu
                    MainMenu.MainScreen(primaryStage);
                }

                // Sets the current time
                LocalDateTime currentTime = LocalDateTime.now();

                // Check if Pacman hit a ghost
                // Returns true if the ghost is not scared
                if(!pacman.wasHit && pacman.gotHit(allGhosts)) {
                    nextTime = currentTime.plusSeconds(3);
                    pacman.wasHit = true;
                }

                // Checks if pacman was hit and waits 3 seconds
                if(pacman.wasHit && currentTime.isAfter(nextTime)) {
                    restartGame(primaryStage, foodLeft);
                    stop();
                } else if(!pacman.wasHit) {
                    // Check if Pacman is eating food, or a powerpellet
                    // Returns true if Pacman atw a powerpellet
                    if(pacman.eating(foodLeft, allGhosts)) {
                        // Sets all ghosts to the scared state
                        nextTime = currentTime.plusSeconds(6);
                        // For each ghost, set scared to true
                        for(int i = 0; i < allGhosts.length; i++) {
                            allGhosts[i].setScared(true);
                        }
                    }

                    // Checks if the ghost scared timer is up
                    if(currentTime.isAfter(nextTime)) {
                        // for each ghost, set scared to false
                        for(int i = 0; i < allGhosts.length; i++) {
                            allGhosts[i].setScared(false);
                        }
                        pacman.ghostsEaten = 0;
                    }

                    // Moves Pacman
                    pacman.move();

                    // For each ghost, move ghost
                    for(int i = 0; i < allGhosts.length; i++) {
                        allGhosts[i].move(pacman, allGhosts);
                    }

                    // Updates display with new information
                    gameboard.render(gc, pacman, foodLeft, allGhosts, text);
                }
            }
        }.start();
    }
}