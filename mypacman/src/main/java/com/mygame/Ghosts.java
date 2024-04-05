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
    public void move(float[] pacPos) {
        //Go until wall hit, then pick new direction

        float[] newPos = Agents.move(direction, new float[] {x_coord, y_coord});

        // Check if a wall was hit
        if(x_coord==newPos[0] && y_coord==newPos[1]) {
            changeDirection(pacPos);
        }

        x_coord = newPos[0];
        y_coord = newPos[1];
    }

    // Returns this ghost's position as {x,y} array
    public float[] getPos() {
        return new float[] {x_coord, y_coord};
    }

    // Changes the direction if able
    public void changeDirection(float[] pacPos) {
        String bestAction = direction;
        float closestDist = 99999f;

        for(int i = 0; i < actions.length; i++) {
            direction = actions[i];
            float[] newPos = Agents.move(direction, new float[] {x_coord, y_coord});

            if(x_coord!=newPos[0] || y_coord!=newPos[1]) {
                float distance = Math.abs(newPos[0] - pacPos[0]) + Math.abs(newPos[1] - pacPos[1]);
                if(distance < closestDist) {
                    closestDist = distance;
                    bestAction = actions[i];
                }
            }
        }
        direction = bestAction;
        float[] newPos = Agents.move(direction, new float[] {x_coord, y_coord});
        x_coord = newPos[0];
        y_coord = newPos[1];
    }
}
