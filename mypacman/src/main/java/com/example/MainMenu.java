package com.example;

//import javax.script.ScriptEngineFactory;

import javafx.application.Application;
import javafx.geometry.Pos;
//import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
//import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
//import javafx.scene.control.Menu;
//import javafx.scene.control.MenuBar;

@SuppressWarnings("exports")
public class MainMenu extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pacman Main Menu");

        // Creates the buttons
        Button startButton = new Button("Start Game");
        Button optionsButton = new Button("Options");
        Button exitButton = new Button("Exit");

        // Set actions for the buttons
        startButton.setOnAction(e -> {
            // Add code to start the Pacman game
            System.out.println("Starting Game");
            game.start(primaryStage);
        });

        optionsButton.setOnAction(e -> {
            // Add code to show options menu
            System.out.println("Opening Options Menu");
        });

        exitButton.setOnAction(e -> primaryStage.close());

        // Create the layout
        //StackPane layout = new StackPane();
        VBox layout = new VBox(10);
        layout.getChildren().addAll(startButton, optionsButton, exitButton);
        layout.setAlignment(Pos.CENTER);

        // Create the scene
        Scene scene = new Scene(layout, 300, 200);

        // Set scene to stage
        primaryStage.setScene(scene);

        // Show stage
        primaryStage.show();

        /*// Creates menu items
        MenuItem fileOpenItem = new MenuItem("Open");
        MenuItem fileSaveItem = new MenuItem("Save");
        MenuItem fileExitItem = new MenuItem("Exit");

        // Creates file menu
        Menu fileMenu = new Menu("File");
        fileMenu.getItems().addAll(fileOpenItem, fileSaveItem, fileExitItem);

        // Creates edit menu
        Menu editMenu = new Menu("Edit");
        // Add edit menu items if needed

        // Creates menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, editMenu);

        // Create layout
        VBox vbox = new VBox(menuBar);

        // Create scene
        Scene scene = new Scene(vbox, 300, 200);

        // Set the scene
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();*/
    }   
    
}
