package uiComponents;

import de.rst.core.Wall;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;


public class WallViewPolyline  extends Group{


    private final Wall wall;

    public WallViewPolyline(Wall wall){
       this.wall = wall;

       initWall();
    }

    private void initWall() {
        double[] points = new double[wall.getPoints().size()*2];
        //TODO Benchmark what is faster add or addAll on getChildren
        int i = 0;
        for (Point2D point:wall.getPoints().values()){

            points[i++]=point.getX();
            points[i++]=point.getY();
        }



        Polyline line = new Polyline(points);

        line.setStrokeWidth(wall.getWidth());

        this.getChildren().add(line);
    }

    public void replaceLastPoint(double x, double y) {



        Object node = this.getChildren().get(0);

        if (node instanceof Polyline){
            ObservableList<Double> points = ((Polyline) node).getPoints();


            System.out.println(points.get(0));
            points.remove(points.size() -2,points.size()-1);
            points.addAll(x,y);
        }
    }

    public Wall getWall() {
        return wall;
    }
}
