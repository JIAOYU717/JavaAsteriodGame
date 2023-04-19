package com.example.demo3;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class PlayerShip extends Polygons{

    Double[] shipPoints =  {15.0, 0.0, -15.0, 10.0, -5.0, 0.0, -15.0, -10.0};
    public Polygons ship;

    boolean alive = true;

    public PlayerShip(double x, double y){
        this.ship = new Polygons(this.shipPoints, Color.MEDIUMSEAGREEN, Color.YELLOW, 1,  x, y);
        this.ship.setRadius(10);
    }

    public Polygon getPolygon(){
        return this.ship.getPolygon();
    }

    public void changeOpacity(double opacity){this.ship.changeOpacity(opacity);}

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
        double randomX = Math.random() * 1280;
        double randomY = Math.random() * 700;
        this.getPolygon().setTranslateX(randomX);
        this.getPolygon().setTranslateY(randomY);
        this.ship.halt();
    }
}
