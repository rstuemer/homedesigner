package sample;


import UIController.MainController;
import com.google.inject.Singleton;
import de.rst.core.guice.modules.InjectLogger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;


import javax.inject.Inject;
import java.io.IOException;

@Singleton
public class HomeDesignerGUI {


    @InjectLogger
    Logger logger;

    @Inject
    private FXMLLoader mainLoader;


    @Inject
    private MainController mainController;


    public void start(final Stage stage) throws IOException {

                mainLoader.setLocation(getClass().getResource("../mainView.fxml"));
        Parent root = mainLoader.load();

        initialise(stage);

        Scene scene = new Scene(root);


        stage.setTitle("Home Designer 0.0.1");
        String cssFile =  getClass().getResource("../styles/dark.css").toExternalForm();
        scene.getStylesheets().add(cssFile);
//        scene.setOnKeyPressed(this::handleKeyEvent);
//        stage.setOnCloseRequest(mainController.getCloseRequestHandler());


        stage.setScene(scene);

//        Placement placement = placementManager.getMainWindow();
//        stage.setWidth(placement.getWidth());
//        stage.setHeight(placement.getHeight());
//        if (placement.isPositioned()) {
//            stage.setX(placement.getX());
//            stage.setY(placement.getY());
//        }


        stage.show();
        logger.info("User interface started");


    }

    private void initialise(final Stage stage) {
        mainController.initialise(stage);



    }


}
