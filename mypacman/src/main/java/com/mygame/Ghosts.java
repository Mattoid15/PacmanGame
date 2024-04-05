// Written by: Matthew Lingenfelter

package com.mygame;

//@SuppressWarnings("exports")
public class Ghosts {
    private float x_coord;
    private float y_coord;
    private String direction;
    private String[] actions = {"North", "West", "South", "East"};

    public Ghosts() {
        direction = "East";
        x_coord = 1f;
        y_coord = 5f;
    }

    // Moves this ghost and updates location
    public void move() {
        //Go until wall hit, then pick new direction

        float[] newPos = Agents.move(direction, new float[] {x_coord, y_coord});

        // Check if a wall was hit
        if(x_coord==newPos[0] && y_coord==newPos[1]) {
            changeDirection();
        }

        //System.out.println("Before move: ("+x_coord+", "+y_coord+")");
        //System.out.println("After move: ("+newPos[0]+", "+newPos[1]+")");
        x_coord = newPos[0];
        y_coord = newPos[1];
    }

    // Returns this ghost's position as {x,y} array
    public float[] getPos() {
        return new float[] {x_coord, y_coord};
    }

    // Changes the direction if able
    public void changeDirection() {
        // Pick first action available
        for(int i = 0; i < actions.length; i++) {
            direction = actions[i];
            float[] newPos = Agents.move(direction, new float[] {x_coord, y_coord});
            if(x_coord!=newPos[0] || y_coord!=newPos[1]) {
                break;
            }
            x_coord = newPos[0];
            y_coord = newPos[1];
        }
    }
}
