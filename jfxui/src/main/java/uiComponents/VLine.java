package uiComponents;

import javafx.scene.shape.Line;

class VLine extends Line {
    private final double ystart, yend;
    private final int strokeWidth;

    public VLine(double xstart, double xend, double y) {
        this(xstart, xend,y,1);
    }


    public VLine(double ystart, double yend , double x,int strokeWidth){
        super(x, ystart, x, yend);
        super.setStrokeWidth(strokeWidth);
        this.ystart = ystart;
        this.yend = yend;
        this.strokeWidth = strokeWidth;
    }



    public Line copy(double x) {
        Line line = new Line(x, ystart, x, yend);
        line.setStrokeWidth(strokeWidth);
        return line;
    }
}