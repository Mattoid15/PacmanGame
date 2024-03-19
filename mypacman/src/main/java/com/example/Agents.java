package com.example;

public class Agents {
    //private float x_coord;
    //private float y_coord;
    private static float speed = 1f;
    private float tollerance = 0.001f; // Use the tollerance to determine where the agent is in the map
    //private String direction;
    
    public static float[] move(String direction, float[] pos) {
        //float x_coord = pos[0];
        //float y_coord = pos[1];
        float newY = -1f;
        float newX = -1f;
        int x_int = -1;
        int y_int = -1;
        //float[] result = new float[] {x_coord};

        switch(direction){
            case "North":
                newY = pos[1] - speed;
                x_int = (int) (pos[0] + 0.5f);
                y_int = (int) (newY + 0.5f);
                // Checks if the agent is inbetween grid locations, meaning they must continue the 
                //if(Math.abs(x_coord-x_int) + Math.abs(y_coord-y_int) > tollerance) {
                //    break;
                //}
                // Checks if the next location to move to is not a wall
                if(!Wall.isWall(x_int, y_int)) {
                    pos[1] = newY;
                }
                
                break;
            case "South":
                newY = pos[1] - speed;
                x_int = (int) (pos[0] + 0.5f);
                y_int = (int) (newY + 0.5f);
                if(!Wall.isWall(x_int, y_int)) {
                    pos[1] = newY;
                }
                break;
            case "East":
                newX = pos[0] + speed;
                x_int = (int) (newX + 0.5f);
                y_int = (int) (pos[1] + 0.5f);
                if(!Wall.isWall(x_int, y_int)) {
                    pos[0] = newX;
                }
                break;
            case "West":
                newX = pos[0] - speed;
                x_int = (int) (newX + 0.5f);
                y_int = (int) (pos[1] + 0.5f);
                if(!Wall.isWall(x_int, y_int)) {
                    pos[0] = newX;
                }
                break;
            default:
                System.out.println("There was an error in Agents.move(), in the switch statment.");
                break;
        }
        return pos;
    }
}
