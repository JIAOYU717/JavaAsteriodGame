package com.example.demo3;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class GameLogic extends Application{

    // all access modifier can be changed appropriately later
     public PlayerShip ship;
     public PlayerShip life;

     public AlienShip alien;

     public List<AsteroidClass> asteroidList;
     public List<Bullet> bulletList;
     public List<PlayerShip> noOfLives;

     public List<AlienBullet> alienBulletList;

     public List<AlienShip> alienShipList;

     public long AlienBullettime = 0;





//这个方法可以写到工厂class里
    public AsteroidClass createLargeAsteroid ()
        {
        AsteroidClass LargeAsteroid = null;
        double positionx;
        double positiony;
        //Generate initial random asteroid
        Random randomBorder = new Random();
        Random randomx = new Random();
        Random randomy = new Random();
        int spawnSide = randomBorder.nextInt(4) + 1;
        if(spawnSide == 1){
            positionx = randomx.nextInt(60) - 60 ;
            positiony =  randomy.nextInt(832);
            LargeAsteroid  = (AsteroidClass) PolygonsFactory.createEntity(Polygons.PolygonType.LARGE_ASTEROID,positionx, positiony);
            double asteroidAngle = randomx.nextDouble() * 360;
            LargeAsteroid.asteroid.setRotation(asteroidAngle);
            LargeAsteroid.applyAcceleration(1);
        } else if(spawnSide == 2){
            positionx = randomx.nextInt(1280);
            positiony = randomy.nextInt(60) + 832;
            LargeAsteroid  = (AsteroidClass) PolygonsFactory.createEntity(Polygons.PolygonType.LARGE_ASTEROID,positionx, positiony);
            double asteroidAngle = randomx.nextDouble() * 360;
            LargeAsteroid.asteroid.setRotation(asteroidAngle);
            LargeAsteroid.applyAcceleration(1);
        } else if(spawnSide == 3){
            positionx = randomx.nextInt(60) + 1280;
            positiony = randomy.nextInt(832);
            LargeAsteroid  = (AsteroidClass) PolygonsFactory.createEntity(Polygons.PolygonType.LARGE_ASTEROID,positionx, positiony);
            double asteroidAngle = randomx.nextDouble() * 360;
            LargeAsteroid.asteroid.setRotation(asteroidAngle);
            LargeAsteroid.applyAcceleration(1);
        } else if(spawnSide == 4){
            positionx = randomx.nextInt(1280);
            positiony = randomy.nextInt(60) - 60;
            LargeAsteroid  = (AsteroidClass) PolygonsFactory.createEntity(Polygons.PolygonType.LARGE_ASTEROID,positionx, positiony);
            double asteroidAngle = randomx.nextDouble() * 360;
            LargeAsteroid.asteroid.setRotation(asteroidAngle);
            LargeAsteroid.applyAcceleration(1);

        }
        return LargeAsteroid;
    }


//alienShip.alienship.setRotation(0.0); 然后飞船就不见了
    public AlienShip createAlienShip ()
    {
        AlienShip alienShip= null;
        double positionx;
        double positiony;
        //Generate initial random asteroid
        Random randomBorder = new Random();
        Random randomx = new Random();
        Random randomy = new Random();
        int spawnSide = randomBorder.nextInt(4) + 1;
        if(spawnSide == 1){
            positionx = randomx.nextInt(60) - 60 ;
            positiony =  randomy.nextInt(832);
            alienShip  = (AlienShip) PolygonsFactory.createEntity(Polygons.PolygonType.ALIEN_SHIP,positionx, positiony);
            double asteroidAngle = randomx.nextDouble() * 360;
            alienShip.alienship.setRotation(asteroidAngle);
            alienShip.applyAcceleration(2);
        } else if(spawnSide == 2){
            positionx = randomx.nextInt(1280);
            positiony = randomy.nextInt(60) + 832;
            alienShip  = (AlienShip) PolygonsFactory.createEntity(Polygons.PolygonType.ALIEN_SHIP,positionx, positiony);
            double asteroidAngle = randomx.nextDouble() * 360;
            alienShip.alienship.setRotation(asteroidAngle);
            alienShip.applyAcceleration(2);
        } else if(spawnSide == 3){
            positionx = randomx.nextInt(60) + 1280;
            positiony = randomy.nextInt(832);
            alienShip  = (AlienShip) PolygonsFactory.createEntity(Polygons.PolygonType.ALIEN_SHIP,positionx, positiony);
            double asteroidAngle = randomx.nextDouble() * 360;
            alienShip.alienship.setRotation(asteroidAngle);
            alienShip.applyAcceleration(2);
        } else if(spawnSide == 4){
            positionx = randomx.nextInt(1280);
            positiony = randomy.nextInt(60) - 60;
            alienShip  = (AlienShip) PolygonsFactory.createEntity(Polygons.PolygonType.ALIEN_SHIP,positionx, positiony);
            double asteroidAngle = randomx.nextDouble() * 360;
            alienShip.alienship.setRotation(asteroidAngle);
            alienShip.applyAcceleration(2);

        }
        return alienShip;
    }


    public void start(Stage mainStage) //'mainStage' is the application window.
    {
        mainStage.setTitle("Asteroids"); //title of the application window.
        Group root = new Group();
        Scene mainScene = new Scene(root, 1280, 832, Color.BLACK); //a drawing surface
        mainStage.setHeight(832); //height when not in fullscreen mode
        mainStage.setWidth(1280); //width when not in fullscreen mode
        mainStage.setScene(mainScene);

        //Make player ship that is controllable by the player
//        ship = new PlayerShip(600, 400);
        ship = (PlayerShip) PolygonsFactory.createEntity(Polygons.PolygonType.Player_SHIP,600, 400);

        //Make a list that contain ass the Asteroids
        asteroidList = new ArrayList<>();

        asteroidList.add(createLargeAsteroid ());

        bulletList = new ArrayList<>();

        noOfLives = new ArrayList<>();

        alienBulletList = new ArrayList<>();

        alienShipList = new ArrayList<>();

        alienShipList.add(createAlienShip());





        for (int i = 0; i < 3; i++){
//            life = new PlayerShip(900 + 50 * i, 40);
            life = (PlayerShip) PolygonsFactory.createEntity(Polygons.PolygonType.Player_SHIP,900 + 50 * i, 40);
            life.ship.setRotation(-90);
            noOfLives.add(life);
            root.getChildren().add(life.getPolygon());
        }



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


        TextClass youDiedText = new TextClass("YOU DIED，press ENTER to revive", 575, 380, Color.WHITE, 40);
        root.getChildren().add(youDiedText.mytext);


        AtomicInteger score = new AtomicInteger(0);

        TextClass scoreText = new TextClass("Score: " + score, 30, 40, Color.WHITE, 40);
        root.getChildren().add(scoreText.mytext);

        TextClass level = new TextClass("Level 1", 1100, 50, Color.WHITE, 30);

        AtomicInteger gameLevel = new AtomicInteger(1);
        root.getChildren().add(level.mytext);

        TextClass gameOver = new TextClass("Game Over", 505, 380, Color.RED, 60);
        gameOver.mytext.setOpacity(0);
        root.getChildren().add(gameOver.mytext);




        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long l) {
                level.SetText("Level " + gameLevel.get());

                if (noOfLives.size() == 0) {
                    gameOver.mytext.setOpacity(1);
                }

                //下面是每个level初始化的逻辑，可以考虑抽象出来，不必要

                if (asteroidList.isEmpty()) {
                    gameLevel.set(gameLevel.get() + 1);


                    for (int j = 0; j < gameLevel.get(); j++) {
                        asteroidList.add(createLargeAsteroid());
                        alien = createAlienShip();
                        alienShipList.add(alien);
                    }
                    alienShipList.forEach(alienship -> root.getChildren().add(alienship.getPolygon()));

                    asteroidList.forEach(asteroid -> {
                        // applyMove 方法可以全部摘出来，asteroid.applyMove方法重复，这里删除
//                        asteroid.applyMove(1280, 832);
                        root.getChildren().add(asteroid.getPolygon());
                        //这里不需要冲突检测， 而且，这个是每个level初始化
                        if (ship.collision(asteroid) && noOfLives.size() != 0) {

                            ship.alive = false;
                            //remove life rather than ship here or the game will be stop
                            root.getChildren().remove(ship.getPolygon());
                        }
                    });
                }

                Timeline alienShipShow = new Timeline(new KeyFrame(Duration.seconds(10), event -> {

                    alienShipList.forEach(alien -> root.getChildren().add(alien.getPolygon()));
                }));
                alienShipShow.play();




//                if (alien.getAlive()==true && System.currentTimeMillis()-AlienBullettime > 2000 ){
//
//                    AlienBullet alienbullet = (AlienBullet) PolygonsFactory.createEntity(Polygons.PolygonType.ALIEN_BULLET, alien.getPolygon().getTranslateX(), alien.getPolygon().getTranslateY());
//                    alienbullet.setRotation(alien.getPolygon().getRotate());
//                    alienbullet.applyAcceleration(10.0);
//                    alienBulletList.add(alienbullet);
//
//                    root.getChildren().add(alienbullet.getPolygon());
//
//                    AlienBullettime = System.currentTimeMillis();
//
//                }






//                alienShipList.forEach(alienship -> root.getChildren().add(alienship.getPolygon()));



                if (!ship.alive && noOfLives.size() != 0) {
                        youDiedText.mytext.setOpacity(1);
                    } else {
                        youDiedText.mytext.setOpacity(0);
                    }


//这个是复活
                    if (pressedKeys.getOrDefault(KeyCode.ENTER, false) && !ship.isAlive() && noOfLives.size() != 0) {
                        ship.ship.getPolygon().setTranslateX(600);
                        ship.ship.getPolygon().setTranslateY(400);
                        ship.ship.halt();
                        ship.ship.setRotation(0);
                        ship.alive = true;
                        noOfLives.remove(noOfLives.size() - 1);
                        root.getChildren().add(ship.getPolygon());
                        root.getChildren().remove(life.getPolygon());
                        root.getChildren().remove(youDiedText);
                    }
                    if (pressedKeys.getOrDefault(KeyCode.LEFT, false) && ship.isAlive()) {
                        ship.rotLeft();
                    }
                    if (pressedKeys.getOrDefault(KeyCode.RIGHT, false) && ship.isAlive()) {
                        ship.rotRight();
                    }

                    if (pressedKeys.getOrDefault(KeyCode.C, false) && ship.isAlive()) {
                        ship.hyperjump();
                        asteroidList.forEach(asteroid -> {

                            //player is immune for the first 2 seconds, so that if an asteroid
                            //spawns close to the player, they can move away quickly
                            Timeline immune = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                                if (ship.isAlive() && ship.collision(asteroid)) {
                                    ship.alive = false;
                                    root.getChildren().remove(ship.getPolygon());
                                }
                            }));
                            immune.play();
                        });
                    }
                    ;

