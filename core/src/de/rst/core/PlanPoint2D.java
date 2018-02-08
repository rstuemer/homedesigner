package de.rst.core;

import javafx.beans.NamedArg;
import javafx.geometry.Point2D;
import jdk.jfr.Name;

public class PlanPoint2D extends Point2D {


    private double zoom;


    /**
     * Creates a new instance of {@code Point2D}.
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     */
    public PlanPoint2D(@NamedArg("x") double x, @NamedArg("y") double y, @NamedArg("zoom") double zoom) {
        super(x/ zoom, y/ zoom);
        this.zoom = zoom;
    }




    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }
}
