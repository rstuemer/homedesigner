package UIController;

import de.rst.core.Plan;
import de.rst.core.Project;
import de.rst.core.Wall;
import de.rst.core.guice.modules.InjectLogger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.StackedFontIcon;
import org.slf4j.Logger;
import sample.Main;
import uiComponents.PlanView;
import uiComponents.Ruler;
import uiComponents.WallView;

import javax.inject.Inject;
import java.lang.reflect.Field;


public class MainController implements IController {
    @FXML
    private BorderPane mainPane;
    @FXML
    private ToolBar embeddedToolbar;
    @FXML
    private Parent embeddedMenu; //embeddedElement
    @Inject
    private MenuController embeddedMenuController; // $embeddedElement+Controller
    @Inject
    private ToolbarController embeddedToolbarController; // $embeddedElement+Controller
//    @Inject
//    private View3DController embeddedView3DController;
    @Inject
    private View2dController embeddedView2dController;
    @Inject
    private BottomToolbarController bottomToolbarController;

    @InjectLogger
    private Logger logger;

    @Inject @FXML
    private ScrollPane view2D;

    @Inject @FXML
    private StackPane view3D;

    @Inject
    private AppState appState;



    private Group view2DGroup;
    private WallView editedWallView;
    private PlanView planView;
    private Stage primaryStage;

    public MainController() {

    }



    public void initialize() {
        primaryStage = appState.getPrimaryStage();

    }



    @Override
    public void initView() {

        for (Field field: this.getClass().getDeclaredFields()) {
            if(IController.class.isAssignableFrom(field.getType())){
                try {
                    ((IController)  field.get(this)).initView();
                } catch (IllegalAccessException e) {
                    logger.warn("Controller not implement interface");
                    e.printStackTrace();
                }
            }
        }
    }

}

