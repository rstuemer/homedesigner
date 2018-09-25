package sample;


import UIController.AppState;
import UIController.MainController;
import com.google.inject.Singleton;
import de.rst.core.guice.modules.InjectLogger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import services.FXMLFileManager;
import services.FXMLFileManagerImpl;
//import services.FXMLFileManager;


import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;

@Singleton
public class HomeDesignerGUI {


    @InjectLogger
    Logger logger;

    @Inject
    private FXMLFileManager fxmlFileManager;

    @Inject
    private AppState appState;

    @Inject
    private MainController mainController;


    public void start(final Stage stage) throws IOException {

        stage.setMaximized(true);
        appState.setPrimaryStage(stage);

        Parent root = fxmlFileManager.load("mainViewOnly2D");


        Scene scene = new Scene(root);
        stage.setTitle("Home Designer 0.0.1");
        scene.cursorProperty().bind(appState.getCursor());

        URL cssFile = appState.getSettings().getTheme().getCssFile();
        if(cssFile != null)
        scene.getStylesheets().add(cssFile.toExternalForm());





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



}
