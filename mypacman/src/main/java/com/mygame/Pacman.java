// Written by: Matthew Lingenfelter

package com.mygame;

import javafx.scene.input.KeyCode;

@SuppressWarnings("exports")
public class Pacman extends Agents {
    private float x_coord;
    private float y_coord;
    private float speed = 0.05f;
    private String direction;
    private int score = 0;

    public Pacman() {
        direction = "West";
        x_coord = 14f;
        y_coord = 23f;
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

    // Method used to tell if pacman is eating food
    public boolean eating(int[][] food) {
        if(food[(int)y_coord][(int)x_coord] == 1) {
            food[(int)y_coord][(int)x_coord] = 0;
            updateScore(10);
        } else if (food[(int)y_coord][(int)x_coord] == 2) {
            food[(int)y_coord][(int)x_coord] = 0;
            updateScore(50);
            // set ghosts to be scared
            return true;
        }
        return false;
    }

    // Returns the current score pacman has
    public int getScore() {
        return score;
    }

    // Method used to update pacman's score
    public void updateScore(int x) {
        score += x;
    }

    // Returns pacman's current direction
    public String getDirection() {
        return direction;
    }
}
