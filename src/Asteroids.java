package com.example.demo3;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

import java.util.*;
import java.util.stream.Collectors;

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


        List<Asteroid> asteroidList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            List<Double> asteroidSizes = new ArrayList<>();
            asteroidSizes.add(0.5); //for small asteroid
            asteroidSizes.add(1.0); //for medium asteroid
            asteroidSizes.add(2.0); //for large asteroid
            int randomSize = random.nextInt(3);
            Asteroid randomAsteroid = new Asteroid(random.nextInt(1280), random.nextInt(832), asteroidSizes.get(randomSize));

            //smaller asteroid need to go faster
            double asteroidAcceleration;
            double asteroidScale = randomAsteroid.asteroid.scale;
            if (asteroidScale == 0.5){
                asteroidAcceleration = 3;
            } else if (asteroidScale == 1.0) {
                asteroidAcceleration = 2;
            } else {asteroidAcceleration = 1;};

            double asteroidAngle = random.nextFloat(360);
            randomAsteroid.asteroid.setRotation(asteroidAngle);
            randomAsteroid.applyAcceleration(asteroidAcceleration);
            asteroidList.add(randomAsteroid);
        }


        List<Bullet> bulletList = new ArrayList<>();

        // Hashmaps store items in key-value pairs.
        // here we keep track of when a key is pressed and when it is released
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

                if (pressedKeys.getOrDefault(KeyCode.SPACE, false) && bulletList.size() < 30) {
                    Bullet bullet = new Bullet(ship.getPolygon().getTranslateX(), ship.getPolygon().getTranslateY());

                    bullet.bullet.setRotation(ship.getPolygon().getRotate());
                    bullet.applyAcceleration(10.0);
                    bulletList.add(bullet);
                    root.getChildren().add(bullet.getPolygon());

                    Timeline timeToLive = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                        bulletList.remove(bullet);
                        root.getChildren().remove(bullet.getPolygon());
                    }));
                    timeToLive.play();

                }

                ship.applyMove(1280,832);

                asteroidList.forEach(asteroid -> asteroid.applyMove(1280, 832));

                asteroidList.forEach(asteroid -> {

                    //player is immune for the first 2 seconds, so that if an asteroid
                    //spawns close to the player, they can move away quickly
                    Timeline immune = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                        if (ship.collision(asteroid)) {
                            stop();
                        }
                    }));
                    immune.play();

                });

                bulletList.forEach(bullet -> {
                    bullet.applyMove(1280, 832);

                });

                List<Bullet> bulletsToRemove = bulletList.stream().filter(bullet -> {
                    List<Asteroid> collides = asteroidList.stream().filter(asteroid -> asteroid.collision(bullet))
                            .collect(Collectors.toList());

                    if (collides.isEmpty()) {
                        return false;
                    }

                    collides.stream().forEach(collided -> {
                        asteroidList.remove(collided);
                        root.getChildren().remove(collided.getPolygon());
                    });
                    return true;
                }).collect(Collectors.toList());

                bulletsToRemove.forEach(bullet -> {
                    bulletList.remove(bullet);
                    root.getChildren().remove(bullet.getPolygon());
                });
            }
        };

        root.getChildren().add(ship.getPolygon());
        asteroidList.forEach(asteroid -> root.getChildren().add(asteroid.getPolygon()));
        timer.start();

        //Show application window
        mainStage.show();
    }
}