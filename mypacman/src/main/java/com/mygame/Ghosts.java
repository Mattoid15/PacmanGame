// Written by: Matthew Lingenfelter

package com.mygame;

import javafx.css.Style;

public class Ghosts {
    private float x_coord;
    private float y_coord;
    private float speed = 0.025f;
    private String direction;
    private String[] actions = {"North", "South", "East", "West"};
    private String[] oppActions = {"South", "North", "West", "East"};

    public Ghosts() {
        direction = "East";
        x_coord = 1f;
        y_coord = 5f;
    }

    // Moves this ghost and updates location
    public void move(float[] pacPos) {
        int possibleMoves = 0;

        // Count the number of moves the ghost can make
        //System.out.println("Currently: "+direction+" "+x_coord+" "+y_coord);
        //System.out.println("Rounding north: "+direction+" "+Math.round(x_coord)+" "+(int)(y_coord-speed));


        // Check if a wall was hit, or at an intersection
        float[] newPos = Agents.move(direction, new float[] {x_coord, y_coord}, speed);
        if((x_coord==newPos[0] && y_coord==newPos[1])){//} || possibleMoves<=1) {
            changeDirection(pacPos);
        }
        // If not, continue forward
        x_coord = newPos[0];
        y_coord = newPos[1];
    }

    // Returns this ghost's position as {x,y} array
    public float[] getPos() {
        return new float[] {x_coord, y_coord};
    }

    // Returns this ghost's direction
    public String getDir() {
        return direction;
    }

    // Picks the direction that moves towards pacman
    public void changeDirection(float[] pacPos) {
        String bestAction = direction;
        float closestDist = 99999f;

        for(int i = 0; i < actions.length; i++) {
            float[] newPos = Agents.move(actions[i], new float[] {x_coord, y_coord}, speed);

            if(x_coord!=newPos[0] || y_coord!=newPos[1]) {
                float distance = Math.abs(newPos[0] - pacPos[0]) + Math.abs(newPos[1] - pacPos[1]);
                if(distance < closestDist) {
                    closestDist = distance;
                    bestAction = actions[i];
                }
            }
        }

        direction = bestAction;
        float[] newPos = Agents.move(direction, new float[] {x_coord, y_coord}, speed);
        x_coord = newPos[0];
        y_coord = newPos[1];
    }
}
