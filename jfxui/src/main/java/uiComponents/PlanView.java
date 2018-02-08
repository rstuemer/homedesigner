package uiComponents;

import de.rst.core.Plan;
import de.rst.core.Wall;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;

public class PlanView extends Group {


    private final Plan plan;

    public PlanView(Plan plan){


        this.plan = plan;

        initView();
    }



    public void initView(){




        for (Wall wall: plan.getWalls()) {

            WallView wallView = new WallView(wall,plan);
            this.getChildren().add(wallView);
        }




    }


}
