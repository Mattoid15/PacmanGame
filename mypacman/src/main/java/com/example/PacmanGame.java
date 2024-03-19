package com.example;

public class PacmanGame {
    public static void main(String[] args) {
        // Initialize game objects
        //gameboard
        Pacman pacman = new Pacman();
        boolean gameOver = false;

        // Game loop
        while (!gameOver) {
            pacman.move();
            gameOver = true;
        }
    }
}
