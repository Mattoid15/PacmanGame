// Written by: Matthew Lingenfelter
package com.mygame;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@SuppressWarnings("exports")
public class PacmanGame extends Application {
    private static final int TILE_SIZE = 30;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pac-Man");

        // Creates Pacman
        Pacman pacman = new Pacman();

        // Creates the gameBoard object, used to update the canvas/screen
        GameBoard gameboard = new GameBoard();

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
        Canvas canvas = new Canvas(gameboard.getWidth()*TILE_SIZE, gameboard.getHeight()*TILE_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        // Creates a new scene for the game
        Scene scene = new Scene(root, gameboard.getWidth()*TILE_SIZE, gameboard.getHeight()*TILE_SIZE);

        // When a key is pressed, change pacman's direction
        scene.setOnKeyPressed(e -> pacman.changeDirection(e.getCode()));

        primaryStage.setScene(scene);
        primaryStage.show();

        // Initialize game objects
        //boolean gameOver = false;

        // Game loop
        //while (!gameOver) {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                pacman.move(); // Move pacman 
                // Check if pacman dies
                pacman.eating(foodsLeft); // Check if pacman ate food
                System.out.print("Score: "+pacman.getScore()+"\r");
                for(int i = 0; i < allGhosts.length; i++) { // For each ghost
                    allGhosts[i].move(pacman, allGhosts); // Move ghost
                }
                gameboard.render(gc, pacman, foodsLeft, allGhosts); // Update the screen
            }
        }.start();
    }
}
