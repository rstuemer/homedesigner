package sample;

import UIController.AppState;
import com.gluonhq.ignite.guice.GuiceContext;
import com.google.inject.AbstractModule;
import de.rst.core.EditState;
import de.rst.core.Project;
import de.rst.core.guice.modules.CoreModule;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modules.GuiModule;
import services.AppSettings;
import services.Settings;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;


public class GuiceApp extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    private GuiceContext context = new GuiceContext(this, () -> Arrays.asList(new CoreModule(), new GuiModule()));

    @Inject
    private FXMLLoader fxmlLoader;
    @Inject
    private HomeDesignerGUI homeDesignerGUI;
    @Inject
    private AppState appState;

    @Override
    public void start(Stage primaryStage) throws IOException {


//        fxmlLoader.setLocation(getClass().getResource("../mainView.fxml"));
//        Parent root = fxmlLoader.load();


//        try {
        context.init();

        //TODO Load Settings from File
        Settings settings = new AppSettings();
        Project project = new Project();
        project.init();
        appState.setSettings(settings);
        appState.setProject(project);
        appState.setEditState(new EditState());

        homeDesignerGUI.start(primaryStage);
//        } catch (final RuntimeException e) {
//            //logError(e);
//            Platform.exit();
//        }
    }
}


