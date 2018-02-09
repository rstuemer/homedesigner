package UIController;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import uiComponents.PlanView;
import uiComponents.WallView;

import javax.inject.Inject;

public class View2dController {



    @Inject
    @FXML
    private ScrollPane view2D;

    @Inject
    private AppState appState;



    private Group view2DGroup;
    private WallView editedWallView;
    private PlanView planView;
}
