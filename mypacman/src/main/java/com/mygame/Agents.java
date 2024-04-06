// Written by: Matthew Lingenfelter

package com.mygame;

//import javax.transaction.xa.Xid;

public class Agents {
    //private static float speed = 0.05f;
    //private static float tollerance = 0.1f;
    
    public static float[] move(String direction, float[] pos, float speed) {
        float newX = -1f;
        float newY = -1f;
        int xInt = (int)pos[0];
        int yInt = (int)pos[1];

        // Checks if the next location to move to is not a wall, based on the direction
        switch(direction){
            case "North": // Up
                // Calculate the new position based on the speed
                newY = pos[1] - speed;

                // Don't move if there is a wall to the north
                if(Wall.isWall(xInt, yInt-1) && newY < yInt) {
                    break;
                }
                // Update position
                pos[1] = newY;
                pos[0] = xInt;
                break;
            case "South": // Down
                // Calculate the new position based on the speed
                newY = pos[1] + speed;

                // Don't move if there is a wall to the south
                if(Wall.isWall(xInt, yInt+1) && newY > yInt) {
                    break;
                }
                // Update position
                pos[1] = newY;
                pos[0] = xInt;
                break;
            case "East": // Right
                // Calculate the new position based on the speed
                newX = pos[0] + speed;

                // If the next position is wall, don't move
                if(Wall.isWall(xInt+1, yInt) && newX > xInt) {
                    break;
                }
                // Update position
                pos[0] = newX;
                pos[1] = yInt;
                break;
            case "West": // Left
                // Calculate the new position based on the speed
                newX = pos[0] - speed;

                // If the next position is wall, don't move
                if(Wall.isWall(xInt-1, yInt) && newX < xInt) {
                    break;
                }
                // Update position
                pos[0] = newX;
                pos[1] = yInt;
                break;
            default:
                System.out.println("There was an error in Agents.move(), in the switch statment.");
                break;
        }
        return pos;
    }
}
