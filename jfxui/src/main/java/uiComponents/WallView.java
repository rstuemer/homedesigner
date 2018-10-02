package uiComponents;

import de.rst.core.Plan;
import de.rst.core.Wall;

import de.rst.core.geo.Line2D;
import de.rst.core.geo.Point2D;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.joml.Vector2d;

import java.awt.*;
import java.util.SortedMap;

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

    }

    public void initWall() {

        PlanView planView = (PlanView) this.getParent();

        //TODO Benchmark what is faster add or addAll on getChildren
        SortedMap<Integer, Point2D> points = wall.getPoints();
        if (points.size() > 1) {
            for (int i = 1; i < points.size(); i++) {
//                Point2D point1 = points.get(i); //planView.convertToViewPoint(points.get(i));
//                Point2D point2 = points.get(i + 1); //planView.convertToViewPoint(points.get(i+1));
                Point2D point1 = planView.convertToViewPoint(points.get(i));
                Point2D point2 = planView.convertToViewPoint(points.get(i+1));
                Point2D segmentBefore = null;
                Point2D segmentAfter = null;
                if(i > 1) {
                    Point2D point0 = planView.convertToViewPoint(points.get(i - 1));
                    if (i > 2) {
                        segmentBefore = point1.subtract(point0);
                    }
                    if (i < points.size() - 1) {
                        segmentAfter = points.get(i + 2).subtract(point2);
                    }

                    renderWallSegmentWithCorner(point1, point2, wall.getWidth() * plan.getZoom(), segmentBefore, segmentAfter, point0);
                }else{
                    renderWall(point1,point2,wall.getWidth() * plan.getZoom());
                }


            }
            if (this.previewPoint != null) {
                addLineforPreviewPoint();
            }

        } else if (this.previewPoint != null) {
            Point2D point1 = planView.convertToViewPoint(points.get(1));

            Point2D previewPoint = planView.convertToViewPoint(this.previewPoint);
            renderWall(point1, previewPoint, wall.getWidth() * plan.getZoom());
//            Line2D line = new Line2D(point1.getX(), point1.getY(), previewPoint.getX(), previewPoint.getY());
//            line.setSmooth(true);
//            line.setStrokeWidth(wall.getWidth() * plan.getZoom());
//            line.setStrokeLineCap(StrokeLineCap.BUTT);
//            line.setStroke(getPreviewColor());


//            this.getChildren().add(line);
        }


    }


    void renderWallSegmentWithCorner(de.rst.core.geo.Point2D startPoint, de.rst.core.geo.Point2D endPoint, double width,Point2D segmentBefore,Point2D segmentAfter,Point2D point0){


        Point2D p0 = startPoint;
        Point2D p1 = endPoint.subtract(startPoint);
        Point2D p4 = p1.perpendicular().normalize().multiply(width);
        Point2D p2 = p4.invert();
        Point2D p3 = startPoint.subtract(endPoint);
        Path path = new Path();
        path.getStyleClass().add("default_wall");

        if(segmentBefore != null) {
            double angleCornerStart = p1.angleRelToThis(segmentBefore);
            double orientation = p1.crossProduct(segmentBefore).getZ();

            if(orientation >  0 ){
                Line2D line = new Line2D(startPoint.add(p2),endPoint.add(p2));
                Point2D multiply = startPoint.subtract(point0).perpendicular().invert().normalize().multiply(width);
                point0 =  point0.add(multiply);
                Line2D line2 = new Line2D(point0,startPoint.add(multiply));
                Point2D intersection = line.intersection(line2);

                System.out.println("IntersectionPoint: " + intersection);


                Circle circle = new Circle(intersection.getX(),intersection.getY(),5);
                Line viewLine = line.getViewLine();
                viewLine.setStroke(Color.GREEN);
                this.getChildren().add(circle);
                viewLine.setStroke(Color.GREEN);
                viewLine.setStrokeWidth(10);

                Line viewLine1 = line2.getViewLine();
                viewLine1.setStroke(Color.YELLOW);
                viewLine1.setStrokeWidth(10);

                //this.getChildren().add(viewLine1);

                createInnerPath(p0,intersection.subtract(startPoint),p3.add(p1), p4,p1, path);


            }


            System.out.println("Angle Conrenr Start:" + angleCornerStart + "Point:"+segmentBefore+ " Orientation:"+orientation);
        }


        if(segmentAfter != null) {
            double angleCornerEnd = p1.angleRelToThis(segmentAfter);
            double orientation = p1.crossProduct(segmentAfter).getZ();

            System.out.println("Angle Conrenr End:" + angleCornerEnd  + "Point:"+segmentAfter + " Orientation:"+orientation);
            createInnerPath(p0, p1, p2, p3, p4, path);
        }



        //TODO Add SELECTION FOR WALL ORIENTATION INNER,MIDDLE,OUTER



        formatPath(width, path);

        addContextMenu(path);
        this.getChildren().add(path);
    }

    private void addLineTo(Point2D p1, Path path) {
        LineTo lineTo = new LineTo();
        lineTo.setX(p1.getX());
        lineTo.setY(p1.getY());
        lineTo.setAbsolute(false);
        path.getElements().add(lineTo);
    }

    /**
     * p3<----------------------p2
     * |                        ▲
     * |                        |
     * v                        |
     * p0---------------------->p1
     * <p>
     * p0 = startpoint ;  p3 = endpoint
     *
     * @param startPoint
     * @param endPoint
     * @param width
     */
    void renderWall(de.rst.core.geo.Point2D startPoint, de.rst.core.geo.Point2D endPoint, double width) {


        Point2D p0 = startPoint;
        Point2D p1 = endPoint.subtract(startPoint);
        Point2D p2 = p1.perpendicular().normalize().multiply(width);
        Point2D p3 = startPoint.subtract(endPoint);
        Point2D p4 = p2.invert();



        p2 = p4;
        p4 = p2.invert();


        //Point2D con = new Point2D(100, 200);


        Path path = new Path();

        path.getStyleClass().add("default_wall");
        createInnerPath(p0, p1, p2, p3, p4, path);


        formatPath(width, path);

        addContextMenu(path);
        this.getChildren().add(path);

    }

    private void createInnerPath(Point2D p0, Point2D p1, Point2D p2, Point2D p3, Point2D p4, Path path) {
        MoveTo moveTo = new MoveTo();
        moveTo.setX(p0.getX());
        moveTo.setY(p0.getY());
        path.getElements().add(moveTo);

        addLineTo(p1, path);

        addLineTo(p2, path);

        addLineTo(p3, path);

        addLineTo(p4, path);
    }

    private void formatPath(double width, Path path) {
        path.setFill(Color.WHITE);
        path.setStrokeType(StrokeType.INSIDE);
        path.setStrokeWidth(width / 10);
        path.setStroke(Color.BLACK);
        path.setStrokeLineJoin(StrokeLineJoin.BEVEL);
    }

    private void addContextMenu(Path path) {
        ContextMenu contextMenu = getContextMenu();
        path.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>()

        {
            @Override
            public void handle(ContextMenuEvent event) {

                contextMenu.show(path, event.getScreenX(), event.getScreenY());
            }
        });
    }


    ContextMenu getContextMenu() {
        // Create ContextMenu
        ContextMenu contextMenu = new ContextMenu();

        MenuItem item1 = new MenuItem("Menu Item 1");
        item1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
//                label.setText("Select Menu Item 1");
            }
        });
        MenuItem item2 = new MenuItem("Menu Item 2");
        item2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
//                label.setText("Select Menu Item 2");
            }
        });

        // Add MenuItem to ContextMenu
        contextMenu.getItems().addAll(item1, item2);

        return contextMenu;
    }

    void addLineforPreviewPoint() {

        PlanView planView = (PlanView) this.getParent();

        Point2D lastPoint = wall.getPoints().get(wall.getPoints().lastKey());
        lastPoint = planView.convertToViewPoint(lastPoint);
        Point2D previewPoint = this.previewPoint;
        previewPoint = planView.convertToViewPoint(previewPoint);
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

        if (this.previewPoint != null)
            this.getChildren().remove(this.getChildren().size() - 1);
        this.previewPoint = new Point2D(eventX, eventY);


        addLineforPreviewPoint();
    }

    public void clearPreviewPoint() {
        this.previewPoint = null;
    }
}

