package com.example.demo3;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
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

        PlayerShip ship = new PlayerShip(600, 400);
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

        Random randomx = new Random();
        Random randomy = new Random();
        Asteroid LargeAsteroid = new Asteroid(randomx.nextInt(1280),randomy.nextInt(832),2);
        double asteroidAngle = randomx.nextFloat(360);
        LargeAsteroid.asteroid.setRotation(asteroidAngle);
        LargeAsteroid.applyAcceleration(1);
        asteroidList.add(LargeAsteroid);

//        for (int i = 0; i < 10; i++) {
//            Random random = new Random();
//            List<Double> asteroidSizes = new ArrayList<>();
//            asteroidSizes.add(0.5); //for small asteroid
//            asteroidSizes.add(1.0); //for medium asteroid
//            asteroidSizes.add(2.0); //for large asteroid
//            int randomSize = random.nextInt(3);
//            Asteroid randomAsteroid = new Asteroid(random.nextInt(1280), random.nextInt(832), asteroidSizes.get(randomSize));
//
//            //smaller asteroid need to go faster
//            double asteroidAcceleration;
//            double asteroidScale = randomAsteroid.asteroid.scale;
//            if (asteroidScale == 0.5){
//                asteroidAcceleration = 3;
//            } else if (asteroidScale == 1.0) {
//                asteroidAcceleration = 2;
//            } else {asteroidAcceleration = 1;};
//
//            double asteroidAngle = random.nextFloat(360);
//            randomAsteroid.asteroid.setRotation(asteroidAngle);
//            randomAsteroid.applyAcceleration(asteroidAcceleration);
//            asteroidList.add(randomAsteroid);
//        }


        List<Bullet> bulletList = new ArrayList<>();

        // Hashmaps store items in key-value pairs.
        // here we keep track of when a key is pressed and when it is released
        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

        AtomicBoolean actionToBePerformed = new AtomicBoolean(false);

        mainScene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });

        mainScene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });

        Text youDiedText = new Text("YOU DIED");
        youDiedText.setFont(new Font(40));
        youDiedText.setFill(Color.WHITE);
        youDiedText.setTranslateX(575);
        youDiedText.setTranslateY(380);

        AtomicInteger score = new AtomicInteger(0);

        Text genPurText = new Text("Score: " + score);
        genPurText.setFont(new Font(40));
        genPurText.setFill(Color.WHITE);
        genPurText.setTranslateX(30);
        genPurText.setTranslateY(40);
        root.getChildren().add(genPurText);

        TextClass debugText = new TextClass("TESTING", 600, 40, Color.WHITE, 20);

        root.getChildren().add(debugText.mytext);

        TextClass level = new TextClass("Level 1", 1100, 40, Color.WHITE, 30);
        AtomicInteger gameLevel = new AtomicInteger(1);

        root.getChildren().add(level.mytext);




        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long l) {
                level.SetText("Level " + gameLevel.get());

                if (asteroidList.isEmpty()){
                    gameLevel.set(gameLevel.get() + 1);

                    for (int j = 0; j < gameLevel.get(); j++){

                        Random randomx = new Random();
                        Random randomy = new Random();
                        Asteroid LargeAsteroid = new Asteroid(randomx.nextInt(1280),randomy.nextInt(832),2);
                        LargeAsteroid.applyAcceleration(1);
                        double asteroidAngle = randomx.nextFloat(360);
                        LargeAsteroid.asteroid.getPolygon().setRotate(asteroidAngle);
                        asteroidList.add(LargeAsteroid);
                    }
                }

                debugText.SetText("dvx: " + ship.ship.dvx + "\n dvy: " + ship.ship.dvy + "\n move: " + ship.ship.move + "\n alive: " + ship.alive);

                if (pressedKeys.getOrDefault(KeyCode.ENTER, false) && ship.isAlive() == false) {
                    ship.ship.getPolygon().setTranslateX(600);
                    ship.ship.getPolygon().setTranslateY(400);
                    ship.ship.halt();
                    ship.ship.setRotation(0);
                    ship.alive = true;
                    root.getChildren().add(ship.getPolygon());
                    root.getChildren().remove(youDiedText);
                }

                if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                    ship.rotLeft();
                }
                if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                    ship.rotRight();
                }

                if (pressedKeys.getOrDefault(KeyCode.C, false)) {
                    ship.hyperjump();
                }


                if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
                    ship.applyAcceleration(0.06);
                }
                mainScene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
                    if (actionToBePerformed.get() == false && keyEvent.getCode() == KeyCode.SPACE && bulletList.size() < 7)
                    {
                        Bullet bullet = new Bullet(ship.getPolygon().getTranslateX(), ship.getPolygon().getTranslateY());
                        bullet.bullet.setRotation(ship.getPolygon().getRotate());
                        bullet.applyAcceleration(10.0);
                        bulletList.add(bullet);
                        root.getChildren().add(bullet.getPolygon());

                        Timeline timeToLive = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                            bulletList.remove(bullet);
                            root.getChildren().remove(bullet.getPolygon());
                        }));
                        timeToLive.play();
                        actionToBePerformed.set(true);
                    }
                });

                mainScene.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {
                    if ( actionToBePerformed.get() == true && keyEvent.getCode() == KeyCode.SPACE) {
                        actionToBePerformed.set(false);
                    }
                });


                ship.applyMove(1280,832);
                asteroidList.forEach(asteroid -> asteroid.applyMove(1280, 832));
                asteroidList.forEach(asteroid -> {

                    //player is immune for the first 2 seconds, so that if an asteroid
                    //spawns close to the player, they can move away quickly
                    Timeline immune = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                        if (ship.collision(asteroid)) {
                            ship.alive = false;
                            root.getChildren().remove(ship.getPolygon());
                            root.getChildren().add(youDiedText);
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

                    if (collided.asteroid.scale == 2.0) {
                        score.set(score.get() + 500);
                        genPurText.setText("Score: " + score);
                        for (int i = 0; i < 2; i++) {
                            Asteroid mediumAsteroid = new Asteroid(collided.getPolygon().getTranslateX(), collided.getPolygon().getTranslateY(), 1);
                            double asteroidAngle = collided.asteroid.getAngle() + (-90 * i + 45);
                            mediumAsteroid.asteroid.setRotation(asteroidAngle);
                            mediumAsteroid.applyAcceleration(2);
                            asteroidList.add(mediumAsteroid);
                            root.getChildren().add(mediumAsteroid.getPolygon());
                        }}

                    else if (collided.asteroid.scale == 1.0) {
                        score.set(score.get() + 750);
                        genPurText.setText("Score: " + score);
                        for (int i = 0; i < 2; i++) {
                            Asteroid smallAsteroid = new Asteroid(collided.getPolygon().getTranslateX(), collided.getPolygon().getTranslateY(), 0.5);
                            double asteroidAngle = collided.asteroid.getAngle() + (-90 * i + 45);
                            smallAsteroid.asteroid.setRotation(asteroidAngle);
                            smallAsteroid.applyAcceleration(2);
                            asteroidList.add(smallAsteroid);
                            root.getChildren().add(smallAsteroid.getPolygon());
                        }
                        } else {
                            score.set(score.get() + 1000);
                            genPurText.setText("Score: " + score);
                        }
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