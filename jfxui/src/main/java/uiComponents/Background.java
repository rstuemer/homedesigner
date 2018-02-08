package uiComponents;

import de.rst.core.Plan;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class Background extends Group {


    private double pixelPerMeter;
    private final ObservableList<Node> rulerList;

    public Background(double width, Plan plan) {
        super();
        rulerList = super.getChildren();
        pixelPerMeter = plan.getZoom();
//
//        if(width > 0 && (pixelPerMeter * plan.getWidth() < width) )
//            pixelPerMeter = width/plan.getWidth();


        createHorizontalLines(plan);
        createVerticalLines(plan);
    }


    private void createVerticalLines(Plan plan) {
        int rulerWidth=20;



        for (int i = 1; i <  plan.getWidth() ;  i++) {

            double x = (i * pixelPerMeter)+rulerWidth;

            if(i % plan.getPlanScale() == 0) {
               rulerList.add(new VLine(rulerWidth, plan.getHeight()*pixelPerMeter , x,2));
            }
            else
                rulerList.add(new VLine(rulerWidth, plan.getHeight()*pixelPerMeter, x));
        }



    }

    private void createHorizontalLines(Plan plan) {
        int rulerWidth=20;
        for (int i = 1; i <  plan.getHeight() ;  i++) {

            double y = i * pixelPerMeter+rulerWidth;
            if(i %  plan.getPlanScale() == 0) {

                rulerList.add(new HLine(rulerWidth, plan.getWidth()*pixelPerMeter , y,2));
            }
            else
                rulerList.add(new HLine(rulerWidth, plan.getWidth()*pixelPerMeter , y));
        }


    }



}
