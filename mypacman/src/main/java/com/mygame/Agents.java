// Written by: Matthew Lingenfelter

package com.mygame;

public class Agents {
    // Method used by all agents (Pacman and Ghosts)
    // Used to update agent position and check wall collition
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
                if(Wall.isWall(xInt, yInt-1) && newY < yInt) { break; }

                // Update position
                pos[1] = newY;
                pos[0] = xInt;
                break;
            case "South": // Down
                // Calculate the new position based on the speed
                newY = pos[1] + speed;

                // Don't move if there is a wall to the south
                if(Wall.isWall(xInt, yInt+1) && newY > yInt) { break; }

                // Update position
                pos[1] = newY;
                pos[0] = xInt;
                break;
            case "East": // Right
                // Calculate the new position based on the speed
                newX = pos[0] + speed;

                // If agent is moving from the right side of the screen to the left side
                if(newX >= Wall.getWalls()[0].length-1) {
                    pos[0] = 0;
                    pos[1] = yInt;
                    break;
                }

                // If the next position is wall, don't move
                if(Wall.isWall(xInt+1, yInt) && newX > xInt) { break; }

                // Update position
                pos[0] = newX;
                pos[1] = yInt;
                break;
            case "West": // Left
                // Calculate the new position based on the speed
                newX = pos[0] - speed;

                // If agent is moving from the left side of the screen to the right side
                //System.out.println(newX);
                if(xInt <= 0) {
                    //System.out.println(newX);
                    pos[0] = Wall.getWalls()[0].length-1;
                    pos[1] = yInt;
                    break;
                }

                // If the next position is wall, don't move
                if(Wall.isWall(xInt-1, yInt) && newX < xInt) { break; }

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

    // Returns true if a ghost is hitting another ghost
    public static boolean checkCollition(Ghosts[] ghosts, String name, float[] newPos) {        
        // Check if new location has a different ghost
        for(int i = 0; i < ghosts.length; i++) {
            if(name != ghosts[i].getName()) { // Check each ghost position that is not the current ghost
                if((int)newPos[0] == (int)ghosts[i].getPos()[0] && (int)newPos[1] == (int)ghosts[i].getPos()[1]) {
                    return true;
                }
            }
        }
        return false;
    }
}
