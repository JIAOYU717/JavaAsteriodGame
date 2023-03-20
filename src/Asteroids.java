package com.example.demo3;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class Asteroids extends Application{
    public static void main(String[] args) {
        try {
            Application.launch(args);
        }

        catch (Exception error) {
            error.printStackTrace();
        }
        finally {
            System.exit(0);
        }
    }
    public void start(Stage mainStage) //'mainStage' is the application window.
    {
        mainStage.setTitle("Asteroids"); //title of the application window.
        Group root = new Group();
        Scene mainScene = new Scene(root, 1280, 832, Color.BLACK); //a drawing surface
        mainStage.setHeight(832); //height when not in fullscreen mode
        mainStage.setWidth(1280); //width when not in fullscreen mode
        mainStage.setScene(mainScene);


        //Create the player Ship

        PlayerShip ship = new PlayerShip(800, 600);
//        Polygons shipThruster = new Polygons(new Double[]{-25.0, 0.0, -12.0, 5.0, -7.0, 0.0, -12.0, -5.0},
//                Color.YELLOW, Color.RED, 1, 0, ship.getTranslateX(), ship.getTranslateY());
//        Create an asteroid instance

        Asteroid mediumAsteroid = new Asteroid(100,100, 1.0);
        root.getChildren().add(mediumAsteroid.getPolygon());

        Asteroid largeAsteroid = new Asteroid(200,300, 2.0);
        root.getChildren().add(largeAsteroid.getPolygon());

        Asteroid smallAsteroid = new Asteroid(400,600, 0.5);
        root.getChildren().add(smallAsteroid.getPolygon());

        Bullet bullet = new Bullet(300, 400);
        root.getChildren().add(bullet.getPolygon());
        bullet.applyAcceleration(30.0);

        //Create Alien spaceship
//        Polygons alienShipBody = new Polygons(new Double[] {-30.0,0.0,
//                -10.0,10.0,
//                10.0,10.0,
//                30.0,0.0,
//                10.0,-10.0,
//                -10.0,-10.0},
//                Color.BLACK, Color.WHITE, 1,20);
//
//        Polygons alienShipCanopy = new Polygons(new Double[] {-10.0, 10.0,
//                -10.0, 15.0,
//                0.0, 20.0,
//                10.0, 15.0,
//                10.0, 10.0},
//                Color.CYAN, Color.WHITE, 1,2);


        smallAsteroid.applyAcceleration(0.8);
        smallAsteroid.rotLeft();


        mediumAsteroid.applyAcceleration(0.4);
        mediumAsteroid.rotLeft();


        largeAsteroid.applyAcceleration(0.2);
        largeAsteroid.rotRight();

        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();
        mainScene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });

        mainScene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });

        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long l) {
                if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                    ship.rotLeft();
                }
                if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                    ship.rotRight();
                }

                if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
                        ship.applyAcceleration(0.05);
                }
                ship.applyMove(1280,832);
                smallAsteroid.applyMove(1280,832);
                mediumAsteroid.applyMove(1280,832);
                largeAsteroid.applyMove(1280,832);
                bullet.applyMove(1280, 832);

                if (ship.collision(smallAsteroid)){
                    stop();
                }
                if (ship.collision(mediumAsteroid)){
                    stop();
                }
                if (ship.collision(largeAsteroid)){
                    stop();
                }
            }
        };

        root.getChildren().add(ship.getPolygon());
        timer.start();

        //Show application window
        mainStage.show();
    }
}