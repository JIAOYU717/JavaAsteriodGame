package com.example.demo3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.util.Duration;



public class AsteroidsGame extends Application {
    private Stage primaryStage;
    private TextField playerNameField;




    public GameLogic gameLoop = new GameLogic();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showMainMenu();
    }


    private void showMainMenu() {
        Label title = new Label("Asteroids");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 42));
        title.setTextFill(Color.web("#ffffff"));

        Button startButton = new Button("Start");
        startButton.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        Button controlsButton = new Button("Controls");
        controlsButton.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        Button highScoresButton = new Button("High Scores");
        highScoresButton.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        Button exitButton = new Button("Exit");
        exitButton.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        startButton.setOnAction(event -> {
            showEnterNameScreen();
        });

        controlsButton.setOnAction(event -> {
            showControlsScreen();
        });

        highScoresButton.setOnAction(event -> {
            showHighScores();
        });

        exitButton.setOnAction(event -> {
            primaryStage.close();
        });

        VBox menuLayout = new VBox(10);
        menuLayout.getChildren().addAll(title, startButton, controlsButton, highScoresButton, exitButton);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setStyle("-fx-background-color: #000000;");

        Scene menuScene = new Scene(menuLayout, 1280, 832);
        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Asteroids");
        primaryStage.show();
    }

    private void showControlsScreen() {
        Label controlsLabel = new Label("Controls\n");
        controlsLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 48));
        controlsLabel.setTextFill(Color.WHITE);

        Label controlsText = new Label("The aim of the game is to avoid Asteroids and survive as long " +
                "as possible in a series of increasingly difficult levels!\n\n" +
                "LEFT ARROW KEY: Press the left arrow key to rotate the ship anti-clockwise.\n\n" +
                "RIGHT ARROW KEY: Press the right arrow key to rotate the ship clockwise.\n\n" +
                "UP ARROW KEY: Press the up arrow key to apply thrust and move forward.\n\n" +
                "SPACE: Press the space bar to shoot at enemies.\n\n" +
                "C KEY: Press the C key to perform a hyperjump, which randomly teleports you and provides brief immunity.\n\n" +
                "L KEY: Press the L key to spend 10,000 points and replenish a life.\n\n\n\n");
        controlsText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        controlsText.setTextFill(Color.WHITE);

        Button startButton = new Button("Start Game");
        startButton.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        startButton.setOnAction(event -> {
            showEnterNameScreen();
        });

        Button backButton = new Button("Back to Main Menu");
        backButton.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        backButton.setOnAction(event -> {
            showMainMenu();
        });

        VBox buttonLayout = new VBox(20);
        buttonLayout.getChildren().addAll(startButton, backButton);
        buttonLayout.setAlignment(Pos.CENTER);

        VBox controlsLayout = new VBox(10);
        controlsLayout.getChildren().addAll(controlsLabel, controlsText, buttonLayout);
        controlsLayout.setAlignment(Pos.CENTER);
        controlsLayout.setStyle("-fx-background-color: #000000;");

        Scene controlsScene = new Scene(controlsLayout, 1280, 832);
        primaryStage.setScene(controlsScene);
        primaryStage.setTitle("Controls");
    }
    private void showHighScores() {

        List<String> highScores = readHighScores("high_scores.txt");

        VBox highScoresLayout = new VBox(10);
        highScoresLayout.setAlignment(Pos.CENTER);
        highScoresLayout.setStyle("-fx-background-color: #000000;");

        Label highScoresTitle = new Label("High Scores");
        highScoresTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        highScoresTitle.setTextFill(Color.web("#ffffff"));

        highScoresLayout.getChildren().add(highScoresTitle);

        for (String score : highScores) {
            Label scoreLabel = new Label(score);
            scoreLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
            highScoresLayout.getChildren().add(scoreLabel);
        }

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            showMainMenu();
        });

        highScoresLayout.getChildren().add(backButton);

        Scene highScoresScene = new Scene(highScoresLayout, 1280, 832);
        primaryStage.setScene(highScoresScene);
        primaryStage.setTitle("High Scores");
    }

    private List<String> readHighScores(String fileName) {
        List<String> highScores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/" + fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                highScores.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return highScores;
    }
    private void showEnterNameScreen() {
        Label enterNameLabel = new Label("Enter Your Name");
        enterNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        enterNameLabel.setTextFill(Color.web("#ffffff"));

        playerNameField = new TextField();
        playerNameField.setPromptText("Player Name");
        playerNameField.setMaxWidth(240);

        Button submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        submitButton.setOnAction(event -> {
            // Save player's name
            String playerName = playerNameField.getText().trim();
            if (!playerName.isEmpty()) {
                showGame();
            }
        });

        VBox enterNameLayout = new VBox(10);
        enterNameLayout.getChildren().addAll(enterNameLabel, playerNameField, submitButton);
        enterNameLayout.setAlignment(Pos.CENTER);
        enterNameLayout.setStyle("-fx-background-color: #000000;");

        Scene enterNameScene = new Scene(enterNameLayout, 1280, 832);
        primaryStage.setScene(enterNameScene);
        primaryStage.setTitle("Enter Your Name");
    }



    private void showGame() {
        gameLoop.start(primaryStage);
    }
}
