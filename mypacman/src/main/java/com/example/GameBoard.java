// Written by: Matthew Lingenfelter
package com.example;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

@SuppressWarnings("exports")
public class GameBoard {
    private static final int TILE_SIZE = 30;
    private int[][] walls;
    private static int height;
    private static int width;

    public GameBoard() {
        walls = Wall.getWalls();
        height = walls.length;
        width = walls[0].length;
    }

    // Returns the height of the maze
    public int getHeight() {
        return height;
    }

    // Returns the width of the maze
    public int getWidth() {
        return width;
    }

    // Updates the screen to display the walls
    public void render(GraphicsContext gc, Pacman pacman) {
        gc.clearRect(0, 0, width*TILE_SIZE, height*TILE_SIZE);
        // Draw the walls on the screen
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if(walls[row][col] == 1) {
                    gc.setFill(Color.BLUE);
                    gc.fillRect(col*TILE_SIZE, row*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                } else {
                    gc.setFill(Color.BLACK);
                    gc.fillRect(col*TILE_SIZE, row*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
        // Draw pacman on the screen
        float[] pacPos = pacman.getPos();
        gc.setFill(Color.YELLOW);
        gc.fillOval(pacPos[0]*TILE_SIZE, pacPos[1]*TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

}
