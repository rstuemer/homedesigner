package services;

import UIController.AppState;
import UIController.IController;
import de.rst.core.guice.modules.InjectLogger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Locale;
import java.util.ResourceBundle;

public class FXMLFileManagerImpl implements FXMLFileManager {


    @Inject
    AppState appState;

    @Inject
    FXMLLoader loader;

    @InjectLogger
    Logger logger;



    public Parent load(String viewName){


        URL resource = getClass().getResource("../"+viewName+".fxml");


        Locale local = appState.getSettings().getLocal();
        ResourceBundle bundle = ResourceBundle.getBundle("i18n.UIResources", local);


       File file =  new File(resource.getPath());
        if(!file.exists())
            logger.error("VIEWFILE NOT EXISTS");
        loader.setLocation(resource);
        loader.setResources(bundle);
        try {
            Parent load = loader.load();

            IController controller = loader.getController();
            controller.initView();
            return load;
        } catch (IOException e) {
            logger.error("Can't load FXML File for: " + viewName,e);
            e.printStackTrace();
        }



        return null;
    }

}
