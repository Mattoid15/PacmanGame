// Written by: Matthew Lingenfelter

package com.mygame;

//@SuppressWarnings("exports")
public class Ghosts {
    private float x_coord;
    private float y_coord;
    private String direction;

    public Ghosts() {
        direction = "East";
        x_coord = 1f;
        y_coord = 5f;
    }

    // Moves this ghost and updates location
    public void move() {
        float[] newPos = Agents.move(direction, new float[] {x_coord, y_coord});
        x_coord = newPos[0];
        y_coord = newPos[1];
    }

    // Returns this ghost's position as {x,y} array
    public float[] getPos() {
        return new float[] {x_coord, y_coord};
    }
}
