package UIController;

import de.rst.core.Plan;
import de.rst.core.Project;
import de.rst.core.Wall;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.StackedFontIcon;
import sample.Main;
import uiComponents.PlanView;
import uiComponents.Ruler;
import uiComponents.WallView;

import javax.inject.Inject;


public class MainController {


    @FXML
    private Parent embeddedMenu; //embeddedElement
    @Inject
    private MenuController embeddedMenuController; // $embeddedElement+Controller
    @Inject
    private ToolbarController embeddedToolbarController; // $embeddedElement+Controller



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

        initView3D();


    }

    private void initView3D() {
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setSpecularColor(Color.ORANGE);
        redMaterial.setDiffuseColor(Color.RED);


        Box box = new Box(100, 100, 100);
        box.setMaterial(redMaterial);
        box.setTranslateX(250);
        box.setTranslateY(-100);
        box.setTranslateZ(-100);

        Group root = new Group(box);


        view3D.getChildren().add(root);
    }





}

