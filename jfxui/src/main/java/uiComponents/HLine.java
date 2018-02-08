package uiComponents;

import javafx.scene.shape.Line;

class HLine extends Line {
    private final double xstart, xend;
    private final int strokeWidth;

    public HLine(double xstart, double xend , double y) {
        this(xstart, xend, y,1);
    }


    public HLine(double xstart, double xend, double y, int strokeWidth){
        super(xstart, y, xend, y);
        super.setStrokeWidth(strokeWidth);
        this.xstart = xstart;
        this.xend = xend;
        this.strokeWidth = strokeWidth;
    }


    public Line copy(double y) {
        Line line = new Line(xstart,y,xend, y);
        line.setStrokeWidth(strokeWidth);
        return line;
    }
}
