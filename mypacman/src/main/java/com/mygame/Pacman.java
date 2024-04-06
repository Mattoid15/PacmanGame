// Written by: Matthew Lingenfelter

package com.mygame;

import javafx.scene.input.KeyCode;

@SuppressWarnings("exports")
public class Pacman extends Agents {
    private float x_coord;
    private float y_coord;
    private float speed = 0.05f;
    private String direction;

    public Pacman() {
        direction = "West";
        x_coord = 5f;
        y_coord = 1f;
    }

    // Moves pacman and updates his location
    public void move() {
        float[] newPos = Agents.move(direction, new float[] {x_coord, y_coord}, speed);
        x_coord = newPos[0];
        y_coord = newPos[1];
    }

    // Returns pacman's position as {x,y} array
    public float[] getPos() {
        return new float[] {x_coord, y_coord};
    }

    // Changes the direction pacman is going based on the key pressed
    public void changeDirection(KeyCode code) {
        switch (code) {
            case UP:
                direction = "North";
                break;
            case DOWN:
                direction = "South";
                break;
            case LEFT:
                direction = "West";
                break;
            case RIGHT:
                direction = "East";
                break;
            default:
                break;
        }
    }

    // Returns the distance pacman is from a given location
    //public float getDistance(float x, float y) {
        // gets the Manhattan distance
    //    return Math.abs(x_coord - x) + Math.abs(y_coord - y);
    //}
}
