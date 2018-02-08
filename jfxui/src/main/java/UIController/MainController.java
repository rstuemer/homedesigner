package UIController;

import de.rst.core.Plan;
import de.rst.core.Project;
import de.rst.core.Wall;
import javafx.beans.value.ChangeListener;
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


    @Inject @FXML
    private ScrollPane view2D;

    @Inject @FXML
    private StackPane view3D;


    private String editState = "none";
    private Plan plan;
    private Group view2DGroup;
    private WallView editedWallView;
    private PlanView planView;
    private Stage primaryStage;

    public MainController() {

    }

    public void initialise(Stage stage) {

        primaryStage = stage;
        //System.out.println(embeddedMenuController);
        //System.out.println(embeddedMenu);
        embeddedMenuController.foo("It works"); //Console print "It works"

        initialize();
        initZoomListener();
    }

    public void initialize() {

        //TODO Create on New Menu Entry and Inject with CDI
        plan = new Plan();
        plan.setHeight(25);
        plan.setWidth(25);
        plan.setPlanScale(5);
        plan.setZoom(55);
        plan.setRulerWidth(20);

        Project project = new Project();
        project.setPlan(plan);

        embeddedMenuController.setProject(project);

        initView3D();
        initView2d(view2D);



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

    private void initZoomListener() {


        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->
                initView2d(view2D, (Double) newValue);


        ChangeListener<Boolean> stageMaximezedListener = (observable, oldValue, newValue) ->
                initView2d(view2D);


        primaryStage.widthProperty().addListener(stageSizeListener);
        primaryStage.maximizedProperty().addListener(stageMaximezedListener);


        view2D.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            /**
             * Invoked when a specific event of the type for which this handler is
             * registered happens.
             *
             * @param event the event which occurred
             */
            @Override
            public void handle(ScrollEvent event) {

                double zoom = plan.getZoom();
//                System.out.println("ScrollEvent" + plan.getZoom());
                if (event.isControlDown()) {
                    if (event.getDeltaY() > 0) {
                        if (zoom == 1) {
                            plan.setWidth(plan.getWidth() + 5);
                            plan.setHeight(plan.getHeight() + 5);
                        } else {
                            zoom = zoom - 1;
                        }
                    } else if (event.getDeltaY() < 0) {
                        zoom = zoom + 1;
                    }
                    plan.setZoom(zoom);
                    initView2d(view2D, view2D.getWidth());
                    event.consume();
                }
            }

        });
    }

    public void initView2d(ScrollPane view2d) {
        initView2d(view2d, 0.0);
    }


    public void initView2d(ScrollPane view2d, double width) {

        System.out.println("ScrollPane Size " + width + "/" + view2d.getWidth());


        //TODO not regenerate All just update  (properties)

        view2DGroup = new Group();
        uiComponents.Background background = new uiComponents.Background(width, plan);
        view2DGroup.getChildren().add(background);
        Ruler ruler = new Ruler(width, plan);
        view2DGroup.getChildren().add(ruler);


        Wall wall = new Wall(5, 10);
        wall.addPoint(10, 10);


        plan.addWall(wall);


        plan.fitToScreenIfNecessary(width);
        planView = new PlanView(plan);
        view2DGroup.getChildren().add(planView);

        view2D.setContent(view2DGroup);


    }


    @FXML
    public void zoomPlan(ZoomEvent event) {
        System.out.println("ZoomFactor:" + event.getZoomFactor());
    }

    @FXML
    public void enterPickMode(ActionEvent event) {
        editState = "pickMode";
        editedWallView = null;
        plan.setWallInEditMode(null);
        initView2d(view2D);
    }


    @FXML
    public void enterCreateWallMode(ActionEvent event) {
        event.consume();

        if (!editState.equals("create_wall")) {
            System.out.println("Button create Wall");
            editState = "create_wall";
        } else {
            editState = "none";
        }
    }


    @FXML
    public void mouseEnter2dView(MouseEvent event) {

        System.out.println("Mouse Entered 2dView " + editState);

        event.consume();

        if (editState.equals("create_wall"))
            Main.getPrimaryStage().getScene().setCursor(Cursor.CROSSHAIR);
        else if( editState.equals("pickMode")){
            Main.getPrimaryStage().getScene().setCursor(Cursor.HAND);
        }
    }


    @FXML
    public void mouseExited2dView(MouseEvent event) {
        event.consume();
        if (editState.equals("create_wall") || editState.equals("create_wall_points")) {
           primaryStage.getScene().setCursor(Cursor.DEFAULT);

        }
    }

    public void mouseClickedIn2dView(MouseEvent mouseEvent) {


        ScrollPane source = (ScrollPane) mouseEvent.getSource();
        source.getHvalue();

        double vValue = source.getVvalue();
        double unvisiblePixel = source.getHeight() * vValue;
        System.out.println("unvisiblePixel:" + unvisiblePixel);

        if (editState.equals("create_wall")) {
            Point2D planPoint = plan.createPlanPoint(mouseEvent.getX() - plan.getRulerWidth(), mouseEvent.getY() - plan.getRulerWidth() + unvisiblePixel);
            System.out.println("New Wall Point: " + planPoint);
            Wall wall = new Wall(planPoint);
            System.out.println("Create EDITED WALL :" + wall);

            plan.setWallInEditMode(wall);
            editState = "create_wall_points";

        } else if (editState.equals("create_wall_points")) {
            Wall wall = editedWallView.getWall();
            plan.addWall(wall);
            System.out.println("ADD WALL TO PLAN:" + wall);
            Point2D lastPoint = wall.getPoints().get(wall.getPoints().lastKey());


            Wall newWall = new Wall(lastPoint);

            System.out.println("NEW EDITED WALL :" + newWall);
            plan.setWallInEditMode(newWall);

            this.view2DGroup.getChildren().remove(editedWallView);
            editedWallView = null;
            planView.initView();
        }
    }

    private void createNewEditedWall(double eventX, double eventY) {
        Wall wallInEditMode = plan.getWallInEditMode();
        wallInEditMode.addPoint(plan.createPlanPoint(eventX, eventY));
        editedWallView = new WallView(wallInEditMode, Color.WHITE, plan);
        this.view2DGroup.getChildren().add(editedWallView);
    }


    public void mouseMoved2dView(MouseEvent mouseEvent) {

        if (editState.equals("create_wall_points")) {


            double eventX = mouseEvent.getX() - plan.getRulerWidth();
            double eventY = mouseEvent.getY() - plan.getRulerWidth();
            if (editedWallView == null) {
                createNewEditedWall(eventX, eventY);
            } else {
                int size = editedWallView.getWall().getPoints().size();
                if (size > 1) {
                    System.out.println(eventX + " " + eventY);
                    editedWallView.getWall().replaceLastPoint(plan.createPlanPoint(eventX, eventY));
                    editedWallView.replaceLastPoint(eventX + plan.getRulerWidth(), eventY + plan.getRulerWidth());
                } else {
                    System.out.println("SHOULD NEVER HAPPEN");
                    editedWallView.addPoint(eventX + plan.getRulerWidth(), eventY + plan.getRulerWidth());
                }
            }


        }

    }


}

