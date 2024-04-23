// Written by: Matthew Lingenfelter

package com.mygame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@SuppressWarnings("exports")

public class MainMenu extends Application {
    public static boolean ShowTracking = true;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pacman Main Menu");
        MainScreen(primaryStage);
    }

    // Displays the main menu screen
    public static void MainScreen(Stage primaryStage) {
        // Create each button option
        Button startButton = new Button("Start Game");
        Button scoresButton = new Button("Show Scores");
        Button trackButton = new Button("Show Ghost Tracking: On");
        Button exitButton = new Button("Exit");

        // Set actions for the buttons
        startButton.setOnAction(e -> {
            // Add code to start the Pacman game
            System.out.println("Starting Game");
            PacmanGame.start(primaryStage);
        });
        scoresButton.setOnAction(e -> {
            // Add code to show options menu
            showScores(primaryStage);
        });
        trackButton.setOnAction(e -> {
            if(ShowTracking) {
                ShowTracking = false;
                trackButton.setText("Show Ghost Tracking: Off");
            } else {
                ShowTracking = true;
                trackButton.setText("Show Ghost Tracking: On");
            }
        });

        exitButton.setOnAction(e -> primaryStage.close());

        // Create the layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(startButton, scoresButton, trackButton, exitButton);
        layout.setAlignment(Pos.CENTER);

        // Create the scene
        Scene scene = new Scene(layout, 300, 200);
        
        // Set scence to stage
        primaryStage.setScene(scene);
        
        // Show stage
        primaryStage.show();
    }

    // Shows the top 10 scores
    public static void showScores(Stage primaryStage) {
        Button backButton = new Button("Back to Menu");
        Button resetButton = new Button("Reset Scores");
        Text text = new Text();
        text.setText("Previous Games");

        backButton.setOnAction(e -> {
            MainScreen(primaryStage);
        });

        // Clears the scores file, resetting previous scores
        resetButton.setOnAction(e -> {
            try {
                PrintWriter writer = new PrintWriter("scores");
                writer.print("");
                writer.close();
                showScores(primaryStage);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            
        });

        try {
            File file = new File("scores");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                text.setText(text.getText()+"\n"+data);
                System.out.println(data);
            }
            reader.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        // Create the layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(text, backButton, resetButton);
        layout.setAlignment(Pos.CENTER);

        // Create the scene
        Scene scene = new Scene(layout, 300, 200);
        
        // Set scence to stage
        primaryStage.setScene(scene);
        
        // Show stage
        primaryStage.show();
    }
}
