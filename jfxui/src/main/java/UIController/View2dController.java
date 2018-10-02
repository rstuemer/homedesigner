package UIController;

import de.rst.core.EditState;
import de.rst.core.Plan;
import de.rst.core.Wall;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uiComponents.PlanView;
import uiComponents.Ruler;
import uiComponents.WallView;

import javax.inject.Inject;

public class View2dController implements IController {




    @Inject
    @FXML
    private ScrollPane view2D;

    @Inject
    private AppState appState;


    private Group view2DGroup;
    private WallView editedWallView;
    private PlanView planView;

    @Inject
    private BottomToolbarController bottomtoolbarController;




    public void initialize() {

        appState.getZoomProperty().addListener((observable, oldValue, newValue) -> updateView2d());
        appState.projectProperty().addListener((observable, oldValue, newValue) -> updateView2d());
        appState.getProject().updateView2dProperty().addListener((observable, oldValue, newValue) -> updateView2d());
        initView();
    }


    public void updateView2d() {
        initView2d(view2D.getWidth(),view2D.getHeight());
    }

    public void initView() {

        System.out.println("init View 2D");
        initView2d(0.0,0.0);
        initZoomListener();

    }


    public void initView2d(double width,double height) {
        Plan plan = appState.getProject().getPlan();
        System.out.println("initView2d");


        //TODO not regenerate All just update  (properties)

        view2DGroup = new Group();
        uiComponents.Background background = new uiComponents.Background();
        view2DGroup.getChildren().add(background);
        Ruler ruler = new Ruler();

        view2DGroup.getChildren().add(ruler);


//        Wall wall = new Wall(5, 10);
//        wall.addPoint(10, 10);


//        plan.addWall(wall);


        plan.fitToScreenIfNecessary(width,height);

        this.planView  = new PlanView(plan,appState);

        view2DGroup.getChildren().add(this.planView);
        view2D.setContent(view2DGroup);
        this.planView.initView();

        background.initBackground(plan);
        ruler.initRuler(plan);
    }


    @FXML
    public void zoomPlan(ZoomEvent event) {
        System.out.println("ZoomFactor:" + event.getZoomFactor());
    }


    @FXML
    public void mouseEnter2dView(MouseEvent event) {


        event.consume();
        String currentState = this.appState.getEditState().getValue().getCurrentState();

        if ("START_CREATION".equals(currentState)) {
            appState.setCursor(Cursor.CROSSHAIR);


        }
        else if ("PICK_MODE".equals(currentState)) {
            appState.setCursor(Cursor.HAND);
        }
    }




    public void mouseClickedIn2dView(MouseEvent mouseEvent) {

        //TODO PLANVIEW AN Mouselistener binden ?
        String currentState = this.appState.getEditState().getValue().getCurrentState();
        double[] unvisiblePixel = getUnvisiblePixel(mouseEvent);

        System.out.println("Event mouse Click x:" + mouseEvent.getX() + "eventY"+ mouseEvent.getY() + " unvisiblePixel[0]" + unvisiblePixel[0]+ " unvisiblePixel[1]" + unvisiblePixel[1] + "Source:"+ mouseEvent.getSource() );

        double x = this.planView.convertToAbsolutePoint(mouseEvent.getX() + unvisiblePixel[1]);
        double y = this.planView.convertToAbsolutePoint(mouseEvent.getY() + unvisiblePixel[0]);
        if ( "START_CREATION".equals(currentState)) {

           if(this.appState.changeEditState("ADD_SEGMENT")){
               this.planView.addEditWall(x, y);

           }else
               System.out.println("Transition not allowed");

        } else if( "ADD_SEGMENT".equals(currentState) ){
            if(mouseEvent.getClickCount() >=2) {
                if(this.appState.changeEditState("FINISH")){
                    this.planView.finishWall(x, y);
                }
            }else{
                if(this.appState.changeEditState("ADD_SEGMENT")){

                    this.planView.addSegmentWall(x, y);
                }
            }

        }
        //this.planView.updateTempWallView(  this.planView.getTempWallView());
        //TODO Find better way to update
        view2DGroup.getChildren().remove(this.planView);
        view2DGroup.getChildren().add(this.planView);
//        oldMouseEventHandle(mouseEvent);
    }


