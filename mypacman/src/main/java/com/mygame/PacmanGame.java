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

        Ghosts testGhost = new Ghosts();


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
                testGhost.move();
                gameboard.render(gc, pacman, testGhost);
            }
        }.start();
    }
}
