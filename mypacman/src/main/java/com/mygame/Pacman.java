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
    public int ghostsEaten = 0;
    public int livesLeft = 3;
    public boolean wasHit = false;

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

    // Returns pacman to the starting location
    // Used to respawn
    public void resetPos() {
        direction = "West";
        x_coord = 14f;
        y_coord = 23f;
        livesLeft--;
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
    public boolean eating(int[][] food, Ghosts[] ghosts) {
        if(food[(int)y_coord][(int)x_coord] == 1) {
            food[(int)y_coord][(int)x_coord] = 0;
            updateScore(10);
        } else if (food[(int)y_coord][(int)x_coord] == 2) {
            food[(int)y_coord][(int)x_coord] = 0;
            updateScore(50);
            // set ghosts to be scared
            // Eating Ghost
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

    // Method that returns true if pacman hits a not scared ghost
    public boolean gotHit(Ghosts[] ghosts) {
        // For each ghost
        for(int i = 0; i < ghosts.length; i++) {
            // get ghost position
            float[] ghostPos = ghosts[i].getPos();
            // Checks if in contact with ghost
            if((int)x_coord == (int)ghostPos[0] && (int)y_coord == (int)ghostPos[1]) {
                // check if ghost is scared
                if(ghosts[i].isScared) {
                    // update score
                    ghostsEaten++;
                    switch (ghostsEaten) {
                        case 1:
                            updateScore(200);
                            break;
                        case 2:
                            updateScore(400);
                            break;
                        case 3:
                            updateScore(800);
                            break;
                        case 4:
                            updateScore(1600);
                            break;
                        default:
                            break;
                    }
                    // set ghost to not scared
                    ghosts[i].setScared(false);
                    // move ghost to start position
                    ghosts[i].setPos(14f, 14f);
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }
}
