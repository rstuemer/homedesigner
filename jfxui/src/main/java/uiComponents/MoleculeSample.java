package uiComponents;

import UIController.AppState;
import com.google.common.eventbus.Subscribe;
import de.rst.core.guice.modules.InjectLogger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.animation.Timeline;
import javafx.event.EventHandler;

import static javafx.scene.input.KeyCode.*;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.slf4j.Logger;

import javax.inject.Inject;

public class MoleculeSample extends Group {


    final Group axisGroup = new Group();
    final Xform world = new Xform();
    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final Xform cameraXform = new Xform();
    final Xform cameraXform2 = new Xform();
    final Xform cameraXform3 = new Xform();
    final double cameraDistance = 450;
    final Xform moleculeGroup = new Xform();
    private Parent parent;
    private Timeline timeline;
    boolean timelinePlaying = false;
    double ONE_FRAME = 1.0 / 24.0;
    double DELTA_MULTIPLIER = 200.0;
    double CONTROL_MULTIPLIER = 0.1;
    double SHIFT_MULTIPLIER = 0.1;
    double ALT_MULTIPLIER = 0.5;
    double mousePosX;
    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;
    double mouseDeltaY;
    final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);


    @Inject
    AppState appState;

    @InjectLogger
    private Logger logger;

    public void init(Parent parent) {
        this.parent = parent;
        buildScene();
        buildCamera();
        buildAxes();
        buildMolecule();

        SubScene scene = new SubScene(this, 1024.0, 768.0, true, SceneAntialiasing.BALANCED);
        scene.setPickOnBounds(true);
        scene.setFill(Color.AZURE);
        handleKeyboard(scene, world);
        handleMouse(parent, world);


        scene.setCamera(camera);


        zoomProperty.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                camera.setTranslateZ(zoomProperty.get());
            }
        });

        parent.addEventFilter(ScrollEvent.ANY, event -> {
            if (event.getDeltaY() > 0) {
                zoomProperty.set(zoomProperty.get() * 1.1);
            } else if (event.getDeltaY() < 0) {
                zoomProperty.set(zoomProperty.get() / 1.1);
            }
        });

    }

    private void buildScene() {
        this.getChildren().add(world);
    }

    private void buildCamera() {
        this.getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        cameraXform3.setRotateZ(180.0);

        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-cameraDistance);
        cameraXform.ry.setAngle(320.0);
        cameraXform.rx.setAngle(40);
    }

    private void buildAxes() {
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        final Box xAxis = new Box(240.0, 1, 1);
        final Box yAxis = new Box(1, 240.0, 1);
        final Box zAxis = new Box(1, 1, 240.0);

        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);

        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        world.getChildren().addAll(axisGroup);
    }

    private void buildMolecule() {

        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial whiteMaterial = new PhongMaterial();
        whiteMaterial.setDiffuseColor(Color.WHITE);
        whiteMaterial.setSpecularColor(Color.LIGHTBLUE);

        final PhongMaterial greyMaterial = new PhongMaterial();
        greyMaterial.setDiffuseColor(Color.DARKGREY);
        greyMaterial.setSpecularColor(Color.GREY);

        // Molecule Hierarchy
        // [*] moleculeXform
        //     [*] oxygenXform
        //         [*] oxygenSphere
        //     [*] hydrogen1SideXform
        //         [*] hydrogen1Xform
        //             [*] hydrogen1Sphere
        //         [*] bond1Cylinder
        //     [*] hydrogen2SideXform
        //         [*] hydrogen2Xform
        //             [*] hydrogen2Sphere
        //         [*] bond2Cylinder

        Xform moleculeXform = new Xform();
        Xform oxygenXform = new Xform();
        Xform hydrogen1SideXform = new Xform();
        Xform hydrogen1Xform = new Xform();
        Xform hydrogen2SideXform = new Xform();
        Xform hydrogen2Xform = new Xform();

        Sphere oxygenSphere = new Sphere(40.0);
        oxygenSphere.setMaterial(redMaterial);

        Sphere hydrogen1Sphere = new Sphere(30.0);
        hydrogen1Sphere.setMaterial(whiteMaterial);
        hydrogen1Sphere.setTranslateX(0.0);

        Sphere hydrogen2Sphere = new Sphere(30.0);
        hydrogen2Sphere.setMaterial(whiteMaterial);
        hydrogen2Sphere.setTranslateZ(0.0);

        Cylinder bond1Cylinder = new Cylinder(5, 100);
        bond1Cylinder.setMaterial(greyMaterial);
        bond1Cylinder.setTranslateX(50.0);
        bond1Cylinder.setRotationAxis(Rotate.Z_AXIS);
        bond1Cylinder.setRotate(90.0);

        Cylinder bond2Cylinder = new Cylinder(5, 100);
        bond2Cylinder.setMaterial(greyMaterial);
        bond2Cylinder.setTranslateX(50.0);
        bond2Cylinder.setRotationAxis(Rotate.Z_AXIS);
        bond2Cylinder.setRotate(90.0);

        moleculeXform.getChildren().add(oxygenXform);
        moleculeXform.getChildren().add(hydrogen1SideXform);
        moleculeXform.getChildren().add(hydrogen2SideXform);
        oxygenXform.getChildren().add(oxygenSphere);
        hydrogen1SideXform.getChildren().add(hydrogen1Xform);
        hydrogen2SideXform.getChildren().add(hydrogen2Xform);
        hydrogen1Xform.getChildren().add(hydrogen1Sphere);
        hydrogen2Xform.getChildren().add(hydrogen2Sphere);
        hydrogen1SideXform.getChildren().add(bond1Cylinder);
        hydrogen2SideXform.getChildren().add(bond2Cylinder);

        hydrogen1Xform.setTx(100.0);
        hydrogen2Xform.setTx(100.0);
        hydrogen2SideXform.setRotateY(104.5);

        moleculeGroup.getChildren().add(moleculeXform);

        world.getChildren().addAll(moleculeGroup);
        world.setStyle("-fx-background-color: white;");
    }

    private void handleMouse(Parent parent, final Node root) {
        parent.addEventFilter(MouseEvent.MOUSE_CLICKED, me -> {

            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();

            logger.info("MousePos [ x:" + mousePosX + " y:" + mousePosY + "] MousePoseOld [ x: " + mouseOldX + " y:" + mouseOldY + "]");
        });
        parent.addEventFilter(MouseEvent.MOUSE_DRAGGED, me -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX);
            mouseDeltaY = (mousePosY - mouseOldY);

            double modifier = 1.0;
            double modifierFactor = 0.1;

            if (me.isControlDown()) {
                modifier = 0.1;
            }
            if (me.isShiftDown()) {
                modifier = 10.0;
            }
            if (me.isPrimaryButtonDown()) {
                cameraXform.ry.setAngle(cameraXform.ry.getAngle() - mouseDeltaX * modifierFactor * modifier * 2.0);  // +
                cameraXform.rx.setAngle(cameraXform.rx.getAngle() + mouseDeltaY * modifierFactor * modifier * 2.0);  // -
            } else if (me.isSecondaryButtonDown()) {
                double z = camera.getTranslateZ();
                double newZ = z + mouseDeltaX * modifierFactor * modifier;
                camera.setTranslateZ(newZ);
            } else if (me.isMiddleButtonDown()) {
                cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX * modifierFactor * modifier * 0.3);  // -
                cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY * modifierFactor * modifier * 0.3);  // -
            }
        });
    }

    private void handleKeyboard(SubScene parent, final Node root) {
        final boolean moveCamera = true;
        root.setOnKeyPressed( event -> {
            Duration currentTime;
            logger.info("Key Pressed");
            switch (event.getCode()) {
                case Z:
                    if (event.isShiftDown()) {
                        cameraXform.ry.setAngle(0.0);
                        cameraXform.rx.setAngle(0.0);
                        camera.setTranslateZ(-300.0);
                    }
                    cameraXform2.t.setX(0.0);
                    cameraXform2.t.setY(0.0);
                    break;
                case X:
                    if (event.isControlDown()) {
                        if (axisGroup.isVisible()) {
                            axisGroup.setVisible(false);
                        } else {
                            axisGroup.setVisible(true);
                        }
                    }
                    break;
                case S:
                    if (event.isControlDown()) {
                        if (moleculeGroup.isVisible()) {
                            moleculeGroup.setVisible(false);
                        } else {
                            moleculeGroup.setVisible(true);
                        }
                    }
                    break;
                case SPACE:
                    if (timelinePlaying) {
                        timeline.pause();
                        timelinePlaying = false;
                    } else {
                        timeline.play();
                        timelinePlaying = true;
                    }
                    break;
                case UP:
                    if (event.isControlDown() && event.isShiftDown()) {
                        cameraXform2.t.setY(cameraXform2.t.getY() - 10.0 * CONTROL_MULTIPLIER);
                    } else if (event.isAltDown() && event.isShiftDown()) {
                        cameraXform.rx.setAngle(cameraXform.rx.getAngle() - 10.0 * ALT_MULTIPLIER);
                    } else if (event.isControlDown()) {
                        cameraXform2.t.setY(cameraXform2.t.getY() - 1.0 * CONTROL_MULTIPLIER);
                    } else if (event.isAltDown()) {
                        cameraXform.rx.setAngle(cameraXform.rx.getAngle() - 2.0 * ALT_MULTIPLIER);
                    } else if (event.isShiftDown()) {
                        double z = camera.getTranslateZ();
                        double newZ = z + 5.0 * SHIFT_MULTIPLIER;
                        camera.setTranslateZ(newZ);
                    }
                    break;
                case DOWN:
                    if (event.isControlDown() && event.isShiftDown()) {
                        cameraXform2.t.setY(cameraXform2.t.getY() + 10.0 * CONTROL_MULTIPLIER);
                    } else if (event.isAltDown() && event.isShiftDown()) {
                        cameraXform.rx.setAngle(cameraXform.rx.getAngle() + 10.0 * ALT_MULTIPLIER);
                    } else if (event.isControlDown()) {
                        cameraXform2.t.setY(cameraXform2.t.getY() + 1.0 * CONTROL_MULTIPLIER);
                    } else if (event.isAltDown()) {
                        cameraXform.rx.setAngle(cameraXform.rx.getAngle() + 2.0 * ALT_MULTIPLIER);
                    } else if (event.isShiftDown()) {
                        double z = camera.getTranslateZ();
                        double newZ = z - 5.0 * SHIFT_MULTIPLIER;
                        camera.setTranslateZ(newZ);
                    }
                    break;
                case RIGHT:
                    if (event.isControlDown() && event.isShiftDown()) {
                        cameraXform2.t.setX(cameraXform2.t.getX() + 10.0 * CONTROL_MULTIPLIER);
                    } else if (event.isAltDown() && event.isShiftDown()) {
                        cameraXform.ry.setAngle(cameraXform.ry.getAngle() - 10.0 * ALT_MULTIPLIER);
                    } else if (event.isControlDown()) {
                        cameraXform2.t.setX(cameraXform2.t.getX() + 1.0 * CONTROL_MULTIPLIER);
                    } else if (event.isAltDown()) {
                        cameraXform.ry.setAngle(cameraXform.ry.getAngle() - 2.0 * ALT_MULTIPLIER);
                    }
                    break;
                case LEFT:
                    if (event.isControlDown() && event.isShiftDown()) {
                        cameraXform2.t.setX(cameraXform2.t.getX() - 10.0 * CONTROL_MULTIPLIER);
                    } else if (event.isAltDown() && event.isShiftDown()) {
                        cameraXform.ry.setAngle(cameraXform.ry.getAngle() + 10.0 * ALT_MULTIPLIER);  // -
                    } else if (event.isControlDown()) {
                        cameraXform2.t.setX(cameraXform2.t.getX() - 1.0 * CONTROL_MULTIPLIER);
                    } else if (event.isAltDown()) {
                        cameraXform.ry.setAngle(cameraXform.ry.getAngle() + 2.0 * ALT_MULTIPLIER);  // -
                    }
                    break;
            }
        });
    }
}
