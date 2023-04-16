package com.example.demo3;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.Random;

public class AlienShip extends Polygons {

    //形状和角度很奇怪。
    Double[] alienShipPoints = {0.0,-5.0,30.0, 5.0,45.0, 5.0, 75.0,-5.0, 60.0,-15.0, 45.0,-15.0, 45.0,-22.5, 30.0,-22.5, 30.0,-15.0, 15.0,-15.0};


    public Polygons alienship;

    public boolean alive = true;

    public AlienShip(PolygonType polygonType,double x, double y, double s){
        this.alienship = new Polygons(polygonType, this.alienShipPoints, Color.TRANSPARENT.brighter(), Color.WHITE, s, x, y);
        this.polygonType=polygonType;

    }
    public Polygon getPolygon(){
        return this.alienship.getPolygon();
    }

    public void applyAcceleration(double acc){
        this.alienship.applyAcceleration(acc);
    }

    public void rotLeft(){
        this.alienship.rotLeft();
    }

    public void rotRight(){
        this.alienship.rotRight();
    }


    public void applyMove(int ScreenWidth, int ScreenHeight){
        this.alienship.applyMove(ScreenWidth, ScreenHeight);
    }

    public boolean getAlive(){
        return this.alive;
    }

    public boolean collision(com.example.demo3.Polygons other){
        //getBoundsInLocal gives the bounds of a node in its own coordinate system
        Shape collisionArea = intersect(this.alienship.getPolygon(), other.getPolygon());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }


}
