package UIController;

import de.rst.core.Project;
import de.rst.core.guice.modules.InjectLogger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import uiComponents.MoleculeSample;
import uiComponents.View3D;

import javax.inject.Inject;

import static javafx.scene.input.KeyCode.*;
import static javafx.scene.transform.Rotate.*;

public class View3DController implements IController {


    @Inject
    @FXML
    private StackPane view3DRoot;

    @Inject
    private AppState appState;
    @Inject
    private MoleculeSample view;


    @InjectLogger
    private org.slf4j.Logger logger;

    private boolean mouseClicked;


    public void initialize(){
       // initView3D();
        appState.projectProperty().addListener(new ChangeListener<Project>() {
            @Override
            public void changed(ObservableValue<? extends Project> observable, Project oldValue, Project newValue) {
                updateView3d();
            }
        });

        appState.getProject().updateView2dProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                updateView3d();
            }
        });

//        view3D.viewportBoundsProperty().addListener((observable, oldValue, newValue) ->  {
//            Node content = view3D.getContent();
//            view3D.setFitToWidth(content.prefWidth(-1)<oldValue.getWidth());
//            view3D.setFitToHeight(content.prefHeight(-1)<newValue.getHeight());
//        });
        initView();



    }

    public void initView(){
        initView3D();
    }

    private void initView3D() {


        view.init(view3DRoot);
        view3DRoot.getChildren().add(view);




        //view3DRoot.setAlignment(view, Pos.CENTER);

        view3DRoot.setStyle("-fx-background-color: cornsilk;");
        view.setFocusTraversable(true);



//        view3DRoot.v().addListener(
//                new ChangeListener<Bounds>() {
//                    @Override
//                    public void changed(ObservableValue<? extends Bounds> observableValue, Bounds oldBounds, Bounds newBounds) {
//                        nodeContainer.setPrefSize(
//                                Math.max(view.getBoundsInParent().getMaxX(), newBounds.getWidth()),
//                                Math.max(view.getBoundsInParent().getMaxY(), newBounds.getHeight())
//                        );
//                    }
//                });



        logger.info("NodeContainer:"+view3DRoot.toString()  +"  Size:" + view3DRoot.getLayoutBounds());


    }

    private void  updateView3d(){
        //view.update(view3D.getWidth(),view3D.getHeight());


    }









    public void mouseEntered(MouseEvent mouseEvent) {
        System.out.println("View3D Entered");

        view.requestFocus();
    }



}
