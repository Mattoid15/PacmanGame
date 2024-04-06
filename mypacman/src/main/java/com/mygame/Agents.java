// Written by: Matthew Lingenfelter

package com.mygame;

public class Agents {
    //private static float speed = 0.05f;
    private static float tollerance = 0.025f;
    
    public static float[] move(String direction, float[] pos, float speed) {
        float newY = -1f;
        float newX = -1f;

        // Checks if the next location to move to is not a wall, based on the direction
        switch(direction){
            case "North":
                newY = pos[1] - speed;
                //pos[0] = Math.round(pos[0]);

                if(!Wall.isWall((int)Math.round(pos[0]), (int)(newY))) {
                    pos[1] = newY;
                } else {
                    pos[1] = (int)newY+1;
                }
                break;
            case "South":
                newY = pos[1] + speed;
                //pos[0] = Math.round(pos[0]);

                if(!Wall.isWall(Math.round(pos[0]), (int)(newY)+1)) {
                    pos[1] = newY;
                } else {
                    pos[1] = (int)newY;
                }
                break;
            case "East":
                newX = pos[0] + speed;
                //pos[1] = Math.round(pos[1]);

                if(!Wall.isWall((int)(newX)+1, Math.round(pos[1]))) {
                    pos[0] = newX;
                } else {
                    pos[0] = (int)newX;
                }
                break;
            case "West":
                newX = pos[0] - speed;
                //pos[1] = Math.round(pos[1]);
               // System.out.println("Current y: "+pos[1]);
                //System.out.println("Rounded y: "+Math.round(pos[1]));
                //System.out.println("Round Tollerance y: "+Math.round(pos[1]-tollerance));
                //pos[1] = Math.round(pos[1]-tollerance);

                if(!Wall.isWall((int)(newX), Math.round(pos[1]))) {
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
