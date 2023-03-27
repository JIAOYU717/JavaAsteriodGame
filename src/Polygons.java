package com.example.demo3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class Polygons extends javafx.scene.shape.Polygon {

    public double rotation;
    Double[] points;

    double scale = 1;

    public double velocity = 0;
    double dvx = 0;
    double dvy = 0;

    double radius = 30;
    private Polygon polygon = new Polygon();

    public Point2D move = new Point2D(0,0);

    public Polygons(){

    }

    public Polygons (Double[] points, Color fillColor, Color strokeColor, double scale, double x, double y ) {
        this.polygon.setTranslateX(x);
        this.polygon.setTranslateY(y);
        this.move = new Point2D(0, 0);
        this.rotation = 0;
        this.points = points;
        this.polygon.setRotate(this.rotation);
        this.polygon.getPoints().addAll(this.points);
        this.scale = scale;
        this.polygon.setScaleX(this.scale);
        this.polygon.setScaleY(this.scale);
        this.radius = this.radius*scale;
        this.polygon.setFill(fillColor);
        this.polygon.setStroke(strokeColor);
    }

    public void setRadius(double radius){
        this.radius = radius;
    }
    public void setPosition(double x, double y){
        this.polygon.setTranslateX(x);
        this.polygon.setTranslateY(y);
    }

    public void rotLeft() {
        this.polygon.setRotate(this.polygon.getRotate() + 3);
    }

    public void rotRight() {
        this.polygon.setRotate(this.polygon.getRotate() - 3);
    }

    public void setRotation(double angle){
        this.rotation = angle;
    }

    public void applyMove( int ScreenWidth, int ScreenHeight ) {
        this.polygon.setTranslateX(this.polygon.getTranslateX() + this.move.getX());
        this.polygon.setTranslateY(this.polygon.getTranslateY() + this.move.getY());

        if (this.polygon.getTranslateX() > ScreenWidth+this.radius){
            this.polygon.setTranslateX(0-this.radius);
        }
        if (this.polygon.getTranslateX() < - this.radius){
            this.polygon.setTranslateX(ScreenWidth + this.radius);
        }
        if (this.polygon.getTranslateY() > ScreenHeight + this.radius){
            this.polygon.setTranslateY(- this.radius);
        }
        if (this.polygon.getTranslateY() < -this.radius){
            this.polygon.setTranslateY(ScreenHeight + this.radius);
        }
    }

    public void applyAcceleration(double da){
        double dx = da*Math.cos(Math.toRadians(this.polygon.getRotate()));
        double dy = da*Math.sin(Math.toRadians(this.polygon.getRotate()));
        this.dvx += dx;
        this.dvy += dy;
        this.velocity = Math.sqrt(dvx*dvx + dvy*dvy);
        this.move =  this.move.add(dx, dy);
    }

    public Polygon getPolygon(){
        return polygon;
    }


}
