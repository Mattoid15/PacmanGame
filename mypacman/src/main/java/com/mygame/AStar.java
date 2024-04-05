package com.mygame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;


class Node implements Comparable<Node> {
    int x, y; // Coordinates of the node
    int g;      // Cost from the start node to this node
    int h;      // Heuristic estimate from this node to the goal node
    Node parent; // Parent node in the path

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.g = 0;
        this.h = 0;
        this.parent = null;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.g + this.h, other.g + other.h);
    }
}

@SuppressWarnings("exports")
public class AStar {
    private static final int[][] MAZE = game.maze;

    public static int[] getNext(int startX, int startY, int goalX, int goalY) {
        List<Node> path = findPath(startX, startY, goalX, goalY);
        int[] val = new int[2];
        val[0] = startX;
        val[1] = startY;
        if (path != null) {
            for (Node node : path) {
                val[0] = (int) node.x;
                val[1] = (int) node.y;
                return val;
            }
        }
        return val;
    }
    
    public static List<Node> findPath(int startX, int startY, int goalX, int goalY) {
        Node startNode = new Node(startX, startY);
        Node goalNode = new Node(goalX, goalY);

        PriorityQueue<Node> openSet = new PriorityQueue<>();
        List<Node> closedSet = new ArrayList<>();

        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll();

            if (currentNode.x == goalNode.x && currentNode.y == goalNode.y) {
                // Path found, reconstruct and return it
                List<Node> path = new ArrayList<>();
                while (currentNode != null) {
                    path.add(currentNode);
                    currentNode = currentNode.parent;
                }
                Collections.reverse(path);
                return path;
            }

            closedSet.add(currentNode);

            for (Node neighbor : getNeighbors(currentNode)) {
                if (closedSet.contains(neighbor)) {
                    continue; // Skip already processed nodes
                }

                int tentativeG = currentNode.g + 1;

                if (!openSet.contains(neighbor) || tentativeG < neighbor.g) {
                    neighbor.parent = currentNode;
                    neighbor.g = tentativeG;
                    neighbor.h = calculateHeuristic(neighbor, goalNode);

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return null; // No Path Found
    }

    private static List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int newX = node.x + dir[0];
            int newY = node.y + dir[1];

            if (isValid(newX, newY) && MAZE[newX][newY] != 1) {
                neighbors.add(new Node(newX, newY));
            }
        }

        return neighbors;
    }

    private static boolean isValid(int x, int y) {
        return x >= 0 && x < MAZE.length && y >= 0 && y < MAZE[0].length;
    }

    private static int calculateHeuristic(Node current, Node goal) {
        // Using Manhattan distance heuristic
        return Math.abs(current.x-goal.x) + Math.abs(current.y-goal.y);
    }
}
