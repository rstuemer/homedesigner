package uiComponents;

import de.rst.core.Plan;
import de.rst.core.Wall;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class WallView extends Group {


    private Plan plan;
    private Wall wall;
    private Point2D previewPoint;

    private Color strokeColor = Color.BLUE;


    public WallView(Wall wall, Plan plan) {
        this(wall, Color.BLUE, plan);
    }

    public WallView(Wall wall, Color strokeColor, Plan plan) {
        this.wall = wall;
        this.plan = plan;
        this.setStrokeColor(strokeColor);
        initWall();
    }

    public void initWall() {

        PlanView planView = (PlanView) this.getParent();
        //TODO Benchmark what is faster add or addAll on getChildren
        if (wall.getPoints().size() > 1) {
            for (int i = 1; i < wall.getPoints().size(); i++) {
                Point2D point1 =  planView.convertToViewPoint(wall.getPoints().get(i));
                Point2D point2 =  planView.convertToViewPoint(wall.getPoints().get(i+1));


                Line line = new Line(point1.getX(), point1.getY(), point2.getX(), point2.getY());
                line.setSmooth(true);
                line.setStrokeWidth(wall.getWidth() * plan.getZoom());
                line.setStrokeLineCap(StrokeLineCap.BUTT);
                line.setStroke(getStrokeColor());
                this.getChildren().add(line);


            }
            if (this.previewPoint != null){
                addLineforPreviewPoint();
            }

        } else if (this.previewPoint != null) {
            Point2D point1 =  planView.convertToViewPoint(wall.getPoints().get(1));

            Point2D previewPoint = planView.convertToViewPoint(this.previewPoint);

            Line line = new Line(point1.getX(), point1.getY(), previewPoint.getX(), previewPoint.getY());
            line.setSmooth(true);
            line.setStrokeWidth(wall.getWidth() * plan.getZoom());
            line.setStrokeLineCap(StrokeLineCap.BUTT);
            line.setStroke(getPreviewColor());


            this.getChildren().add(line);
        }


    }

    void addLineforPreviewPoint() {

        PlanView planView = (PlanView) this.getParent();

        Point2D lastPoint = wall.getPoints().get(wall.getPoints().lastKey());
        lastPoint =  planView.convertToViewPoint(lastPoint);
        Point2D previewPoint = this.previewPoint;
        previewPoint =  planView.convertToViewPoint(previewPoint);
        Line line = new Line(lastPoint.getX(), lastPoint.getY(), previewPoint.getX(), previewPoint.getY());
        line.setSmooth(true);
        line.setStrokeWidth(wall.getWidth() * plan.getZoom());
        line.setStrokeLineCap(StrokeLineCap.BUTT);
        line.setStroke(getPreviewColor());
        this.getChildren().add(line);
    }

    private Paint getPreviewColor() {

        return Color.RED;
    }


    public void replaceLastPoint(Point2D point) {


        replacePointAtPosition(point.getX(), point.getY(), this.getChildren().size() - 1);
    }

    public void replaceLastPoint(double x, double y) {


        replacePointAtPosition(x, y, this.getChildren().size() - 1);
    }

//    public void addPoint(double x, double y) {
//
//
//        int size = this.getChildren().size();
//
//        replacePointAtPosition(x, y, size);
//    }

    public void addPoint(Point2D point2d) {
        this.wall.addPoint(point2d);
        initWall();
    }

    void replacePointAtPosition(double x, double y, int size) {
        if (this.getChildren().size() > 0) {
            Object node = this.getChildren().get(size);

            if (node instanceof Line) {

                ((Line) node).setEndX(x);
                ((Line) node).setEndY(y);


            }
        }
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }


    public Wall getWall() {
        return wall;
    }

    public void changePreviewPoint(double eventX, double eventY) {

        if(this.previewPoint != null)
            this.getChildren().remove(this.getChildren().size()-1);
        this.previewPoint = new Point2D(eventX, eventY);


        addLineforPreviewPoint();
    }

    public void clearPreviewPoint() {
        this.previewPoint = null;
    }
}

