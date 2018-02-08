package uiComponents;

import de.rst.core.Plan;
import de.rst.core.Wall;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class WallView extends Group {


    private  Plan plan;
    private  Wall wall;


    private Color strokeColor = Color.BLUE;


    public WallView(Wall wall,Plan plan){
        this(wall,Color.BLUE,plan);
    }

    public WallView(Wall wall,Color strokeColor,Plan plan){
        this.wall = wall;
        this.plan = plan;
        this.setStrokeColor(strokeColor);
        initWall();
    }

    public void initWall() {

        //TODO Benchmark what is faster add or addAll on getChildren
        if(wall.getPoints().size() > 1)
        for (int i=1;i < wall.getPoints().size();i++){
            Point2D point1 = plan.convertToViewPoint(wall.getPoints().get(i));
            Point2D point2 = plan.convertToViewPoint(wall.getPoints().get(i+1));


            Line line = new Line(point1.getX(),point1.getY(),point2.getX(),point2.getY());
            line.setSmooth(true);
//            System.out.println(line + " zoom" + plan.getZoom());
            line.setStrokeWidth(wall.getWidth()*plan.getZoom());
            line.setStrokeLineCap(StrokeLineCap.BUTT);
            line.setStroke(getStrokeColor());
            this.getChildren().add(line);
        }
    }

    public void replaceLastPoint(double x, double y) {


        replacePointAtPosition(x, y, this.getChildren().size() - 1);
    }

    public void addPoint(double x, double y) {


        int size = this.getChildren().size();

        replacePointAtPosition(x, y, size);
    }

    void replacePointAtPosition(double x, double y, int size) {
        if(this.getChildren().size() > 0) {
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
}

