package com.example.demo3;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class AlienBullet extends Polygons{

    Double[] alienBulletPoints = {2.0,0.0,0.0,2.0,-2.0,0.0,0.0,-2.0};

    public Polygons alienBullet;

    public AlienBullet(PolygonType polygonType,double x, double y){
        //这俩color有什么区别？
        this.alienBullet = new Polygons(this.polygonType,this.alienBulletPoints, Color.RED, Color.RED, 1, x, y);
        this.alienBullet.setRadius(2);
    }

    public Polygon getPolygon(){
        return this.alienBullet.getPolygon();
    }

    public void applyAcceleration(double acc){
        this.alienBullet.applyAcceleration(acc);
    }

    public void rotLeft(){
        this.alienBullet.rotLeft();
    }

    public void rotRight(){
        this.alienBullet.rotRight();
    }

    public void applyMove(int ScreenWidth, int ScreenHeight){
        this.alienBullet.applyMove(ScreenWidth, ScreenHeight);
    }

    public boolean collision(Polygons other){
        //getBoundsInLocal gives the bounds of a node in its own coordinate system
        Shape collisionArea = Shape.intersect(this.alienBullet.getPolygon(), other.getPolygon());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }

}
