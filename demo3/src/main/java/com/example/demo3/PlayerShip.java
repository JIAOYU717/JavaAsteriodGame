package com.example.demo3;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class PlayerShip extends Polygons{

    Double[] shipPoints =  {15.0, 0.0, -15.0, 10.0, -5.0, 0.0, -15.0, -10.0};
    public Polygons ship;
    public Polygons thruster;
    boolean alive = true;
    boolean immune = false;
    public PlayerShip(PolygonType polygonType, double x, double y){
        this.ship = new Polygons(polygonType, this.shipPoints, Color.TRANSPARENT, Color.YELLOW, 1,  x, y);
        this.ship.setRadius(10);
        this.ship.getPolygon().setOpacity(1);

    }

    public Polygon getPolygon(){
        return this.ship.getPolygon();
    }


    public void changeOpacity(double opacity){this.ship.getPolygon().setOpacity(opacity);}

    public void applyAcceleration(double acc){
        this.ship.applyAcceleration(acc);
    }

    public void rotLeft(){
        this.ship.rotLeft();
    }

    public void rotRight(){
        this.ship.rotRight();
    }

    public void applyMove(int ScreenWidth, int ScreenHeight){
        this.ship.applyMove(ScreenWidth, ScreenHeight);
    }

    public boolean collision(Polygons other){
        //getBoundsInLocal gives the bounds of a node in its own coordinate system
        Shape collisionArea = Shape.intersect(this.ship.getPolygon(), other.getPolygon());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }

    public boolean isAlive(){
        return alive;
    }

    public void hyperjump() {
        double randomX = Math.random() * 1200;
        double randomY = Math.random() * 800;
        this.getPolygon().setTranslateX(randomX);
        this.getPolygon().setTranslateY(randomY);
        this.ship.halt();
    }
    public void setImmune(boolean immune) {
        this.immune = immune;

        if (immune) {
            Timeline immunity = new Timeline(
                    new KeyFrame(Duration.seconds(0.10), event -> this.getPolygon().setFill(Color.YELLOW)),
                    new KeyFrame(Duration.seconds(0.20), event -> this.getPolygon().setFill(Color.TRANSPARENT))
            );
            // Immune for 5 seconds, each cycle is 0.2 seconds. Can change
            immunity.setCycleCount(25);
            immunity.setOnFinished(event -> this.immune = false);
            immunity.play();
        } else {
            this.getPolygon().setFill(Color.TRANSPARENT);
        }
    }

    public boolean isImmune() {
        return immune;
    }
}