//&& ship.isAlive 是否冗余，尝试删除项目中的冗余代码

                    if (pressedKeys.getOrDefault(KeyCode.UP, false) && ship.isAlive()) {
                        ship.applyAcceleration(0.06);
                    }


                    mainScene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
                        if (!actionToBePerformed.get() && keyEvent.getCode() == KeyCode.SPACE && bulletList.size() < 7 && ship.isAlive()) {

//                        Bullet bullet = new Bullet(ship.getPolygon().getTranslateX(), ship.getPolygon().getTranslateY());

                            Bullet bullet = (Bullet) PolygonsFactory.createEntity(Polygons.PolygonType.BULLET, ship.getPolygon().getTranslateX(), ship.getPolygon().getTranslateY());
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
                        if (actionToBePerformed.get() && keyEvent.getCode() == KeyCode.SPACE) {
                            actionToBePerformed.set(false);
                        }
                    });


//更新所有成员位置，单独写成一个方法

                    ship.applyMove(1280, 832);

                    asteroidList.forEach(asteroid -> asteroid.applyMove(1280, 832));

                    asteroidList.forEach(asteroid -> {

                        //player is immune for the first 2 seconds, so that if an asteroid
                        //spawns close to the player, they can move away quickly
                        Timeline immune = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                            if (ship.isAlive() && ship.collision(asteroid)) {
//                            noOfLives.forEach(life -> root.getChildren().remove(life.getPolygon()));
                                root.getChildren().remove(noOfLives.get(noOfLives.size() - 1).getPolygon());
                                noOfLives.remove(noOfLives.get(noOfLives.size() - 1));
                                ship.alive = false;
                                root.getChildren().remove(ship.getPolygon());
                            }
                        }));
                        immune.play();
                    });

                     alienShipList.forEach(alienship -> alienship.applyMove(1280, 832));


                    bulletList.forEach(bullet -> {
                        bullet.applyMove(1280, 832);

                    });


                    List<Bullet> bulletsToRemove = bulletList.stream().filter(bullet -> {
//                    List<AsteroidClass> collides = asteroidList.stream().filter(asteroid -> asteroid.collision(bullet)).toList();
                        List<AsteroidClass> collides = asteroidList.stream()
                                .filter(asteroid -> asteroid.collision(bullet))
                                .collect(Collectors.toList());
//                            collect(Collectors.toList());

                        if (collides.isEmpty()) {
                            //这个return false 和下面的 return true 是干什么的？？？
                            return false;
                        }
//
                        collides.stream().forEach(collided -> {
                            asteroidList.remove(collided);
                            root.getChildren().remove(collided.getPolygon());

                            if (collided.polygonType == Polygons.PolygonType.LARGE_ASTEROID) {
                                score.set(score.get() + 500);
                                scoreText.SetText("Score: " + score);
                                for (int i = 0; i < 2; i++) {
//                          AsteroidClass mediumAsteroid = new AsteroidClass(collided.getPolygon().getTranslateX(), collided.getPolygon().getTranslateY(), 1);
                                    AsteroidClass mediumAsteroid = (AsteroidClass) PolygonsFactory.createEntity(Polygons.PolygonType.MEDIUM_ASTEROID, collided.getPolygon().getTranslateX(), collided.getPolygon().getTranslateY());
                                    //随机数好说，new一个随机数在这里加上就好了
                                    double asteroidAngle = collided.asteroid.getAngle() + (-90 * i + 45);
                                    mediumAsteroid.asteroid.setRotation(asteroidAngle);
                                    mediumAsteroid.applyAcceleration(2);
                                    asteroidList.add(mediumAsteroid);
                                    root.getChildren().add(mediumAsteroid.getPolygon());
                                }
                            }



                            else if (collided.polygonType == Polygons.PolygonType.MEDIUM_ASTEROID) {
                                score.set(score.get() + 750);
                                scoreText.SetText("Score: " + score);
                                for (int i = 0; i < 2; i++) {
//                            AsteroidClass smallAsteroid = new AsteroidClass(collided.getPolygon().getTranslateX(), collided.getPolygon().getTranslateY(), 0.5);
                                    AsteroidClass smallAsteroid = (AsteroidClass) PolygonsFactory.createEntity(Polygons.PolygonType.SMALL_ASTEROID, collided.getPolygon().getTranslateX(), collided.getPolygon().getTranslateY());
                                    double asteroidAngle = collided.asteroid.getAngle() + (-90 * i + 45);
                                    smallAsteroid.asteroid.setRotation(asteroidAngle);
                                    smallAsteroid.applyAcceleration(3);

                                    asteroidList.add(smallAsteroid);
                                    root.getChildren().add(smallAsteroid.getPolygon());
                                }
                            } else {
                                score.set(score.get() + 1000);
                                scoreText.SetText("Score: " + score);
                            }
                        });
                        return true;
                    }).collect(Collectors.toList());
//                        collect(Collectors.toList());
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

        };

}

