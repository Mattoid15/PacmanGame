// Written by: Matthew Lingenfelter

package com.example;

public class Agents {
    //private float x_coord;
    //private float y_coord;
    private static float speed = 0.05f;
    //private static float tollerance = 0.001f; // Use the tollerance to determine where the agent is in the map
    //private String direction;
    //private static final int TILE_SIZE = 30;
    
    public static float[] move(String direction, float[] pos) {
        //float x_coord = pos[0];
        //float y_coord = pos[1];
        float newY = -1f;
        float newX = -1f;
        //int x_int = -1;
        //int y_int = -1;
        //float[] result = new float[] {x_coord};

        // Checks if the next location to move to is not a wall, based on the direction
        switch(direction){
            case "North":
                newY = pos[1] - speed;
                if(!Wall.isWall((int)Math.round(pos[0]), (int)(newY))) {
                    pos[1] = newY;
                } else {
                    pos[1] = (int)newY+1;
                }
                break;
            case "South":
                newY = pos[1] + speed;
                if(!Wall.isWall((int)Math.round(pos[0]), (int)(newY)+1)) {
                    pos[1] = newY;
                } else {
                    pos[1] = (int)newY;
                }
                break;
            case "East":
                newX = pos[0] + speed;
                if(!Wall.isWall((int)(newX)+1, (int)Math.round(pos[1]))) {
                    pos[0] = newX;
                } else {
                    pos[0] = (int)newX;
                }
                break;
            case "West":
                newX = pos[0] - speed;
                if(!Wall.isWall((int)(newX), (int)Math.round(pos[1]))) {
                    pos[0] = newX;
                } else {
                    pos[0] = (int)newX+1;
                }
                break;
            default:
                System.out.println("There was an error in Agents.move(), in the switch statment.");
                break;
        }
        return pos;
    }
}
