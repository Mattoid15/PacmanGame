package com.example;

public class Movement {
    public static int[] updateLoc(int[] current, int rowChange, int colChange) {
        int newRow = current[0] + rowChange;
        int newCol = current[1] + colChange;

        int[][] maze = game.maze;
        int[][] agents = game.agentLoc;
        // Check if the new location is in the bounds of the maze, and is not a wall
        if (newRow >= 0 && newRow < maze.length && newCol >= 0 && newCol < maze[0].length && maze[newRow][newCol] != 1) {
            // Checks if the new location for this agent is one of the other agents' locations
            // If it is, this agent will not move
            for (int i = 0; i < agents.length; i++) {
                if (isEqual(newRow, newCol, agents[i][0], agents[i][1])) {
                    return current;
                }
            }
            
            current[0] = newRow;
            current[1] = newCol;
        }

        // Handles the agent going from one side of the screen to the other
        if (newCol < 0) {
            current[0] = newRow;
            current[1] = maze[0].length-1;
        }
        if (newCol > maze[0].length-1) {
            current[0] = newRow;
            current[1] = 0;
        }

        return current;
    }

    private static boolean isEqual(int x1, int y1, int x2, int y2) {
        return x1 == x2 && y1 == y2;
    }

}
