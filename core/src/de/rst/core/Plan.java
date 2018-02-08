package de.rst.core;


import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Plan {


    private  int height;
    private int width;
    private double planScale;
    private double zoom;
    private final List<Wall> walls;
    private Wall wallInEditMode;
    private int rulerWidth;
    private String name;



    public Plan (){
        walls = new ArrayList<>();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public double getPlanScale() {
        return planScale;
    }

    public void setPlanScale(double planScale) {
        this.planScale = planScale;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public void addWall(Wall wall) {
        walls.add(wall);
    }

    public List<Wall> getWalls(){
        return walls;
    }


    public Wall getWallInEditMode() {
        return wallInEditMode;
    }

    public void setWallInEditMode(Wall wallInEditMode) {
        this.wallInEditMode = wallInEditMode;
    }

    public Point2D createPlanPoint(double x, double y ){

        x = x/zoom;
        y = y/zoom;

        return new Point2D(x,y);
    }

    public Point2D convertToViewPoint(Point2D planPoint){

      double  x = planPoint.getX() * zoom + rulerWidth;
        double  y = planPoint.getY() * zoom + rulerWidth;

        return new Point2D(x,y);
    }

    public void fitToScreenIfNecessary(double width) {

        if(this.getWidth()*zoom < width){
            int newWidth = Math.toIntExact(Math.round(width / zoom));
            this.setWidth(newWidth);
        }
    }

    public int getRulerWidth() {
        return rulerWidth;
    }

    public void setRulerWidth(int rulerWidth) {
        this.rulerWidth = rulerWidth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
