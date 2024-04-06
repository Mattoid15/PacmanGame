// Written by: Matthew Lingenfelter
package com.mygame;
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
    public void render(GraphicsContext gc, Pacman pacman, Ghosts ghost) {
        float[] gPos = ghost.getPos();
        float[] pacPos = pacman.getPos();
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
        
        // Draw ghost on the screen
        // Ghost Body
        gc.setFill(Color.PINK);
        gc.fillOval(gPos[0]*TILE_SIZE, gPos[1]*TILE_SIZE, TILE_SIZE, 28);
        gc.fillOval(gPos[0]*TILE_SIZE, gPos[1]*TILE_SIZE+5, 15, 25);
        gc.fillOval(gPos[0]*TILE_SIZE+7.5, gPos[1]*TILE_SIZE+5, 15, 25);
        gc.fillOval(gPos[0]*TILE_SIZE+15, gPos[1]*TILE_SIZE+5, 15, 25);

        // Ghost Eyes
        gc.setFill(Color.WHITE);
        gc.fillOval(gPos[0]*TILE_SIZE+5, gPos[1]*TILE_SIZE+5, 10, 10);
        gc.fillOval(gPos[0]*TILE_SIZE+15, gPos[1]*TILE_SIZE+5, 10, 10);

        // Ghost Puples
        gc.setFill(Color.BLACK);
        switch(ghost.getDir()) {
            case "North":
                gc.fillOval(gPos[0]*TILE_SIZE+7.5, gPos[1]*TILE_SIZE+5, 5, 5);
                gc.fillOval(gPos[0]*TILE_SIZE+17.5, gPos[1]*TILE_SIZE+5, 5, 5);
                break;
            case "South":
                gc.fillOval(gPos[0]*TILE_SIZE+7.5, gPos[1]*TILE_SIZE+10, 5, 5);
                gc.fillOval(gPos[0]*TILE_SIZE+17.5, gPos[1]*TILE_SIZE+10, 5, 5);
                break;
            case "West":
                gc.fillOval(gPos[0]*TILE_SIZE+5, gPos[1]*TILE_SIZE+7.5, 5, 5);
                gc.fillOval(gPos[0]*TILE_SIZE+15, gPos[1]*TILE_SIZE+7.5, 5, 5);
                break;
            case "East":
            default://East
                gc.fillOval(gPos[0]*TILE_SIZE+10, gPos[1]*TILE_SIZE+7.5, 5, 5);
                gc.fillOval(gPos[0]*TILE_SIZE+20, gPos[1]*TILE_SIZE+7.5, 5, 5);
                break;
        }


        // Draw pacman on the screen
        gc.setFill(Color.BEIGE);
        gc.fillRect(((int)pacPos[0])*TILE_SIZE, ((int)pacPos[1])*TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // Pacman Body
        gc.setFill(Color.YELLOW);
        gc.fillOval(pacPos[0]*TILE_SIZE, pacPos[1]*TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // Pacman indicator
        gc.setFill(Color.RED);
        gc.fillOval(pacPos[0]*TILE_SIZE, pacPos[1]*TILE_SIZE, 2, 2);

        
    }

}
