package com.example.demo3;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class Bullet extends Polygons{

    Double[] bulletPoints = {2.0,0.0,0.0,2.0,-2.0,0.0,0.0,-2.0};

    public Polygons bullet;

    public Bullet(PolygonType polygonType,double x, double y){
        this.bullet = new Polygons(this.polygonType,this.bulletPoints, Color.WHITE, Color.CYAN, 1, x, y);
        this.bullet.setRadius(2);
    }

    public Polygon getPolygon(){
        return this.bullet.getPolygon();
    }

    public void applyAcceleration(double acc){
        this.bullet.applyAcceleration(acc);
    }

    public void rotLeft(){
        this.bullet.rotLeft();
    }

    public void rotRight(){
        this.bullet.rotRight();
    }

    public void applyMove(int ScreenWidth, int ScreenHeight){
        this.bullet.applyMove(ScreenWidth, ScreenHeight);
    }

    public boolean collision(Polygons other){
        //getBoundsInLocal gives the bounds of a node in its own coordinate system
        Shape collisionArea = Shape.intersect(this.bullet.getPolygon(), other.getPolygon());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }

}
