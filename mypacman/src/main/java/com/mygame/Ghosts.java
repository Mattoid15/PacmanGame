// Written by: Matthew Lingenfelter

package com.mygame;

public class Ghosts {
    private float x_coord;
    private float y_coord;
    private float speed = 0.025f;
    private String direction;
    private String[] actions = {"North", "South", "East", "West"};
    private String[] oppActions = {"South", "North", "West", "East"};
    private static float tollerance = 0.1f;
    private boolean inJail = true;
    private String name = "";

    public Ghosts(String n, float x, float y) {
        name = n;
        direction = "North";
        x_coord = x;
        y_coord = y;
    }

    // Moves this ghost and updates location
    public void move(Pacman pacMan) {
        float[] pacPos = pacMan.getPos();
        // Check if ghost is in jail
        checkJail();
        if(inJail) {
            direction = "North";
            float[] newPos = Agents.move(direction, new float[] {x_coord, y_coord}, speed);
            x_coord = newPos[0];
            y_coord = newPos[1];
            return;
        }

        int possibleMoves = 0;

        // Count the number of moves the ghost can make
        if(!Wall.isWall((int)x_coord, ((int)y_coord)+1)) {
            if(x_coord >= ((int)x_coord)-tollerance && x_coord <= ((int)x_coord)+tollerance) {
                possibleMoves++;
            }
        }
        if(!Wall.isWall((int)x_coord, ((int)y_coord)-1)) {
            if(x_coord >= ((int)x_coord)-tollerance && x_coord <= ((int)x_coord)+tollerance) {
                possibleMoves++;
            }
        }
        if(!Wall.isWall(((int)x_coord)+1, (int)y_coord)) {
            if(y_coord >= ((int)y_coord)-tollerance && y_coord <= ((int)y_coord)+tollerance) {
                possibleMoves++;
            }
        }
        if(!Wall.isWall(((int)x_coord)-1, (int)y_coord)) {
            if(y_coord >= ((int)y_coord)-tollerance && y_coord <= ((int)y_coord)+tollerance) {
                possibleMoves++;
            }
        }

        // Check if a wall was hit, or at an intersection
        float[] newPos = Agents.move(direction, new float[] {x_coord, y_coord}, speed);
        if((x_coord==newPos[0] && y_coord==newPos[1]) || possibleMoves>=3) {
            switch (name) {
                case "Blinky":
                    Blinky(pacPos);
                    break;
                case "Pinky":
                    Pinky(pacPos, pacMan.getDirection());
                    break;
                case "Inky":
                    Inky(pacPos, pacMan.getDirection());
                    break;
                case "Clyde":
                    Clyde(pacPos);
                default:
                    //Blinky(pacPos);
                    break;
            }
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

    public String getName() {
        return name;
    }
    // Checks if this ghost is currently in jail
    private void checkJail() {
        if((x_coord >= 11f && x_coord <= 16f)&&(y_coord >= 12f && y_coord <= 16f)) {
            inJail = true;
        } else {
            inJail = false;
        }
    }

    // Picks the direction that moves towards pacman
    // Blinky algorithm - chases pacman directly
    public void Blinky(float[] pacPos) {
        // Sets the location to track to pacman's exact location
        float[] track = new float[] {pacPos[0], pacPos[1]};

        String bestAction = getBestAction(track);

        direction = bestAction;
        float[] newPos = Agents.move(direction, new float[] {x_coord, y_coord}, speed);
        x_coord = newPos[0];
        y_coord = newPos[1];
    }

    // Inky algorithm - tries to get behind pacman
    public void Inky(float[] pacPos, String pacDir) {
        // Sets the location to track to the location behind pacman
        float[] track = new float[] {pacPos[0], pacPos[1]};
        switch (pacDir) {
            case "North":
            track[1] +=1;
                break;
            case "South":
            track[1] -=1;
                break;
            case "East":
            track[0] -=1;
                break;
            case "West":
            track[0] +=1;
                break;
        }
        String bestAction = getBestAction(track);
        direction = bestAction;
        float[] newPos = Agents.move(direction, new float[] {x_coord, y_coord}, speed);
        x_coord = newPos[0];
        y_coord = newPos[1];
    }

    // Pinky algorithm - tries to get infront of pacman
    public void Pinky(float[] pacPos, String pacDir) {
        // Sets the location to track to in front of pacman
        float[] track = new float[] {pacPos[0], pacPos[1]};
        switch (pacDir) {
            case "North":
            track[1] -=1;
                break;
            case "South":
            track[1] +=1;
                break;
            case "East":
            track[0] +=1;
                break;
            case "West":
            track[0] -=1;
                break;
        }
        String bestAction = getBestAction(track);
        direction = bestAction;
        float[] newPos = Agents.move(direction, new float[] {x_coord, y_coord}, speed);
        x_coord = newPos[0];
        y_coord = newPos[1];
    }

    // Clyde alorithm - moves towards pacman, until pacman is within 8 tiles
    // Then tries to get to the lower left corner
    public void Clyde(float[] pacPos) {
        float distance = Math.abs(x_coord - pacPos[0]) + Math.abs(y_coord - pacPos[1]);
        String bestAction = direction;
        if(distance > 8f) {
            bestAction = getBestAction(pacPos);
        } else {
            bestAction = getBestAction(new float[] {1f, 29f});
        }
        direction = bestAction;
        float[] newPos = Agents.move(direction, new float[] {x_coord, y_coord}, speed);
        x_coord = newPos[0];
        y_coord = newPos[1];
    }


    // Helper function that returns the action that results in moving closer to the given location "track"
    private String getBestAction(float[] track) {
        String bestAction = direction;
        float bestDist = 99999999f;

        for(int i = 0; i < actions.length; i++) {
            float[] newPos = Agents.move(actions[i], new float[] {x_coord, y_coord}, speed);

            if(x_coord!=newPos[0] || y_coord!=newPos[1]) {
                float distance = Math.abs(newPos[0] - track[0]) + Math.abs(newPos[1] - track[1]);
                if(distance < bestDist && direction != oppActions[i]) {
                    bestDist = distance;
                    bestAction = actions[i];
                }
            }
        }
        return bestAction;
    }

    public float[] getTrackingPosition(float[] pacPos, String pacDir) {
        float[] track = new float[] {pacPos[0], pacPos[1]};
        if(name=="Inky") {
            switch (pacDir) {
                case "North":
                track[1] +=1;
                    break;
                case "South":
                track[1] -=1;
                    break;
                case "East":
                track[0] -=1;
                    break;
                case "West":
                track[0] +=1;
                    break;
                default:
                    break;
            }
        } else {
            switch (pacDir) {
                case "North":
                track[1] -=1;
                    break;
                case "South":
                track[1] +=1;
                    break;
                case "East":
                track[0] +=1;
                    break;
                case "West":
                track[0] -=1;
                    break;
            }
        }
        return track;
    }
}
