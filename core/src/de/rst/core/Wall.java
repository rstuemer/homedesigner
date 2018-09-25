package de.rst.core;


import javafx.geometry.Point2D;

import java.util.*;

public class Wall  implements Cloneable  {


    SortedMap<Integer, Point2D> points = new TreeMap<Integer, Point2D>();
    private double width;
    private double length;


    public Wall() {

    }

    public Wall(double startX, double startY) {


        points.put(1, new Point2D(startX, startY));
        width = 0.1;

    }


    public Wall(Point2D startPoint) {


        points.put(1, startPoint);
        width = 0.1;

    }


    public void addPoint(double startX, double startY) {
        addPoint(new Point2D(startX, startY));
    }

    public void addPoint(Point2D point) {
        points.put(points.lastKey() + 1, point);
        updateLength();
    }

    private void updateLength() {

        Point2D lastPoint = null;
        boolean startPoint = true;
        for (Point2D point : points.values()) {
            if (!startPoint) {
                length += point.distance(lastPoint);

            }
            startPoint = false;
            lastPoint = point;
        }
    }

    public void replaceLastPoint(double x, double y) {

        replaceLastPoint(new Point2D(x, y));

    }

    public void replaceLastPoint(Point2D lastPoint) {


        points.put(points.lastKey(), lastPoint);
        updateLength();
    }

    public SortedMap<Integer, Point2D> getPoints() {
        return points;
    }

    public void setPoints(SortedMap<Integer, Point2D> points) {
        this.points = points;
        updateLength();
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }


    public double getLength() {

        if (this.length == 0) {
            updateLength();
        }

        return length;
    }


    public Wall copy() {
        Wall copy = new Wall();
        copy.setPoints(getPoints());
        copy.setWidth(width);
        return copy;
    }


    public String toString() {

        String points = "";
        for (Point2D point : getPoints().values()) {
            points += "x:" + point.getX() + " y:" + point.getY() + " | ";
        }

        return "Wall [" + points + "]";
    }

}
