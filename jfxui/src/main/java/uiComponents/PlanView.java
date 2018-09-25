package uiComponents;

import UIController.AppState;
import UIController.View2dController;
import de.rst.core.Plan;
import de.rst.core.Wall;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Light;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;

import javax.inject.Inject;

public class PlanView extends Group {


    private final Plan plan;
    private WallView tempWallView;
    private AppState appState;


    public PlanView(Plan plan) {


        this.plan = plan;

        initView();


    }


    public void initView() {

        for (Wall wall : plan.getWalls()) {

            WallView wallView = new WallView(wall, plan);
            this.getChildren().add(wallView);
        }

        if (this.tempWallView != null)
            this.getChildren().add(this.tempWallView);

    }


    public WallView getTempWallView() {
        return tempWallView;
    }

    public void setTempWallView(WallView tempWallView) {
        this.tempWallView = tempWallView;
    }

    public void updateTempWallView(WallView newTempWallview) {
        if (this.getChildren().contains(this.tempWallView))
            this.getChildren().remove(this.tempWallView);

        this.tempWallView = newTempWallview;
        this.getChildren().add(this.tempWallView);

    }


    public void addWall(double x, double y) {

        Point2D planPoint = plan.createPlanPoint(x - plan.getRulerWidth(), y - plan.getRulerWidth());
        System.out.println("New Wall Point: " + planPoint);
        Wall wall = new Wall(planPoint);
        System.out.println("Create EDITED WALL :" + wall);

//        appState.setCurrentWall(wall);
//        appState.setEditMode("create_wall_points");

    }

    public void addEditWall(double x, double y) {
        System.out.println("finishWall");
        Point2D planPoint = new Point2D(x, y);
        System.out.println("New Wall Point: " + planPoint);
        Wall wall = new Wall(planPoint);
        System.out.println("Create EDITED WALL :" + wall);
        WallView wallView = new WallView(wall, plan);
        updateTempWallView(wallView);

    }

    public void finishWall(double x, double y) {
        System.out.println("finishWall");
        Point2D planPoint = new Point2D(x, y);
        Wall wall = this.tempWallView.getWall();
        wall.addPoint(planPoint);
        this.plan.addWall(wall);
        this.tempWallView.addPoint(planPoint);

    }

    public void addSegmentWall(double x, double y) {
        System.out.println("addSegmentWall");
        Point2D planPoint = new Point2D(x, y);
        System.out.println(planPoint);
        this.tempWallView.addPoint(planPoint);

    }

    public void cleanView() {
        this.getChildren().remove(this.tempWallView);
        this.tempWallView = null;
    }

    public void updateLastSegment(double x, double y) {
        //System.out.println("updateLastSegment");
//        Point2D planPoint = plan.createPlanPoint(x - plan.getRulerWidth(), y - plan.getRulerWidth());
        Point2D planPoint = new Point2D(x, y);

        if (this.tempWallView != null) {
            if (this.tempWallView.getWall().getPoints().size() == 1) {
                this.tempWallView.addPoint(planPoint);
            } else
                this.tempWallView.replaceLastPoint(planPoint);
            this.updateTempWallView(this.tempWallView);
        } else {
            System.out.println("TEmpwall is null");
        }


    }


    public void changePreviewPoint(double eventX, double eventY) {
        this.tempWallView.changePreviewPoint(eventX, eventY);

        this.updateTempWallView(this.tempWallView);
    }

    public void persistTempwall() {
        if (this.tempWallView != null)
            this.plan.addWall(this.tempWallView.getWall());
    }

    public Point2D convertToViewPoint(Point2D point2D) {
        double[] unvisiblePixel;

        Node parent  =this.getScene().lookup("#view2d");
        if (parent != null) {
            ScrollPane parentPane = (ScrollPane)parent;
            unvisiblePixel = getUnvisiblePixel(parentPane);
        } else {
            unvisiblePixel = new double[]{0, 0};
        }

        System.out.println(point2D);
//        double x = point2D.getX() - unvisiblePixel[1];
//        double y = point2D.getY() - unvisiblePixel[0];
        double x = point2D.getX();
        double y = point2D.getY();

//        if(unvisiblePixel[1] > 0)
//         x = point2D.getX() - 20;
//        if(unvisiblePixel[0] > 0)
//         y = point2D.getY() -20;

        System.out.println(x + " y: " + y);

        return new Point2D(x, y);
    }

    double[] getUnvisiblePixel(ScrollPane source) {

        double hvalue = source.getHvalue();

        double vValue = source.getVvalue();
        double unvisiblePixelV = source.getHeight() * vValue;
        double unvisiblePixelH = source.getWidth() * hvalue;
        double[] unvisiblePixel = {unvisiblePixelV, unvisiblePixelH};


        System.out.println( "0:" + unvisiblePixel[0] + " 1: "+unvisiblePixel[1]);
        return unvisiblePixel;
    }
}
