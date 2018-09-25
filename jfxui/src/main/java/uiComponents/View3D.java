package uiComponents;

import UIController.AppState;
import com.javafx.experiments.jfx3dviewer.AutoScalingGroup;
import com.javafx.experiments.jfx3dviewer.Xform;
import de.rst.core.Project;
import de.rst.core.Wall;
import de.rst.core.guice.modules.InjectLogger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class View3D extends Group
{


    @Inject
    private AppState appstate;
    private AutoScalingGroup autoScalingGroup = new AutoScalingGroup(2);
    private SubScene subScene;


    private  PerspectiveCamera camera = new PerspectiveCamera(true);
    private  Xform cameraXform = new Xform();
    private  Xform cameraXform2 = new Xform();
    private double dimModel=500d;
    private double mousePosX, mousePosY;
    private double mouseOldX, mouseOldY;
    private double mouseDeltaX, mouseDeltaY;
    private final double modifierFactor = 0.3;
    private final Rotate yUpRotate = new Rotate(90,0,0,0,Rotate.X_AXIS); // y Up
    private final Rotate cameraLookXRotate = new Rotate(0,0,0,0,Rotate.X_AXIS);
    private final Rotate cameraLookZRotate = new Rotate(0,0,0,0,Rotate.Z_AXIS);
    private final Translate cameraPosition = new Translate(0,0,0);
    private final AmbientLight ambientLight = new AmbientLight(Color.WHITE);
    private final PointLight light1 = new PointLight(Color.WHITE);
    private  double paneW, paneH;

    @InjectLogger
    private Logger logger;


    public View3D(){



    }



    public PerspectiveCamera getCamera() {
        return camera;
    }

    public void setCamera(PerspectiveCamera camera) {
        this.camera = camera;
    }





    public void initView(double width,double height) {


        if(width == 0 ) {
            paneW = 500;
            paneH = 300;
        }else{
            paneW = width;
            paneH = height;
        }

        this.prefHeight(600);
        this.prefWidth(800);

        double width1 = this.getBoundsInParent().getWidth();

        if(width1 > 1)
        dimModel = width1;
        buildCamera();
        buildSubScene();
        //buildAxes();
        addLights();
//
//        Translate pivot = new Translate();
//        Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);

//        // Create and position camera
//        camera = new PerspectiveCamera(true);
//        camera.getTransforms().addAll (
//                pivot,
//                yRotate,
//                new Rotate(-20, Rotate.X_AXIS),
//                new Translate(0, 0, -50)
//        );



        // Build the Scene Graph



        List<Wall> walls = appstate.getProject().getPlan().getWalls();

        for (Wall wall:walls) {
            Box box = new Box(wall.getLength(), 20, appstate.getProject().getPlan().getWidth());
            box.setMaterial(new PhongMaterial(Color.WHITE));
            box.setTranslateX(wall.getPoints().get(wall.getPoints().firstKey()).getX());
            autoScalingGroup.getChildren().add(box);

        }
        final String cssDefault = "-fx-border-color: red;\n"
                + "-fx-border-insets: 5;\n"
                + "-fx-border-width: 3;\n"
                + "-fx-border-style: dashed;\n";
        this.setStyle(cssDefault);

        final String cssDefault2 = "-fx-border-color: green;\n"
                + "-fx-border-insets: 5;\n"
                + "-fx-border-width: 3;\n"
                + "-fx-border-style: dashed;\n";
        autoScalingGroup.setStyle(cssDefault2);





    }



    private void buildCamera() {
        camera.setNearClip(1.0);
        camera.setFarClip(10000.0);
        double fieldOfView = 2d * dimModel / 3d;
        camera.setFieldOfView(fieldOfView);
//        camera.getTransforms().addAll(yUpRotate,cameraPosition,cameraLookXRotate,cameraLookZRotate);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(camera);
        double value = -2d * dimModel;
        cameraPosition.setZ(value);
        this.getChildren().add(cameraXform);
        /*
        Rotate camera to show isometric view X right, Y top, Z 120º left-down from each

        Three ways:

        One: Calculate vector and angle of rotation to combine two rotations:

        Qy[Pi/6].Qx[-Pi/6] (note angles for camera are opposite to model rotations)

        With some calculations:
        (http://jperedadnr.blogspot.com.es/2013/06/leap-motion-controller-and-javafx-new.html)
        * camera.setRotationAxis(new Point3D(-0.694747,0.694747,0.186157));
        * camera.setRotate(42.1812);

        Two: with rotation matrix, taking all the previous transformations, by appending all of
        them in a single matrix before prepending these two rotations:

        * Affine affineCamIni=new Affine();
        * camera.getTransforms().stream().forEach(affineCamIni::append);
        * affineCamIni.prepend(new Rotate(-30, Rotate.X_AXIS));
        * affineCamIni.prepend(new Rotate(30, Rotate.Y_AXIS));
        * camera.getTransforms().setAll(affineCamIni);

        Three: setting first RX rotation, then RY:
        */
        cameraXform.setRx(-30.0);
        cameraXform.setRy(30);


    }

    private void buildSubScene() {
        this.getChildren().add(autoScalingGroup);

        subScene = new SubScene(this,paneW,paneH,true,javafx.scene.SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        subScene.setFill(Color.CADETBLUE);
        setListeners(true);

        final String cssDefault = "-fx-border-color: yellow;\n"
                + "-fx-border-insets: 5;\n"
                + "-fx-border-width: 3;\n"
                + "-fx-border-style: dashed;\n";
        subScene.setStyle(cssDefault);
    }

    private void buildAxes() {
        double length = 2d*dimModel;
        double width = dimModel/100d;
        double radius = 2d*dimModel/100d;
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);
        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        Sphere xSphere = new Sphere(radius);
        Sphere ySphere = new Sphere(radius);
        Sphere zSphere = new Sphere(radius);
        xSphere.setMaterial(redMaterial);
        ySphere.setMaterial(greenMaterial);
        zSphere.setMaterial(blueMaterial);

        xSphere.setTranslateX(dimModel);
        ySphere.setTranslateY(dimModel);
        zSphere.setTranslateZ(dimModel);

        Box xAxis = new Box(length, width, width);
        Box yAxis = new Box(width, length, width);
        Box zAxis = new Box(width, width, length);
        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);

        autoScalingGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        autoScalingGroup.getChildren().addAll(xSphere, ySphere, zSphere);
    }

    private void addLights(){
        this.getChildren().add(ambientLight);
        this.getChildren().add(light1);
        light1.setTranslateX(dimModel*0.6);
        light1.setTranslateY(dimModel*0.6);
        light1.setTranslateZ(dimModel*0.6);
    }

    private final EventHandler<MouseEvent> mouseEventHandler = event -> {
        handleMouseEvent(event);
    };

    private void handleMouseEvent(MouseEvent event) {
        double xFlip = -1.0, yFlip=1.0; // yUp
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
            mouseOldX = event.getSceneX();
            mouseOldY = event.getSceneY();

        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            double modifier = event.isControlDown()?0.1:event.isShiftDown()?3.0:1.0;

            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX);
            mouseDeltaY = (mousePosY - mouseOldY);

            if(event.isMiddleButtonDown() || (event.isPrimaryButtonDown() && event.isSecondaryButtonDown())) {
                cameraXform2.setTx(cameraXform2.t.getX() + xFlip*mouseDeltaX*modifierFactor*modifier*0.3);
                cameraXform2.setTy(cameraXform2.t.getY() + yFlip*mouseDeltaY*modifierFactor*modifier*0.3);
            }
            else if(event.isPrimaryButtonDown()) {
                cameraXform.setRy(cameraXform.ry.getAngle() - yFlip*mouseDeltaX*modifierFactor*modifier*2.0);
                cameraXform.setRx(cameraXform.rx.getAngle() + xFlip*mouseDeltaY*modifierFactor*modifier*2.0);
            }
            else if(event.isSecondaryButtonDown()) {
                double z = cameraPosition.getZ();
                double newZ = z - xFlip*(mouseDeltaX+mouseDeltaY)*modifierFactor*modifier;
                cameraPosition.setZ(newZ);
            }
        }
        logger.info("mouseEvent" + event);
    }

    private final EventHandler<ScrollEvent> scrollEventHandler = event -> {
        if (event.getTouchCount() > 0) { // touch pad scroll
            cameraXform2.setTx(cameraXform2.t.getX() - (0.01*event.getDeltaX()));  // -
            cameraXform2.setTy(cameraXform2.t.getY() + (0.01*event.getDeltaY()));  // -
        } else { // mouse wheel
            double z = cameraPosition.getZ()-(event.getDeltaY()*0.2);
            z = Math.max(z,-10d*dimModel);
            z = Math.min(z,0);
            cameraPosition.setZ(z);
        }
    };
    private final EventHandler<ZoomEvent> zoomEventHandler = event -> {
        if (!Double.isNaN(event.getZoomFactor()) && event.getZoomFactor() > 0.8 && event.getZoomFactor() < 1.2) {
            double z = cameraPosition.getZ()/event.getZoomFactor();
            z = Math.max(z,-10d*dimModel);
            z = Math.min(z,0);
            cameraPosition.setZ(z);
        }
    };

    private void setListeners(boolean addListeners){
        if(addListeners){
            this.addEventHandler(MouseEvent.ANY, mouseEventHandler);
            this.addEventHandler(ZoomEvent.ANY, zoomEventHandler);
            this.addEventHandler(ScrollEvent.ANY, scrollEventHandler);
        } else {
            this.removeEventHandler(MouseEvent.ANY, mouseEventHandler);
            this.removeEventHandler(ZoomEvent.ANY, zoomEventHandler);
            this.removeEventHandler(ScrollEvent.ANY, scrollEventHandler);
        }
    }

    public void update(double width, double height) {
        subScene.setWidth(width);
        subScene.setHeight(height);
    }
}
