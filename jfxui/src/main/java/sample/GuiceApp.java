package sample;

import com.gluonhq.ignite.guice.GuiceContext;
import com.google.inject.AbstractModule;
import de.rst.core.guice.modules.CoreModule;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;



public class GuiceApp extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    private GuiceContext context = new GuiceContext(this, () -> Arrays.asList(new CoreModule()));

    @Inject
    private FXMLLoader fxmlLoader;
    @Inject
    private HomeDesignerGUI homeDesignerGUI;


    @Override
    public void start(Stage primaryStage) throws IOException {
        context.init();

//        fxmlLoader.setLocation(getClass().getResource("../mainView.fxml"));
//        Parent root = fxmlLoader.load();


//        try {
            context.init();
            homeDesignerGUI.start(primaryStage);
//        } catch (final RuntimeException e) {
//            //logError(e);
//            Platform.exit();
//        }
    }
}


