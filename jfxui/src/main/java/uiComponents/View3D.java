package uiComponents;

import UIController.AppState;
import de.rst.core.Wall;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import javax.inject.Inject;
import java.util.List;

public class View3D extends Group {


    @Inject
    private AppState appstate;

    public View3D(){

    }

    public void initView(double width,double height) {


        if(width == 0 ) {
            width = 500;
            height = 300;
        }





        Translate pivot = new Translate();
        Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);

        // Create and position camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll (
                pivot,
                yRotate,
                new Rotate(-20, Rotate.X_AXIS),
                new Translate(0, 0, -50)
        );


        // Build the Scene Graph
        Group root = new Group();
        root.getChildren().add(camera);


        List<Wall> walls = appstate.getProject().getPlan().getWalls();

        for (Wall wall:walls) {
            Box box = new Box(wall.getLength(), 20, appstate.getProject().getPlan().getWidth());
            box.setMaterial(new PhongMaterial(Color.RED));
            box.setTranslateX(wall.getPoints().get(wall.getPoints().firstKey()).getX());
            root.getChildren().add(box);

        }



        // set the pivot for the camera position animation base upon mouse clicks on objects
        root.getChildren().stream()
                .filter(node -> !(node instanceof Camera))
                .forEach(node ->
                        node.setOnMouseClicked(event -> {
                            pivot.setX(node.getTranslateX());
                            pivot.setY(node.getTranslateY());
                            pivot.setZ(node.getTranslateZ());
                        })
                );

        // Use a SubScene
        SubScene subScene = new SubScene(
                root,
                width,height,
                true,
                SceneAntialiasing.BALANCED
        );
        subScene.setFill(Color.ALICEBLUE);
        subScene.setCamera(camera);


        this.getChildren().add(subScene);
    }


}
