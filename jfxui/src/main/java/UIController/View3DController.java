package UIController;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import uiComponents.View3D;

import javax.inject.Inject;

public class View3DController {




    @Inject
    @FXML
    private StackPane view3D;

    @Inject
    private AppState appState;
    @Inject
    private View3D view;



    public void initialize(){
        initView3D();
    }

    private void initView3D() {

        view.initView(view3D.getWidth(),view3D.getHeight());

        view3D.getChildren().add(view);
    }


}
