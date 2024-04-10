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
    private boolean isScared = false;
    private String name = "";

    public Ghosts(String n, float x, float y) {
        name = n;
        direction = "North";
        x_coord = x;
        y_coord = y;
    }

    // Moves this ghost and updates location
    public void move(Pacman pacMan, Ghosts[] ghosts) {
        if(isScared) {
            scaredMove(ghosts);
            return;
        }

        float[] pacPos = pacMan.getPos();
        // Check if ghost is in jail
        checkJail();
        if(inJail) {
            direction = "North";
            float[] newPos = Agents.move(direction, new float[] {x_coord, y_coord}, speed);
            if(!Agents.checkCollition(ghosts, name, newPos)) {
                x_coord = newPos[0];
                y_coord = newPos[1];
            }
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
                    newPos = Blinky(pacPos);
                    break;
                case "Pinky":
                    newPos = Pinky(pacPos, pacMan.getDirection());
                    break;
                case "Inky":
                    newPos = Inky(pacPos, pacMan.getDirection());
                    break;
                case "Clyde":
                    newPos = Clyde(pacPos);
                    break;
                default:
                    System.out.println("Error occured finding ghost "+name+" alogrithm");
                    break;
            }
        }

        // Check if the next position has a ghost, 
        // If it does, don't move and flip directions
        // If not, continue the same direction
        if(!Agents.checkCollition(ghosts, name, newPos) && (newPos[0] >= 1 && newPos[1] < 27f)) {
            x_coord = newPos[0];
            y_coord = newPos[1];
        } else {
            for(int i = 0; i < actions.length; i++) {
                if(direction == actions[i]) {
                    direction = oppActions[i];
                    break;
                }
            }
        }
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

    // Blinky algorithm - chases pacman directly
    private float[] Blinky(float[] pacPos) {
        // Sets the location to track to pacman's exact location
        float[] track = new float[] {pacPos[0], pacPos[1]};

        String bestAction = getBestAction(track);

        direction = bestAction;
        float[] newPos = Agents.move(direction, new float[] {x_coord, y_coord}, speed);
        
        return newPos;
    }

    // Inky algorithm - tries to get behind pacman
    private float[] Inky(float[] pacPos, String pacDir) {
        // Sets the location to track to the location behind pacman
        float[] track = new float[] {pacPos[0], pacPos[1]};
        switch (pacDir) {
            case "North":
            track[1] +=2;
                break;
            case "South":
            track[1] -=2;
                break;
            case "East":
            track[0] -=2;
                break;
            case "West":
            track[0] +=2;
                break;
        }

        // Check if the location behind pacman is a wall
        // If it is, track pacman's actual location instead
        if((track[0]<=0||track[0]>Wall.getWalls()[0].length-1)||
            (track[1]<=0||track[1]>Wall.getWalls().length-1)||
            Wall.isWall((int)track[0],(int)track[1])){
//        if(Wall.isWall((int)track[0], (int)track[1])) {
            track[0] = pacPos[0];
            track[1] = pacPos[1];
        }

        String bestAction = getBestAction(track);
        direction = bestAction;
        float[] newPos = Agents.move(direction, new float[] {x_coord, y_coord}, speed);
        return newPos;
    }

    // Pinky algorithm - tries to get infront of pacman
    private float[] Pinky(float[] pacPos, String pacDir) {
        // Sets the location to track to in front of pacman
        float[] track = new float[] {pacPos[0], pacPos[1]};
        switch (pacDir) {
            case "North":
            track[1] -=2;
                break;
            case "South":
            track[1] +=2;
                break;
            case "East":
            track[0] +=2;
                break;
            case "West":
            track[0] -=2;
                break;
        }

        // Check if the location in front of pacman is a wall
        // If it is, track pacman's actual location instead
        if((track[0]<=0||track[0]>Wall.getWalls()[0].length-1)||
            (track[1]<=0||track[1]>Wall.getWalls().length-1)||
            Wall.isWall((int)track[0],(int)track[1])){

        //if(Wall.isWall((int)track[0],(int)track[1])) {
            track[0] = pacPos[0];
            track[1] = pacPos[1];
        }

        String bestAction = getBestAction(track);
        direction = bestAction;
        float[] newPos = Agents.move(direction, new float[] {x_coord, y_coord}, speed);
        return newPos;
    }

    // Clyde alorithm - moves towards pacman, until pacman is within 8 tiles
    // Then tries to get to the lower left corner
    private float[] Clyde(float[] pacPos) {
        float distance = Math.abs(x_coord - pacPos[0]) + Math.abs(y_coord - pacPos[1]);
        String bestAction = direction;
        if(distance > 8f) {
            bestAction = getBestAction(pacPos);
        } else {
            bestAction = getBestAction(new float[] {1f, 29f});
        }
        direction = bestAction;
        float[] newPos = Agents.move(direction, new float[] {x_coord, y_coord}, speed);
        return newPos;
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

    public void setScared(boolean s) {
        isScared = s;
    }

    public void scaredMove(Ghosts[] ghosts) {
        // Pick random direction to move when in a corner
        float[] newPos = Agents.move(direction, new float[] {x_coord, y_coord}, (speed*0.5f));
        if(x_coord==newPos[0] && y_coord==newPos[1]) {
            // pick new direction
            for(int i = 0; i < actions.length; i++) {
                direction = actions[i];
                newPos = Agents.move(direction, new float[] {x_coord, y_coord}, (speed*0.5f));
                if(x_coord!=newPos[0] || y_coord!=newPos[1]) {
                    break;
                }
            }
        }


        // Check if the next position has a ghost, 
        // If it does, don't move and flip directions
        // If not, continue the same direction
        if(!Agents.checkCollition(ghosts, name, newPos)) {
            x_coord = newPos[0];
            y_coord = newPos[1];
        } else {
            for(int i = 0; i < actions.length; i++) {
                if(direction == actions[i]) {
                    direction = oppActions[i];
                    break;
                }
            }
        }
    }

    public float[] trackLocation(Pacman pacMan) {
        float[] pacPos = pacMan.getPos();
        float[] tracking = new float[] {pacPos[0], pacPos[1]};
        switch (name) {
            case "Blinky":
                break;
            case "Pinky":
                switch (pacMan.getDirection()) {
                    case "North":
                        tracking[1] -=2;
                        break;
                    case "South":
                        tracking[1] +=2;
                        break;
                    case "East":
                        tracking[0] +=2;
                        break;
                    case "West":
                        tracking[0] -=2;
                        break;
                }
                if((tracking[0]<=0||tracking[0]>Wall.getWalls()[0].length-1)||
                    (tracking[1]<=0||tracking[1]>Wall.getWalls().length-1)||
                    Wall.isWall((int)tracking[0],(int)tracking[1])){
                    tracking[0] = pacPos[0];
                    tracking[1] = pacPos[1];
                }
                break;
            case "Inky":
                switch (pacMan.getDirection()) {
                    case "North":
                        tracking[1] +=2;
                        break;
                    case "South":
                        tracking[1] -=2;
                        break;
                    case "East":
                        tracking[0] -=2;
                        break;
                    case "West":
                        tracking[0] +=2;
                        break;
                }
                if((tracking[0]<=0||tracking[0]>Wall.getWalls()[0].length-1)||
                    (tracking[1]<=0||tracking[1]>Wall.getWalls().length-1)||
                    Wall.isWall((int)tracking[0],(int)tracking[1])){
//                if(Wall.isWall((int)tracking[0], (int)tracking[1])) {
                    tracking[0] = pacPos[0];
                    tracking[1] = pacPos[1];
                }
                break;
            case "Clyde":
                float distance = Math.abs(x_coord - pacPos[0]) + Math.abs(y_coord - pacPos[1]);
                if(distance <= 8f) {
                    tracking[0] = 1;
                    tracking[1] = 29;
                }
                break;
            default:
                System.out.println("Error occured finding ghost "+name+" alogrithm");
                break;
        }
        return tracking;
    }
}
