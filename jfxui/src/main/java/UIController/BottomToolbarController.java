package UIController;

import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
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

        String value = Double.toString(this.appState.getProject().getPlan().getZoom());
        System.out.println("Init BottomBar" + value);
        this.scaleTextField.setText(value);
        this.scaleSlider.setValue(this.appState.getProject().getPlan().getZoom());
        this.scaleSlider.setMax(500);
        this.scaleSlider.setShowTickLabels(true);
        this.scaleSlider.setShowTickMarks(true);
        this.scaleSlider.setMajorTickUnit(50);
        this.scaleSlider.setMinorTickCount(5);
        this.scaleSlider.setBlockIncrement(10);

        this.scaleSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {

                scaleTextField.setText(String.format("%.2f", new_val));
            }
        });
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


        String value = "x: " + x + "y: " + y + "absolute x: " + xAbsolute + "absolute y: " + yAbsolute;

        this.coordinatesLabel.setText(value);

    }




}


