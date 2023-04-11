package com.example.demo3;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class AsteroidClass extends Polygons{

    Double[] asteroidPoints = {0.0, -30.0, //for code re-usability it's better to define the points outside
            -20.0, -20.0,
            -30.0, 10.0,
            0.0, 30.0,
            30.0, 20.0,
            30.0, 10.0,
            10.0, 10.0,
            30.0, 0.0,
            20.0, -20.0};

    //定义这个成员变量的意思是？
    public Polygons asteroid;

    public PolygonType polygonType;

    public AsteroidClass(PolygonType polygonType, double x, double y, double s){

        this.asteroid = new Polygons(this.polygonType,this.asteroidPoints, Color.GRAY, Color.WHITE, s, x, y);
        this.polygonType = polygonType;

    }
    public Polygon getPolygon(){
        return this.asteroid.getPolygon();
    }


    public void applyAcceleration(double acc){
        this.asteroid.applyAcceleration(acc);
    }

    public void rotLeft(){
        this.asteroid.rotLeft();
    }

    public void rotRight(){
        this.asteroid.rotRight();
    }

    public void applyMove(int ScreenWidth, int ScreenHeight){
        this.asteroid.applyMove(ScreenWidth, ScreenHeight);
    }

    public boolean collision(Polygons other){
        //getBoundsInLocal gives the bounds of a node in its own coordinate system
        Shape collisionArea = Shape.intersect(this.asteroid.getPolygon(), other.getPolygon());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }

}