    public void mouseMoved2dView(MouseEvent mouseEvent) {
        String currentState = this.appState.getEditState().getValue().getCurrentState();

        //TODO UPDATE COORDINATE ZEIGER UNTEN RECHTS


        if("ADD_SEGMENT".equals(currentState)){

            double[] unvisiblePixel = getUnvisiblePixel(mouseEvent);
            double x = this.planView.convertToAbsolutePoint(mouseEvent.getX() + unvisiblePixel[1]);
            double y = this.planView.convertToAbsolutePoint(mouseEvent.getY() + unvisiblePixel[0]);
            //System.out.println("Event x:" + mouseEvent.getX() + "eventY"+ mouseEvent.getY() + " unvisiblePixel[0]" + unvisiblePixel[0]);
            this.planView.changePreviewPoint(x,y);
            //this.planView.updateTempWallView(  this.planView.getTempWallView());
            view2DGroup.getChildren().remove(this.planView);
            view2DGroup.getChildren().add(this.planView);
            view2D.setContent(view2DGroup);

            this.bottomtoolbarController.updateCoordinatesLabel( mouseEvent.getX(),mouseEvent.getY(),x,y);

        }else{
            //System.out.println("CURRENT STATE " + currentState);
        }

    }


    @FXML
    public void mouseExited2dView(MouseEvent event) {
        event.consume();
       // String editState = appState.getEditMode();
        String currentState = this.appState.getEditState().getValue().getCurrentState();


        appState.setCursor(Cursor.DEFAULT);
        if ( !"NONE".equals(currentState)) {
            //planView.cleanView();
            this.planView.persistTempwall();
        }
    }









    private void initZoomListener() {
        Stage primaryStage = appState.getPrimaryStage();

        if (primaryStage != null) {
            Plan plan = appState.getProject().getPlan();
            ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->
                    initView2d(primaryStage.getWidth(),primaryStage.getHeight());


            ChangeListener<Boolean> stageMaximezedListener = (observable, oldValue, newValue) ->
                    updateView2d();


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

//                    view2D.setScaleX(2);
//                    view2D.setScaleY(2);
                    double zoom = plan.getZoom();
               System.out.println("ScrollEvent" + plan.getZoom());
                    if (event.isControlDown()) {
                        if (event.getDeltaY() > 0) {
                            if (zoom == 1) {
                                plan.setWidth(plan.getWidth() + 5);
                                plan.setHeight(plan.getHeight() + 5);
                            } else {
                                zoom--;
                            }
                        } else if (event.getDeltaY() < 0) {
                            zoom++;
                        }




                        appState.setZoom(zoom);
                        event.consume();
//                }
                    }

                }
            });

        }
    }

    public ScrollPane getView2D() {
        return view2D;
    }

    public void setView2D(ScrollPane view2D) {
        this.view2D = view2D;
    }

    double[] getUnvisiblePixel(MouseEvent mouseEvent) {
        ScrollPane source = (ScrollPane) mouseEvent.getSource();
        double hvalue = source.getHvalue();
        double vValue = source.getVvalue();


        double contentHeight = source.getContent().getLayoutBounds().getHeight();
         contentHeight = contentHeight - source.getHeight();
       // System.out.println("VMAX:"+ contentHeight);
        double unvisiblePixelV = contentHeight * vValue;
        double   unvisiblePixelH = source.getWidth() * hvalue;
        //System.out.println("hvalue:"+hvalue + "vValue:" + vValue);
        return new double[]{unvisiblePixelV,unvisiblePixelH};
    }

    public void handleKeys(KeyEvent keyEvent) {

        if(keyEvent.getCode() == KeyCode.ESCAPE){
            this.planView.cleanView();
            this.appState.changeEditState("NONE");
        }
    }
}
