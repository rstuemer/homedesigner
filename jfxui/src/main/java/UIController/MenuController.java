package UIController;

import de.rst.core.Project;
import de.rst.core.guice.modules.InjectLogger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import org.slf4j.Logger;
import sample.Main;

import javax.inject.Inject;
import java.io.File;
import java.util.ResourceBundle;

public class MenuController implements Initializable {


    @FXML     @Inject
    private MenuBar menuBar;


    @Inject
   private AppState appState;

    @InjectLogger
    Logger logger;

    private MainController mainController;

    /**
     * Handle action related to "About" menu item.
     *
     * @param event Event on "About" menu item.
     */
    @FXML
    private void handleSaveAction(final ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HDP files (*.hdp)", "*.hdp");
        fileChooser.getExtensionFilters().add(extFilter);

        //TODO CDI: REPLACE WITH CDI INJECTION of primaryStage
        //Show save file dialog
        File file = fileChooser.showSaveDialog(appState.getPrimaryStage());
        logger.info("Save to file:" + file.getName());
        if(file != null){
            appState.getProject().saveProject(file);
        }

    }



    /**
     * Handle action related to "About" menu item.
     *
     * @param event Event on "About" menu item.
     */
    @FXML
    private void handleAboutAction(final ActionEvent event)
    {
        provideAboutFunctionality();
    }

    /**
     * Handle action related to input (in this case specifically only responds to
     * keyboard event CTRL-A).
     *
     * @param event Input event.
     */
    @FXML
    private void handleKeyInput(final InputEvent event)
    {
        if (event instanceof KeyEvent)
        {
            final KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.A)
            {
                provideAboutFunctionality();
            }
        }
    }

    /**
     * Perform functionality associated with "About" menu selection or CTRL-A.
     */
    private void provideAboutFunctionality()
    {
        System.out.println("You clicked on About!");
    }


    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        menuBar.setFocusTraversable(true);

    }


    public void foo(String foo) {
        System.out.println(foo);
    }



    public void handleNewAction(ActionEvent event) {


        Project project = new Project();
        project.init();
        appState.setProject(project);
        logger.info("New Project Created");



    }

    public void handleOpenAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HomeDesigner Project files (*.hdp)", "*.hdp");
        fileChooser.getExtensionFilters().add(extFilter);


        File file = fileChooser.showOpenDialog(appState.getPrimaryStage());


        //TODO move Method to ProjectManager
        Project project = appState.getProject().loadProject(file);
        appState.setProject(project);
        appState.getPrimaryStage().setTitle( appState.getPrimaryStage().getTitle() + " " + project.getPlan().getName());

    }

    public void handleSaveAsAction(ActionEvent event) {
    }

    public void addParent(MainController mainController) {
        this.mainController = mainController;
    }
}
