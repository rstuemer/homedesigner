package de.rst.core.geo;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class Line2D extends org.apache.commons.math3.geometry.euclidean.twod.Line {

    /** Default value for tolerance. */
    private static final double DEFAULT_TOLERANCE = 1.0e-10;

    private Point2D startPoint;
    private Point2D directionvector;




    public Line2D(Point2D startPoint, Point2D directionvector) {
        super(startPoint.getVector2D(),directionvector.getVector2D(), DEFAULT_TOLERANCE);
        this.startPoint = startPoint;
        this.directionvector = directionvector;
    }




    public Point2D getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point2D startPoint) {
        this.startPoint = startPoint;
    }

    public Point2D getDirectionvector() {
        return directionvector;
    }

    public void setDirectionvector(Point2D directionvector) {
        this.directionvector = directionvector;
    }


    public Point2D intersection(Line2D line2D){

        Vector2D intersection = super.intersection(line2D);
        return new Point2D(intersection);
    }


    public Line getViewLine(){
        Line line = new Line(this.startPoint.getX(), this.startPoint.getY(), this.directionvector.getX(), this.directionvector.getY());

        return line;
    }
}
