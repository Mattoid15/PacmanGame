// Written by: Matthew Lingenfelter
package com.mygame;

public class Wall {
    //private static int height;
    //private static int width;
    private static int[][] walls = {
        {1,1,1,1,1,1,1},
        {1,0,0,0,0,0,1},
        {1,1,1,0,1,1,1},
        {1,0,0,0,0,0,1},
        {1,0,1,1,1,0,1},
        {1,0,0,0,0,0,1},
        {1,1,1,1,1,1,1}
    };
    
    // Default constructor that sets the height and width correctly
    public Wall() {
        //height = walls.length;
        //width = walls[0].length;
    }

    // Returns the height of the maze
    //public int getHeight() {
    //    return height;
    //}

    // Returns the width of the maze
    //public int getWidth() {
    //    return width;
    //}

    // Checks if a given location is within the bounds of the maze, and is a wall
    public static boolean isWall(int y, int x) {
        //System.out.println("("+x+", "+y+")="+walls[x][y]);
        return walls[x][y] == 1;
        //if(x >= 0 && x < width && y >= 0 && y < height) {
        //    return walls[x][y] == 1;
        //} else {
        //    System.out.print("Location ("+x+", "+y+") out of bounds\r");
        //    //return true;
        //    return walls[x][y] == 1;
        //}
    }

    // Returns a 2D array of where the walls are
    public static int[][] getWalls() {
        return walls;
    }
}
