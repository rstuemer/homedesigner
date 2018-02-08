package uiComponents;
import de.rst.core.Plan;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

public class Ruler extends Group {

    private double pixelPerMeter;
    private ObservableList<Node> rulerList;



    /**
     * @param plan Main Plan Object
     *
     */
    public Ruler(double width, Plan plan) {
        super();
        rulerList = super.getChildren();
        pixelPerMeter = plan.getZoom();


//        if(width > 0 && (pixelPerMeter * plan.getWidth() < width) )
//            pixelPerMeter = width/plan.getWidth();



        createVerticalRuler(plan);
        createHorizontalRuler(plan);


    }

    private void createHorizontalRuler(Plan plan) {
        double length = plan.getRulerWidth();
        double startSmallRuler = (length /3)*2;
        for (int i = 0; i <  plan.getWidth() ;  i++) {

            double y = i * pixelPerMeter + length;
            if(i % plan.getPlanScale() == 0) {
                if(i != 0) {
                    Label label = new Label(i +"");
                    label.setTranslateX(y + 5);
                    rulerList.add(label);
                }

                rulerList.add(new VLine(0, length , y));
            }

            else
                rulerList.add(new VLine(startSmallRuler, length, y));
        }

        rulerList.add(new HLine(length, plan.getWidth() *pixelPerMeter  , length));

    }

    private void createVerticalRuler(Plan plan) {
        double length = plan.getRulerWidth();
        double startSmallRuler = (length /3)*2;
        for (int i = 0; i <  plan.getHeight() ;  i++) {

//            System.out.println(pixelPerMeter);
            double y = i * pixelPerMeter + length;
            if(i %  plan.getPlanScale() == 0) {

                Label label = new Label(i +"");
                label.setTranslateY(y +5);

                rulerList.add(label);
                HLine hLine = new HLine(0, length, y);
                rulerList.add(hLine);

//                System.out.println(hLine);
            }
            else
                rulerList.add(new HLine(startSmallRuler, length , y));
        }

        rulerList.add(new VLine(length, plan.getHeight() *pixelPerMeter  , length));

    }


}