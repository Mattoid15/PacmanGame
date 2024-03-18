package com.example;

import java.util.*;

public class ghostAgents {
    private static final int[][] allMoves = {{1,0},{-1,0},{0,1},{0,-1}};
    // Blinky - red
    //     Directly chases pacman
    // Pinky - pink
    //     Trys to get in front of pacman
    // Inky - cyan
    //     Trys to get in front of pacman <- instead...
    //     Trys to get behind pacman
    // Clyde - orange
    //     Goes towards pacman until a certain distance, then runs away from pacman
    
    // Returns an action for the ghost to preform
    // *** Somehow change for which ghoast 
    public static int[] getAction(int index, int[] loc) {
        if (index == 1) {
            int currentX = loc[0];
            int currentY = loc[1];
            //int count = 0;

            for (int i = 0; i < allMoves.length; i++) {
                int newX = currentX+allMoves[i][0];
                int newY = currentY+allMoves[i][1];
                if (isValid(newX, newY, game.maze)) {
                    //count++;
                }
            }
            //if (count >= 3) {
            //    int[] move = AStar.getNext(currentX, currentY, game.agentLoc[0][0], game.agentLoc[0][1]);
            //    System.out.println(move[0]+" "+move[1]);
            //    return move;
            //}
        }

        Random random = new Random();

        int ran = random.nextInt(4);
        int [] result = {allMoves[ran][0], allMoves[ran][1]};
        return result;
    }

    private static boolean isValid(int x, int y, int[][] grid) {
        int[][] agents = game.agentLoc;
        if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y] != 1) {
            for (int i = 0; i < agents.length; i++) {
                if (isEqual(x, y, agents[i][0], agents[i][1])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private static boolean isEqual(int x1, int y1, int x2, int y2) {
        return x1 == x2 && y1 == y2;
    }
}
