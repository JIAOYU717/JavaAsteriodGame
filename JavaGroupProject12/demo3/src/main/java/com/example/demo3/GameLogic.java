package com.example.demo3;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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

public class GameLogic
{
    public Scene mainScene;
    public PlayerShip ship;
    public PlayerShip life;
    public AlienShip alien;

    public Group root;

    public AsteroidClass asteroids;

    public int alienRemovedFlag = 0;
    public int alienAppearFlag = 0;
    public long AlienBulletTime = 0;
    public long levelBeginTime = 0;
    public Map<KeyCode, Boolean> pressedKeys;


    public List<AlienBullet> alienBulletList;

    public List<PlayerShip> noOfLives;

    public List<AsteroidClass> asteroidList;

    public List<Bullet> bulletList;

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
            alienShip.applyAcceleration(1);
        } else if(spawnSide == 2){
            positionx = randomx.nextInt(1280);
            positiony = randomy.nextInt(60) + 832;
            alienShip  = (AlienShip) PolygonsFactory.createEntity(Polygons.PolygonType.ALIEN_SHIP,positionx, positiony);
            double asteroidAngle = randomx.nextDouble() * 360;
            alienShip.alienship.setRotation(asteroidAngle);
            alienShip.applyAcceleration(1);
        } else if(spawnSide == 3){
            positionx = randomx.nextInt(60) + 1280;
            positiony = randomy.nextInt(832);
            alienShip  = (AlienShip) PolygonsFactory.createEntity(Polygons.PolygonType.ALIEN_SHIP,positionx, positiony);
            double asteroidAngle = randomx.nextDouble() * 360;
            alienShip.alienship.setRotation(asteroidAngle);
            alienShip.applyAcceleration(1);
        } else if(spawnSide == 4){
            positionx = randomx.nextInt(1280);
            positiony = randomy.nextInt(60) - 60;
            alienShip  = (AlienShip) PolygonsFactory.createEntity(Polygons.PolygonType.ALIEN_SHIP,positionx, positiony);
            double asteroidAngle = randomx.nextDouble() * 360;
            alienShip.alienship.setRotation(asteroidAngle);
            alienShip.applyAcceleration(1);

        }
        return alienShip;
    }

    private AnimationTimer timer;

    public Scene start(Stage mainStage)
    {
        root = new Group();
        this.mainScene = new Scene(root, 1280, 832, Color.BLACK); //a drawing surface
        mainStage.setScene(mainScene);

        //Make player ship that is controllable by the player
        ship = (PlayerShip) PolygonsFactory.createEntity(Polygons.PolygonType.Player_SHIP, 600, 400);

        asteroidList = new ArrayList<>();
        asteroids = createLargeAsteroid();
        asteroidList.add(asteroids);
        alien = createAlienShip();

        alienBulletList = new ArrayList<>();

        bulletList = new ArrayList<>();

        levelBeginTime = System.currentTimeMillis();

        noOfLives = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            life = new PlayerShip(Polygons.PolygonType.Player_SHIP, 900 + 50 * i, 40);
            life.ship.setRotation(-90);
            noOfLives.add(life);
            root.getChildren().add(life.getPolygon());
        }

        // Hashmaps store items in key-value pairs.
        // here we keep track of when a key is pressed and when it is released
        pressedKeys = new HashMap<>();

        AtomicBoolean actionToBePerformed = new AtomicBoolean(false);
        AtomicBoolean didHyperJump = new AtomicBoolean(false);

        mainScene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });
        mainScene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });

        TextClass noOfAsteroids = new TextClass("No. of asteroids:", 30, 80, Color.WHITE, 30);
        root.getChildren().add(noOfAsteroids.mytext);

        TextClass youDiedText = new TextClass("YOU DIED", 575, 380, Color.WHITE, 40);
        root.getChildren().add(youDiedText.mytext);

        AtomicInteger score = new AtomicInteger(0);

        TextClass scoreText = new TextClass("Score: " + score, 30, 40, Color.WHITE, 40);
        root.getChildren().add(scoreText.mytext);

        TextClass debugText = new TextClass("" , 400, 50, Color.YELLOW, 40);
        root.getChildren().add(debugText.mytext);

        TextClass level = new TextClass("Level 1", 1100, 50, Color.WHITE, 30);

        AtomicInteger gameLevel = new AtomicInteger(1);
        root.getChildren().add(level.mytext);

        TextClass gameOver = new TextClass("Game Over", 505, 380, Color.RED, 60);
        gameOver.mytext.setOpacity(0);
        root.getChildren().add(gameOver.mytext);

        TextClass noOfHyperjumps = new TextClass("Hyper jump available!", 850, 90, Color.WHITE, 20);
        noOfHyperjumps.mytext.setOpacity(0);
        root.getChildren().add(noOfHyperjumps.mytext);

        TextClass debug1 = new TextClass("alienRemovedFlag::"+alienRemovedFlag, 250, 250, Color.WHITE, 20);
        debug1.mytext.setOpacity(0.5);
        root.getChildren().add(debug1.mytext);

        TextClass debug2 = new TextClass("alienAppearFlag:"+alienAppearFlag, 250, 550, Color.WHITE, 20);
        debug2.mytext.setOpacity(0.5);
        root.getChildren().add(debug2.mytext);

        AtomicBoolean replenishedLife = new AtomicBoolean(false);

        AtomicBoolean immunity = new AtomicBoolean(false);


        timer = new AnimationTimer() {

            @Override
            public void handle(long l) {
//
                level.SetText("Level " + gameLevel.get());
                noOfAsteroids.SetText("No. of asteroids: " + asteroidList.size());
                if (noOfLives.size() == 0){
                    gameOver.mytext.setOpacity(1);
                }

                if (!didHyperJump.get()) {
                    noOfHyperjumps.mytext.setOpacity(1);
                } else {noOfHyperjumps.mytext.setOpacity(0);}

                if (asteroidList.isEmpty()
                        && alienAppearFlag==1 && alienRemovedFlag == 1
                ){
                    debug1.SetText("alienRemovedFlag:"+alienRemovedFlag);
                    debug2.SetText("alienAppearFlag:"+alienAppearFlag);

                    levelBeginTime = System.currentTimeMillis();
                    gameLevel.set(gameLevel.get() + 1);
                    didHyperJump.set(false);
                    replenishedLife.set(false);
                    for (int j = 0; j < gameLevel.get(); j++)
                    {   asteroids = createLargeAsteroid();
                        asteroidList.add(asteroids);
                    }
                    alien = createAlienShip();
                    alienRemovedFlag = 0;
                    alienAppearFlag = 0;
                    debug1.SetText("alienRemovedFlag:"+alienRemovedFlag);
                    debug2.SetText("alienAppearFlag:"+alienAppearFlag);
                    asteroidList.forEach(asteroid -> {
                        asteroid.applyMove(1280, 832);
                        root.getChildren().add(asteroid.getPolygon());
                    });
                }


                if (!root.getChildren().contains(alien.getPolygon())&&System.currentTimeMillis()-levelBeginTime > 8000 && alienAppearFlag==0 && alienRemovedFlag == 0 ){
                        root.getChildren().add(alien.getPolygon());
                        alienAppearFlag = 1;
                    debug1.SetText("alienRemovedFlag:"+alienRemovedFlag);
                    debug2.SetText("alienAppearFlag:"+alienAppearFlag);
                    }

                if (alienAppearFlag==1 &&alienRemovedFlag== 0 && System.currentTimeMillis()-AlienBulletTime > 2000 ){
                    AlienBullet alienbullet = (AlienBullet) PolygonsFactory.createEntity(Polygons.PolygonType.ALIEN_BULLET, alien.getPolygon().getTranslateX(), alien.getPolygon().getTranslateY());
                    double angle = Math.toDegrees(Math.atan((ship.getPolygon().getTranslateY() - alien.getPolygon().getTranslateY())/
                            (ship.getPolygon().getTranslateX() - alien.getPolygon().getTranslateX())));
                    System.out.println("angle: "+ angle);
                    alienbullet.getPolygon().setRotate(angle);

                    alienbullet.applyAcceleration(7.0);
                    alienBulletList.add(alienbullet);
                    root.getChildren().add(alienbullet.getPolygon());
                    AlienBulletTime = System.currentTimeMillis();

                    Timeline alienBulletRemove = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                        alienBulletList.remove(alienbullet);
                        root.getChildren().remove(alienbullet.getPolygon());
                    }));
                    alienBulletRemove.play();
                }


                if(!ship.alive && noOfLives.size() != 0)
                {
                    youDiedText.mytext.setOpacity(1);
                } else {youDiedText.mytext.setOpacity(0);}

                if (immunity.get() == true){
                    ship.getPolygon().setOpacity(0.5);
                } else {ship.getPolygon().setOpacity(1);}

                if (pressedKeys.getOrDefault(KeyCode.ENTER, false) && !ship.isAlive() && noOfLives.size() == 0){
                    timer.stop();
                    AsteroidsGame game = AsteroidsGame.getInstance();
                    game.showHighScores();
                }

                if (pressedKeys.getOrDefault(KeyCode.ENTER, false) && !ship.isAlive() && noOfLives.size() != 0) {
                    ship.ship.getPolygon().setTranslateX(600);
                    ship.ship.getPolygon().setTranslateY(400);
                    ship.ship.halt();
                    ship.ship.setRotation(0);
                    ship.alive = true;
                    root.getChildren().add(ship.getPolygon());
                    root.getChildren().remove(youDiedText);
                    immunity.set(true);
                    //player is immune for the first 2 seconds, so that if an asteroid
                    //spawns close to the player, they can move away quickly
                    Timeline immune = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                        immunity.set(false);
                    }));
                    immune.play();
                }

                if (!replenishedLife.get() && pressedKeys.getOrDefault(KeyCode.L, false) && ship.isAlive() && noOfLives.size() < 3 && score.get() >= 10000) {
                    if (noOfLives.size() == 1){
                        PlayerShip life = new PlayerShip(Polygons.PolygonType.Player_SHIP, 900 + 50, 40);
                        life.ship.setRotation(-90);
                        noOfLives.add(life);
                        root.getChildren().add(life.getPolygon());
                        replenishedLife.set(true);}
                    else {
                        PlayerShip life = new PlayerShip(Polygons.PolygonType.Player_SHIP, 900 + 50 * 2, 40);
                        life.ship.setRotation(-90);
                        noOfLives.add(life);
                        root.getChildren().add(life.getPolygon());
                        replenishedLife.set(true);
                    }
                    score.set(score.get() - 10000);
                    scoreText.SetText("Score: " + score.get());
                }

                if (pressedKeys.getOrDefault(KeyCode.LEFT, false) && ship.isAlive()) {
                    ship.rotLeft();
                }
                if (pressedKeys.getOrDefault(KeyCode.RIGHT, false) && ship.isAlive()) {
                    ship.rotRight();
                }
                if (pressedKeys.getOrDefault(KeyCode.C, false) && ship.isAlive() && !didHyperJump.get()) {
                    ship.hyperjump();
                    didHyperJump.set(true);
                    immunity.set(true);
                        //player is immune for the first 2 seconds, so that if an asteroid
                        //spawns close to the player, they can move away quickly
                        Timeline immune = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                                immunity.set(false);
                        }));
                        immune.play();
                };


                if (pressedKeys.getOrDefault(KeyCode.UP, false) && ship.isAlive()) {
                    ship.applyAcceleration(0.06);
                };

                mainScene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
                            if (!actionToBePerformed.get() && keyEvent.getCode() == KeyCode.SPACE && bulletList.size() < 7 && ship.isAlive()) {
                                Bullet bullet = new Bullet(Polygons.PolygonType.BULLET, ship.getPolygon().getTranslateX(), ship.getPolygon().getTranslateY());
                                bullet.bullet.setRotation(ship.getPolygon().getRotate());
                                bullet.applyAcceleration(10.0);
                                bulletList.add(bullet);
                                root.getChildren().add(bullet.getPolygon());
                                actionToBePerformed.set(true);
                            }
                        });

                mainScene.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {
                    if ( actionToBePerformed.get() && keyEvent.getCode() == KeyCode.SPACE) {
                        actionToBePerformed.set(false);
                    }
                });

                ship.applyMove(1280,832);

               if ( (noOfLives.size() >= 1) && ship.collision(alien)) {
                    root.getChildren().remove(noOfLives.get(noOfLives.size() - 1).getPolygon());
                    noOfLives.remove(noOfLives.get(noOfLives.size() - 1));
                    ship.alive = false;
                    root.getChildren().remove(life.getPolygon());
                    root.getChildren().remove(ship.getPolygon());

                }


                asteroidList.forEach(asteroid -> {
                    asteroid.applyMove(1280, 832);
                    if(alien!=null){
                        alien.applyMove(1280, 832);
                    }

                    alienBulletList.forEach(alienbullet -> {
                        if (!immunity.get() && !(noOfLives.size() == 1) && ship.isAlive() && ship.collision(alienbullet)) {
                            root.getChildren().remove(noOfLives.get(noOfLives.size()-1).getPolygon());
                            noOfLives.remove(noOfLives.get(noOfLives.size()-1));
                            root.getChildren().remove(ship.getPolygon());
                            ship.alive = false;
                            root.getChildren().remove(ship.getPolygon());
                        } else if (!immunity.get() && (noOfLives.size() == 1) && ship.isAlive() && ship.collision(alienbullet)) {
                            root.getChildren().remove(noOfLives.get(noOfLives.size() - 1).getPolygon());
                            noOfLives.remove(noOfLives.get(noOfLives.size() - 1));
                            ship.alive = false;
                            root.getChildren().remove(life.getPolygon());
                            root.getChildren().remove(ship.getPolygon());

                        }});


                    if (immunity.get() == false){
                        if (ship.isAlive() && ship.collision(asteroid)) {
                            root.getChildren().remove(noOfLives.get(noOfLives.size()-1).getPolygon());
                            noOfLives.remove(noOfLives.get(noOfLives.size()-1));
                            root.getChildren().remove(ship.getPolygon());
                            ship.alive = false;
                    };
                }});
                alienBulletList.forEach(alienbullet -> {
                    alienbullet.applyMove(1280, 832);
                });

                bulletList.forEach(bullet -> {
                    bullet.applyMove(1280, 832);
                    if (bullet.getPolygon().getTranslateX() < 0 || bullet.getPolygon().getTranslateX() > 1280
                            || bullet.getPolygon().getTranslateY() < 0 || bullet.getPolygon().getTranslateY() > 832) {
                        bulletList.remove(bullet);
                        root.getChildren().remove(bullet.getPolygon());
                    }
                });
                List<Bullet> bulletsToRemove = bulletList.stream().filter(bullet -> {
                    List<AsteroidClass> collides = asteroidList.stream().filter(asteroid -> asteroid.collision(bullet))
                            .toList();

                if (collides.isEmpty()) {
                    return false;
                }

                collides.stream().forEach(collided -> {
                    asteroidList.remove(collided);
                    root.getChildren().remove(collided.getPolygon());

                    if (collided.asteroid.scale == 2.0) {
                        score.set(score.get() + 500);
                        scoreText.SetText("Score: " + score);
                        for (int i = 0; i < 2; i++) {
                            AsteroidClass mediumAsteroid = new AsteroidClass(Polygons.PolygonType.MEDIUM_ASTEROID, collided.getPolygon().getTranslateX(), collided.getPolygon().getTranslateY(), 1);
                            double asteroidAngle = collided.asteroid.getAngle() + (-90 * i + 45);
                            mediumAsteroid.asteroid.setRotation(asteroidAngle);
                            mediumAsteroid.applyAcceleration(2);
                            asteroidList.add(mediumAsteroid);
                            root.getChildren().add(mediumAsteroid.getPolygon());
                        }}

                    else if (collided.asteroid.scale == 1.0) {
                        score.set(score.get() + 750);
                        scoreText.SetText("Score: " + score);
                        for (int i = 0; i < 2; i++) {
                            AsteroidClass smallAsteroid = new AsteroidClass(Polygons.PolygonType.SMALL_ASTEROID, collided.getPolygon().getTranslateX(), collided.getPolygon().getTranslateY(), 0.5);
                            double asteroidAngle = collided.asteroid.getAngle() + (-90 * i + 45);
                            smallAsteroid.asteroid.setRotation(asteroidAngle);
                            smallAsteroid.applyAcceleration(2);
                            asteroidList.add(smallAsteroid);
                            root.getChildren().add(smallAsteroid.getPolygon());
                        }
                        } else {
                            score.set(score.get() + 1000);
                            scoreText.SetText("Score: " + score);
                        }
                        });
                return true;
                }).toList();
                bulletsToRemove.forEach(bullet -> {
                    bulletList.remove(bullet);
                    root.getChildren().remove(bullet.getPolygon());
                });

                Iterator<Bullet> bulletIterator = bulletList.iterator();
                while (bulletIterator.hasNext()) {
                    Bullet bullet = bulletIterator.next();
                    if (ship.isAlive() && alien.collision(bullet)) {
                        bulletIterator.remove(); //  use Iterator to delete element safer
                        root.getChildren().remove(bullet.getPolygon());
                        root.getChildren().remove(alien.getPolygon());
                        alienRemovedFlag = 1;
                        debug1.SetText("alienRemovedFlag:"+alienRemovedFlag);
                        debug2.SetText("alienAppearFlag:"+alienAppearFlag);
                        score.set(score.get() + 5000);
                        scoreText.SetText("Score: " + score);
                    }
                }

            }
        };

        root.getChildren().add(ship.getPolygon());
        asteroidList.forEach(asteroid -> root.getChildren().add(asteroid.getPolygon()));
        timer.start();
    return mainScene;
    }

}