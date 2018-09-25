package UIController;

import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ZoomEvent;

import javax.inject.Inject;

public class BottomToolbarController implements IController {

    @Inject
    AppState appState;


    @FXML
    @Inject
    private TextField scaleTextField;
    @FXML
    @Inject
    public Slider scaleSlider;

    /**
     *
     */
    @FXML
    @Inject
    private Label coordinatesLabel;

    public void initialize() {

        this.appState.getZoomProperty().addListener((observable, oldValue, newValue) ->updateZoomUi(observable, oldValue, newValue) );

        initView();
    }

    public void initView() {

        System.out.println("Init BottomBar" + Double.toString(this.appState.getProject().getPlan().getZoom()));
        this.scaleTextField.setText(Double.toString(this.appState.getProject().getPlan().getZoom()));
       // this.scaleTextField.setPromptText(Double.toString(this.appState.getProject().getPlan().getZoom()));

    }

    private void updateZoomUi(ObservableValue ob, Number oldValue, Number zoom){


        double d = zoom.doubleValue();
        String value = Double.toString(d);

        this.scaleTextField.setText(value);
        this.scaleSlider.setValue(d);
    }

    public void updateScale(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {
            String gText = this.scaleTextField.getText();

            try {
                double zoom = Double.parseDouble(gText);
                this.appState.setZoom(zoom);
                System.out.println("updateScale:" + gText);
            } catch (NumberFormatException ne) {
                //TODO Prompt not a number
            }

        }
    }


    public void updateCoordinatesLabel(double x, double y,double xAbsolute,double yAbsolute){
        this.coordinatesLabel.setText("x: " + x + "y: " + y +"absolute x: " + xAbsolute + "absolute y: " + yAbsolute );

    }




}


