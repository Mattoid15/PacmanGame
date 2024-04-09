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

        Pacman pacman = new Pacman();
        GameBoard gameboard = new GameBoard();

        Food foods = new Food();
        int[][] foodsLeft = foods.getFood();

        Ghosts inky = new Ghosts("Inky", 14f, 14f);
        Ghosts blinky = new Ghosts("Blinky", 13f, 14f);
        Ghosts pinky = new Ghosts("Pinky", 14f, 15f);
        Ghosts clyde = new Ghosts("Clyde", 13f, 15f);

        Ghosts[] allGhosts = {inky, blinky, pinky, clyde};


        Canvas canvas = new Canvas(gameboard.getWidth()*TILE_SIZE, gameboard.getHeight()*TILE_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root, gameboard.getWidth()*TILE_SIZE, gameboard.getHeight()*TILE_SIZE);
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
                pacman.move();
                pacman.eating(foodsLeft);
                System.out.print("Score: "+pacman.getScore()+"\r");
                for(int i = 0; i < allGhosts.length; i++) {
                    allGhosts[i].move(pacman);
                }
                gameboard.render(gc, pacman, foodsLeft, allGhosts);
            }
        }.start();
    }
}
