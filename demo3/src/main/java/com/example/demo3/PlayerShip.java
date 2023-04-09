package com.example.demo3;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

public class PlayerShip extends Polygons{

    Double[] shipPoints =  {15.0, 0.0, -15.0, 10.0, -5.0, 0.0, -15.0, -10.0};
    public Polygons ship;

    public Polygons thruster;
    boolean alive = true;

    public PlayerShip(PolygonType polygonType,double x, double y){
        this.ship = new Polygons(this.polygonType,this.shipPoints, Color.MEDIUMSEAGREEN, Color.YELLOW, 1,  x, y);
        this.ship.setRadius(10);
        this.ship.getPolygon().setOpacity(1);
//        this.thruster = new Polygons(new Double[]{-25.0, 0.0, -12.0, 5.0, -7.0, 0.0, -12.0, -5.0},
//                Color.YELLOW, Color.RED, 1, 500, 600);

    }

    public Polygon getPolygon(){
        return this.ship.getPolygon();
    }

//    public void showThruster(){
//        this.thruster.getPolygon().setRotate(this.ship.getPolygon().getRotate());
//        this.thruster.getPolygon().setTranslateX(this.ship.getPolygon().getTranslateX());
//        this.thruster.getPolygon().setTranslateY(this.ship.getPolygon().getTranslateY());
//        this.thruster.getPolygon().setOpacity(1);
//    }
//
//    public Polygon getThruster(){
//        return this.thruster.getPolygon();
//    }

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
        double randomX = Math.random() * 1280;
        double randomY = Math.random() * 700;
        this.getPolygon().setTranslateX(randomX);
        this.getPolygon().setTranslateY(randomY);
        this.ship.halt();
    }
}
