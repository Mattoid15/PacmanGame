package com.example;

public class Pacman extends Agents {
    private float x_coord;
    private float y_coord;
    private float speed = 0.2f;
    private String direction;

    public Pacman() {
        direction = "East";
        x_coord = 1f;
        y_coord = 1f;
    }

    public void move() {
        float[] newPos = Agents.move(direction, new float[] {x_coord, y_coord});
        x_coord = newPos[0];
        y_coord = newPos[1];
    }

    // Returns pacman's current direction
    public String getDirection() {
        return direction;
    }
    
    // Returns pacman's position as {x,y} array
    public float[] getPos() {
        return new float[] {x_coord, y_coord};
    }
}
